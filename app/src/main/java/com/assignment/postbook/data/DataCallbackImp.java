package com.assignment.postbook.data;

import android.content.Context;

import com.assignment.postbook.data.database.UserDataLocalRepository;
import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;
import com.assignment.postbook.data.remote.UserDataRemoteRespository;

import java.util.List;

import io.reactivex.Observable;

public class DataCallbackImp implements DataCallbackInterface {

    private UserDataLocalRepository mUserDataLocalRepository;
    private UserDataRemoteRespository mUserDataRemoteRespository;

    private Observable<List<UserPostBean>> mRemoteDataObservable;
    private Observable<List<UserPostBean>> mlocalDataObservable;

    private Observable<List<UserCommentBean>> mUserPostDetailObservable;

    public DataCallbackImp() {
        mUserDataRemoteRespository = new UserDataRemoteRespository();
    }

    public DataCallbackImp(Context context) {
        mUserDataRemoteRespository = new UserDataRemoteRespository();
        mUserDataLocalRepository = new UserDataLocalRepository(context);
    }


    @Override
    public Observable<List<UserPostBean>> userDataRemoteCallback(String userId) {
        mRemoteDataObservable = mUserDataRemoteRespository.getUserPostObservableList(userId);
        return mRemoteDataObservable;
    }

    @Override
    public Observable<List<UserPostBean>> getUserFavPostDataCallback() {
        mlocalDataObservable = mUserDataLocalRepository.getUserPostFrmDB();
        return mlocalDataObservable;
    }

    @Override
    public Observable<List<UserCommentBean>> userPostDetailResponseCallback(int postId) {
        mUserPostDetailObservable = mUserDataRemoteRespository.getUserPostDetailObservableList(postId);

        return mUserPostDetailObservable;
    }

    @Override
    public void insertUserFavPostIDB(UserPostBean userPostBean) {
        mUserDataLocalRepository.insertUserPostiNDB(userPostBean);
    }

    @Override
    public void deleteUserFavPostFrmDB(UserPostBean userPostBean) {
        mUserDataLocalRepository.deleteFavPostFrmDB(userPostBean);
    }

    @Override
    public void deleteAllData() {
        mUserDataLocalRepository.deleteTableData();
    }

}
