package com.example.fasterpro11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfflineQueueDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "offline_queue.db";
    private static final int DB_VERSION = 1;

    public OfflineQueueDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE queue (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "json TEXT NOT NULL," +
                        "created_at INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS queue");
        onCreate(db);
    }
}
