package com.example.pennapphack.models;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Preferences")
public class Preferences extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_PRICE = "price";
    public static final String KEY_TIME = "time";
    public static final String KEY_ACCESS = "access";


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

    public int getAccess() {return getInt(KEY_ACCESS); }

    public void setAccess(int access) { put(KEY_ACCESS, access); }
}
