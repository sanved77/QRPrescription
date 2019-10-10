package edu.binghamton.qrprescription;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheckPres extends AppCompatActivity {

    RecyclerView rv;
    LinearLayoutManager llm;
    RVAdapt adapt;
    SQLiteHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_pres);

        db = new SQLiteHelper(this);

        rv = findViewById(R.id.rv);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapt = new RVAdapt(db.allPrescriptions());
        rv.setAdapter(adapt);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 10/9/2019 Remove the deleteall
        db.deleteAll();
    }
}
