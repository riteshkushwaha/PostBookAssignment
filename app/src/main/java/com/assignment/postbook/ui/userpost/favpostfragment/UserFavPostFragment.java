package com.assignment.postbook.ui.userpost.favpostfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserPostBean;
import com.assignment.postbook.ui.usercomment.UserCommentActivity;
import com.assignment.postbook.ui.userpost.FavItemChangeListener;
import com.assignment.postbook.ui.userpost.allpostfragment.UserPostFragAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.assignment.postbook.util.AppUtils.USER_POST_DATA;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link UserFavPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFavPostFragment extends Fragment implements UserFavPostContract.favPostView, UserPostFragAdapter.OnItemClickInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public static final String TAG = "FavPost";
    private ProgressBar mProgressBar;
    private RecyclerView mFavPostRecyclerView;
    private TextView mNoPostAvailable;
    private UserFavPresenter userFavPresenter;
    private UserPostFragAdapter mUserPostFragAdapter;
    private ArrayList<UserPostBean> mUserPostBeanList =
            new ArrayList<>();


    public UserFavPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment UserFavPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFavPostFragment newInstance(String param1) {
        UserFavPostFragment fragment = new UserFavPostFragment();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FavItemChangeListener) {
            favItemChangeListener = (FavItemChangeListener) context;
        }
    }

    private FavItemChangeListener favItemChangeListener;
    private View viewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (viewGroup == null) {
            viewGroup = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_user_fav_post, container, false);

            mProgressBar = (ProgressBar) viewGroup.findViewById(R.id.fav_post_progressbar);
            Toolbar toolbar = getActivity().findViewById(R.id.post_activity_toolbar);
            toolbar.setTitle(getString(R.string.fav_post_title));

            mNoPostAvailable = (TextView) viewGroup.findViewById(R.id.nodata_found_tv);

            setUpRecyclerView(viewGroup);

            userFavPresenter = new UserFavPresenter(this, getActivity());
            userFavPresenter.requestFavPostFromDatabase();
        }

        return viewGroup;
    }

    private void setUpRecyclerView(View viewGroup) {

        mFavPostRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.fav_post_recyclerview);
        mFavPostRecyclerView.setHasFixedSize(true);
        mFavPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFavPostRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mUserPostFragAdapter = new UserPostFragAdapter(mUserPostBeanList);
        mUserPostFragAdapter.setOnItemClickInterface(this);
        mFavPostRecyclerView.setAdapter(mUserPostFragAdapter);

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
    public void setDataToRecyclerView(List<UserPostBean> userPostBeanList) {
        mNoPostAvailable.setVisibility(View.INVISIBLE);
        mFavPostRecyclerView.setVisibility(View.VISIBLE);

        mUserPostBeanList.addAll(userPostBeanList);
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayFailureMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void noDataAvailable() {
        mFavPostRecyclerView.setVisibility(View.INVISIBLE);
        mNoPostAvailable.setVisibility(View.VISIBLE);
    }

    @Override
    public void postAddedAsFavSuccessfully(int position) {
        //mark flag true in list and change bgcolor
        mUserPostBeanList.get(position).setMarkedFav(true);
        mUserPostFragAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFavpostSuccessfully(int position) {
        favItemChangeListener.onFavItemChangeListener(mUserPostBeanList.get(position));
        if (mUserPostBeanList.get(position) != null) {
            mUserPostBeanList.remove(position);
            mUserPostFragAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void notifyFragment(UserPostBean userPostBean) {
        onUserPostItemClicked(userPostBean);
    }

    @Override
    public void onUserPostItemClicked(UserPostBean userPostBean) {
        navigateToCommentPage(userPostBean);
    }

    @Override
    public void markPostAsFav(int position) {
        if (mUserPostBeanList.get(position).isMarkedFav()) {
            userFavPresenter.deleteFavpostFrmDatabase(mUserPostBeanList.get(position), position);
        }
    }

    private void navigateToCommentPage(UserPostBean userPostBean) {

        Intent commentPageIntent = new Intent(getActivity(), UserCommentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_POST_DATA, userPostBean);
        commentPageIntent.putExtras(bundle);
        startActivity(commentPageIntent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userFavPresenter.rxUnsubscribe();
    }
}
