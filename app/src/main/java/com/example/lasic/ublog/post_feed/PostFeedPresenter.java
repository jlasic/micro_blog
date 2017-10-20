package com.example.lasic.ublog.post_feed;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.lasic.ublog.SimpleCallback;
import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.helpers.Constants;
import com.example.lasic.ublog.singletons.PostManager;
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

    private PostFeedListener listener;

    public void setListener(PostFeedListener listener) {
        this.listener = listener;
    }

    public PostFeedPresenter(Context context){
        mContext = context;
        posts = new ArrayList<>();
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void requestPosts(){
        PostManager.getInstance(mContext).requestNewPosts(new SimpleCallback<ArrayList<Post>, String>() {
            @Override
            public void onSuccess(ArrayList<Post> data) {
                posts = data;
                if (listener != null)
                    listener.setPosts(posts);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void showFullPost(Post post){
        listener.showFullPost(post);
    }

}
