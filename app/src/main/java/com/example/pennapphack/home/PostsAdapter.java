package com.example.pennapphack.home;

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
import com.example.pennapphack.details.PostDetailsActivity;
import com.example.pennapphack.models.Favorite;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.R;
import com.example.pennapphack.models.Preferences;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    protected Context context;
    private List<Post> posts;
    private boolean isLiked;
    public static final String TAG = "Adapter";

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isLiked) {
                        btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                        addFavorite(posts.get(getAdapterPosition())); 
                    } else {
                        btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                        removeFavorite(posts.get(getAdapterPosition()));
                    }
                   
                }
            });
        }

        public void bind(final Post post) {
            //bind data into view elements
            tvRecipeName.setText(post.getRecipeName());
            tvUsername.setText(post.getUser().getUsername());
            int time = post.getTime();
            tvTime.setText(String.valueOf(time));
            ratingBar.setRating(post.getPrice());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
            checkFavorite(post);

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
                        btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                        isLiked = false;
                    } else {
                        btnLike.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                        isLiked = true;
                    }


                }
            });
        }


        @Override
        public void onClick(View view) {
            //getting adapter position
            int position = getAdapterPosition();
            //make sure position is valid (it exists in view)
            if (position != RecyclerView.NO_POSITION) {
                //get the movie at that position
                Post post = posts.get(position);
                //make an intent to display MovieDetailsActivity
                Intent intent = new Intent(context, PostDetailsActivity.class);
                //serialize the movie using parceler, use short name as key
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                //show the activity
                context.startActivity(intent);
            }
        }

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

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}
