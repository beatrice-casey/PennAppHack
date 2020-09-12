package com.example.pennapphack.search;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pennapphack.R;
import com.example.pennapphack.home.PostsAdapter;
import com.example.pennapphack.models.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText etEnterRecipe;
    private Button btnClear;
    private ImageView ivSearchIcon;
    private RecyclerView rvPosts;
    private List<Post> posts;
    private PostsAdapter adapter;
    private String query;
    private TextView tvEmptyScreenNote;
    private ProgressBar progressBar;

    private SearchViewModel mViewModel;
    private LinearLayoutManager linearLayoutManager;

    public static final String TAG = "SearchFragment";


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProviders().of(this).get(SearchViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEnterRecipe = view.findViewById(R.id.etEnterLocation);
        btnClear = view.findViewById(R.id.btnClear);
        btnClear.setBackgroundResource(R.drawable.ic_baseline_clear_24);
        ivSearchIcon = view.findViewById(R.id.ivSearchIcon);
        tvEmptyScreenNote = view.findViewById(R.id.tvEmptyScreenNote);
        tvEmptyScreenNote.setText("Please enter a recipe to try!");
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        rvPosts = view.findViewById(R.id.rvPosts);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        posts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), posts);
        rvPosts.setAdapter(adapter);


        etEnterRecipe.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyCode == EditorInfo.IME_ACTION_SEARCH
                        || keyCode == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    progressBar.setVisibility(View.VISIBLE);
                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(etEnterRecipe.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    query = etEnterRecipe.getText().toString();
                    mViewModel.getPostsforRecipe(query).observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
                        @Override
                        public void onChanged(List<Post> posts) {
                            // update UI
                            if (!posts.isEmpty()) {
                                tvEmptyScreenNote.setText("");
                                adapter.setPosts(posts);
                                progressBar.setVisibility(View.GONE);
                            } else {
                                tvEmptyScreenNote.setText("There are no recipes for this entry yet.");
                            }

                        }
                    });
                    return true;
                }
                return false;
            }
        });


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etEnterRecipe.setText("");
                adapter.clear();
            }
        });
    }
}