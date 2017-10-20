package com.example.lasic.ublog.singletons;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lasic.ublog.SimpleCallback;
import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.helpers.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by lasic on 18.10.2017..
 */

public class PostManager {
    private static Context mContext;
    private static PostManager mInstance;

    private ArrayList<Post> allPosts;

    private PostManager(Context context){
        mContext = context;
    }

    public static PostManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PostManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void requestNewPosts(final SimpleCallback<ArrayList<Post>, String> callback){
        //TODO: remove this if, only done for testing puropses
        if (allPosts != null){
            if (callback != null)
                callback.onSuccess(allPosts);
            return;
        }

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.POST_REQ_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        allPosts = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<Post>>() {}.getType());
                        ArrayList<Post> tmpPosts = new ArrayList<>();
                        tmpPosts.addAll(allPosts);
                        if (callback != null)
                            callback.onSuccess(tmpPosts);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TEST123a", "onErrorResponse: " + error.getMessage());
                        if (callback != null)
                            callback.onError("");
                    }
                });
        RequestManager.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

    //TODO: remove this, only done for testing puropses
    public void addPost(Post post){
        allPosts.add(0,post);
    }
}
