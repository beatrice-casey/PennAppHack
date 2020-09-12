package com.example.pennapphack.details;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ReviewsViewModel extends AndroidViewModel {

    protected MutableLiveData<List<Review>> reviews;
    protected List<Review> listReviews;
    public static final String TAG = "ReviewsViewModel";
    private Post post;

    public ReviewsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Review>> getReviews(Post post) {
        reviews = new MutableLiveData<>();
        listReviews = new ArrayList<>();
        this.post = post;
        Log.i(TAG, "Getting reviews");
        queryReviews();

        return reviews;
    }

    protected void queryReviews() {

        //Specify which class to query
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        //get the user who's review it is
        query.include(Review.KEY_USER);
        query.include(Review.KEY_RECIPE_NAME);
        query.whereEqualTo(Review.KEY_RECIPE_NAME, post.getRecipeName());
        query.addDescendingOrder(Review.KEY_CREATED);
        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> parseReviews, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                if (parseReviews.size() != 0) {
                    listReviews.addAll(parseReviews);
                    for (Review review: parseReviews) {
                        Log.i(TAG, review.getUser().getUsername() + ": " + review.getDescription());
                    }
                    reviews.setValue(listReviews);
                    //Log.i("FavoritesViewModel", "Restaurants in Parse: " + favorites.get(0).getRestaurant());

                }
            }
        });
    }
}

