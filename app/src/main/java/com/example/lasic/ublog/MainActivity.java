package com.example.lasic.ublog;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    private PostFeedPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPager.setAdapter(viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager()));
        presenter = new PostFeedPresenter(this);
        presenter.setListener(this);
        presenter.getPosts();
    }

    @Override
    public void onDataReady(ArrayList<Post> posts) {
        ((PostFeedFragment)viewPagerAdapter.getItem(0)).setData(posts);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private PostFeedFragment postFeedFragment;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    if (postFeedFragment == null)
                        postFeedFragment = new PostFeedFragment();
                    return postFeedFragment;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
