package com.example.lasic.ublog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.lasic.ublog.data.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostFeedActivity extends AppCompatActivity implements PostFeedFragment.PostFeedNavigation{

    @BindView(R.id.container)
    FrameLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_feed);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,  new PostFeedFragment())
                .commit();
    }

    @Override
    public void showFullPost(Post post) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, FullPostFragment.getInstance(post))
                .addToBackStack("test")
                .commit();
    }
}
