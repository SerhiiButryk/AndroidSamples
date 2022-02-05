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
 *  3. Restores UI data on configuration changes or process death
 */

public class AppViewModel extends ViewModel {

    // Hold enter information by User
    private final MutableLiveData<UserInfo> userInfoData = new MutableLiveData<>();
    // Used to trigger specific logic in response to User's button click
    private final MutableLiveData<Boolean> userInfoProvidedFlag = new MutableLiveData<>();
    private final MutableLiveData<Boolean> openEnterInfoUIFlag = new MutableLiveData<>();

    // Handler for restoring UI data
    // Use to save simple key/value data
    private SavedStateHandle stateHandle;

    public AppViewModel(SavedStateHandle stateHandle) {
        this.stateHandle = stateHandle;

        // Set initial values
        userInfoProvidedFlag.setValue(false);
        openEnterInfoUIFlag.setValue(false);

        // Restore UI data
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

    public LiveData<UserInfo> getUserInfoLiveData() {
        return userInfoData;
    }

    public void setUserInfo(UserInfo userInfo) {
        userInfoData.setValue(userInfo);
    }

    public LiveData<Boolean> getUserInfoProvidedLiveData() {
        return userInfoProvidedFlag;
    }

    public void onUserInfoProvided() {
        // As a consequence, after this call all subscribed observers will get notification.
        userInfoProvidedFlag.setValue(true);
        // Reset to previous state
        openEnterInfoUIFlag.setValue(false);
    }

    public void openEnterInfoUI() {
        // As a consequence, after this call all subscribed observers will get notification.
        openEnterInfoUIFlag.setValue(true);
        // Reset to previous state
        openEnterInfoUIFlag.setValue(false);
    }

    public LiveData<Boolean> getOpenEnterInfoUILiveData() {
        return openEnterInfoUIFlag;
    }

}
