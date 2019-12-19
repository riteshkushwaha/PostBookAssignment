package com.assignment.postbook.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.assignment.postbook.data.model.UserPostBean;


@Database(entities = {UserPostBean.class}, version = 1, exportSchema = false)
public abstract class UserFavPostDatabase extends RoomDatabase {

    public abstract UserPostDAO getUserPostDAO();

    private static UserFavPostDatabase sDB_INSTANCE;

    private static final Object sLock = new Object();

    public static UserFavPostDatabase getDBInstance(Context context) {
        synchronized (sLock) {
            if (sDB_INSTANCE == null) {
                sDB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UserFavPostDatabase.class, "user_post.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return sDB_INSTANCE;
        }
    }

}
