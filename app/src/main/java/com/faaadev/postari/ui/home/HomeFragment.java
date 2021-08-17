package com.faaadev.postari.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LayananAdapter;
import com.faaadev.postari.adapter.LayananMainAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.screen.DetailOrtuActivity;
import com.faaadev.postari.service.LayananList;
import com.faaadev.postari.service.Ortu;
import com.faaadev.postari.service.OrtuList;
import com.faaadev.postari.widget.LoadingDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView username, time, date;
    private int hours;
    String today;
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", new Locale("id"));
    private Calendar c = Calendar.getInstance();
    private ApiInterface apiInterface;
    private LoadingDialog loadingDialog;
    private List<Layanan> layananList;
    private RecyclerView rv_layanan;
    private LayananMainAdapter adapter;
    private com.faaadev.postari.model.Ortu ortu = new com.faaadev.postari.model.Ortu();
    private LinearLayout menu1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        _init(root);

        return root;
    }

    private void _init(View root) {
        username = root.findViewById(R.id.tv_username);
        time = root.findViewById(R.id.timesmap);
        date = root.findViewById(R.id.date);
        rv_layanan = root.findViewById(R.id.rv_layanan);
        menu1 = root.findViewById(R.id.menu1);

        _implement();
    }

    private void _implement() {
        hours = c.get(Calendar.HOUR_OF_DAY);
        if (hours >= 12 && hours < 15) {
            time.setText("Selamat Siang,");
        } else if (hours >= 15 && hours < 18) {
            time.setText("Selamat Sore,");
        } else if (hours >= 18) {
            time.setText("Selamat Malam,");
        } else {
            time.setText("Selamat Pagi,");
        }

        today = sdf.format(new Date());

        date.setText(today);

        username.setText(Preferences.getUsername(getContext()));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        loadingDialog = new LoadingDialog(getActivity());

        getortuListWithParam();

        menu1.setOnClickListener(v-> {
            Intent i = new Intent(getActivity(), DetailOrtuActivity.class);
            i.putExtra("ortu", ortu);
            startActivity(i);
        });
    }

    private void getLayananList() {
        loadingDialog.startLoading();
        layananList = new ArrayList<>();

        Call<LayananList> getLayanan = apiInterface.getLayananList(Preferences.getUserId(getContext()));
        getLayanan.enqueue(new Callback<LayananList>() {
            @Override
            public void onResponse(Call<LayananList> call, Response<LayananList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()) {
                    layananList = response.body().getLayanan();
                    adapter = new LayananMainAdapter(getContext(), layananList, ortu);
                    rv_layanan.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LayananList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getortuListWithParam(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<Ortu> getOrtu = apiInterface.getOrtuById(Preferences.getUserId(getContext()));
        getOrtu.enqueue(new Callback<Ortu>() {
            @Override
            public void onResponse(Call<Ortu> call, Response<Ortu> response) {
                getLayananList();
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    ortu = response.body().getOrtu();
                }
            }

            @Override
            public void onFailure(Call<Ortu> call, Throwable t) {
                loadingDialog.dismis();
                getLayananList();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}