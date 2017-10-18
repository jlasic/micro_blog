package com.example.lasic.ublog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.singletons.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostFeedActivity extends AppCompatActivity implements PostFeedFragment.PostFeedNavigation{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_feed);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setTitle("Post Feed");

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostFeedActivity.this, PostFeedActivity.class);
                startActivity(intent);
            }
        });


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
