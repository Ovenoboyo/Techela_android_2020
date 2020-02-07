package com.sitfest.techela;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.sitfest.techela.Auth.LoginActivity;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;

public class SplashActivity extends Activity {

    private Handler mHandler;
    private LicenseChecker mChecker;
    private LicenseCheckerCallback mLicenseCheckerCallback;
    boolean licensed;
    boolean checkingLicense;
    boolean didCheck;
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2vBqexHUH36i2+DZrLtt0Ut1quT6TFKC1qNeTefuTJ9AVRMzf5LbJifpIkr3WviHGnoHJ6uBrFWxrsHeZ+dcE2YSOczRdmsw1zefXgBpGymYPJ/X1BL28ed42NK9lI1qPRVpCTgkRafio2qWV5i+8ydsaruEQ0UwmJ7A0+D1ntw8f1nWz9+9kP1VA1d+24JN9w6qMemIt/ugoRzFTW5HDiYKYCEqe5JkE1o4GCEMu4T/6FzdqeDoUblVM2b5olenoDghAX+kia6elDyM8WBEqzybQSMK6M37AXNVraMVzMiwtyjUPweUfLn5q/QFVYFeyEIVsiP2vX/ZbNj0/AKTGQIDAQAB";
    private static final byte[] SALT = new byte[] {58, 24, 52, 34, 67, 32, 85, 51, 12, 36, 6, 7, 18, 50, 14, 67, 39, 71, 77, 80,};
    private static final int PERMISSION_REQUEST_CAMERA = 69;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        mHandler = new Handler();
        mLicenseCheckerCallback = new mLicenseCheckerCallback();
        mChecker = new LicenseChecker(this, new ServerManagedPolicy(this, new AESObfuscator(SALT, getPackageName(), deviceId)), BASE64_PUBLIC_KEY);

        doCheck();
        if (!licensed) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Camera Permission is required to run QR Scanner", Toast.LENGTH_LONG).show();
                } else {
                    // No explanation needed; request the permission

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

            mHandler =new Handler();
            mHandler.postDelayed(() -> {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            },2500);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length <= 0
                    || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finishAndRemoveTask();
            }
        }
    }

    private void doCheck() {

        didCheck = false;
        checkingLicense = true;
        setProgressBarIndeterminateVisibility(true);

        mChecker.checkAccess(mLicenseCheckerCallback);
    }


    private class mLicenseCheckerCallback implements LicenseCheckerCallback {

        @Override
        public void allow(int reason) {
            // TODO Auto-generated method stub
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            Log.i("License","Accepted!");


            licensed = true;
            checkingLicense = false;
            didCheck = true;

        }

        @SuppressWarnings("deprecation")
        @Override
        public void dontAllow(int reason) {
            // TODO Auto-generated method stub
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            Log.i("License","Denied!");
            Log.i("License","Reason for denial: "+reason);


            licensed = false;
            checkingLicense = false;
            didCheck = true;

            showDialog(0);

        }

        @SuppressWarnings("deprecation")
        @Override
        public void applicationError(int reason) {
            // TODO Auto-generated method stub
            Log.i("License", "Error: " + reason);
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }

            licensed = true;
            checkingLicense = false;
            didCheck = false;

            showDialog(0);
        }


    }

    protected Dialog onCreateDialog(int id) {
        // We have only one dialog.
        return new AlertDialog.Builder(this)
                .setTitle("Unlicensed Build")
                .setMessage("This application is not licensed, please get it from the play store.")
                .setPositiveButton("Get", (dialog, which) -> {
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                            "https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(marketIntent);
                    finish();
                })
                .setNegativeButton("Exit", (dialog, which) -> finish())
                .setNeutralButton("Re-Check", (dialog, which) -> doCheck())

                .setCancelable(false)
                .setOnKeyListener((dialogInterface, i, keyEvent) -> {
                    Log.i("License", "Key Listener");
                    finish();
                    return true;
                })
                .create();

    }
}