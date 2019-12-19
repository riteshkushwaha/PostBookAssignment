package com.assignment.postbook.ui.usercomment;


import com.assignment.postbook.data.model.UserCommentBean;

import java.util.List;

public interface CommentPageContract {

    interface CommentView {

        void noDataAvailable();

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<UserCommentBean> userCommentBeanList);

        void onResponseFailure(String msg);

    }

    interface CommentPresenter {

        void requestUserCommentData(int postId);

        void rxUnsubscribe();

    }

}
