package com.example.pennapphack.home;

import android.content.Context;
import android.content.Intent;
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
import com.example.pennapphack.models.Post;
import com.example.pennapphack.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvRecipeName;
        private TextView tvTimeText;
        private TextView tvTime;
        private RatingBar ratingBar;
        private Button btnLike;
        final ParseUser currentUser;

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
                intent.putExtra("post", post);
                //show the activity
                context.startActivity(intent);
            }
        }

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
