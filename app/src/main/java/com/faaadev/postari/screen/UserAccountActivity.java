package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.UserAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.User;
import com.faaadev.postari.service.UserList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountActivity extends AppCompatActivity implements AddUserFragment.DismisListener {

    private FloatingActionButton fab_add_user;
    private ApiInterface apiInterface;
    private RecyclerView rv_user;
    private List<User> list;
    private UserAdapter userAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

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
        fab_add_user = findViewById(R.id.fab_add_user);
        rv_user = findViewById(R.id.rv_user);

        _implement();
    }

    private void _implement(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        fab_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserFragment addUserFragment = new AddUserFragment();
                addUserFragment.show(getSupportFragmentManager(), addUserFragment.getTag());

            }
        });

        getUserList();
    }

    private void getUserList(){
        System.out.println("REFRESHED");
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        list = new ArrayList<>();
        Call<UserList> userListCall = apiInterface.getUserList();
        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                loadingDialog.dismis();
                if (response.body().getStatus().equals("true")){
                    list = response.body().getUser();

                    userAdapter = new UserAdapter(getApplicationContext(), list);
                    rv_user.setAdapter(userAdapter);
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                loadingDialog.dismis();
            }
        });
    }


    @Override
    public void onDismisSheet() {
        getUserList();
    }
}