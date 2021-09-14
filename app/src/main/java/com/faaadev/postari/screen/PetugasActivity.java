package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.faaadev.postari.ChatActivity;
import com.faaadev.postari.ProfileFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PetugasActivity extends AppCompatActivity {

    private CardView menu1, menu2, menu3, menu4, menu5, menu6;
    private FloatingActionButton fab_live_chat;
    private String type;
    private TextView username, date, greetings;
    private ImageView img_profile;
    String today;
    private int hours;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id"));
    private Calendar c = Calendar.getInstance();

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
        menu3 = findViewById(R.id.menu3);
        menu4 = findViewById(R.id.menu4);
        menu5 = findViewById(R.id.menu5);
        menu6 = findViewById(R.id.menu6);
        username = findViewById(R.id.username);
        fab_live_chat = findViewById(R.id.fab_live_chat);
        img_profile = findViewById(R.id.img_profile);
        date = findViewById(R.id.date);
        greetings = findViewById(R.id.greetings);

        type = getIntent().getStringExtra("type");

        _implement();
    }

    private void _implement(){
        hours = c.get(Calendar.HOUR_OF_DAY);
        if (hours >= 12 && hours < 15) {
            greetings.setText("Selamat siang "+Preferences.getUsername(getApplicationContext()));
        } else if (hours >= 15 && hours < 18) {
            greetings.setText("Selamat sore "+Preferences.getUsername(getApplicationContext()));
        } else if (hours >= 18) {
            greetings.setText("Selamat malam "+Preferences.getUsername(getApplicationContext()));
        } else {
            greetings.setText("Selamat pagi "+Preferences.getUsername(getApplicationContext()));
        }

        today = sdf.format(new Date());

        date.setText(today);

        if (type.equals("petugas_kesehatan")){
            menu1.setVisibility(View.GONE);
            menu2.setVisibility(View.GONE);
        }

        username.setText(Preferences.getUsername(getApplicationContext()));

        menu1.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, UserAccountActivity.class)));

        menu2.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, DataOrtuActivity.class)));

        menu6.setOnClickListener(v -> startActivity(new Intent(PetugasActivity.this, DataPosyanduActivity.class)));

        menu3.setOnClickListener(v ->{
            Intent intent = new Intent(PetugasActivity.this, DataAnakActivity.class);
            intent.putExtra("type", "penimbangan");
            startActivity(intent);
        });

        menu4.setOnClickListener(v ->{
            Intent intent = new Intent(PetugasActivity.this, DataAnakActivity.class);
            intent.putExtra("type", "imunisasi");
            startActivity(intent);
        });

        menu5.setOnClickListener(v -> {
            Intent intent = new Intent(PetugasActivity.this, DataOrtuActivity.class);
            intent.putExtra("pemeriksaan", true);
            startActivity(intent);
        });

        fab_live_chat.setOnClickListener(v -> {
            startActivity(new Intent(this, ChatActivity.class));
        });

        img_profile.setOnClickListener(v -> {
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.show(getSupportFragmentManager(), profileFragment.getTag());
        });
    }
}