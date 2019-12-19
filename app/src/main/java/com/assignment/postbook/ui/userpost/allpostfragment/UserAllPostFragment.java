package com.assignment.postbook.ui.userpost.allpostfragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserPostBean;
import com.assignment.postbook.ui.usercomment.UserCommentActivity;
import com.assignment.postbook.ui.userpost.UserPostActivity;
import com.assignment.postbook.ui.userpost.allpostfragment.UserPostFragAdapter.OnItemClickInterface;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.postbook.util.AppUtils.USER_POST_DATA;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAllPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAllPostFragment extends Fragment implements UserPostFragContract.UserPostFragView, OnItemClickInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private UserPostFragContract.UserPostFragPresenter userPostFragPresenter;
    private ProgressBar mProgressbar;
    private ArrayList<UserPostBean> mUserPostDataList = new ArrayList<>();
    private UserPostFragAdapter mUserPostFragAdapter;
    private TextView mNoPostAvailableTv;
    private RecyclerView mUserPostRecyclerView;

    private static final String TAG = UserPostActivity.class.getName();


    // TODO: Rename and change types of parameters
    private String mParam1;


    public UserAllPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UserAllPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAllPostFragment newInstance(String param1) {
        UserAllPostFragment fragment = new UserAllPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private View viewParent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (viewParent == null) {

            viewParent = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_user_all_post, container, false);

            mProgressbar = (ProgressBar) viewParent.findViewById(R.id.progress_loader);
            mNoPostAvailableTv = (TextView) viewParent.findViewById(R.id.no_post_textview);

            userPostFragPresenter = new UserPostFragPresenter(this, getActivity());
            userPostFragPresenter.requestDataFromServer(mParam1);

            // Set up the toolbar.
            Toolbar toolbar = getActivity().findViewById(R.id.post_activity_toolbar);
            toolbar.setTitle(getString(R.string.user_post_tite));

            setDataAdapter(viewParent);

        }

        return viewParent;
    }


    private void setDataAdapter(View view) {

        mUserPostRecyclerView = (RecyclerView) view.findViewById(R.id.user_post_recyclerview);

        mUserPostFragAdapter = new UserPostFragAdapter(mUserPostDataList);
        mUserPostFragAdapter.setOnItemClickInterface(this);
        mUserPostRecyclerView.setAdapter(mUserPostFragAdapter);

        mUserPostRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mUserPostRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mUserPostRecyclerView.setHasFixedSize(true);
        mUserPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void showProgress() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(List<UserPostBean> userPostBeanArrayList) {
        mUserPostDataList.addAll(userPostBeanArrayList);
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showUserPostList() {
        mNoPostAvailableTv.setVisibility(View.INVISIBLE);
        mUserPostRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUserPostList() {
        mNoPostAvailableTv.setVisibility(View.VISIBLE);
        mUserPostRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displayFailureMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void postAddedAsFavSuccessfully(int position) {
        mUserPostDataList.get(position).setMarkedFav(true);
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFavpostSuccessfully(int position) {
        mUserPostDataList.get(position).setMarkedFav(false);
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void onUserPostItemClicked(UserPostBean userPostBean) {
        navigateToCommentPage(userPostBean);
    }

    @Override
    public void markPostAsFav(int position) {
        if (mUserPostDataList.get(position).isMarkedFav()) {
            //already marked , unmarked
            userPostFragPresenter.deleteMarkedFavPost(mUserPostDataList.get(position), position);
        } else {
            //unmark
            userPostFragPresenter.insertFavPostInDatabase(mUserPostDataList.get(position), position);
        }
    }

    private void navigateToCommentPage(UserPostBean userPostBean) {

        Intent commentPageIntent = new Intent(getActivity(), UserCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_POST_DATA, userPostBean);
        commentPageIntent.putExtras(bundle);
        startActivity(commentPageIntent);

    }

    public void refreshUserPostDataList(UserPostBean userPostBean) {
        for (UserPostBean userPost : mUserPostDataList) {
            if (userPost.getPost_id() == userPostBean.getPost_id()) {
                userPost.setMarkedFav(false);
            }
        }
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userPostFragPresenter.rxUnsubscribe();
    }


}
