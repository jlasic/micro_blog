package com.example.lasic.ublog;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.singletons.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by lasic on 16.10.2017..
 */

public class PostFeedPresenter {

    private Context mContext;

    private ArrayList<Post> posts;

    public PostFeedPresenter(Context context){
        mContext = context;
        posts = new ArrayList<>();
        requestPosts();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    private void requestPosts(){
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.POST_REQ_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        posts = new Gson().fromJson(response.toString(), new TypeToken<ArrayList<Post>>() {}.getType());
                        for (Post p : posts)
                            Log.d("TEST123a", "onResponse: " + p.getTitle());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TEST123a", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestManager.getInstance(mContext).addToRequestQueue(jsObjRequest);
    }

}
