package com.example.pennapphack.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pennapphack.R;
import com.example.pennapphack.details.PostDetailsActivity;
import com.example.pennapphack.home.PostsAdapter;
import com.example.pennapphack.models.Favorite;
import com.example.pennapphack.models.Post;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    protected Context context;
    private List<Favorite> favorites;
    private boolean isLiked;
    public static final String TAG = "Adapter";

    public FavoritesAdapter(Context context, List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);
        holder.bind(favorite);

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder  {
        protected TextView tvUsername;
        private ImageView ivImage;
        private TextView tvRecipeName;
        private TextView tvTimeText;
        private TextView tvTime;
        private RatingBar ratingBar;
        private Button btnLike;
        final ParseUser currentUser;
        private boolean isLiked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            btnLike = itemView.findViewById(R.id.btnFavorites);
            tvTimeText = itemView.findViewById(R.id.tvTimeText);
            tvTime = itemView.findViewById(R.id.tvTime);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            currentUser = ParseUser.getCurrentUser();

        }

        public void bind(final Favorite favorite) {
            //bind data into view elements
            tvRecipeName.setText(favorite.getRecipeName());
            tvUsername.setText(favorite.getUser().getUsername());
            //int time = favorite.getPost().getTime();
            //tvTime.setText(String.valueOf(time));
//            ratingBar.setRating(favorite.getPost().getPrice());
//            ParseFile image = favorite.getPost().getImage();
//            if (image != null) {
//                Glide.with(context).load(favorite.getPost().getImage().getUrl()).into(ivImage);
//            }
            btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

        }


    }


    // Clean all elements of the recycler
    public void clear() {
        favorites.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Favorite> list) {
        favorites.addAll(list);
        notifyDataSetChanged();
    }

}