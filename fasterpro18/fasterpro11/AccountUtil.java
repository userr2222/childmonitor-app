package com.example.fasterpro11;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AccountUtil {
    private static final String TAG = "AccountUtil";
    public String sim1_Number="";
    public String sim2_Number="";

    // Method to get default Google account
    public String getDefaultGoogleAccount(Context context) {
        // For Android version 9 and below, fetch Google account
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
            String accountName = null;

            try {
                if (accountManager != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.GET_ACCOUNTS)
                                == PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG, "permission GET_ACCOUNTS is granted.");

                            Account[] accounts = accountManager.getAccounts();

                            for (Account account : accounts) {
                                Log.d(TAG, "Account Name: " + account.name + " Type: " + account.type);
                                if (account.name != null && !account.name.isEmpty()) {
                                    return account.name;
                                }
                            }

                            Account[] googleAccounts = accountManager.getAccountsByType("com.google");

                            if (googleAccounts.length > 0) {
                                accountName = googleAccounts[0].name;
                                Log.d(TAG, "Default Google Account Name: " + accountName);
                            } else {
                                Log.d(TAG, "No Google accounts found.");
                            }
                        } else {
                            Log.d(TAG,"Permission not granted to access accounts.");
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{android.Manifest.permission.GET_ACCOUNTS}, 1);
                            return null;
                        }
                    } else {
                        Account[] accounts = accountManager.getAccounts();
                        for (Account account : accounts) {
                            Log.d(TAG, "Account Name: " + account.name + " Type: " + account.type);
                            if (account.name != null && !account.name.isEmpty()) {
                                return account.name;
                            }
                        }

                        Account[] googleAccounts = accountManager.getAccountsByType("com.google");

                        if (googleAccounts.length > 0) {
                            accountName = googleAccounts[0].name;
                            Log.d(TAG, "Default Google Account Name: " + accountName);
                        } else {
                            Log.d(TAG, "No Google accounts found.");
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while getting Google account", e);
            }
        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

        return null; // Ensure a return value is provided for all cases
    }

    // Method to fetch Subscriber ID (SIM number) after checking permissions
    public String getSubscriberId(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            try {
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_NUMBERS)
                                == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permissions READ_PHONE_STATE and READ_PHONE_NUMBERS are granted.");
                    return fetchSubscriberId(context);
                } else {
                    Log.d(TAG,"Permissions not granted to access phone state.");
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_PHONE_NUMBERS},
                            2);
                    return null;
                }
            } catch (SecurityException e) {
                Log.e(TAG, "Error fetching subscriber ID due to SecurityException", e);
                return null; // Return null in case of security exceptions
            } catch (Exception e) {
                Log.e(TAG,"Unexpected error fetching subscriber ID", e);
                return null; // Return null for any other errors
            }

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }
    }

    private String fetchSubscriberId(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                try {
                    String subscriberId = telephonyManager.getSubscriberId();
                    if (subscriberId != null && !subscriberId.isEmpty()) {
                        Log.d(TAG,"Fetched IMSI: " + subscriberId); // Successfully fetched IMSI
                        return subscriberId;
                    } else {
                        Log.d(TAG, "IMSI is empty or null, returning fallback.");
                        return fetchFallbackSimNumber(context); // Fallback to another method
                    }
                } catch (SecurityException e) {
                    // Handle SecurityException if the app doesn't have necessary permissions
                    Log.e(TAG, "Error fetching IMSI: SecurityException - " + e.getMessage(), e);
                    return null; // Return null in case of security exceptions
                } catch (Exception e) {
                    // Handle any other unexpected exceptions
                    Log.e(TAG, "Unexpected error fetching IMSI: " + e.getMessage(), e);
                    return null; // Return null for any other errors
                }
            }
            Log.d(TAG,"TelephonyManager is null, cannot fetch IMSI.");
            return null; // Return null if TelephonyManager is unavailable

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG,"Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

    private String fetchFallbackSimNumber(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simNumber = "Unknown"; // Default fallback value
            if (telephonyManager != null) {
                try {
                    simNumber = telephonyManager.getLine1Number();
                    if (simNumber != null && !simNumber.isEmpty()) {
                        Log.d(TAG, "SIM number via fallback: " + simNumber);
                    }
                } catch (SecurityException e) {
                    Log.e(TAG, "Error fetching SIM number via fallback: " + e.getMessage(), e);
                }
            }
            return simNumber;
        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }
    }

    // Method to handle permission result for phone state
    public void onRequestPermissionsResultForPhoneState(int requestCode, String[] permissions, int[] grantResults, Context context) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String subscriberId = getSubscriberId(context);
                Log.d(TAG,"Fetched subscriber ID: " + subscriberId);
            } else {
                Log.d(TAG, "Permission READ_PHONE_STATE or READ_PHONE_NUMBERS denied.");
            }
        }
    }

    // Method to get user SIM number
    public String getUserSimNumber(Context context) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simNumber = "";

            try {
                if (telephonyManager != null) {
                    // Check permissions for Android M (API level 23) and above
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED) {
                            // Try fetching the phone number
                            simNumber = telephonyManager.getLine1Number(); // Get phone number
                            Log.d(TAG,"SIM number: " + simNumber);
                        } else {
                            Log.d(TAG, "Permission not granted.");
                            // Request permissions if not granted
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{android.Manifest.permission.READ_PHONE_STATE}, 2);
                            return null; // Return null if permission not granted
                        }
                    } else {
                        // For older Android versions (before Android M)
                        simNumber = telephonyManager.getLine1Number();
                        Log.d(TAG, "SIM number: " + simNumber);
                    }

                    if (simNumber == null || simNumber.isEmpty()) {
                        // Fallback to get Subscriber ID (IMSI) if phone number is not available
                        simNumber = getSimCardDetails(telephonyManager);
                    }
                }
            } catch (Exception e) {
                Log.e("SIM_DEBUG", "Unexpected error", e);
            }

            if (simNumber == null || simNumber.isEmpty()) {
                Log.d(TAG, "No SIM number found, returning 'Unknown'.");
                return "Unknown"; // If no number found, return "Unknown"
            }
            return simNumber;

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

    private String getSimCardDetails(TelephonyManager telephonyManager) {
        // For Android version 9 and below, fetch IMSI and SIM number
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {

            String simNumber = "";
            try {
                String imsi = telephonyManager.getSubscriberId();
                Log.d(TAG, "IMSI: " + imsi);
                if (imsi != null && !imsi.isEmpty()) {
                    simNumber = imsi; // Use IMSI if available
                }
            } catch (SecurityException e) {
                Log.e("SIM_DEBUG", "Error fetching IMSI: ", e);
            } catch (Exception e) {
                Log.e(TAG, "Unexpected error fetching IMSI: ", e);
            }
            return simNumber;

        } else {
            // For Android version 10 (Q) and above, return null due to security restrictions
            Log.d(TAG, "Android version >= 10. Returning null due to security restrictions.");
            return null;
        }

    }

}
