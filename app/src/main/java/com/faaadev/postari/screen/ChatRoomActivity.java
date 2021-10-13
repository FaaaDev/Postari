package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.ChatAdapter;
import com.faaadev.postari.adapter.ChatDetailAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Chat;
import com.faaadev.postari.model.User;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.service.ChatList;
import com.faaadev.postari.util.MyFirebaseMessagingService;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomActivity extends AppCompatActivity {

    private EditText message;
    private CardView send;
    private RecyclerView rv_chat;
    private TextView username, role;
    private User data;
    private ImageView state_false;
    private ProgressBar state_true;
    private ApiInterface apiInterface;
    private String sender_id, receiver_id, name, rawRole;
    private List<Chat> list;
    private ChatDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                this.getWindow().setNavigationBarColor(Color.argb(255,248,248,248));
            }
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(updates_receiver, new IntentFilter(MyFirebaseMessagingService.INFO_UPDATE_FILTER));

        init();
    }

    private void init(){
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        username = findViewById(R.id.username);
        role = findViewById(R.id.role);
        state_false = findViewById(R.id.state_false);
        state_true = findViewById(R.id.state_true);
        rv_chat = findViewById(R.id.rv_chat);

        if (getIntent().getStringExtra("from").equals("new")){
            data = (User) getIntent().getSerializableExtra("user");
            name = data.getUsername();
            rawRole = data.getRole();
            receiver_id = data.getUser_id();
        } else {
            name = getIntent().getStringExtra("receiverName");
            rawRole = getIntent().getStringExtra("receiverRole");
            receiver_id = getIntent().getStringExtra("receiverId");
        }

        sender_id = Preferences.getUserId(getApplicationContext());

        implement();
    }

    private  void implement(){
        username.setText(name);
        switch (rawRole) {
            case "ortu":
                role.setText("Orang Tua");
                break;
            case "petugas_posyandu":
                role.setText("Petugas Posyandu");
                break;
            case "petugas_kesehatan":
                role.setText("Petugas Kesehatan");
                break;
            default:
                role.setText("Petugas");
                break;
        }

        setSendLoading(false);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        list = new ArrayList<>();
        adapter = new ChatDetailAdapter(getApplicationContext(), list);
        rv_chat.setAdapter(adapter);
        getChat();

    }

    private BroadcastReceiver updates_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String filter = MyFirebaseMessagingService.INFO_UPDATE_FILTER;
            if(intent.getAction().equals(filter)){
                getChat();
            }
        }
    };

    private void setSendLoading(Boolean state){
        if (state) {
            state_true.setVisibility(View.VISIBLE);
            state_false.setVisibility(View.GONE);
            send.setEnabled(false);
        } else {
            state_true.setVisibility(View.GONE);
            state_false.setVisibility(View.VISIBLE);
            send.setEnabled(true);
            send.setOnClickListener(v->{
                if (!TextUtils.isEmpty(message.getText().toString().trim())){
                    setSendLoading(true);
                    addChat();
                }
            });
        }
    }

    private void addChat(){
        setSendLoading(true);
        Call<BasicResponse> addChat = apiInterface.addChat(message.getText().toString(), sender_id, receiver_id);
        addChat.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if (response.body().getStatus()){

                    list.add(new Chat(sender_id, receiver_id, message.getText().toString()));
                    adapter.notifyDataSetChanged();
                    rv_chat.smoothScrollToPosition(adapter.getItemCount() - 1);
                    message.setText("");
                    setSendLoading(false);
                } else {
                    setSendLoading(false);
                    System.out.println(response.body().getMessage());
                    Toast.makeText(getApplicationContext(), "Gagal Mengirim Pesan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Kesalahan saat mengirim ke server", Toast.LENGTH_LONG).show();
                setSendLoading(false);
            }
        });
    }

    private void getChat(){
        setSendLoading(true);

        Call<ChatList> chatList = apiInterface.getChatDetail(Preferences.getUserId(getApplicationContext()), receiver_id);
        chatList.enqueue(new Callback<ChatList>() {
            @Override
            public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                if(response.body().isSuccess()){
                    setSendLoading(false);
                    list.addAll(response.body().getChat());
                    adapter.notifyDataSetChanged();
                    rv_chat.smoothScrollToPosition(adapter.getItemCount() - 1);
                } else {
                    setSendLoading(false);
                }
            }

            @Override
            public void onFailure(Call<ChatList> call, Throwable t) {
                setSendLoading(false);
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updates_receiver);
    }
}