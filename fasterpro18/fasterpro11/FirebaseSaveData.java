package com.example.fasterpro11;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FirebaseSaveData {
    public static void saveFileLinkToDatabase(String fileUrl, String fileType) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("UploadedFiles");

        String fileId = databaseRef.push().getKey();
        Map<String, Object> fileData = new HashMap<>();
        fileData.put("url", fileUrl);
        fileData.put("type", fileType);
        fileData.put("timestamp", System.currentTimeMillis());

        if (fileId != null) {
            databaseRef.child(fileId).setValue(fileData)
                    .addOnSuccessListener(aVoid -> Log.d("FirebaseDB", "File URL Saved Successfully"))
                    .addOnFailureListener(e -> Log.e("FirebaseDB", "Failed to save file URL", e));
        }
    }
}
