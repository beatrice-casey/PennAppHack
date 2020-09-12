package com.example.pennapphack.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pennapphack.home.MainActivity;
import com.example.pennapphack.models.Preferences;
import com.example.pennapphack.R;
import com.google.android.material.card.MaterialCardView;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SelectPreferences extends AppCompatActivity {

    public static final String TAG = "SelectPreferences";
    String [] TIMELIST ={"30","60","90","120"};
    String [] PRICELIST ={"$","$$","$$$"};
    String [] ACCESSLIST ={"Dorm Room", "Full Kitchen"};

    private MaterialCardView cardView;
    private TextView selectPref;
    private Spinner timeSpin;
    private Spinner priceSpin;
    private Spinner accessSpin;
    private Button setPref;
    private TextView change;
    private Spinner timeSpinner;
    private Spinner priceSpinner;
    private Spinner accessSpinner;

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
        timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PRICELIST);
        priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);

        //access spinner
        ArrayAdapter<String> arrayAdapterAccess = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, ACCESSLIST);
        accessSpinner = (Spinner)accessSpin;
        accessSpinner.setAdapter(arrayAdapterAccess);

        setPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        Preferences preferences = new Preferences();

        String timeString = timeSpinner.getSelectedItem().toString();
        int time = Integer.parseInt(timeString);
        preferences.setTime(time);

        String priceString = priceSpinner.getSelectedItem().toString();
        int price = priceString.length();
        preferences.setPrice(price);

        String accessString = accessSpinner.getSelectedItem().toString();
        if (accessString.compareTo("Dorm Room") != 0) {
            preferences.setAccess(1);
        } else {
            preferences.setAccess(0);
        }


        preferences.setUser(ParseUser.getCurrentUser());

        preferences.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                }
                goMainActivity();
            }

        });



    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}