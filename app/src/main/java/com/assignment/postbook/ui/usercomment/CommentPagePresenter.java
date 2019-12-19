package com.assignment.postbook.ui.usercomment;


import com.assignment.postbook.data.DataCallbackImp;
import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.ui.usercomment.CommentPageContract.CommentView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CommentPagePresenter implements CommentPageContract.CommentPresenter {

    private CommentPageContract.CommentView commentView;
    private Disposable disposable;
    private DataCallbackImp dataCallbackImp;

    public CommentPagePresenter(CommentView commentView) {
        this.commentView = commentView;
        dataCallbackImp = new DataCallbackImp();
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

}
