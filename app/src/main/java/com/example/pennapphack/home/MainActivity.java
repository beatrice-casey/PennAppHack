package com.example.pennapphack.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.pennapphack.create.CreateFragment;
import com.example.pennapphack.profile.ProfileFragment;
import com.example.pennapphack.R;
import com.example.pennapphack.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        Log.i(TAG, "Home selected");
                        //menuItem.setIcon(R.drawable.ic_home);
                        break;
                    case R.id.action_create:
                        fragment = new CreateFragment();
                        Log.i(TAG, "Favorites selected");
                        //menuItem.setIcon(R.drawable.ic_create_fill);
                        break;
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        //menuItem.setIcon(R.drawable.ic_profile);
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        //menuItem.setIcon(R.drawable.ic_profile);
                        break;
                    default:
                        fragment = new HomeFragment();
                        //menuItem.setIcon(R.drawable.ic_home);
                        break;
                }
                //fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.flContainer, fragment);
                transaction.commit();




                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }
}