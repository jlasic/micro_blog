package com.example.lasic.ublog;

import com.example.lasic.ublog.data.Post;

import java.util.ArrayList;

/**
 * Created by lasic on 16.10.2017..
 */

public interface PostFeedInterface {
    void onDataReady(ArrayList<Post> posts);
}
