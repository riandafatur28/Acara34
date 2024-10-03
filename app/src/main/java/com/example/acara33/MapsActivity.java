package com.example.acara33;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.acara33.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_ACCESS = 1;
    private Marker currentLocationMarker; // Variabel untuk menyimpan marker lokasi saat ini

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Inisialisasi SupportMapFragment dan panggil ketika peta siap digunakan.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Cek izin lokasi
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Minta izin jika belum diberikan
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_ACCESS);
            return;
        }

        // Dapatkan lokasi terakhir
        getLastLocation();

        // Set titik awal di peta
        LatLng initialPosition = new LatLng(-6.200000, 106.816666); // Koordinat Jakarta
        mMap.addMarker(new MarkerOptions().position(initialPosition).title("Marker in Jakarta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialPosition));

        // Tambahkan proximity alert untuk lokasi tertentu
        addProximityAlert(-6.200000, 106.816666, 5);
    }

    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        // Hapus marker lama jika ada
                        if (currentLocationMarker != null) {
                            currentLocationMarker.remove();
                        }
                        // Tambahkan marker di lokasi saat ini
                        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Lokasi Saat Ini"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_ACCESS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, coba ambil lokasi terakhir lagi
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // Menambahkan proximity alert untuk lokasi tertentu
    private void addProximityAlert(double latitude, double longitude, float radius) {
        Intent intent = new Intent("com.example.ACTION_PROXIMITY_ALERT");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Tambahkan alert ketika lokasi tertentu tercapai
        Toast.makeText(this, "Proximity alert added!", Toast.LENGTH_SHORT).show();
    }
}
