package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LayananAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.service.LayananList;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrtuActivity extends AppCompatActivity {

    private Ortu ortu = new Ortu();
    private TextView tv_dadname, tv_momname, tv_location, tv_address;
    private LinearLayout layanan_container;
    private RecyclerView rv_layanan, rv_anak_ortu;
    private List<Layanan> layananList;
    private LayananAdapter layananAdapter;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ortu);

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

        ortu = (Ortu) getIntent().getSerializableExtra("ortu");

        _init();
    }

    private void _init(){
        tv_dadname = findViewById(R.id.tv_dadname);
        tv_momname = findViewById(R.id.tv_momname);
        tv_location = findViewById(R.id.tv_location);
        tv_address = findViewById(R.id.tv_address);
        layanan_container = findViewById(R.id.layanan_container);
        rv_anak_ortu = findViewById(R.id.rv_anak_ortu);
        rv_layanan = findViewById(R.id.rv_layanan);

        _implement();
    }

    private void _implement(){
        tv_dadname.setText(ortu.getDad_name());
        tv_momname.setText(ortu.getMom_name());
        tv_location.setText(ortu.getPosyandu());
        tv_address.setText(ortu.getAlamat());
        layananList = new ArrayList<>();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        getLayananList();
    }

    private void getLayananList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        layananList = new ArrayList<>();

        Call<LayananList> getLayanan = apiInterface.getLayananList(ortu.getUser_id());
        getLayanan.enqueue(new Callback<LayananList>() {
            @Override
            public void onResponse(Call<LayananList> call, Response<LayananList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    layananList = response.body().getLayanan();

                    if (layananList.size() == 0){
                        layanan_container.setVisibility(View.GONE);
                    }

                    layananAdapter = new LayananAdapter(getApplicationContext(), layananList);
                    rv_layanan.setAdapter(layananAdapter);
                }
            }

            @Override
            public void onFailure(Call<LayananList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}