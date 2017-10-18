package com.example.lasic.ublog.singletons;

import android.content.Context;

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

    public boolean isCurrentUser(String username){
        return currentUser != null && currentUser.equals(username);
    }
}
