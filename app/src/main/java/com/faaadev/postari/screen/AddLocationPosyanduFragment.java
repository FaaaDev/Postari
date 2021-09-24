package com.faaadev.postari.screen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.screen.AddUserFragment;
import com.faaadev.postari.screen.DismisListener;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddLocationPosyanduFragment extends BottomSheetDialogFragment {

    private TextView title;
    private EditText name, alamat;
    private Button add_button;
    private ApiInterface apiInterface;
    DismisListener listener;
    private Lokasi lokasi = new Lokasi();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_location_posyandu, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        name = root.findViewById(R.id.name);
        alamat = root.findViewById(R.id.alamat);
        add_button = root.findViewById(R.id.btn_add);
        title = root.findViewById(R.id.title);

        _implement();
    }

    private void _implement(){
        add_button.setOnClickListener(v-> validation(false));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (getArguments() != null) {
            if (getArguments().getBoolean("isUpdate")) {
                title.setText("Edit Anak");
                add_button.setText("Perbarui Data Anak");
                lokasi = (Lokasi) getArguments().getSerializable("data");
                name.setText(lokasi.getNamaPosyandu());
                alamat.setText(lokasi.getAlamat());

                add_button.setOnClickListener(v -> {
                    validation(true);
                });
            }
        }
    }

    private void validation(boolean update){
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(name.getText())){
            if (TextUtils.isEmpty(name.getText())){
                name.setError("Bagian ini tidak boleh kosong");
            }

            if (TextUtils.isEmpty(alamat.getText())){
                alamat.setError("Bagian ini tidak boleh kosong");
            }
        } else {
            if (update) {
                updateLocation();
            } else {
                addLocation();
            }
        }
    }

    private void addLocation(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addLoc = apiInterface.addLoc(name.getText().toString(), alamat.getText().toString());
        addLoc.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                loadingDialog.dismis();
                if (response.body().getStatus()){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateLocation() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addLoc = apiInterface.editLoc(name.getText().toString(), alamat.getText().toString(), lokasi.getId());
        addLoc.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                loadingDialog.dismis();
                if (response.body().getStatus()){
                    Toast.makeText(getContext(), "Data berhasil diupdate", Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Data gagal diupdate", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DismisListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must implement this");
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        listener.onDismisSheet("lokasi");
    }
}