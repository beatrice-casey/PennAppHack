package com.example.pennapphack.details;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pennapphack.R;
import com.example.pennapphack.models.Favorite;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    Context context;
    Post post;
    public static final String TAG = "DetailsFragment";

    private TextView tvRecipeName;
    private TextView tvRecipe;
    private Button btnFavorites;
    private ImageView ivFoodImage;
    private TextView tvUsername;
    private TextView tvReviews;
    private FloatingActionButton btnCreateReview;
    private RecyclerView rvReviews;
    private RatingBar ratingBar;
    private TextView tvTimeText;
    private TextView tvTime;
    private TextView tvPriceText;
    private RatingBar rbPrice;
    private TextView tvAccess;
    private TextView tvAccessType;
    protected ReviewsAdapter adapter;
    private List<Review> reviews;
    private TextView tvEmptyReviewsNote;
    private boolean isLiked;

    private ReviewsViewModel mViewModel;
    LinearLayoutManager linearLayoutManager;


    //protected ReviewsAdapter adapter;
    //private List<Review> reviews;
    private float rating;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(Context context, Post post) {
        this.context = context;
        this.post = post;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProviders().of(this).get(ReviewsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvRecipeName = view.findViewById(R.id.tvRecipeName);
        tvRecipe = view.findViewById(R.id.tvRecipe);
        btnFavorites = view.findViewById(R.id.btnFavorites);
        ivFoodImage = view.findViewById(R.id.ivFoodImage);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvReviews = view.findViewById(R.id.tvReviewTag);
        btnCreateReview = view.findViewById(R.id.btnAddReview);
        rvReviews = view.findViewById(R.id.rvReviews);
        ratingBar = view.findViewById(R.id.rbDetails);
        tvTimeText = view.findViewById(R.id.tvTimeText);
        tvTime = view.findViewById(R.id.tvTime);
        tvEmptyReviewsNote = view.findViewById(R.id.tvEmptyTextNote);

        tvPriceText = view.findViewById(R.id.tvPriceText);
        rbPrice = view.findViewById(R.id.rbPrice);
        tvAccess = view.findViewById(R.id.tvAccess);
        tvAccessType = view.findViewById(R.id.tvAccessType);


        if (post.getAccess() == 0) {
            tvAccessType.setText("Dorm");

        } else {
            tvAccessType.setText("Full Kitchen");
        }

        tvRecipeName.setText(post.getRecipeName());
        tvRecipe.setText(post.getRecipe());
        tvUsername.setText(post.getUser().getUsername());

        tvTime.setText(String.valueOf(post.getTime()));
        rbPrice.setRating(post.getPrice());

        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(context).load(post.getImage().getUrl()).into(ivFoodImage);
        }

        checkFavorite(post);

        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLiked) {
                    btnFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    addFavorite(post);
                } else {
                    btnFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    removeFavorite(post);
                }
            }
        });

        btnCreateReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ComposeReviewFragment(post);
                replaceFragment(fragment);

            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        rvReviews.setLayoutManager(linearLayoutManager);

        reviews = new ArrayList<>();
        adapter = new ReviewsAdapter(getContext(), reviews);
        rvReviews.setAdapter(adapter);

        mViewModel.getReviews(post).observe(getViewLifecycleOwner(), new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewsResults) {
                // update UI

                tvEmptyReviewsNote.setText("");
                adapter.setReviews(reviewsResults);
                rating = adapter.getRating(reviewsResults);
                ratingBar.setRating(rating);


            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.flContainerReview, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkFavorite(Post post) {
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        query.whereEqualTo(Favorite.KEY_POST, post);
        query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> favorites, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                if (favorites.isEmpty()) {
                    btnFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    isLiked = false;
                } else {
                    btnFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    isLiked = true;
                }


            }
        });
    }


    private void removeFavorite(Post post) {
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        query.whereEqualTo(Favorite.KEY_POST, post);
        query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
        query.getFirstInBackground(new GetCallback<Favorite>() {

            @Override
            public void done(Favorite object, ParseException e) {
                try {
                    object.delete();
                    object.saveInBackground();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

            }
        });
        isLiked = false;
    }


    private void addFavorite(Post post) {
        Favorite favorite = new Favorite();
        favorite.setPost(post);
        favorite.setRecipeName(post.getRecipeName());
        favorite.setUser(ParseUser.getCurrentUser());
        favorite.saveInBackground();
        isLiked = true;

    }
}