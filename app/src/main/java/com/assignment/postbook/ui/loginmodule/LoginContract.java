package com.assignment.postbook.ui.loginmodule;

public interface LoginContract {

    interface View {

        void navigationToHomePage();

        void displayValidationErrorMsg(String msg);

        void showErrorMessage(String errorMsg);

        void netWorkNotFound(String message);

        void proceedForLogin();

        void setLogOutOption();

        void setLoginOption();

        String getUserId();

        void showUserIdError(int resId);

    }

    interface Presenter {

        void checkUserIDValidation();

        void checkNetworkStatus();

        void userLogout();

    }

}
