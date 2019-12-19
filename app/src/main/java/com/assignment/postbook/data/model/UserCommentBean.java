package com.assignment.postbook.data.model;

import com.google.gson.annotations.SerializedName;

public class UserCommentBean {

    @SerializedName("postId")
    private int postId;
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("body")
    private String postDetail;

    public UserCommentBean(int postId, int id, String name, String email, String postDetail) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.postDetail = postDetail;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(String postDetail) {
        this.postDetail = postDetail;
    }
}
