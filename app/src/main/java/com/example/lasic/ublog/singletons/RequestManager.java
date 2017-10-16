package com.example.lasic.ublog.singletons;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lasic on 16.10.2017..
 */

public class RequestManager {
    private static Context mContext;
    private static RequestManager mInstance;
    private RequestQueue mRequestQueue;

    private RequestManager(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static RequestManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestManager(context.getApplicationContext());
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        mRequestQueue.add(req);
    }
}
