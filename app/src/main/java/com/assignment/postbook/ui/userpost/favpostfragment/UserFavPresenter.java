package com.assignment.postbook.ui.userpost.favpostfragment;

import android.content.Context;
import android.util.Log;

import com.assignment.postbook.data.DataCallbackImp;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserFavPresenter implements UserFavPostContract.favPostPresenter {

    private UserFavPostContract.favPostView favPostView;
    private Disposable disposable;
    private DataCallbackImp dataCallbackImp;

    public UserFavPresenter(UserFavPostContract.favPostView favPostView, Context context) {
        this.favPostView = favPostView;
        dataCallbackImp = new DataCallbackImp(context);
    }


    @Override
    public void informAboutDeletePost(UserPostBean userPostBean) {
        favPostView.notifyFragment(userPostBean);
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
                        favPostView.postAddedAsFavSuccessfully(position);
                    }

                    @Override
                    public void onError(Throwable e) {
                        favPostView.displayFailureMsg(e.getMessage().toString());
                    }
                });
    }

    @Override
    public void deleteFavpostFrmDatabase(final UserPostBean userPostBean, final int position) {
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
                        favPostView.deleteFavpostSuccessfully(position);

                    }

                    @Override
                    public void onError(Throwable e) {
                        favPostView.displayFailureMsg(e.getMessage().toString());
                    }
                });
    }

    @Override
    public void requestFavPostFromDatabase() {

        favPostView.showProgress();
        dataCallbackImp.getUserFavPostDataCallback().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UserPostBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<UserPostBean> userPostBeans) {
                        if (userPostBeans != null && userPostBeans.size() != 0) {
                            favPostView.setDataToRecyclerView(userPostBeans);
                        } else {
                            favPostView.noDataAvailable();
                        }
                        favPostView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        favPostView.displayFailureMsg(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Presenter", "onComplete: ");
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
