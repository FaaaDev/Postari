package com.faaadev.postari;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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

import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.User;
import com.faaadev.postari.service.BasicResponse;

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
    private String sender_id, receiver_id;

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

        init();
    }

    private void init(){
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);
        username = findViewById(R.id.username);
        role = findViewById(R.id.role);
        state_false = findViewById(R.id.state_false);
        state_true = findViewById(R.id.state_true);

        data = (User) getIntent().getSerializableExtra("user");

        sender_id = Preferences.getUserId(getApplicationContext());
        receiver_id = data.getUser_id();

        implement();
    }

    private  void implement(){
        username.setText(data.getUsername());
        switch (data.getRole()) {
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
    }

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
        Call<BasicResponse> addChat = apiInterface.addChat(sender_id, receiver_id);
        addChat.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if (response.body().getStatus()){
                    addToChatRoom(response.body().getMessage());
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

    private void addToChatRoom(String chat_id){
        Call<BasicResponse> addToChat = apiInterface.addToChatRoom(chat_id, sender_id, message.getText().toString());
        addToChat.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if (response.body().getStatus()){
                    setSendLoading(false);
                    message.setText("");
                } else {
                    setSendLoading(false);
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
}