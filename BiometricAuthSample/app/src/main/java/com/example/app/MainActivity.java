package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.security.InvalidKeyException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "CryptoSampleApp";

    private BiometricManager biometricManager;
    private KeystoreManager cryptoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cryptoManager = new KeystoreManager();
        cryptoManager.deleteKeyStoreKey();
        cryptoManager.createKey();

        biometricManager = new BiometricManager();
        biometricManager.initBiometric(this);
        biometricManager.setAuthenticationListener(new BiometricManager.AuthenticationListener() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {

                Cipher cipher = result.getCryptoObject().getCipher();

                /*
                   Perform encryption with retrieved cipher object
                */
                byte[] plaintext = new byte[]{0};

                try {

                    byte[] ciphertext = cipher.doFinal(plaintext);
                    byte[] salt = cipher.getIV();

                    Toast.makeText(MainActivity.this, "Encryption is succeed", Toast.LENGTH_LONG).show();

                } catch (IllegalBlockSizeException e) {
                    Log.i(MainActivity.TAG, "exception: " + e);
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this, "Encryption is failed with IllegalBlockSizeException exception", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.i(MainActivity.TAG, "exception: " + e);
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this, "Encryption is failed with unknown exception", Toast.LENGTH_LONG).show();
                }

                cryptoManager.deleteKeyStoreKey();
            }

            @Override
            public void onAuthenticationFailed() {

                cryptoManager.deleteKeyStoreKey();
            }
        });

        findViewById(R.id.show_biometric).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!cryptoManager.hasKey()) {
                    cryptoManager.createKey();
                }

                /*
                    Create symmetric key
                 */
                Cipher cipher = cryptoManager.getCipher();

                SecretKey secretKey = cryptoManager.getSecretKey();

                try {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                    return;
                }

                biometricManager.authenticate(cipher);
            }
        });
    }
}