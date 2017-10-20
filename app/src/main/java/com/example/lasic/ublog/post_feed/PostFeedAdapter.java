package com.example.lasic.ublog.post_feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lasic.ublog.R;
import com.example.lasic.ublog.data.Post;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lasic on 16.10.2017..
 */

public class PostFeedAdapter extends RecyclerView.Adapter {

    public interface PostClickListener {
        void onPostClicked(Post post);
    }

    private LayoutInflater layoutInflater;
    private ArrayList<Post> posts;
    private PostClickListener clickListener;

    public PostFeedAdapter(Context context, PostClickListener listener){
        layoutInflater = LayoutInflater.from(context);
        clickListener = listener;
    }

    public void setPosts(ArrayList<Post> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_post_less, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            Post post = posts.get(position);
            ((ViewHolder) holder).setPost(post);
        }
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_body)
        TextView tvBody;

        private Post post;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.onPostClicked(post);
                }
            });
        }

        public void setTitle(String title){
            tvTitle.setText(title);
        }

        public void setBody(String body){
            tvBody.setText(body);
        }

        public void setPost(Post post){
            this.post = post;
            setTitle(post.getTitle());
            setBody(post.getBody());
        }
    }
}
