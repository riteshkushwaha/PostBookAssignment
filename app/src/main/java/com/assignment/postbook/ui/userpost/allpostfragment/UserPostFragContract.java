package com.assignment.postbook.ui.userpost.allpostfragment;


import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

public interface UserPostFragContract {

    interface UserPostFragView {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<UserPostBean> userPostBeanArrayList);

        void onResponseFailure(String errorMsg);

        void showUserPostList();

        void hideUserPostList();

        void displayFailureMsg(String msg);

        void postAddedAsFavSuccessfully(int position);

        void deleteFavpostSuccessfully(int position);

    }

    interface UserPostFragPresenter {

        void deleteMarkedFavPost(UserPostBean userPostBean, int position);

        void insertFavPostInDatabase(UserPostBean userPostBean, int position);

        void requestDataFromServer(String userId);

        void  rxUnsubscribe();

    }

}
