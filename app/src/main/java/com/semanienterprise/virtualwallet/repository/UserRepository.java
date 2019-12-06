package com.semanienterprise.virtualwallet.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.semanienterprise.virtualwallet.local.db.UserDao;
import com.semanienterprise.virtualwallet.local.db.UserDatabase;
import com.semanienterprise.virtualwallet.local.entities.User;

/**
 * Created by edetebenezer on 05/12/2019
 **/
public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        UserDatabase db = UserDatabase.getInstance(application);
        userDao = db.getUserDao();
    }

    public User getUserWithUsername(String username) {
        return userDao.getSingleUserByName(username);
    }

    public LiveData<User> getUserWithUsernameLiveData(String username) {
        return userDao.getSingleUserByNameLiveData(username);
    }

    public int updateUserAccountBalance(String username, double topUpAmount) {
        return userDao.updateUserBalance(username, topUpAmount);
    }
}
