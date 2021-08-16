package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.AddPemeriksaanFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.adapter.ImunisasiAdapter;
import com.faaadev.postari.adapter.PenimbanganAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Imunisasi;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.model.Penimbangan;
import com.faaadev.postari.service.ImunisasiList;
import com.faaadev.postari.service.PenimbanganList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAnakActivity extends AppCompatActivity implements DismisListener {
    private Anak anak = new Anak();
    private Ortu ortu = new Ortu();
    private String type;
    private TextView title;
    private LinearLayout is_empty;
    private ScrollView has_data;
    private RecyclerView rv_imunisasi, rv_penimbangan, rv_pemeriksaan;
    private FloatingActionButton fab_add_anak;
    private List<Penimbangan> penimbanganList;
    private List<Imunisasi> imunisasiList;
    private PenimbanganAdapter penimbanganAdapter;
    private ImunisasiAdapter imunisasiAdapter;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anak);

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

        anak = (Anak) getIntent().getSerializableExtra("anak");
        ortu = (Ortu) getIntent().getSerializableExtra("ortu");
        type = getIntent().getStringExtra("type");

        _init();
    }

    private void _init(){
        title = findViewById(R.id.title);
        has_data = findViewById(R.id.has_data);
        is_empty = findViewById(R.id.is_empty);
        rv_imunisasi = findViewById(R.id.rv_imunisasi);
        rv_penimbangan = findViewById(R.id.rv_penimbangan);
        rv_pemeriksaan = findViewById(R.id.rv_pemeriksaan);
        fab_add_anak = findViewById(R.id.fab_add_anak);

        _implement();
    }

    private void _implement(){
        if (type.equals("pemeriksaan")){
            title.setText("Ibu "+ortu.getMom_name());
        } else {
            title.setText(anak.getNama());
        }

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        is_empty.setVisibility(View.GONE);

        if (type.equals("penimbangan")){
            rv_imunisasi.setVisibility(View.GONE);
            rv_pemeriksaan.setVisibility(View.GONE);
            fab_add_anak.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", anak.getId());
                AddPenimbanganFragment addPenimbanganFragment = new AddPenimbanganFragment();
                addPenimbanganFragment.setArguments(bundle);
                addPenimbanganFragment.show(getSupportFragmentManager(), addPenimbanganFragment.getTag());
            });
            getPenimbanganList();
        } else if (type.equals("pemeriksaan")){
            rv_penimbangan.setVisibility(View.GONE);
            rv_imunisasi.setVisibility(View.GONE);
            fab_add_anak.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", ortu.getUser_id());
                AddPemeriksaanFragment addPemeriksaanFragment = new AddPemeriksaanFragment();
                addPemeriksaanFragment.setArguments(bundle);
                addPemeriksaanFragment.show(getSupportFragmentManager(), addPemeriksaanFragment.getTag());
            });
            getPemeriksaanList();
        } else {
            rv_penimbangan.setVisibility(View.GONE);
            rv_pemeriksaan.setVisibility(View.GONE);
            fab_add_anak.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", anak.getId());
                AddImunisasiFragment addImunisasiFragment = new AddImunisasiFragment();
                addImunisasiFragment.setArguments(bundle);
                addImunisasiFragment.show(getSupportFragmentManager(), addImunisasiFragment.getTag());
            });
            getImunisasiList();
        }
    }

    private void getPenimbanganList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        penimbanganList = new ArrayList<>();

        Call<PenimbanganList> penimbanganListCall = apiInterface.getPenimbanganList(anak.getId());
        penimbanganListCall.enqueue(new Callback<PenimbanganList>() {
            @Override
            public void onResponse(Call<PenimbanganList> call, Response<PenimbanganList> response) {
                loadingDialog.dismis();
                if(response.body().isSuccess()){
                    is_empty.setVisibility(View.GONE);
                    has_data.setVisibility(View.VISIBLE);
                    penimbanganList = response.body().getPenimbangan();
                    penimbanganAdapter = new PenimbanganAdapter(getApplicationContext(), penimbanganList);
                    rv_penimbangan.setAdapter(penimbanganAdapter);
                } else {
                    is_empty.setVisibility(View.VISIBLE);
                    has_data.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PenimbanganList> call, Throwable t) {
                is_empty.setVisibility(View.VISIBLE);
                has_data.setVisibility(View.GONE);
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getImunisasiList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        imunisasiList = new ArrayList<>();

        Call<ImunisasiList> imunisasiListCall = apiInterface.getImunisasiList(anak.getId());
        imunisasiListCall.enqueue(new Callback<ImunisasiList>() {
            @Override
            public void onResponse(Call<ImunisasiList> call, Response<ImunisasiList> response) {
                loadingDialog.dismis();
                if(response.body().isSuccess()){
                    is_empty.setVisibility(View.GONE);
                    has_data.setVisibility(View.VISIBLE);
                    imunisasiList = response.body().getImunisasi();
                    imunisasiAdapter = new ImunisasiAdapter(getApplicationContext(), imunisasiList);
                    rv_imunisasi.setAdapter(imunisasiAdapter);
                } else {
                    is_empty.setVisibility(View.VISIBLE);
                    has_data.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ImunisasiList> call, Throwable t) {
                is_empty.setVisibility(View.VISIBLE);
                has_data.setVisibility(View.GONE);
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getPemeriksaanList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        imunisasiList = new ArrayList<>();

        Call<ImunisasiList> imunisasiListCall = apiInterface.getImunisasiList("1");
        imunisasiListCall.enqueue(new Callback<ImunisasiList>() {
            @Override
            public void onResponse(Call<ImunisasiList> call, Response<ImunisasiList> response) {
                loadingDialog.dismis();
                if(response.body().isSuccess()){
                    is_empty.setVisibility(View.GONE);
                    has_data.setVisibility(View.VISIBLE);
                    imunisasiList = response.body().getImunisasi();
                    imunisasiAdapter = new ImunisasiAdapter(getApplicationContext(), imunisasiList);
                    rv_pemeriksaan.setAdapter(imunisasiAdapter);
                } else {
                    is_empty.setVisibility(View.VISIBLE);
                    has_data.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ImunisasiList> call, Throwable t) {
                is_empty.setVisibility(View.VISIBLE);
                has_data.setVisibility(View.GONE);
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDismisSheet(String from) {
        if (from.equals("penimbangan")){
            getPenimbanganList();
        } else {
            getImunisasiList();
        }
    }
}