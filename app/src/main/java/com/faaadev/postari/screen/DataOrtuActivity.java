package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.faaadev.postari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DataOrtuActivity extends AppCompatActivity {

    private FloatingActionButton fab_add_ortu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ortu);

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

        _init();

    }

    private void _init(){
        fab_add_ortu = findViewById(R.id.fab_add_ortu);

        _implement();
    }

    private void _implement(){
        fab_add_ortu.setOnClickListener(v -> {
            AddOrtuFragment addOrtuFragment = new AddOrtuFragment();
            addOrtuFragment.show(getSupportFragmentManager(), addOrtuFragment.getTag());
        });
    }
}