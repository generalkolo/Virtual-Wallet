package com.semanienterprise.virtualwallet.presentation;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.semanienterprise.virtualwallet.local.entities.User;
import com.semanienterprise.virtualwallet.repository.UserRepository;

import java.util.Objects;

/**
 * Created by edetebenezer on 05/12/2019
 **/
public class MainActivityViewModel extends AndroidViewModel {

    private UserRepository mRepository;
    private User loggedInUser;
    private LiveData<User> loggedInUserLiveData;

    public MainActivityViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

    private String toastMessage;

    public String getToastMessage() {
        return toastMessage;
    }

    public LiveData<User> getLoggedInUser() {
        return loggedInUserLiveData;
    }

    private MutableLiveData<Boolean> navigateToFundingFragment;

    public MutableLiveData<Boolean> getNavigateToFundingFragment() {
        if (navigateToFundingFragment == null) {
            navigateToFundingFragment = new MutableLiveData<>();
        }
        return navigateToFundingFragment;
    }

    private MutableLiveData<Boolean> showToast;

    public MutableLiveData<Boolean> getShowToast() {
        if (showToast == null) {
            showToast = new MutableLiveData<>();
        }
        return showToast;
    }

    public void toastShown() {
        showToast.postValue(false);
    }

    public void navigatedToFundingFragment() {
        navigateToFundingFragment.postValue(false);
    }

    public void isUsernameAndPasswordValid(String username, String password) {
        // validate username and password
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            toastMessage = "Please ensure that all fields are filled..";
            showToast.postValue(true);
        } else {
            loggedInUser = mRepository.getUserWithUsername(username);
            if (loggedInUser != null) {
                if (!password.equals(loggedInUser.password)) {
                    toastMessage = "Wrong Password Entered";
                    showToast.postValue(true);
                } else {
                    navigateToFundingFragment.postValue(true);
                    loggedInUserLiveData = mRepository.getUserWithUsernameLiveData(loggedInUser.userName);
                }
            } else {
                toastMessage = "User with username \'" + username + "\' does not exist";
                showToast.postValue(true);
            }
        }
    }

    public void topUpUserAccount(String topUpAmount) {
        if (TextUtils.isEmpty(topUpAmount)) {
            toastMessage = "Please ensure you entered a valid  number";
            showToast.postValue(true);
            return;
        }
        double doubleTopUpAmount = Double.parseDouble(topUpAmount);
        if (doubleTopUpAmount < 100) {
            toastMessage = "Top up amount cannot be less than N100..";
            showToast.postValue(true);
        } else {
            final double userBalance = loggedInUser.accountBalance + doubleTopUpAmount;
            final String username = loggedInUser.userName;
            mRepository.updateUserAccountBalance(username, userBalance);
            toastMessage = "User Account Balance Updated";
            showToast.postValue(true);
        }
    }
}
