package com.example.pennapphack.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Review")
public class Review extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "Photo";
    public static final String KEY_USER = "username";
    public static final String KEY_CREATED = "createdAt";
    public static final String KEY_RATING = "Rating";

    public static final String KEY_RECIPE_NAME = "recipeName";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public float getRating() { return getLong(KEY_RATING);}

    public void setRating(float rating) { put(KEY_RATING, rating);}

    public String getRecipeName() {return getString(KEY_RECIPE_NAME);}

    public void setRecipeName(String recipeName) {put(KEY_RECIPE_NAME, recipeName);}


}

