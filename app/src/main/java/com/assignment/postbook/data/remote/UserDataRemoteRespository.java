package com.assignment.postbook.data.remote;

import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Observable;

public class UserDataRemoteRespository {

    private APIService mAPIService;
    private Observable<List<UserPostBean>> mUserPostObservableList;
    private Observable<List<UserCommentBean>> mUserPostDetailObservableList;

    public UserDataRemoteRespository() {
    }

    public Observable<List<UserPostBean>> getUserPostObservableList(String userId) {

        mAPIService = RetrofitConnClass.getRetrofitAPIService();

        mUserPostObservableList = mAPIService.getUserPostFrmWebAPI(userId);

        return mUserPostObservableList;
    }

    public Observable<List<UserCommentBean>> getUserPostDetailObservableList(int postId) {

        mAPIService = RetrofitConnClass.getRetrofitAPIService();
        mUserPostDetailObservableList = mAPIService.getUserCommentPostFrmAPI(postId);

        return mUserPostDetailObservableList;
    }


}