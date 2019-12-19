package com.assignment.postbook.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "USER_POST")
public class UserPostBean implements Parcelable {

    @NonNull
    @ColumnInfo(name = "user_id")
    @SerializedName("userId")
    private int userID;

    @NonNull
    @PrimaryKey()
    @SerializedName("id")
    private int post_id;

    @SerializedName("title")
    private String post_title;

    @SerializedName("body")
    private String post_desc;

    private boolean isMarkedFav;

    public UserPostBean(int userID, int post_id, String post_title, String post_desc, boolean isMarkedFav) {
        this.userID = userID;
        this.post_id = post_id;
        this.post_title = post_title;
        this.post_desc = post_desc;
        this.isMarkedFav = isMarkedFav;
    }

    protected UserPostBean(Parcel in) {
        userID = in.readInt();
        post_id = in.readInt();
        post_title = in.readString();
        post_desc = in.readString();
        isMarkedFav = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeInt(post_id);
        dest.writeString(post_title);
        dest.writeString(post_desc);
        dest.writeByte((byte) (isMarkedFav ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserPostBean> CREATOR = new Creator<UserPostBean>() {
        @Override
        public UserPostBean createFromParcel(Parcel in) {
            return new UserPostBean(in);
        }

        @Override
        public UserPostBean[] newArray(int size) {
            return new UserPostBean[size];
        }
    };

    public boolean isMarkedFav() {
        return isMarkedFav;
    }

    public void setMarkedFav(boolean markedFav) {
        isMarkedFav = markedFav;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_desc() {
        return post_desc;
    }

    public void setPost_desc(String post_desc) {
        this.post_desc = post_desc;
    }

}
