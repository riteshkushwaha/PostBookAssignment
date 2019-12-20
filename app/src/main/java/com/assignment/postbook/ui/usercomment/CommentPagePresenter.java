package com.assignment.postbook.ui.usercomment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.assignment.postbook.data.DataCallbackImp;
import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;
import com.assignment.postbook.ui.usercomment.CommentPageContract.CommentView;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.assignment.postbook.util.AppUtils.USER_MARK_POST;

public class CommentPagePresenter implements CommentPageContract.CommentPresenter {

    private CommentPageContract.CommentView commentView;
    private Disposable disposable;
    private DataCallbackImp dataCallbackImp;
    private Context context;

    public CommentPagePresenter(CommentView commentView, Context context) {
        this.commentView = commentView;
        this.context = context;
        dataCallbackImp = new DataCallbackImp(context);
    }

    @Override
    public void requestUserCommentData(int postId) {
        commentView.showProgress();

        dataCallbackImp.userPostDetailResponseCallback(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UserCommentBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(List<UserCommentBean> userCommentBeans) {
                        commentView.setDataToRecyclerView(userCommentBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.onResponseFailure(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {
                        commentView.hideProgress();
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        disposable.dispose();
    }

    @Override
    public void removeFavPost(final UserPostBean userPostBean) {
        userPostBean.setMarkedFav(false);
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
                        commentView.favPostRemoved();
                        notifyUserPostList(userPostBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.dbQueryFailed(e.getMessage().toString());
                    }
                });
    }

    @Override
    public void addedFavPost(final UserPostBean userPostBean) {
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
                        commentView.favPostAdded();
                        notifyUserPostList(userPostBean);

                    }

                    @Override
                    public void onError(Throwable e) {
                        commentView.dbQueryFailed(e.getMessage().toString());
                    }
                });
    }

    /***
     * local broadcast for notify fav post changes
     * @param userPostBean
     */
    private void notifyUserPostList(UserPostBean userPostBean) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent localIntent = new Intent("user-mark-postfav");
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_MARK_POST, userPostBean);
        localIntent.putExtra("USERDATA", bundle);
        // Send local broadcast
        localBroadcastManager.sendBroadcast(localIntent);
    }

}
