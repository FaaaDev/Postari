package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.faaadev.postari.DataPosyanduActivity;
import com.faaadev.postari.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PetugasActivity extends AppCompatActivity {

    private CardView menu1, menu2, menu3, menu4, menu5, menu6;
    private FloatingActionButton fab_live_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);

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
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu6 = findViewById(R.id.menu6);
        fab_live_chat = findViewById(R.id.fab_live_chat);

        _implement();
    }

    private void _implement(){
        menu1.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, UserAccountActivity.class)));

        menu2.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, DataOrtuActivity.class)));

        menu6.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, DataPosyanduActivity.class)));

        fab_live_chat.setOnClickListener(v -> {

        });
    }
}