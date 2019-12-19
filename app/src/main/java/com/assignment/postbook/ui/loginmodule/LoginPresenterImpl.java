package com.assignment.postbook.ui.loginmodule;

import android.content.Context;
import android.text.TextUtils;


import com.assignment.postbook.R;
import com.assignment.postbook.data.DataCallbackImp;
import com.assignment.postbook.ui.loginmodule.LoginContract.Presenter;
import com.assignment.postbook.ui.loginmodule.LoginContract.View;
import com.assignment.postbook.util.AppUtils;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl implements Presenter {

    private LoginContract.View mLoginView;
    private Context mContext;

    public LoginPresenterImpl(View mLoginView, Context context) {
        this.mLoginView = mLoginView;
        this.mContext = context;
    }

    @Override
    public void checkUserIDValidation(String userId) {
        if (TextUtils.isEmpty(userId)) {
            mLoginView.showErrorMessage(mContext.getString(R.string.enter_userid));
        } else {
            mLoginView.navigationToHomePage();
            mLoginView.setLogOutOption();
        }
    }

    @Override
    public void checkNetworkStatus() {
        if (!new AppUtils().isInternetAvailable(mContext)) {
            mLoginView.netWorkNotFound(mContext.getString(R.string.internet_not_availabe));
        } else {
            mLoginView.proceedForLogin();
        }
    }

    @Override
    public void userLogout() {
        //delete table data for current user
        final DataCallbackImp dataCallbackImp = new DataCallbackImp(mContext);
        Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                dataCallbackImp.deleteAllData();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mLoginView.setLoginOption();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoginView.showErrorMessage(mContext.getString(R.string.unable_tologout));
                    }
                });
    }


}
