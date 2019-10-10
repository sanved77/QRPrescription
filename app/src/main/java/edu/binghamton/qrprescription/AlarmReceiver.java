package edu.binghamton.qrprescription;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver  extends BroadcastReceiver {

    Context context ;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "69")
                .setSmallIcon(R.drawable.ic_code_scanner_flash_on)
                .setContentTitle("My notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}
