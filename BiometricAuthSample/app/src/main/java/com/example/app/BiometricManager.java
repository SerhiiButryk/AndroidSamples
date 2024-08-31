package com.example.app;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

import javax.crypto.Cipher;

public class BiometricManager extends BiometricPrompt.AuthenticationCallback {

    /**
     *  Examples are taken from https://developer.android.com/training/sign-in/biometric-auth
     */

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private AuthenticationListener authenticationListener;

    public void setAuthenticationListener(AuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    public void initBiometric(FragmentActivity fragmentActivity) {

        if (biometricPrompt != null)
            return;

        Executor executor = ContextCompat.getMainExecutor(fragmentActivity);

        biometricPrompt = new BiometricPrompt(fragmentActivity,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authenticationListener.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authenticationListener.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                authenticationListener.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
    }

    public void authenticate(Cipher cipher) {
        Log.i(MainActivity.TAG, "authenticate() with cipher " + cipher);

        biometricPrompt.authenticate(promptInfo, new BiometricPrompt.CryptoObject(cipher));
    }

    public interface AuthenticationListener {
        void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result);
        void onAuthenticationFailed();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        authenticationListener.onAuthenticationSucceeded(result);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
