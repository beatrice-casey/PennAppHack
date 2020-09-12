package com.example.pennapphack.models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Favorites")
public class Favorite extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_POST = "Post";
    public static final String KEY_RECIPE_NAME = "recipeName";

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String description) {
        put(KEY_RECIPE_NAME, description);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public Post getPost() { return (Post) get(KEY_POST);}

    public void setPost(ParseObject post) { put(KEY_POST, post); }


}
