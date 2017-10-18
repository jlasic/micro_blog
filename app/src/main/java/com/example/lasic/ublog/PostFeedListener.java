package com.example.lasic.ublog;

import com.example.lasic.ublog.data.Post;

import java.util.ArrayList;

/**
 * Created by lasic on 16.10.2017..
 */

public interface PostFeedListener {
    void showFullPost(Post post);
    void setPosts(ArrayList<Post> posts);
    void onError(String error);
}
