package com.example.fasterpro11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallRelatedInfo {
    private static final String TAG = "CallRelated";

    public boolean checkLastCallNumbers(Context context, String[] allowedNumbers) {
        Log.d(TAG, "Checking last call numbers.");

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Call log permission not granted.");
            return false;
        }

        String[] projection = {CallLog.Calls.NUMBER};
        List<String> recentNumbers = new ArrayList<>();
        String sortOrder = CallLog.Calls.DATE + " DESC";

        try (Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder)) {

            if (cursor != null) {
                int count = 0;
                while (cursor.moveToNext() && count < 1) {
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    recentNumbers.add(number);
                    Log.d(TAG, "Recent number added: " + number);
                    count++;
                }
            } else {
                Log.d(TAG, "Cursor is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error accessing call log: ", e);
        }

        List<String> allowedNumbersList = Arrays.asList(allowedNumbers);
        for (String number : recentNumbers) {
            if (allowedNumbersList.contains(number)) {
                Log.d(TAG, "Allowed number found in recent calls: " + number);
                return true;
            }
        }
        Log.d(TAG, "No allowed numbers found in recent calls.");
        return false;
    }


}