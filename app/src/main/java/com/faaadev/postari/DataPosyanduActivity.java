package com.faaadev.postari;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.faaadev.postari.screen.DismisListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DataPosyanduActivity extends AppCompatActivity implements DismisListener {

    private FloatingActionButton add_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_posyandu);

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
        add_data = findViewById(R.id.fab_add_data);
        _implement();
    }

    private void _implement(){
        add_data.setOnClickListener(v->{
            AddPosyanduFragment addPosyanduFragment = new AddPosyanduFragment();
            addPosyanduFragment.show(getSupportFragmentManager(), addPosyanduFragment.getTag());
        });
    }

    @Override
    public void onDismisSheet() {
        System.out.println("Dismis");
    }
}