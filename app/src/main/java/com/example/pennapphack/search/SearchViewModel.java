package com.example.pennapphack.search;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.pennapphack.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class SearchViewModel extends AndroidViewModel {

    private MutableLiveData<List<Post>> posts;
    private List<Post> listPosts;
    public static final String TAG = "RestaurantsViewModel";
    private String recipe;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Post>> getPostsforRecipe(String query) {
        posts = new MutableLiveData<>();
        listPosts = new ArrayList<>();
        recipe = query;
        queryRecipes();

        return posts;
    }

    private void queryRecipes() {
        //Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get the user who made the post
        query.include(Post.KEY_USER);
        //query.whereEqualTo(Post.KEY_PRICE, pricePref);
        query.include(Post.KEY_RECIPE_NAME);
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
                    if (postResults.get(i).getRecipeName().toLowerCase().compareTo(recipe.toLowerCase())!= 0) {
                        postResults.remove(i);
                    }
                }
                listPosts.addAll(postResults);
                posts.setValue(listPosts);

            }
        });
    }
}

