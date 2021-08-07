package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.AnakAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.service.AnakList;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAnakActivity extends AppCompatActivity {
    private String type;
    private TextView title;
    private List<Anak> listAnak;
    private AnakAdapter anakAdapter;
    private ApiInterface apiInterface;
    private RecyclerView rv_anak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_anak);

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
        title = findViewById(R.id.title);
        rv_anak = findViewById(R.id.rv_anak);

        _implement();
    }

    private void _implement(){
        type = getIntent().getStringExtra("type");
        if (type.equals("penimbangan")){
            title.setText("Data Penimbangan");
        } else {
            title.setText("Data Imunisasi & Vitamin");
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getAnakList();
    }

    private void getAnakList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        listAnak = new ArrayList<>();

        Call<AnakList> anakList = apiInterface.getAnakList();
        anakList.enqueue(new Callback<AnakList>() {
            @Override
            public void onResponse(Call<AnakList> call, Response<AnakList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    listAnak = response.body().getAnak();

                    anakAdapter = new AnakAdapter(getApplicationContext(), listAnak, type);
                    rv_anak.setAdapter(anakAdapter);
                }
            }

            @Override
            public void onFailure(Call<AnakList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}