package edu.binghamton.qrprescription;

import android.content.Intent;
import android.content.SharedPreferences;
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

import mehdi.sakout.fancybuttons.FancyButton;

public class StartScreen extends AppCompatActivity {

    CardView scan;
    CardView check;

    SQLiteHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        scan = findViewById(R.id.bScan);
        check = findViewById(R.id.bCheck);
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
            }

            Intent i = new Intent(StartScreen.this, CheckPres.class);
            startActivityForResult(i, 200);
        }
    }
}
