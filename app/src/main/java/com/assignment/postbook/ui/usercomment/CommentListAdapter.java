package com.assignment.postbook.ui.usercomment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.assignment.postbook.R;
import com.assignment.postbook.data.model.UserCommentBean;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    private ArrayList<UserCommentBean> commentBeanArrayList;

    public CommentListAdapter(ArrayList<UserCommentBean> commentBeanArrayList) {
        this.commentBeanArrayList = commentBeanArrayList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_comment_item_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        ((CommentViewHolder) holder).updateCommentData(commentBeanArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentBeanArrayList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserCommentNameTv;
        private TextView mUserCommentBodyTv;
        private TextView mUserEmail;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserCommentNameTv = (TextView) itemView.findViewById(R.id.comment_nametv);
            mUserCommentBodyTv = (TextView) itemView.findViewById(R.id.comment_bodytv);
            mUserEmail = (TextView) itemView.findViewById(R.id.user_emailtv);
        }

        private void updateCommentData(UserCommentBean userCommentBean) {
            mUserEmail.setText(userCommentBean.getEmail());
            mUserCommentBodyTv.setText(userCommentBean.getPostDetail());
            mUserCommentNameTv.setText(userCommentBean.getName());
        }
    }
}
