package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.JadwalAdapter;
import com.faaadev.postari.adapter.LokasiAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.service.JadwalList;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPosyanduActivity extends AppCompatActivity implements DismisListener {

    private FloatingActionButton add_data;
    private RecyclerView rv_jadwal, rv_lokasi;
    private ApiInterface apiInterface;
    private List<Lokasi> lokasi;
    private LokasiAdapter adapter;
    private List<Jadwal> jadwal;
    private JadwalAdapter jadwalAdapter;
    LoadingDialog loadingDialog;

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
        rv_jadwal = findViewById(R.id.rv_jadwal);
        rv_lokasi = findViewById(R.id.rv_lokasi);
        _implement();
    }

    private void _implement(){
        add_data.setOnClickListener(v->{
            AddPosyanduFragment addPosyanduFragment = new AddPosyanduFragment();
            addPosyanduFragment.show(getSupportFragmentManager(), addPosyanduFragment.getTag());
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        loadingDialog = new LoadingDialog(this);

        getJadwalList();
    }

    private void getLokasiList(){
        loadingDialog.startLoading();
        lokasi = new ArrayList<>();

        Call<LokasiList> getLokasi = apiInterface.getLokasiList();
        getLokasi.enqueue(new Callback<LokasiList>() {
            @Override
            public void onResponse(Call<LokasiList> call, Response<LokasiList> response) {
                loadingDialog.dismis();
                if (response.body().getStatus().equals("true")){
                    lokasi = response.body().getLokasi();

                    adapter = new LokasiAdapter(getApplicationContext(), lokasi);
                    rv_lokasi.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LokasiList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getJadwalList(){
        loadingDialog.startLoading();
        jadwal = new ArrayList<>();

        Call<JadwalList> getJadwal = apiInterface.getJadwalList();
        getJadwal.enqueue(new Callback<JadwalList>() {
            @Override
            public void onResponse(Call<JadwalList> call, Response<JadwalList> response) {
                loadingDialog.dismis();
                getLokasiList();
                if (response.body().isSuccess()){
                    jadwal = response.body().getJadwal();

                    jadwalAdapter = new JadwalAdapter(getApplicationContext(), jadwal);
                    rv_jadwal.setAdapter(jadwalAdapter);
                }
            }

            @Override
            public void onFailure(Call<JadwalList> call, Throwable t) {
                loadingDialog.dismis();
                getLokasiList();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDismisSheet(String from) {
        if(from.equals("lokasi")){
            getLokasiList();
        } else {
            getJadwalList();
        }
    }

}