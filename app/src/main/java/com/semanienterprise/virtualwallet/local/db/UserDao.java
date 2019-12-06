package com.semanienterprise.virtualwallet.local.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.semanienterprise.virtualwallet.local.entities.User;

import java.util.List;

import io.reactivex.Flowable;

import static com.semanienterprise.virtualwallet.local.utils.Constants.TABLE_NAME_NOTE;

/**
 * Created by edetebenezer on 05/12/2019
 **/
@Dao
public interface UserDao {
    @Query("SELECT * FROM " + TABLE_NAME_NOTE + " where userName LIKE  :username")
    User getSingleUserByName(String username);

    @Query("SELECT * FROM " + TABLE_NAME_NOTE + " where userName LIKE  :username")
    LiveData<User> getSingleUserByNameLiveData(String username);

    @Query("UPDATE " + TABLE_NAME_NOTE + " SET accountBalance = :topUpAmount where userName LIKE  :username")
    int updateUserBalance(String username, double topUpAmount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);
}
