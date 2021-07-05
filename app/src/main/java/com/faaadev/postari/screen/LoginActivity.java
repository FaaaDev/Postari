package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.http.SharedPrefManager;
import com.faaadev.postari.service.Auth;
import com.faaadev.postari.widget.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText et_user, et_password;
    private ApiInterface apiInterface;
    private SharedPrefManager sharedPrefManager;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container);
            int flags = main.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                this.getWindow().setNavigationBarColor(Color.WHITE);
            }
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        _init();
        _implement();
    }

    private void _init() {
        login_btn = findViewById(R.id.btn_login);
        et_password = findViewById(R.id.et_password);
        et_user = findViewById(R.id.et_user);
    }

    private void _implement() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sharedPrefManager = new SharedPrefManager(LoginActivity.this);

        preferences = new Preferences();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_password.getText().toString()) || TextUtils.isEmpty(et_user.getText().toString())) {
                    if (TextUtils.isEmpty(et_user.getText().toString())) {
                        et_user.setError("Bagian ini harus diisi");
                    }
                    if (TextUtils.isEmpty(et_password.getText().toString())) {
                        et_password.setError("Bagian ini harus diisi");
                    }
                } else {
                    auth();
                }
            }
        }
        );
    }

    private void auth() {
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        Call<Auth> goAuth = apiInterface.goAuth(et_user.getText().toString(), et_password.getText().toString());
        goAuth.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.body().getStatus().equals("true")) {
                    Handler hh = new Handler();
                    hh.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismis();
                            sharedPrefManager.saveUser(response.body().getUser());

                            preferences.saveUser(response.body().getUser(), getApplicationContext());
                           // System.out.println("USER === "+  preferences.getUser(getApplicationContext()).getUser_id());
                            if (response.body().getUser().getRole().equals("ortu")) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(LoginActivity.this, PetugasActivity.class));
                                finish();
                            }
                        }
                    }, 1000);
                } else {
                    loadingDialog.dismis();
                    Toast.makeText(LoginActivity.this, "Periksa Kembali Email atau Password", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Menghubungi Server, Coba Lagi!", Toast.LENGTH_LONG).show();
                loadingDialog.dismis();
            }
        });
    }
}