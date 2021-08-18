package com.faaadev.postari.ui.notifications;

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
import com.faaadev.postari.adapter.LokasiAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {


    private RecyclerView rv_lokasi;
    private ApiInterface apiInterface;
    private List<Lokasi> lokasi;
    private LokasiAdapter adapter;
    LoadingDialog loadingDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        rv_lokasi = root.findViewById(R.id.rv_lokasi);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        loadingDialog = new LoadingDialog(getActivity());

        getLokasiList();
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

                    adapter = new LokasiAdapter(getContext(), lokasi);
                    rv_lokasi.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LokasiList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }
}