package com.example.fasterpro11;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class UninstallProtectionReceiver extends BroadcastReceiver {

    private static final String UNINSTALL_PASSWORD = "564632"; // Your uninstall password

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (packageName.equals(context.getPackageName())) {
                // Check if the password matches
                String receivedPassword = intent.getStringExtra("password");
                if (receivedPassword != null && receivedPassword.equals(UNINSTALL_PASSWORD)) {
                    // Correct password entered, allow uninstallation
                    Toast.makeText(context, "Uninstall password verified. Uninstallation allowed.", Toast.LENGTH_SHORT).show();
                } else {
                    // Incorrect password or no password entered
                    Toast.makeText(context, "Incorrect uninstall password. Uninstallation blocked.", Toast.LENGTH_SHORT).show();

                    // Re-enable the app icon since uninstallation is blocked
                    PackageManager p = context.getPackageManager();
                    p.setComponentEnabledSetting(new ComponentName(context, MainActivity.class),
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                }
            }
        }
    }
}
