package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LokasiAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.service.OrtuList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataOrtuActivity extends AppCompatActivity implements DismisListener{

    private FloatingActionButton fab_add_ortu;
    private RecyclerView rv_ortu;
    private List<Ortu> ortu;
    private OrtuAdapter ortuAdapter;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_ortu);

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
        fab_add_ortu = findViewById(R.id.fab_add_ortu);
        rv_ortu = findViewById(R.id.rv_ortu);
        _implement();
    }

    private void _implement(){
        fab_add_ortu.setOnClickListener(v -> {
            AddOrtuFragment addOrtuFragment = new AddOrtuFragment();
            addOrtuFragment.show(getSupportFragmentManager(), addOrtuFragment.getTag());
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getortuList();
    }

    private void getortuList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        ortu = new ArrayList<>();

        Call<OrtuList> getOrtu = apiInterface.getOrtuList();
        getOrtu.enqueue(new Callback<OrtuList>() {
            @Override
            public void onResponse(Call<OrtuList> call, Response<OrtuList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    ortu = response.body().getOrtu();

                    ortuAdapter = new OrtuAdapter(getApplicationContext(), ortu);
                    rv_ortu.setAdapter(ortuAdapter);
                }
            }

            @Override
            public void onFailure(Call<OrtuList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDismisSheet() {
        getortuList();
    }
}