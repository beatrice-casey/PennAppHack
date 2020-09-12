package com.example.pennapphack;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_RECIPE_NAME = "recipeName";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED = "createdAt";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TIME = "time";

    public String getRecipeName() {
        return getString(KEY_RECIPE_NAME);
    }

    public void setRecipeName(String description) {
        put(KEY_RECIPE_NAME, description);
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

    public int getPrice() { return getInt(KEY_PRICE); }

    public void setPrice(int price) { put(KEY_PRICE, price); }

    public int getTime() {return getInt(KEY_TIME);}

    public void setTime(int time) {put(KEY_TIME, time);}

}
