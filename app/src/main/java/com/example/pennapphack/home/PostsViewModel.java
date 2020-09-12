package com.example.pennapphack.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Preferences;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class PostsViewModel extends AndroidViewModel {

    public static final String TAG = "PostsVM";
    private MutableLiveData<List<Post>> posts;
    private List<Post> listPosts;
    private int timePref;
    private int accessPref;
    private int pricePref;

    public LiveData<List<Post>> getPosts() {
        posts = new MutableLiveData<>();
        listPosts = new ArrayList<>();
        queryTime();
        return posts;
    }



    public PostsViewModel(@NonNull Application application) {
        super(application);
    }

    private void queryTime() {
        ParseQuery<Preferences> query = ParseQuery.getQuery(Preferences.class);
        query.whereEqualTo(Preferences.KEY_USER, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Preferences>() {
            @Override
            public void done(List<Preferences> preferences, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                timePref = preferences.get(0).getTime();
                accessPref = preferences.get(0).getAccess();
                pricePref = preferences.get(0).getPrice();


            }
        });
        queryPosts();

    }


    protected void queryPosts() {
        //Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get the user who made the post
        query.include(Post.KEY_USER);
        //query.whereEqualTo(Post.KEY_PRICE, pricePref);
        //query.whereEqualTo(Post.KEY_ACCESS, accessPref);
        query.addDescendingOrder(Post.KEY_CREATED);
        //get the preferred time

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postResults, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                int i;
                for (i = 0; i < postResults.size(); i++) {
                    if (postResults.get(i).getTime() > timePref) {
                        postResults.remove(i);

                    }
                }
                listPosts.addAll(postResults);
                posts.setValue(listPosts);

            }
        });
    }
}
