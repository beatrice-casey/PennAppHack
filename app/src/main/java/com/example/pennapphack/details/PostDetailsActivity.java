package com.example.pennapphack.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.pennapphack.R;
import com.example.pennapphack.models.Post;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    public static final String TAG = "RestaurantDetails";
    private Context context;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        final FragmentManager fragmentManager = getSupportFragmentManager();


        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        context = this;
        Log.i(TAG, "Post: " + Post.KEY_RECIPE_NAME);

        Fragment fragment = new DetailsFragment(context, post);

        fragmentManager.beginTransaction().replace(R.id.flContainerReview, fragment).commit();
    }
}