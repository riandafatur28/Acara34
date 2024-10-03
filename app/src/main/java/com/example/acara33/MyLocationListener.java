package com.example.acara33;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;
import android.content.Context;

public class MyLocationListener implements LocationListener {

    private Context context;

    // Constructor untuk mengambil context dari activity
    public MyLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            // Menampilkan informasi lintang dan bujur ketika lokasi berubah
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Menampilkan toast dengan informasi lintang dan bujur
            String message = "Location changed: Lat: " + latitude + " Lng: " + longitude;
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Ditampilkan ketika provider (GPS/Network) dinonaktifkan
        Toast.makeText(context, "Provider Disabled: " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Ditampilkan ketika provider (GPS/Network) diaktifkan
        Toast.makeText(context, "Provider Enabled: " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Ditampilkan ketika status provider berubah
        String statusMessage = "Status changed for provider: " + provider + " Status: " + status;
        Toast.makeText(context, statusMessage, Toast.LENGTH_SHORT).show();
    }
}
