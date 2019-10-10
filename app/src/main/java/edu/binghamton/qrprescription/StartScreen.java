package edu.binghamton.qrprescription;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class StartScreen extends AppCompatActivity {

    CardView scan, check, settings;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    SQLiteHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ed = prefs.edit();

        setAlarm();

        if(prefs.getBoolean("firstUse", true)){
            ed.putBoolean("firstUse", false);
            ed.commit();
            createNotificationChannel();
            setTimeConfigs();
        }


        scan = findViewById(R.id.bScan);
        check = findViewById(R.id.bCheck);
        settings = findViewById(R.id.bSettings);

        db = new SQLiteHelper(this);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, QRScanner.class);
                startActivityForResult(i, 150);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, CheckPres.class);
                startActivityForResult(i, 200);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, Settings.class);
                startActivityForResult(i, 250);
            }
        });
    }

    private void setAlarm(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 14);

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_UPDATE_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    private void setTimeConfigs(){
        ed.putInt("morningHour",8);
        ed.putInt("morningMinute", 0);
        ed.putInt("afternoonHour",13);
        ed.putInt("afternoonMinute", 0);
        ed.putInt("nightHour",19);
        ed.putInt("nightMinute", 0);
        ed.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 10/9/2019 Remove delete 
        db.deleteAll();
        if(requestCode == 150 && resultCode == -1){
            Bundle extras = data.getExtras();
            String text = extras.getString("pres");
            String prescriptions[] = text.split("\n");
            for(String pres : prescriptions){
                String presDetails[] = pres.split("\\|");
                int morning = Integer.parseInt(presDetails[1]);
                int afternoon = Integer.parseInt(presDetails[2]);
                int night = Integer.parseInt(presDetails[3]);
                db.addPrescription(new MedEntry(presDetails[0], morning, afternoon, night));
                if(morning != 0){

                }
            }

            Intent i = new Intent(StartScreen.this, CheckPres.class);
            startActivityForResult(i, 200);
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "imp";
            String description = "Med alerts";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("69", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =  getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
