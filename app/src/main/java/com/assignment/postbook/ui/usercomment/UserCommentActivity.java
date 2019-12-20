package com.assignment.postbook.ui.usercomment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserCommentBean;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.postbook.util.AppUtils.USER_POST_DATA;


public class UserCommentActivity extends AppCompatActivity implements CommentPageContract.CommentView, OnClickListener {

    private CommentPagePresenter commentPagePresenter;
    private RecyclerView mPostCommentRecyclerView;
    private UserPostBean mUserPostBean;
    private ProgressBar mProgressBar;
    private ArrayList<UserCommentBean> mCommentList =
            new ArrayList<>();
    private CommentListAdapter mCommentListAdapter;

    private TextView noDataAvailable;
    private TextView mUserPostTv;
    private TextView mUserBodyTv;
    private Button mFavPostBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post_detail);

        Toolbar toolbar = findViewById(R.id.comment_page_toolbar);
        toolbar.setTitle(getString(R.string.user_comment_title));

        mUserBodyTv = (TextView) findViewById(R.id.post_body_textview);
        mUserPostTv = (TextView) findViewById(R.id.user_post_titletv);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_loader);
        mFavPostBtn = (Button) findViewById(R.id.fav_post_btn);
        noDataAvailable = (TextView) findViewById(R.id.no_data_present_tv);
        mFavPostBtn.setOnClickListener(this);

        if (getIntent() != null) {
            mUserPostBean = getIntent().getParcelableExtra(USER_POST_DATA);
            updateUserPostData(mUserPostBean);
        }

        setUpRecyclerView();

        commentPagePresenter = new CommentPagePresenter(this, UserCommentActivity.this);
        commentPagePresenter.requestUserCommentData(mUserPostBean.getPost_id());

    }

    private void setUpRecyclerView() {

        mPostCommentRecyclerView = (RecyclerView) findViewById(R.id.comment_list_recyclerview);
        mPostCommentRecyclerView.setHasFixedSize(true);
        mPostCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCommentListAdapter = new CommentListAdapter(mCommentList);
        mPostCommentRecyclerView.setAdapter(mCommentListAdapter);

    }


    public void updateUserPostData(UserPostBean userPostBean) {
        mUserPostTv.setText(userPostBean.getPost_title());
        mUserBodyTv.setText(userPostBean.getPost_desc());
        if (userPostBean.isMarkedFav()) {
            //set button background
            mFavPostBtn.setTextColor(getResources().getColor(R.color.colorWhite));
            mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        } else {
            mFavPostBtn.setTextColor(getResources().getColor(R.color.colorAccent));
            mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    public void noDataAvailable() {
        mPostCommentRecyclerView.setVisibility(View.INVISIBLE);
        noDataAvailable.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(List<UserCommentBean> userCommentBeanList) {
        noDataAvailable.setVisibility(View.INVISIBLE);
        mPostCommentRecyclerView.setVisibility(View.VISIBLE);

        mCommentList.addAll(userCommentBeanList);
        mCommentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void favPostRemoved() {
        mUserPostBean.setMarkedFav(false);
        mFavPostBtn.setTextColor(getResources().getColor(R.color.colorAccent));
        mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorWhite));

    }

    @Override
    public void favPostAdded() {
        mUserPostBean.setMarkedFav(true);
        mFavPostBtn.setTextColor(getResources().getColor(R.color.colorWhite));
        mFavPostBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }

    @Override
    public void dbQueryFailed(String error) {
        Toast.makeText(UserCommentActivity.this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        if (mUserPostBean.isMarkedFav()) {
            commentPagePresenter.removeFavPost(mUserPostBean);
        } else {
            commentPagePresenter.addedFavPost(mUserPostBean);
        }
    }

}
