package com.assignment.postbook.data.remote;

import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    String POSTS = "/posts";
    String COMMENTS = "/comments";

    @GET(POSTS)
    Observable<List<UserPostBean>> getUserPostFrmWebAPI(@Query("userId") String user_id);

    @GET(COMMENTS)
    Observable<List<UserCommentBean>> getUserCommentPostFrmAPI(@Query("postId") int post_id);

}
