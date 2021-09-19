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
import com.faaadev.postari.adapter.AnakAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Ortu;
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
    private List<Anak> searchAnak;
    private AnakAdapter anakAdapter;
    private ApiInterface apiInterface;
    private RecyclerView rv_anak;
    private EditText search;
    private CardView search_button;

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
        search = findViewById(R.id.search);
        search_button = findViewById(R.id.search_button);

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

        if (Preferences.getRole(getApplicationContext()).equals("ortu")){
            getAnakListById();
        } else {
            getAnakList();
        }

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
                    anakAdapter = new AnakAdapter(getApplicationContext(), listAnak, getSupportFragmentManager(), type);
                    rv_anak.setAdapter(anakAdapter);
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

        getAnakList();
    }

    private void search(String param){
        searchAnak = new ArrayList<>();
        if (listAnak.size() > 0) {
            for (Anak z : listAnak) {
                if (z.getUser_id().toLowerCase().contains(param.toLowerCase()) ||
                        z.getNama().toLowerCase().contains(param.toLowerCase()) ||
                        z.getGender().toLowerCase().contains(param.toLowerCase()) ||
                        z.getBirthdate().toLowerCase().contains(param.toLowerCase())) {
                    System.out.println(z);
                    searchAnak.add(z);
                    //searchAdapter.notifyAll();
                }
            }
        }

        anakAdapter = new AnakAdapter(getApplicationContext(), searchAnak, getSupportFragmentManager(), type);
        rv_anak.setAdapter(anakAdapter);
    }

    private void getAnakList(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        listAnak = new ArrayList<>();

        Call<AnakList> anakList = apiInterface.getAnakWithParam("", type);
        anakList.enqueue(new Callback<AnakList>() {
            @Override
            public void onResponse(Call<AnakList> call, Response<AnakList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    listAnak = response.body().getAnak();

                    anakAdapter = new AnakAdapter(getApplicationContext(), listAnak, getSupportFragmentManager(), type);
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

    private void getAnakListById(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoading();
        listAnak = new ArrayList<>();

        Call<AnakList> anakList = apiInterface.getAnakWithParam(Preferences.getUserId(getApplicationContext()), "");
        anakList.enqueue(new Callback<AnakList>() {
            @Override
            public void onResponse(Call<AnakList> call, Response<AnakList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    listAnak = response.body().getAnak();

                    anakAdapter = new AnakAdapter(getApplicationContext(), listAnak, getSupportFragmentManager(), type);
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