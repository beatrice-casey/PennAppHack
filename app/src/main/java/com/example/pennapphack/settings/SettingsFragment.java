package com.example.pennapphack.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pennapphack.R;
import com.example.pennapphack.login.LoginActivity;
import com.example.pennapphack.models.Preferences;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class SettingsFragment extends Fragment {

    String [] TIMELIST ={"30","60","90","120"};
    String [] PRICELIST ={"$","$$","$$$"};
    String [] ACCESSLIST ={"Dorm Room", "Full Kitchen"};

    private Button btnLogout;
    private TextView selectPref;
    private Spinner timeSpin;
    private Spinner priceSpin;
    private Spinner accessSpin;
    private Button setPref;
    private TextView change;
    private Spinner timeSpinner;
    private Spinner priceSpinner;
    private Spinner accessSpinner;
    private ArrayAdapter<String> arrayAdapterTime;
    private ArrayAdapter<String> arrayAdapterPrice;
    private ArrayAdapter<String> arrayAdapterAccess;
    private String preferencesObjectID;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btnLogout);
        selectPref=view.findViewById(R.id.tvSelectPreferences);
        timeSpin=view.findViewById(R.id.timeDrop);
        priceSpin=view.findViewById(R.id.priceDrop);
        accessSpin=view.findViewById(R.id.accessDrop);
        setPref=view.findViewById(R.id.btnSetPreferences);
        change=view.findViewById(R.id.tvChangePreferences);

        //time spinner
        arrayAdapterTime= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, TIMELIST);
        timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        arrayAdapterPrice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, PRICELIST);
        priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);

        //access spinner
        arrayAdapterAccess = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, ACCESSLIST);
        accessSpinner = (Spinner)accessSpin;
        accessSpinner.setAdapter(arrayAdapterAccess);

        showSelectedPreferences();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                //compose icon has been selected
                //navigate to the compose activity
                Intent intent = new Intent(getContext(), LoginActivity.class);
                //start activity
                startActivity(intent);
            }
        });

        setPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePreferences();
            }
        });
    }

    private void showSelectedPreferences(){
        ParseQuery<Preferences> query = ParseQuery.getQuery(Preferences.class);
        query.include(Preferences.KEY_USER);
        query.whereEqualTo(Preferences.KEY_USER,ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Preferences>() {
            @Override
            public void done(List<Preferences> objects, ParseException e) {
                String time = Integer.toString(objects.get(0).getTime());

                int spinnerPositionTime = arrayAdapterTime.getPosition(time);

                timeSpinner.setSelection(spinnerPositionTime);
                priceSpinner.setSelection(objects.get(0).getPrice()-1);
                accessSpinner.setSelection(objects.get(0).getAccess());

                preferencesObjectID = objects.get(0).getObjectId();
            }
        });
    }

    private void updatePreferences() {
        ParseQuery<Preferences> query = ParseQuery.getQuery(Preferences.class);
        query.whereEqualTo(Preferences.KEY_USER, ParseUser.getCurrentUser());
        query.getInBackground(preferencesObjectID, new GetCallback<Preferences>() {
            @Override
            public void done(Preferences object, ParseException e) {
                String timeString = timeSpinner.getSelectedItem().toString();
                int time = Integer.parseInt(timeString);
                object.setTime(time);

                String priceString = priceSpinner.getSelectedItem().toString();
                int price = priceString.length();
                object.setPrice(price);

                String accessString = accessSpinner.getSelectedItem().toString();
                if (accessString.compareTo("Dorm") != 0) {
                    object.setAccess(1);
                } else {
                    object.setAccess(0);
                }

                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e("PreferenceUpdate", "Issue updating preferences", e);
                            return;
                        }
                        Toast.makeText(getContext(), "Preferences Updated.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}