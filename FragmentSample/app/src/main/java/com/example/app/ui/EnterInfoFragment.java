/*
 * Copyright 2021. Happy codding ! :)
 * Author: Serhii Butryk
 */
package com.example.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app.R;
import com.example.app.model.UserInfo;
import com.example.app.viewmodel.AppViewModel;

import static com.example.app.utils.AppUtils.APP;

/**
 *  App's EnterInfo UI Fragment
 *
 *  1. Controls and saves user input
 *  2. Adds Back press listener
 */
public class EnterInfoFragment extends Fragment {

    private static final String TAG = APP + EnterInfoFragment.class.getSimpleName();

    public static final String FRAG_TAG = "EnterInfoFragment";

    private AppViewModel appViewModel;

    private EditText inputField;
    private SimpleTextWatcher textWatcher;
    private Button ok;

    // To send back press action
    private OnBackPressedDispatcher backPressedDispatcher;

    /**
     * Back pressed listener for displaying a confirm dialog
     */
    private final OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setMessage(R.string.enter_info_fragment_dialog_confirm_message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            setEnabled(false);
                            // Go back to the previous UI
                            if (backPressedDispatcher != null) {
                                backPressedDispatcher.onBackPressed();
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Do nothing
                        }
                    });
            // Show dialog
            builder.create().show();

        }
    };

    /**
     * userInfo - not needed just for demonstration purpose
     */
    public EnterInfoFragment(UserInfo userInfo) {
        super();
        Log.i(TAG, "EnterInfoFragment() object is created");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Register back pressed listener
        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
        // Get back press dispatcher
        backPressedDispatcher = requireActivity().getOnBackPressedDispatcher();

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

        View view = inflater.inflate(R.layout.enter_info_layout, container, false);

        Log.i(TAG, "onCreateView() View " + view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputField = view.findViewById(R.id.input_txt_field);

        ok = view.findViewById(R.id.ok_btn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If User has provided input then set finished flag to true
                if (isInfoProvided()) {
                    appViewModel.onUserInfoProvided();
                } else {
                    Toast.makeText(EnterInfoFragment.this.requireActivity(),
                            getString(R.string.enter_name_info), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Log.i(TAG, "onViewCreated()");
    }

    private boolean isInfoProvided() {
        return textWatcher.isInfoSet;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i(TAG, "onViewStateRestored()");
    }

    // TODO: Remove deprecated calls
    // Looks like that this can be replaced
    // with - https://developer.android.com/reference/android/app/Application#registerActivityLifecycleCallbacks(android.app.Application.ActivityLifecycleCallbacks)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get ViewModel reference and init objects
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        Log.i(TAG, "onActivityCreated: got ViewModel: " + appViewModel);

        textWatcher = new SimpleTextWatcher(appViewModel);
        inputField.addTextChangedListener(textWatcher);

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
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
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
        Log.i(TAG, "onDetach()");
    }

    /**
     * Watcher for EditTextField (User input)
     */
    private static class SimpleTextWatcher implements TextWatcher {

        private AppViewModel viewModel;
        private UserInfo userInfo = new UserInfo();
        private boolean isInfoSet;

        public SimpleTextWatcher(AppViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.i(TAG, "SimpleTextWatcher-onTextChanged:" + charSequence);

            if (charSequence.length() != 0) {
                userInfo.setUserName(charSequence.toString());

                // Notify data change
                viewModel.setUserInfo(userInfo);

                // Set flag to true as User has provided input
                isInfoSet = true;
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

    }

}
