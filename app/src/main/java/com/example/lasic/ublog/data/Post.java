package com.example.lasic.ublog.data;

/**
 * Created by lasic on 16.10.2017..
 */

public class Post {

    private int id;
    private int userId;
    private String title;
    private String body;

    public Post(int id, int userId, String title, String body){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
