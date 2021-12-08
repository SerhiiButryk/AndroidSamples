/**
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.app.model.UserInfo;

/**
 * App's View Model class
 *
 *  1. Encapsulates UI data
 *  2. Handles UI data logic
 *  3. Restore UI data on configuration changes or process death
 */

public class AppViewModel extends ViewModel {

    // LiveData objects
    private MutableLiveData<UserInfo> userInfoLiveData;
    // Used to trigger specific logic in response to User's button click
    private MutableLiveData<Boolean> actionFinishFlag;

    // Handler for restoring UI data
    // Use to save simple key/value data
    private SavedStateHandle stateHandle;

    public AppViewModel(SavedStateHandle stateHandle) {

        userInfoLiveData = new MutableLiveData<>();

        actionFinishFlag = new MutableLiveData<>();
        actionFinishFlag.setValue(false);

        this.stateHandle = stateHandle;
        restoreData();
    }

    /**
     * Perform UI Data restore logic
     */
    private void restoreData() {
        // Work with stateHandler here
        // ...
        // ...
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfoLiveData;
    }

    public void setUserInfo(UserInfo userInfo) {
        userInfoLiveData.setValue(userInfo);
    }

    public LiveData<Boolean> getActionFinishFlag() {
        return actionFinishFlag;
    }

    public void setActionFinishFlag() {
        actionFinishFlag.setValue(true);
    }

    public void resetActionFinishFlag() {
        actionFinishFlag.setValue(false);
    }

}
