package com.example.pennapphack.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pennapphack.details.ReviewsViewModel;
import com.example.pennapphack.home.PostsViewModel;
import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileViewModel extends PostsViewModel {


    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void queryPosts() {

        //Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get the user who made the post
        query.include(Post.KEY_USER);
        //query.whereEqualTo(Post.KEY_PRICE, pricePref);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED);
        //get the preferred time

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postResults, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                listPosts.addAll(postResults);
                posts.setValue(listPosts);

            }
        });
    }
}
