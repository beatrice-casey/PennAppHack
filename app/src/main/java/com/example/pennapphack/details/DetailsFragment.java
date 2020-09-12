package com.example.pennapphack.details;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pennapphack.R;
import com.example.pennapphack.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.ParseFile;

import java.util.List;


public class DetailsFragment extends Fragment {

    Context context;
    Post post;

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

        btnFavorites.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

    }
}