package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.ChatAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Chat;
import com.faaadev.postari.service.ChatList;
import com.faaadev.postari.util.MyFirebaseMessagingService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private FloatingActionButton fab_add;
    private RecyclerView rv_chatlist;
    private ChatAdapter adapter;
    private List<Chat> list;
    private ApiInterface apiInterface;
    private ScrollView has_data;
    private LinearLayout is_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


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

        LocalBroadcastManager.getInstance(this).registerReceiver(updates_receiver, new IntentFilter(MyFirebaseMessagingService.INFO_UPDATE_FILTER));

        _init();
    }

    private void _init(){
        fab_add = findViewById(R.id.fab_add_chat);
        rv_chatlist = findViewById(R.id.rv_chatlist);
        has_data = findViewById(R.id.has_data);
        is_empty = findViewById(R.id.is_empty);

        _implement();
    }

    private void _implement(){
        if (Preferences.getRole(getApplicationContext()).equals("ortu")){
            fab_add.setVisibility(View.VISIBLE);
        } else {
            fab_add.setVisibility(View.GONE);
        }
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        is_empty.setVisibility(View.GONE);
        fab_add.setOnClickListener(v-> {
            AddChatFragment addChatFragment = new AddChatFragment();
            addChatFragment.show(getSupportFragmentManager(), addChatFragment.getTag());
        });
    }

    private BroadcastReceiver updates_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String filter = MyFirebaseMessagingService.INFO_UPDATE_FILTER;
            if(intent.getAction().equals(filter)){
                getChatList();
            }
        }
    };

    private void getChatList(){
        list = new ArrayList<>();

        Call<ChatList> chatList = apiInterface.getChatList(Preferences.getUserId(getApplicationContext()));
        chatList.enqueue(new Callback<ChatList>() {
            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                if(response.body().isSuccess()){
                    is_empty.setVisibility(View.GONE);
                    has_data.setVisibility(View.VISIBLE);
                    list = response.body().getChat();
                    adapter = new ChatAdapter(getApplicationContext(), list);
                    rv_chatlist.setAdapter(adapter);
                } else {
                    is_empty.setVisibility(View.VISIBLE);
                    has_data.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                is_empty.setVisibility(View.VISIBLE);
                has_data.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getChatList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updates_receiver);
    }
}