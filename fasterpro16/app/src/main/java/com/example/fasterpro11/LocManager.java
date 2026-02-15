package com.example.fasterpro11;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocManager implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // লোকেশন পরিবর্তনের জন্য মিনিমাম দূরত্ব
    private static final long MIN_TIME_BW_UPDATES = 60000; // লোকেশন আপডেটের জন্য মিনিমাম সময়
    private boolean canGetLocation;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location lastLocation; // বর্তমান লোকেশন সংরক্ষণের জন্য ভেরিয়েবল
    protected LocationManager locationManager;
    private final Context mContext;

    public LocManager(Context context) {
        this.mContext = context;
        this.canGetLocation = false;
        getLocation();
    }

    public Location getLocation() {
        try {
            this.locationManager = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
            this.isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (this.isGPSEnabled || this.isNetworkEnabled) {
                this.canGetLocation = true;
                if (this.isNetworkEnabled) {
                    this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network Enabled");
                    lastLocation = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (this.isGPSEnabled && lastLocation == null) {
                    this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    lastLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastLocation; // সর্বশেষ লোকেশন রিটার্ন
    }

    public Location getLastLocation() {
        return lastLocation; // সর্বশেষ লোকেশন পাওয়ার জন্য মেথড
    }

    public boolean canGetLocation() {
        return this.canGetLocation; // লোকেশন পাওয়া যাবে কিনা তা জানার জন্য
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location; // লোকেশন পরিবর্তনের সময় সর্বশেষ লোকেশন আপডেট
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void stopUsingGPS() {
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(this);
        }
    }
}
