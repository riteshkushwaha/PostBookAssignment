package com.assignment.postbook.ui.userpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserPostBean;
import com.assignment.postbook.ui.userpost.allpostfragment.UserAllPostFragment;
import com.assignment.postbook.ui.userpost.favpostfragment.UserFavPostFragment;

import java.util.List;

import static com.assignment.postbook.util.AppUtils.USER_ALLPOST_FRAGMENT;
import static com.assignment.postbook.util.AppUtils.USER_FAVPOST_FRAGMENT;
import static com.assignment.postbook.util.AppUtils.USER_ID;


public class UserPostActivity extends AppCompatActivity implements OnClickListener, FavItemChangeListener {

    private String mUserID;
    private Button mAllPostBtn;
    private Button mFavPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        mAllPostBtn = (Button) findViewById(R.id.all_post_btn);
        mFavPostBtn = (Button) findViewById(R.id.fav_post_btn);

        mAllPostBtn.setOnClickListener(this);
        mFavPostBtn.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            mUserID = intent.getStringExtra(USER_ID);
        }

        UserAllPostFragment userAllPostFragment =
                (UserAllPostFragment) getSupportFragmentManager().findFragmentByTag(USER_ALLPOST_FRAGMENT);
        if (userAllPostFragment == null) {
            userAllPostFragment = UserAllPostFragment.newInstance(mUserID);
        }
        addFragmentToActivity(getSupportFragmentManager(), userAllPostFragment, R.id.fragment_container);

    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, USER_ALLPOST_FRAGMENT);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId, String fragmentTag,
                                                 int animEnter, int animExit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(animEnter, animExit);
        transaction.replace(frameId, fragment, fragmentTag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_post_btn:

                enableAllPostButton();

                UserAllPostFragment userAllPostFragment =
                        (UserAllPostFragment) getSupportFragmentManager().findFragmentByTag(USER_ALLPOST_FRAGMENT);
                if (userAllPostFragment == null) {
                    userAllPostFragment = UserAllPostFragment.newInstance(mUserID);
                }
                replaceFragmentToActivity(getSupportFragmentManager(), userAllPostFragment, R.id.fragment_container,
                        USER_ALLPOST_FRAGMENT, R.anim.slide_from_left, R.anim.slide_out_from_right);

                break;

            case R.id.fav_post_btn:

                enableFavPostButton();

                UserFavPostFragment userFavPostFragment =
                        (UserFavPostFragment) getSupportFragmentManager().findFragmentByTag(USER_FAVPOST_FRAGMENT);
                if (userFavPostFragment == null) {
                    userFavPostFragment = UserFavPostFragment.newInstance(null);
                }
                replaceFragmentToActivity(getSupportFragmentManager(), userFavPostFragment, R.id.fragment_container,
                        USER_FAVPOST_FRAGMENT, R.anim.slide_from_right, R.anim.slide_out_from_left);

                break;
        }
    }

    /**
     * enable and change button background
     */
    private void enableFavPostButton() {

        mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mFavPostBtn.setTextColor(getResources().getColor(R.color.colorWhite));

        mAllPostBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mAllPostBtn.setTextColor(getResources().getColor(R.color.colorAccent));

    }

    /**
     * enable and change button background
     */
    private void enableAllPostButton() {

        mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mFavPostBtn.setTextColor(getResources().getColor(R.color.colorAccent));

        mAllPostBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mAllPostBtn.setTextColor(getResources().getColor(R.color.colorWhite));

    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof UserAllPostFragment) {
                enableAllPostButton();
            } else if (fragment instanceof UserFavPostFragment) {
                enableFavPostButton();
            }
        }
    }

    @Override
    public void onFavItemChangeListener(UserPostBean userPostBean) {
        UserAllPostFragment userAllPostFragment =
                (UserAllPostFragment) getSupportFragmentManager().findFragmentByTag(USER_ALLPOST_FRAGMENT);
        userAllPostFragment.refreshUserPostDataList(userPostBean);
    }

}
