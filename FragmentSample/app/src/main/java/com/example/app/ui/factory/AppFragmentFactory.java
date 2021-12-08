/*
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.ui.factory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.example.app.model.UserInfo;
import com.example.app.ui.EnterInfoFragment;
import com.example.app.ui.GreetingsFragment;
import com.example.app.ui.UserActionListener;

/**
 * Demonstrate the usage of FragmentFactory factory
 */

public class AppFragmentFactory extends FragmentFactory {

    private UserInfo userInfo;
    private UserActionListener userActionListener;

    public AppFragmentFactory(UserInfo userInfo, UserActionListener userActionListener) {
        this.userInfo = userInfo;
        this.userActionListener = userActionListener;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);

        if (fragmentClass == EnterInfoFragment.class) {
            return new EnterInfoFragment(userInfo);
        } else if (fragmentClass == GreetingsFragment.class) {
            return new GreetingsFragment(userActionListener);
        } else {
            return super.instantiate(classLoader, className);
        }

    }
}
