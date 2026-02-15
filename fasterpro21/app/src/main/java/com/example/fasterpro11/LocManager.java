package com.example.fasterpro11;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;

public class LocManager implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 60000; // 1 minute
    private boolean canGetLocation;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location lastLocation;
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

            // Check for location provider availability
            this.isGPSEnabled = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (this.isGPSEnabled || this.isNetworkEnabled) {
                this.canGetLocation = true;

                // Request location updates via network provider if network is available
                if (this.isNetworkEnabled) {
                    try {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("Network", "Network Enabled");
                            lastLocation = this.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }
                    } catch (SecurityException e) {
                        Log.e("LocManager", "Permission denied for NETWORK_PROVIDER", e);
                    } catch (Exception e) {
                        Log.e("LocManager", "Error getting location from network provider", e);
                    }
                }

                // Request location updates via GPS provider if network location is not available
                if (this.isGPSEnabled && lastLocation == null) {
                    try {
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            lastLocation = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    } catch (SecurityException e) {
                        Log.e("LocManager", "Permission denied for GPS_PROVIDER", e);
                    } catch (Exception e) {
                        Log.e("LocManager", "Error getting location from GPS provider", e);
                    }
                }
            }
        } catch (SecurityException e) {
            Log.e("LocManager", "Security exception occurred: Permission denied for location access", e);
        } catch (Exception e) {
            Log.e("LocManager", "Unexpected error in getLocation method", e);
        }
        return lastLocation;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            lastLocation = location;
        } catch (Exception e) {
            Log.e("LocManager", "Error updating location in onLocationChanged", e);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        try {
            // Logic when the provider is disabled (e.g., GPS or Network)
        } catch (Exception e) {
            Log.e("LocManager", "Error in onProviderDisabled", e);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        try {
            // Logic when the provider is enabled (e.g., GPS or Network)
        } catch (Exception e) {
            Log.e("LocManager", "Error in onProviderEnabled", e);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        try {
            // Logic to handle status changes for the provider
        } catch (Exception e) {
            Log.e("LocManager", "Error in onStatusChanged", e);
        }
    }

    public void stopUsingGPS() {
        try {
            if (this.locationManager != null) {
                this.locationManager.removeUpdates(this);
            }
        } catch (Exception e) {
            Log.e("LocManager", "Error stopping GPS updates", e);
        }
    }
}
