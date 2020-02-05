package com.example.techela;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.techela.Auth.LoginActivity;

public class SplashActivity extends Activity {

    Handler handler;
    private static final int PERMISSION_REQUEST_CAMERA = 69;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

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

        handler=new Handler();
        handler.postDelayed(new Runnable() {
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
}