package com.assignment.postbook.data;

import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Observable;

public interface DataCallbackInterface {

    Observable<List<UserPostBean>> userDataRemoteCallback(String userId);
    Observable<List<UserPostBean>> getUserFavPostDataCallback();
    Observable<List<UserCommentBean>> userPostDetailResponseCallback(int postId);
    void insertUserFavPostIDB(UserPostBean userPostBean);
    void deleteUserFavPostFrmDB(UserPostBean userPostBean);
    void deleteAllData();


}
