package com.faaadev.postari.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LokasiAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.adapter.UserAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.model.User;
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
    private List<Ortu> searchOrtu;
    private OrtuAdapter ortuAdapter;
    private ApiInterface apiInterface;
    private Boolean pemeriksaan = false;
    private EditText search;
    private CardView search_button;
    private TextView title;

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
        title = findViewById(R.id.title);
        search = findViewById(R.id.search);
        search_button = findViewById(R.id.search_button);

        pemeriksaan = getIntent().getBooleanExtra("pemeriksaan", false);

        _implement();
    }

    private void _implement(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (pemeriksaan){
            fab_add_ortu.setVisibility(View.GONE);
            title.setText("Data Ibu Hamil");
            getortuListWithParam();
        } else {
            getortuList();
        }

        fab_add_ortu.setOnClickListener(v -> {
            AddOrtuFragment addOrtuFragment = new AddOrtuFragment();
            addOrtuFragment.show(getSupportFragmentManager(), addOrtuFragment.getTag());
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    search(s.toString());
                } else {
                    if (pemeriksaan){
                        ortuAdapter = new OrtuAdapter(getApplicationContext(), ortu, getSupportFragmentManager(), pemeriksaan);
                    } else {
                        ortuAdapter = new OrtuAdapter(getApplicationContext(), ortu, getSupportFragmentManager());
                    }
                    rv_ortu.setAdapter(ortuAdapter);
                }
            }
        });

        search.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
            {
                System.out.println("KODE : "+keyCode);
                if (keyCode == 66){
                    View view = getCurrentFocus();
                    if (view!=null){
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
            return false;
        });

        search_button.setOnClickListener(v -> {
            View view = getCurrentFocus();
            if (view!=null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

    }

    private void search(String param){
        searchOrtu = new ArrayList<>();
        if (ortu.size() > 0) {
            for (Ortu z : ortu) {
                if (z.getUser_id().toLowerCase().contains(param.toLowerCase()) ||
                        z.getPosyandu().toLowerCase().contains(param.toLowerCase()) ||
                        z.getDad_name().toLowerCase().contains(param.toLowerCase()) ||
                        z.getMom_name().toLowerCase().contains(param.toLowerCase())) {
                    System.out.println(z);
                    searchOrtu.add(z);
                    //searchAdapter.notifyAll();
                }
            }
        }

        if (pemeriksaan){
            ortuAdapter = new OrtuAdapter(getApplicationContext(), searchOrtu, getSupportFragmentManager(), pemeriksaan);
        } else {
            ortuAdapter = new OrtuAdapter(getApplicationContext(), searchOrtu, getSupportFragmentManager());
        }
        rv_ortu.setAdapter(ortuAdapter);
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

                    ortuAdapter = new OrtuAdapter(getApplicationContext(), ortu, getSupportFragmentManager());
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

    private void getortuListWithParam(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        ortu = new ArrayList<>();

        Call<OrtuList> getOrtu = apiInterface.getOrtuListWithParam("pemeriksaan");
        getOrtu.enqueue(new Callback<OrtuList>() {
            @Override
            public void onResponse(Call<OrtuList> call, Response<OrtuList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    ortu = response.body().getOrtu();

                    ortuAdapter = new OrtuAdapter(getApplicationContext(), ortu, getSupportFragmentManager(), pemeriksaan);
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
    public void onDismisSheet(String from) {
        getortuList();
    }
}