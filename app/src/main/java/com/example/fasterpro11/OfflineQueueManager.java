package com.example.fasterpro11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OfflineQueueManager {

    private final OfflineQueueDbHelper dbHelper;

    public OfflineQueueManager(Context context) {
        dbHelper = new OfflineQueueDbHelper(context);
    }

    // Save when no internet
    public void enqueue(String json) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("json", json);
        cv.put("created_at", System.currentTimeMillis());
        db.insert("queue", null, cv);
        db.close();
    }

    // Get oldest item
    public Cursor getNext() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM queue ORDER BY id ASC LIMIT 1",
                null
        );
    }

    // Remove after success
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("queue", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
