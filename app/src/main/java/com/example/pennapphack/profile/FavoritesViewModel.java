package com.example.pennapphack.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pennapphack.home.PostsViewModel;
import com.example.pennapphack.models.Favorite;
import com.example.pennapphack.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    public static final String TAG = "FavoritesVM";
    protected MutableLiveData<List<Favorite>> favorites;
    protected List<Favorite> listFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Favorite>> getFavorites() {
        favorites = new MutableLiveData<>();
        listFavorites = new ArrayList<>();
        queryFavorites();
        return favorites;
    }

    private void queryFavorites() {
        //Specify which class to query
        ParseQuery<Favorite> query = ParseQuery.getQuery(Favorite.class);
        //get the user who made the post
        query.include(Favorite.KEY_USER);
        query.whereEqualTo(Favorite.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(Post.KEY_CREATED);
        //get the preferred time

        query.findInBackground(new FindCallback<Favorite>() {
            @Override
            public void done(List<Favorite> postResults, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                listFavorites.addAll(postResults);
                favorites.setValue(listFavorites);

            }
        });

    }


}
