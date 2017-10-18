package com.example.lasic.ublog;

import android.app.Activity;
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

    private static final int LOGIN_REQ_CODE = 822;
    private static final String ARG_USER = "user";

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

        String user = getIntent().getStringExtra(ARG_USER);
        if (user == null)
            setTitle("Post Feed");
        else if (Session.getInstance(this).isCurrentUser(user)){
            setTitle("My Profile");
        }
        else{
            setTitle("@" + user);
        }

        if (user == null || !Session.getInstance(this).isCurrentUser(user)){
            ivProfile.setVisibility(View.VISIBLE);
            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Session.getInstance(v.getContext()).isLogged()){
                        startMyProfile();
                    }
                    else {
                        Intent intent = new Intent(PostFeedActivity.this, LoginActivity.class);
                        startActivityForResult(intent, LOGIN_REQ_CODE);
                    }
                }
            });
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQ_CODE)
            startMyProfile();
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void startMyProfile(){
        Intent intent = new Intent(PostFeedActivity.this, PostFeedActivity.class);
        intent.putExtra(ARG_USER, Session.getInstance(this).getCurrentUser());
        startActivity(intent);
    }
}
