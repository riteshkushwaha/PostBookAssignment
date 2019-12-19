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
    }

    interface Presenter {

        void checkUserIDValidation(String userId);

        void checkNetworkStatus();

        void userLogout();

    }

}
