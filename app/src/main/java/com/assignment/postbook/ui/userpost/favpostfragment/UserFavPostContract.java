package com.assignment.postbook.ui.userpost.favpostfragment;


import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

public interface UserFavPostContract {

    interface favPostView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<UserPostBean> userPostBeanList);

        void displayFailureMsg(String msg);

        void noDataAvailable();

        void postAddedAsFavSuccessfully(int position);

        void deleteFavpostSuccessfully(int position);

        void notifyFragment(UserPostBean userPostBean);

    }

    interface favPostPresenter {

        void informAboutDeletePost(UserPostBean userPostBean);

        void insertFavPostInDatabase(UserPostBean userPostBean, int position);

        void deleteFavpostFrmDatabase(UserPostBean userPostBean, int position);

        void requestFavPostFromDatabase();

        void rxUnsubscribe();

    }

}
