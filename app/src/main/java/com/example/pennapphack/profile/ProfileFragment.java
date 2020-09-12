package com.example.pennapphack.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pennapphack.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private Button settings;
    private TextView username;
    private LinearLayout linearContainer;
    private TextView userPosts;
    private RecyclerView userList;
    private TextView savedPosts;
    private RecyclerView savedList;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getView().findViewById(R.id.btnSettings);
        username = getView().findViewById(R.id.usernameText);
        linearContainer = getView().findViewById(R.id.linearHold);
        userPosts = getView().findViewById(R.id.userPosts);
        userList = getView().findViewById(R.id.userList);
        savedPosts = getView().findViewById(R.id.savePosts);
        savedList = getView().findViewById(R.id.saveList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}