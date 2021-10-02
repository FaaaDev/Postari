package com.faaadev.postari.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.ChatActivity;
import com.faaadev.postari.ProfileFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.adapter.JadwalAdapter;
import com.faaadev.postari.adapter.LayananAdapter;
import com.faaadev.postari.adapter.LayananMainAdapter;
import com.faaadev.postari.adapter.OrtuAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.screen.DetailOrtuActivity;
import com.faaadev.postari.service.JadwalList;
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

    private TextView username, time, date, jadwal;
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
    private LinearLayout menu0, menu1, menu2;
    private ImageView img_profile;
    private CardView navigation;
    private String url;

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
        menu0 = root.findViewById(R.id.menu0);
        img_profile = root.findViewById(R.id.img_profile);
        jadwal = root.findViewById(R.id.jadwal);
        navigation = root.findViewById(R.id.navigation);

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

        menu0.setOnClickListener(v-> {
            startActivity(new Intent(getActivity(), ChatActivity.class));
        });

        img_profile.setOnClickListener(v -> {
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.show(getChildFragmentManager(), profileFragment.getTag());
        });

        navigation.setVisibility(View.GONE);
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
                getJadwalList();
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

    private void getJadwalList(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("id"));
        String date = sdf.format(new Date());
        System.out.println(date);
        Call<JadwalList> getJadwal = apiInterface.getJadwalList();
        getJadwal.enqueue(new Callback<JadwalList>() {
            @Override
            public void onResponse(Call<JadwalList> call, Response<JadwalList> response) {
                if (response.body().isSuccess()){
                    for (int i = 0; i < response.body().getJadwal().size(); i++) {
                        if (response.body().getJadwal().get(i).getId().equals(ortu.getIdPosyandu())
                                && response.body().getJadwal().get(i).getTanggal().equals(date)){
                            jadwal.setText(response.body().getJadwal().get(i).getKegiatan()+ "\ndi "+response.body().getJadwal().get(i).getNamaPosyandu());
                            url = response.body().getJadwal().get(i).getUrl();
                            navigation.setVisibility(View.VISIBLE);
                            navigation.setOnClickListener(v -> {
                                if (!TextUtils.isEmpty(url)) {
                                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                            Uri.parse(url));
                                    startActivity(intent);
                                }
                            });

                        }
                        else {
                            jadwal.setText("Tidak ada jadwal hari ini");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JadwalList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}