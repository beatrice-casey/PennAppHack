package com.example.pennapphack.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.pennapphack.R;
import com.example.pennapphack.details.ReviewsAdapter;
import com.example.pennapphack.home.PostsAdapter;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Review;

import java.util.List;

public class ProfileAdapter extends PostsAdapter {

    public ProfileAdapter(Context context, List<Post> posts) {
        super(context, posts);
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reviewView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    public class ViewHolder extends PostsAdapter.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bind(Post post) {
            super.bind(post);
            tvUsername.setText("");
        }
    }

}
