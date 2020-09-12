package com.example.pennapphack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

public class SelectPreferences extends AppCompatActivity {
    String [] TIMELIST ={"30 minutes","60 minutes","90 minutes","120 minutes"};
    String [] PRICELIST ={"$","$$","$$$"};
    String [] ACCESSLIST ={"Dorm Room", "Full Kitchen"};

    private MaterialCardView cardView;
    private TextView selectPref;
    private Spinner timeSpin;
    private Spinner priceSpin;
    private Spinner accessSpin;
    private Button setPref;
    private TextView change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);

        cardView = findViewById(R.id.selectPreferencesCard);
        selectPref=findViewById(R.id.tvSelectPreferences);
        timeSpin=findViewById(R.id.timeDrop);
        priceSpin=findViewById(R.id.priceDrop);
        accessSpin=findViewById(R.id.accessDrop);
        setPref=findViewById(R.id.btnSetPreferences);
        change=findViewById(R.id.tvChangePreferences);

        setPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                goMainActivity();
            }
        });

        //time spinner
        ArrayAdapter<String> arrayAdapterTime= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, TIMELIST);
        Spinner timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PRICELIST);
        Spinner priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);

        //access spinner
        ArrayAdapter<String> arrayAdapterAccess = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, ACCESSLIST);
        Spinner accessSpinner = (Spinner)accessSpin;
        accessSpinner.setAdapter(arrayAdapterAccess);
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}