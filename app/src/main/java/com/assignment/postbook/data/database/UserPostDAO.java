package com.assignment.postbook.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.assignment.postbook.data.model.UserPostBean;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface UserPostDAO {

    /**
     * Select all user post from the USER_POST table.
     *
     * @return all user post data
     */
    @Query("select * from USER_POST")
    Observable<List<UserPostBean>> getUserPostData();

    //public List<UserPostBean> getUserPostData();

    /**
     * @param userPostBean user post item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserFavPost(UserPostBean... userPostBean);

    /**
     * @param userPostBeans user post item
     */
    @Delete
    void deleteUserFavPost(UserPostBean... userPostBeans);

    /**
     * Delete a user post by id.
     *
     * @return the number of post deleted. This should always be 1.
     */
    @Query("DELETE FROM USER_POST WHERE post_id = :postId")
    int deleteUserPostById(String postId);


}
