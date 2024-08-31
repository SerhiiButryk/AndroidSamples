package com.example.app;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class KeystoreManager {

    /**
     *  Examples are taken from https://developer.android.com/training/sign-in/biometric-auth
     */

    private static String KEY_ALIAS = "MyKeyAlias";

    public void createKey() {
        Log.i(MainActivity.TAG, "createKey()");

        generateSecretKey(new KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                // Invalidate the keys if the user has registered a new biometric
                // credential, such as a new fingerprint. Can call this method only
                // on Android 7.0 (API level 24) or higher. The variable
                // "invalidatedByBiometricEnrollment" is true by default.
                .setInvalidatedByBiometricEnrollment(true)
                .build());

    }

    public void generateSecretKey(KeyGenParameterSpec keyGenParameterSpec) {

        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyGenerator.init(keyGenParameterSpec);
            keyGenerator.generateKey();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SecretKey getSecretKey() {

        KeyStore keyStore = null;

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            // Before the keystore can be accessed, it must be loaded.
            keyStore.load(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            return ((SecretKey)keyStore.getKey(KEY_ALIAS, null));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Cipher getCipher() {
        try {
            return Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteKeyStoreKey() {

        KeyStore keyStore = null;

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            // Before the keystore can be accessed, it must be loaded.
            keyStore.load(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String alias = KEY_ALIAS;
        boolean doDelete = true;
        try {
            doDelete = keyStore.containsAlias(alias);
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }

        if (doDelete) {
            try {
                Log.i(MainActivity.TAG, "deleteKeyStoreKey()");

                keyStore.deleteEntry(alias);

                return true;
            } catch (KeyStoreException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    public boolean hasKey() {
        KeyStore keyStore = null;

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            // Before the keystore can be accessed, it must be loaded.
            keyStore.load(null);

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        String alias = KEY_ALIAS;
        boolean hasKey = true;
        try {
            hasKey = keyStore.containsAlias(alias);
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        }

        return hasKey;
    }

}
