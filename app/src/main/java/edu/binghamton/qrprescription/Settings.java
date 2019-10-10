package edu.binghamton.qrprescription;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    Button morning, afternoon, night;
    private int mYear, mMonth, mDay, mHour, mMinute;
    int hour, minutes;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        ed = prefs.edit();

        morning = findViewById(R.id.bMorning);
        afternoon = findViewById(R.id.bAfternoon);
        night = findViewById(R.id.bNight);
        morning.setText(prefs.getInt("morningHour", 0) + ":" + prefs.getInt("morningMinutes", 0));
        afternoon.setText(prefs.getInt("afternoonHour", 0) + ":" + prefs.getInt("afternoonMinute", 0));
        night.setText(prefs.getInt("nightHour", 0) + ":" + prefs.getInt("nightMinute", 0));

        morning.setOnClickListener(this);
        afternoon.setOnClickListener(this);
        night.setOnClickListener(this);

    }

    @Override
    public void onClick(final View v) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // TODO: 10/9/2019 take the toast out
                        Toast.makeText(Settings.this, ""+hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
                        hour = hourOfDay;
                        minutes = minute;
                        switch(v.getId()){
                            case R.id.bMorning:
                                ed.putInt("morningHour",hour);
                                ed.putInt("morningMinute", minutes);
                                ed.commit();
                                morning.setText(hour + ":" + minutes);
                                break;
                            case R.id.bAfternoon:
                                ed.putInt("afternoonHour",hour);
                                ed.putInt("afternoonMinute", minutes);
                                ed.commit();
                                afternoon.setText(hour + ":" + minutes);
                                break;
                            case R.id.bNight:
                                ed.putInt("nightHour",hour);
                                ed.putInt("nightMinute", minutes);
                                ed.commit();
                                night.setText(hour + ":" + minutes);
                                break;
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
