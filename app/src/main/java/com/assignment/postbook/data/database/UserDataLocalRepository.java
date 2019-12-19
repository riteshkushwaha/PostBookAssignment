package com.assignment.postbook.data.database;

import android.content.Context;


import com.assignment.postbook.data.model.UserPostBean;
import java.util.List;
import io.reactivex.Observable;

public class UserDataLocalRepository {

    private Context mContext;
    private UserFavPostDatabase mUserFavPostDatabase;
    private Observable<List<UserPostBean>> dbObservableList;

    public UserDataLocalRepository(Context lContext) {
        this.mContext = lContext;
        mUserFavPostDatabase = UserFavPostDatabase.getDBInstance(lContext);
    }

    public void insertUserPostiNDB(UserPostBean userPostBean) {
        mUserFavPostDatabase.getUserPostDAO().insertUserFavPost(userPostBean);
    }

    public Observable<List<UserPostBean>> getUserPostFrmDB() {
        dbObservableList = mUserFavPostDatabase.getUserPostDAO().getUserPostData();
        return dbObservableList;
    }

    public void deleteFavPostFrmDB(UserPostBean userPostBean) {
        mUserFavPostDatabase.getUserPostDAO().deleteUserFavPost(userPostBean);
    }

    public void deleteTableData(){
        mUserFavPostDatabase.clearAllTables();
    }

}
