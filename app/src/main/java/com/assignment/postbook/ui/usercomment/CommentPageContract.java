package com.assignment.postbook.ui.usercomment;


import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

public interface CommentPageContract {

    interface CommentView {

        void noDataAvailable();

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<UserCommentBean> userCommentBeanList);

        void onResponseFailure(String msg);

        void favPostRemoved();

        void favPostAdded();

        void dbQueryFailed(String error);

    }

    interface CommentPresenter {

        void requestUserCommentData(int postId);

        void rxUnsubscribe();

        void removeFavPost(UserPostBean userPostBean);

        void addedFavPost(UserPostBean userPostBean);

    }

}
