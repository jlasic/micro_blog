package com.example.lasic.ublog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lasic.ublog.data.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lasic on 16.10.2017..
 */

public class PostFeedFragment extends Fragment implements PostFeedListener {

    public interface PostFeedNavigation{
        void showFullPost(Post post);
    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PostFeedNavigation mCallback;

    private PostFeedAdapter adapter;
    private PostFeedPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (PostFeedNavigation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement PostFeedNavigation");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PostFeedPresenter(getContext());
        presenter.setListener(this);

        adapter = new PostFeedAdapter(getContext(), new PostFeedAdapter.PostClickListener() {
            @Override
            public void onPostClicked(Post post) {
                presenter.showFullPost(post);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_feed, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        presenter.requestPosts();

        return view;
    }

    @Override
    public void showFullPost(Post post) {
        mCallback.showFullPost(post);
    }

    @Override
    public void setPosts(ArrayList<Post> posts) {
        adapter.setPosts(posts);
    }

    @Override
    public void onError(String error) {

    }
}
