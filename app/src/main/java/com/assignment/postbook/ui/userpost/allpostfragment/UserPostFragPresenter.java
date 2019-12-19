package com.assignment.postbook.ui.userpost.allpostfragment;

import android.content.Context;


import com.assignment.postbook.data.DataCallbackImp;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserPostFragPresenter implements UserPostFragContract.UserPostFragPresenter {


    private UserPostFragContract.UserPostFragView mUserPostFragView;
    private DataCallbackImp dataCallbackImp;
    private Disposable disposable = null;

    public UserPostFragPresenter(UserPostFragContract.UserPostFragView userPostFragView, Context context) {
        this.mUserPostFragView = userPostFragView;
        dataCallbackImp = new DataCallbackImp(context);
    }


    @Override
    public void deleteMarkedFavPost(final UserPostBean userPostBean, final int position) {
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                dataCallbackImp.deleteUserFavPostFrmDB(userPostBean);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        mUserPostFragView.deleteFavpostSuccessfully(position);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserPostFragView.displayFailureMsg(e.getMessage().toString());
                    }
                });
    }

    @Override
    public void insertFavPostInDatabase(final UserPostBean userPostBean, final int position) {
        userPostBean.setMarkedFav(true);
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                dataCallbackImp.insertUserFavPostIDB(userPostBean);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        mUserPostFragView.postAddedAsFavSuccessfully(position);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserPostFragView.displayFailureMsg(e.getMessage().toString());
                    }
                });
    }

    @Override
    public void requestDataFromServer(String userId) {

        mUserPostFragView.showProgress();

        dataCallbackImp.userDataRemoteCallback(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UserPostBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<UserPostBean> userPostBeans) {
                        if (userPostBeans != null && userPostBeans.size() != 0) {
                            mUserPostFragView.showUserPostList();
                            mUserPostFragView.setDataToRecyclerView(userPostBeans);
                        } else {
                            mUserPostFragView.hideUserPostList();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mUserPostFragView.displayFailureMsg(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {
                        mUserPostFragView.hideProgress();
                    }
                });

    }

    @Override
    public void rxUnsubscribe() {
        if (disposable != null) {
            disposable.dispose();
        }
    }


}
