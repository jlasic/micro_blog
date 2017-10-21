package com.example.lasic.ublog.post_feed;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.lasic.ublog.CreatePostActivity;
import com.example.lasic.ublog.LoginActivity;
import com.example.lasic.ublog.R;
import com.example.lasic.ublog.data.Post;
import com.example.lasic.ublog.singletons.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostFeedActivity extends AppCompatActivity implements PostFeedFragment.PostFeedNavigation{

    private static final int LOGIN_REQ_CODE = 822;
    private static final String ARG_USER = "user";
    public static final String ARG_ACTION = "login.action";
    public static final int ACTION_MY_PROFILE = 32;
    public static final int ACTION_NEW_POST = 54;

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
                        intent.putExtra(ARG_ACTION, ACTION_MY_PROFILE);
                        startActivityForResult(intent, LOGIN_REQ_CODE);
                    }
                }
            });
        }

        //fragment manager takes care of saving fragments back-stack state
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PostFeedFragment())
                    .commit();
        }
    }
    @OnClick(R.id.fab)
    void newPost (){
        if (Session.getInstance(this).isLogged()) {
            Intent intent = new Intent(PostFeedActivity.this, CreatePostActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.alert_not_logged_message)
                    .setTitle(R.string.alert_not_logged_title);
            builder.setPositiveButton(R.string.alert_not_logged_login, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(PostFeedActivity.this, LoginActivity.class);
                    intent.putExtra(ARG_ACTION, ACTION_NEW_POST);
                    startActivityForResult(intent, LOGIN_REQ_CODE);
                }
            });
            builder.setNegativeButton(R.string.msg_cancel, null);
            builder.create().show();
        }
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
        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQ_CODE) {
            int action = data.getIntExtra(ARG_ACTION, -1);
            if (action == ACTION_NEW_POST){
                Intent intent = new Intent(PostFeedActivity.this, CreatePostActivity.class);
                startActivity(intent);
            }
            else if (action == ACTION_MY_PROFILE)
                startMyProfile();
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    private void startMyProfile(){
        Intent intent = new Intent(PostFeedActivity.this, PostFeedActivity.class);
        intent.putExtra(ARG_USER, Session.getInstance(this).getCurrentUser());
        startActivity(intent);
    }
}
