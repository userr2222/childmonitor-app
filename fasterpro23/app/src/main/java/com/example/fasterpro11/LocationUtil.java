package com.example.fasterpro11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

    private static final String TAG = "LocationUtil";
    private String FullCountryName; // দেশের নাম স্টোর করার জন্য

    // কলব্যাক ইন্টারফেস
    public interface LocationCallback {
        void onCountryNameReceived(String countryName);
    }

    // দেশের নাম পাওয়ার জন্য মেথড
    public void getCountryName(Context context, LocationCallback callback) {
        // পারমিশন চেক করা
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission not granted for location access.");
            callback.onCountryNameReceived(null); // পারমিশন না থাকলে null পাঠানো
            return;
        }

        // ফিউজড লোকেশন ক্লায়েন্ট তৈরি করা
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        // শেষ জানা লোকেশন পাওয়ার জন্য রিকোয়েস্ট করা
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // লোকেশন পাওয়া গেলে গিওকোডার ব্যবহার করে দেশের নাম বের করা
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses != null && !addresses.isEmpty()) {
                                    FullCountryName = addresses.get(0).getCountryName(); // দেশের নাম স্টোর করা
                                    Log.d(TAG, "Country: " + FullCountryName);
                                    callback.onCountryNameReceived(FullCountryName); // দেশের নাম কলব্যাকে পাঠানো
                                } else {
                                    callback.onCountryNameReceived(null); // লোকেশন না পাওয়া গেলে null পাঠানো
                                }
                            } catch (IOException e) {
                                Log.e(TAG, "Geocoder error: ", e);
                                callback.onCountryNameReceived(null); // এরর হলে null পাঠানো
                            }
                        } else {
                            callback.onCountryNameReceived(null); // লোকেশন না পাওয়া গেলে null পাঠানো
                        }
                    }
                });
    }

    // আপনি চাইলে FullCountryName সরাসরি এই মেথড দিয়ে পেতে পারেন
    public String getFullCountryName() {
        return FullCountryName;
    }
}
