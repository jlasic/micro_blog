package com.example.lasic.ublog.post_feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lasic.ublog.R;
import com.example.lasic.ublog.data.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lasic on 16.10.2017..
 */

public class FullPostFragment extends Fragment {

    @BindView(R.id.tv_body)
    TextView tvBody;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    public static FullPostFragment getInstance(Post post){
        FullPostFragment f = new FullPostFragment();

        Bundle args = new Bundle();
        args.putString("title", post.getTitle());
        args.putString("body", post.getBody());

        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_full, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvTitle.setText(getArguments().getString("title"));
        tvBody.setText(getArguments().getString("body"));
    }
}
