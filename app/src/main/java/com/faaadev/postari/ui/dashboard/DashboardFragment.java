package com.faaadev.postari.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.JadwalAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.service.JadwalList;
import com.faaadev.postari.service.Ortu;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private RecyclerView rv_jadwal, rv_lainnya;
    private ApiInterface apiInterface;
    private List<Jadwal> jadwal;
    private List<Jadwal> lainnya;
    private JadwalAdapter jadwalAdapter;
    private JadwalAdapter lainnyaAdapter;
    LoadingDialog loadingDialog;
    private com.faaadev.postari.model.Ortu ortu = new com.faaadev.postari.model.Ortu();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        rv_jadwal = root.findViewById(R.id.rv_jadwal);
        rv_lainnya = root.findViewById(R.id.rv_lainnya);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        loadingDialog = new LoadingDialog(getActivity());

        getortuListWithParam();
    }

    private void getortuListWithParam(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<Ortu> getOrtu = apiInterface.getOrtuById(Preferences.getUserId(getContext()));
        getOrtu.enqueue(new Callback<Ortu>() {
            @Override
            public void onResponse(Call<Ortu> call, Response<Ortu> response) {
                loadingDialog.dismis();
                getJadwalList();
                if (response.body().isSuccess()){
                    ortu = response.body().getOrtu();
                }
            }

            @Override
            public void onFailure(Call<Ortu> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getJadwalList(){
        loadingDialog.startLoading();
        jadwal = new ArrayList<>();
        lainnya = new ArrayList<>();

        Call<JadwalList> getJadwal = apiInterface.getJadwalList();
        getJadwal.enqueue(new Callback<JadwalList>() {
            @Override
            public void onResponse(Call<JadwalList> call, Response<JadwalList> response) {
                loadingDialog.dismis();
                if (response.body().isSuccess()){
                    for (int i = 0; i < response.body().getJadwal().size(); i++) {
                        if (response.body().getJadwal().get(i).getId().equals(ortu.getIdPosyandu())){
                            jadwal.add(response.body().getJadwal().get(i));
                            jadwalAdapter = new JadwalAdapter(getContext(), jadwal);
                            rv_jadwal.setAdapter(jadwalAdapter);
                        } else {
                            lainnya.add(response.body().getJadwal().get(i));
                            lainnyaAdapter = new JadwalAdapter(getContext(), lainnya);
                            rv_lainnya.setAdapter(lainnyaAdapter);
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