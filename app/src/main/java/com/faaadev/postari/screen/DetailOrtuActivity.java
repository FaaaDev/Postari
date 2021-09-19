package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.AnakOrtuAdapter;
import com.faaadev.postari.adapter.LayananAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.service.AnakList;
import com.faaadev.postari.service.LayananList;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrtuActivity extends AppCompatActivity implements DismisListener {

    private Ortu ortu = new Ortu();
    private TextView tv_dadname, tv_momname, tv_location, tv_address;
    private LinearLayout layanan_container, edit_container;
    private RecyclerView rv_layanan, rv_anak_ortu;
    private List<Layanan> layananList;
    private List<Anak> anakList;
    private LayananAdapter layananAdapter;
    private AnakOrtuAdapter anakAdapter;
    private ApiInterface apiInterface;
    private LoadingDialog loadingDialog;
    private ImageButton btn_add;
    private Button btn_edit;

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
        btn_add = findViewById(R.id.btn_add);
        edit_container = findViewById(R.id.edit_container);
        btn_edit = findViewById(R.id.btn_edit);

        _implement();
    }

    private void _implement(){
        tv_dadname.setText(ortu.getDad_name());
        tv_momname.setText(ortu.getMom_name());
        tv_location.setText(ortu.getPosyandu());
        tv_address.setText(ortu.getAlamat());
        layananList = new ArrayList<>();
        anakList = new ArrayList<>();

        if (Preferences.getRole(getApplicationContext()).equals("ortu")){
            edit_container.setVisibility(View.GONE);
        }

        layanan_container.setVisibility(View.GONE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        loadingDialog = new LoadingDialog(this);

        getLayananList();

        btn_edit.setOnClickListener(V -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", ortu);
            bundle.putBoolean("isUpdate", true);
            bundle.putBoolean("isPenimbangan", false);
            bundle.putBoolean("isImunisasi", false);
            bundle.putBoolean("isPemeriksaan", false);
            for (int i = 0; i < layananList.size(); i++){
                if (layananList.get(i).getNama().equals("penimbangan")){
                    bundle.putBoolean("isPenimbangan", true);
                } else if (layananList.get(i).getNama().equals("imunisasi")){
                    bundle.putBoolean("isImunisasi", true);
                } else if (layananList.get(i).getNama().equals("pemeriksaan")){
                    bundle.putBoolean("isPemeriksaan", true);
                }
            }
            AddOrtuFragment addOrtuFragment = new AddOrtuFragment();
            addOrtuFragment.setArguments(bundle);
            addOrtuFragment.show(getSupportFragmentManager(), addOrtuFragment.getTag());
        });

        btn_add.setOnClickListener(v->{
            Bundle bundle = new Bundle();
            bundle.putString("user_id", ortu.getUser_id());
            AddAnakFragment addAnakFragment = new AddAnakFragment();
            addAnakFragment.setArguments(bundle);
            addAnakFragment.show(getSupportFragmentManager(), addAnakFragment.getTag());
        });
    }

    private void getLayananList(){
        loadingDialog.startLoading();
        layananList = new ArrayList<>();

        Call<LayananList> getLayanan = apiInterface.getLayananList(ortu.getUser_id());
        getLayanan.enqueue(new Callback<LayananList>() {
            @Override
            public void onResponse(Call<LayananList> call, Response<LayananList> response) {
                loadingDialog.dismis();
                getAnakList();
                if (response.body().isSuccess()){
                    layananList = response.body().getLayanan();

                    if (layananList.size() == 0){
                        layanan_container.setVisibility(View.GONE);
                    } else {
                        layananAdapter = new LayananAdapter(getApplicationContext(), layananList);
                        rv_layanan.setAdapter(layananAdapter);
                        layanan_container.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LayananList> call, Throwable t) {
                loadingDialog.dismis();
                getAnakList();
                layanan_container.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAnakList(){
        loadingDialog.startLoading();
        anakList = new ArrayList<>();

        Call<AnakList> getAnak = apiInterface.getAnakWithParam(ortu.getUser_id(), "");
        getAnak.enqueue(new Callback<AnakList>() {
            @Override
            public void onResponse(Call<AnakList> call, Response<AnakList> response) {
                loadingDialog.dismis();
                if(response.body().isSuccess()){
                    anakList = response.body().getAnak();
                    anakAdapter = new AnakOrtuAdapter(getApplicationContext(), anakList);
                    rv_anak_ortu.setAdapter(anakAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Data Anak Kosong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AnakList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getortuListWithParam(String id){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();

        Call<com.faaadev.postari.service.Ortu> getOrtu = apiInterface.getOrtuById(id);
        getOrtu.enqueue(new Callback<com.faaadev.postari.service.Ortu>() {
            @Override
            public void onResponse(Call<com.faaadev.postari.service.Ortu> call, Response<com.faaadev.postari.service.Ortu> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    ortu = response.body().getOrtu();
                    tv_dadname.setText(ortu.getDad_name());
                    tv_momname.setText(ortu.getMom_name());
                    tv_location.setText(ortu.getPosyandu());
                    tv_address.setText(ortu.getAlamat());
                    getLayananList();
                }
            }

            @Override
            public void onFailure(Call<com.faaadev.postari.service.Ortu> call, Throwable t) {
                loadingDialog.dismis();
                getLayananList();
                Toast.makeText(getApplicationContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDismisSheet(String from) {
        if (from.equals("add_anak")){
            getAnakList();
        } else if (from.equals("update")){
            getortuListWithParam(ortu.getUser_id());
        }
    }

    @Override
    public void onUpdateOrtu(Object object) {

    }
}