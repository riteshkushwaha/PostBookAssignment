package com.assignment.postbook.ui.userpost.allpostfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.ArrayList;

public class UserPostFragAdapter extends RecyclerView.Adapter<UserPostFragAdapter.UserPostViewHolder> {

    private ArrayList<UserPostBean> mUserPostDataList;
    private OnItemClickInterface onItemClickInterface;

    public UserPostFragAdapter(ArrayList<UserPostBean> mUserPostDataList) {
        this.mUserPostDataList = mUserPostDataList;
    }

    @NonNull
    @Override
    public UserPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post_item_layout, parent, false);
        return new UserPostViewHolder(view, onItemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostViewHolder holder, int position) {
        ((UserPostViewHolder) holder).populateDataValue(mUserPostDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserPostDataList == null ? 0 : mUserPostDataList.size();
    }

    public void setOnItemClickInterface(OnItemClickInterface onItemClickInterface) {
        this.onItemClickInterface = onItemClickInterface;
    }

    public class UserPostViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private TextView mUserPostTitleTv;
        private TextView mUserPostBodyTv;
        private Button mUserFavPostBtn;
        private OnItemClickInterface onItemClickInterface;

        public UserPostViewHolder(@NonNull View itemView, final OnItemClickInterface onItemClickInterface) {
            super(itemView);

            this.onItemClickInterface = onItemClickInterface;
            mUserPostTitleTv = (TextView) itemView.findViewById(R.id.user_post_title_tv);
            mUserPostBodyTv = (TextView) itemView.findViewById(R.id.user_post_body_tv);
            mUserFavPostBtn = (Button) itemView.findViewById(R.id.fav_item_btn);
            mUserFavPostBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickInterface.markPostAsFav(getAdapterPosition());
                }
            });
            itemView.setOnClickListener(this);
        }

        private void populateDataValue(UserPostBean userPostBean) {
            mUserPostTitleTv.setText(userPostBean.getPost_title());
            mUserPostBodyTv.setText(userPostBean.getPost_desc());
            if (userPostBean.isMarkedFav()) {
                mUserFavPostBtn.setBackgroundColor(itemView.getResources().getColor(R.color.colorAccent));
                mUserFavPostBtn.setTextColor(itemView.getResources().getColor(R.color.colorWhite));
            } else {
                mUserFavPostBtn.setBackgroundColor(itemView.getResources().getColor(R.color.colorWhite));
                mUserFavPostBtn.setTextColor(itemView.getResources().getColor(R.color.colorAccent));
            }
        }

        @Override
        public void onClick(View view) {
            onItemClickInterface.onUserPostItemClicked(mUserPostDataList.get(getAdapterPosition()));
        }
    }

    public interface OnItemClickInterface {
        void onUserPostItemClicked(UserPostBean userPostBean);

        void markPostAsFav(int position);
    }

}
