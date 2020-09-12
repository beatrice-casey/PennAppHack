package com.example.pennapphack;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    String [] TIMELIST ={"30","60","90","120"};
    String [] PRICELIST ={"$","$$","$$$"};

    private Button post;
    private Button picture;
    private EditText information;
    private ImageView foodPic;
    private TextView recipeName;
    private Spinner timeSpin;
    private Spinner priceSpin;
    private Spinner timeSpinner;
    private Spinner priceSpinner;


    public CreateFragment() {
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
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        post = view.findViewById(R.id.btnPost);
        picture = view.findViewById(R.id.btnFood);
        information = view.findViewById(R.id.etDescription);
        foodPic = view.findViewById(R.id.foodPicture);
        recipeName = view.findViewById(R.id.recipeName);
        timeSpin = view.findViewById(R.id.timeDrop);
        priceSpin = view.findViewById(R.id.priceDrop);

        //time spinner
        ArrayAdapter<String> arrayAdapterTime= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, TIMELIST);
        timeSpinner = (Spinner)timeSpin;
        timeSpinner.setAdapter(arrayAdapterTime);

        //price spinner
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, PRICELIST);
        priceSpinner = (Spinner)priceSpin;
        priceSpinner.setAdapter(arrayAdapterPrice);
    }
}