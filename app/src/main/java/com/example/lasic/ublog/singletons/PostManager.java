package com.example.lasic.ublog.singletons;

import android.content.Context;

import com.android.volley.RequestQueue;

/**
 * Created by lasic on 18.10.2017..
 */

public class PostManager {
    private static Context mContext;
    private static PostManager mInstance;

    private PostManager(Context context){
        mContext = context;
    }

    public static PostManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PostManager(context.getApplicationContext());
        }
        return mInstance;
    }
}
