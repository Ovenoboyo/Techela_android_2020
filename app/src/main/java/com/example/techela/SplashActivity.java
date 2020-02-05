package com.example.techela;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.techela.Auth.LoginActivity;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

public class SplashActivity extends Activity {

    Handler mHandler;
    private LicenseChecker mChecker;
    private LicenseCheckerCallback mLicenseCheckerCallback;
    boolean licensed;
    boolean checkingLicense;
    boolean didCheck;
    private static final String BASE64_PUBLIC_KEY = "YOUR LICENSE KEY FOR THIS APPLICATION";
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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Storage Permission is required to install updates", Toast.LENGTH_LONG).show();
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2500);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
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

            //You can do other things here, like saving the licensed status to a
            //SharedPreference so the app only has to check the license once.

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

            //You can do other things here, like saving the licensed status to a
            //SharedPreference so the app only has to check the license once.

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
                .setPositiveButton("Get", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                                "https://play.google.com/store/apps/details?id=" + getPackageName()));
                        startActivity(marketIntent);
                        finish();
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNeutralButton("Re-Check", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        doCheck();
                    }
                })

                .setCancelable(false)
                .setOnKeyListener(new DialogInterface.OnKeyListener(){
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        Log.i("License", "Key Listener");
                        finish();
                        return true;
                    }
                })
                .create();

    }
}