/*
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.R;
import com.example.app.model.UserInfo;
import com.example.app.viewmodel.AppViewModel;

import static com.example.app.utils.AppUtils.APP;

/**
 *  Represents App Greetings UI
 *
 */
public class GreetingsFragment extends Fragment {

    private static final String TAG = APP + GreetingsFragment.class.getSimpleName();

    public static final String FRAGMENT_TAG = "GreetingsFragment";
    public static final String TITLE_NAME_ARG = "title name arg";

    private Button enterInfo;
    private TextView userGreetings;
    private AppViewModel appViewModel;

    private UserActionListener userActionListener; // Callback to the MainActivity

    public GreetingsFragment(UserActionListener userActionListener) {
        super();
        this.userActionListener = userActionListener;
        Log.i(TAG, "GreetingsFragment() GreetingsFragment object is created");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.greetings_layout, container, false);

        Log.i(TAG, "onCreateView() View " + view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve arguments
        try {

            String titleString = requireArguments().getString(TITLE_NAME_ARG);

            TextView title = view.findViewById(R.id.main_title);
            title.setText(titleString);

            userGreetings = view.findViewById(R.id.greetings);

            Log.i(TAG, "onViewCreated() arguments retrieved");

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace();
            throw e; // Error is logged, so re-throw exception.
        }

        enterInfo = view.findViewById(R.id.add_info_btn);
        enterInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userActionListener.onEnterInfoClicked();
            }
        });

        Log.i(TAG, "onViewCreated()");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // Retrieve data from ViewModel and set user greetings text
        UserInfo userInfo = appViewModel.getUserInfo().getValue();
        if (userInfo != null && userInfo.getUserName() != null) {
            String userName = userInfo.getUserName().trim();
            userGreetings.setText(String.format(getString(R.string.user_greetings), userName));
        }

        Log.i(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Release reference to the activity
        userActionListener = null;

        Log.i(TAG, "onDetach()");
    }
}