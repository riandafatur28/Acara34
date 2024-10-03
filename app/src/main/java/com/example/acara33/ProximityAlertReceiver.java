package com.example.acara33;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ProximityAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("com.example.ACTION_PROXIMITY_ALERT")) {
            Toast.makeText(context, "Proximity alert triggered!", Toast.LENGTH_LONG).show();
            // Tambahkan logika tambahan jika perlu, misalnya membuka aplikasi atau memberikan notifikasi
        }
    }
}
