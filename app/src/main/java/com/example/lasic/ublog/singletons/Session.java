package com.example.lasic.ublog.singletons;

import android.content.Context;
import android.os.Handler;

import com.example.lasic.ublog.SimpleCallback;
import com.example.lasic.ublog.data.Post;

/**
 * Created by lasic on 18.10.2017..
 */

public class Session {
    private static Context mContext;
    private static Session mInstance;

    private String currentUser;

    private Session(Context context){
        mContext = context;
    }

    public static Session getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Session(context.getApplicationContext());
        }
        return mInstance;
    }

    public boolean isLogged(){
        return currentUser != null;
    }

    public boolean isCurrentUser(String username){
        return isLogged() && currentUser.equals(username);
    }

    public String getCurrentUser(){
        return currentUser;
    }

    private void setCurrentUser(String username){
        currentUser = username;
    }

    public void login(final String username, final SimpleCallback<String> callback){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentUser(username);
                if (callback!=null)
                    callback.onSuccess(username);
            }
        }, 1000);
    }
}
