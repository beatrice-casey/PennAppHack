package com.example.pennapphack;


import android.app.Application;

import com.example.pennapphack.models.Post;
import com.example.pennapphack.models.Preferences;
import com.example.pennapphack.models.Review;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line for production
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
        ParseObject.registerSubclass(Preferences.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Review.class);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.networkInterceptors().add(httpLoggingInterceptor);

        // set applicationId, and server server based on the values in the back4app settings.
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("rIRCY1wRqICInAMsNzJHU8JIejIPgj4AjFTwBVVj") // should correspond to Application Id env variable
                .clientKey("mvofRtW5xCpqbUPmr4YBCgj2ggxoD6VtzlhkeWLi")  // should correspond to Client key env variable
                        .server("https://parseapi.back4app.com").build());
    }
}