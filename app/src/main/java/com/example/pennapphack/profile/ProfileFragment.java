package com.example.pennapphack.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pennapphack.R;
import com.example.pennapphack.models.Favorite;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.settings.SettingsFragment;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
    private ProfileViewModel mViewModel;
    private FavoritesViewModel favoritesViewModel;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager2;
    private List<Favorite> favorites;

    protected ProfileAdapter adapter;
    protected FavoritesAdapter adapter2;
    protected List<Post> posts;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProviders().of(this).get(ProfileViewModel.class);
        favoritesViewModel = new ViewModelProviders().of(this).get(FavoritesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settings = view.findViewById(R.id.btnSettings);
        username = view.findViewById(R.id.usernameText);
        linearContainer = view.findViewById(R.id.linearHold);
        userPosts = view.findViewById(R.id.userPosts);
        userList = view.findViewById(R.id.userList);
        savedPosts = view.findViewById(R.id.savePosts);
        savedList = view.findViewById(R.id.saveList);

        posts = new ArrayList<>();
        favorites = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), posts);
        adapter2 = new FavoritesAdapter(getContext(), favorites);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager2 = new LinearLayoutManager(getContext());

        userList.setAdapter(adapter);
        userList.setLayoutManager(linearLayoutManager);

        savedList.setAdapter(adapter2);
        savedList.setLayoutManager(linearLayoutManager2);

        mViewModel.getPosts().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> reviewsResults) {
                // update UI
                if (!reviewsResults.isEmpty()) {
                    adapter.setPosts(reviewsResults);
                }


            }
        });

        favoritesViewModel.getFavorites().observe(getViewLifecycleOwner(), new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> reviewsResults) {
                // update UI
                if (!reviewsResults.isEmpty()) {
                    adapter2.setFavorites(reviewsResults);
                }


            }
        });

        username.setText(ParseUser.getCurrentUser().getUsername());

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SettingsFragment();
                replaceFragment(fragment);
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.flContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}