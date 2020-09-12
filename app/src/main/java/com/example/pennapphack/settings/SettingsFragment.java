package com.example.pennapphack.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pennapphack.R;
import com.example.pennapphack.login.LoginActivity;
import com.parse.ParseUser;


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
        ArrayAdapter<String> arrayAdapterTime= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, TIMELIST);
        timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, PRICELIST);
        priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);

        //access spinner
        ArrayAdapter<String> arrayAdapterAccess = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, ACCESSLIST);
        accessSpinner = (Spinner)accessSpin;
        accessSpinner.setAdapter(arrayAdapterAccess);


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


    }
}