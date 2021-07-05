package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.http.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                this.getWindow().setNavigationBarColor(Color.WHITE);
            }
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
        }


        sharedPrefManager = new SharedPrefManager(SplashActivity.this);

        checkAuth();

    }

    private void checkAuth() {
        if (Preferences.isLogedIn(getApplicationContext())){
            System.out.println("USERNAME == "+Preferences.getUsername(getApplicationContext()));
            Handler hh = new Handler();
            hh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(Preferences.getRole(getApplicationContext()))){
                        if (Preferences.getRole(getApplicationContext()).equals("ortu")){
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, PetugasActivity.class));
                        }
                    }
                    finish();
                }
            }, 2000);
        } else {
            Handler hh = new Handler();
            hh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }, 3000);
        }
    }
}