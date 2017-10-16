package com.example.lasic.ublog;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.singletons.RequestManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PostFeedInterface{

    @BindView(R.id.container)
    FrameLayout container;

    private PostFeedPresenter presenter;

    private PostFeedFragment postFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        postFeedFragment = new PostFeedFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, postFeedFragment)
                .commit();

        presenter = new PostFeedPresenter(this);
        presenter.setListener(this);
        presenter.getPosts();
    }

    @Override
    public void onDataReady(ArrayList<Post> posts) {
        postFeedFragment.setData(posts, new PostInteraction(){
            @Override
            public void onPostClicked(Post post) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, FullPostFragment.getInstance(post))
                        .addToBackStack("test")
                        .commit();
            }
        });
    }
}
