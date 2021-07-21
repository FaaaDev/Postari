package com.faaadev.postari.screen;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LokasiAdapter;
import com.faaadev.postari.adapter.LokasiSpinnerAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddOrtuFragment extends BottomSheetDialogFragment {

    private ApiInterface apiInterface;
    private List<Lokasi> lokasi;
    private LokasiSpinnerAdapter adapter;
    private Spinner spinner_loc;
    private String location_id;
    private Button btn_add;
    private EditText no_hp, mom_name, dad_name, alamat;
    private String layanan;
    private CheckBox cb1, cb2, cb3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_ortu, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        spinner_loc = root.findViewById(R.id.spinner_loc);
        btn_add = root.findViewById(R.id.btn_add);
        no_hp = root.findViewById(R.id.no_hp);
        mom_name = root.findViewById(R.id.mom_name);
        dad_name = root.findViewById(R.id.dad_name);
        alamat = root.findViewById(R.id.alamat);
        cb1 = root.findViewById(R.id.cb1);
        cb2 = root.findViewById(R.id.cb2);
        cb3 = root.findViewById(R.id.cb3);

        _implement();
    }

    private void _implement(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        getLokasiList();

        spinner_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Lokasi loc = lokasi.get(position);
                location_id = loc.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add.setOnClickListener(v -> _validation());
    }

    private void getLokasiList(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();
        lokasi = new ArrayList<>();

        Call<LokasiList> getLokasi = apiInterface.getLokasiList();
        getLokasi.enqueue(new Callback<LokasiList>() {
            @Override
            public void onResponse(Call<LokasiList> call, Response<LokasiList> response) {
                loadingDialog.dismis();
                if (response.body().getStatus().equals("true")){
                   lokasi.add(new Lokasi(null, "Pilih Lokasi", null));
                   lokasi.addAll(response.body().getLokasi());

                   adapter = new LokasiSpinnerAdapter(getContext(), lokasi);
                   spinner_loc.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<LokasiList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void _validation(){
        layanan = null;
        if (cb1.isChecked()){
            if (TextUtils.isEmpty(layanan)){
                layanan = "pemeriksaan";
            } else {
                layanan = layanan+",pemeriksaan";
            }
        }
        if (cb2.isChecked()){
            if (TextUtils.isEmpty(layanan)){
                layanan = "imunisasi";
            } else {
                layanan = layanan+",imunisasi";
            }
        }
        if (cb3.isChecked()){
            if (TextUtils.isEmpty(layanan)){
                layanan = "penimbangan";
            } else {
                layanan = layanan+",penimbangan";
            }
        }
        //System.out.println("LAYANAN = "+layanan);

        if (TextUtils.isEmpty(no_hp.getText()) || TextUtils.isEmpty(mom_name.getText()) || TextUtils.isEmpty(dad_name.getText())
                || TextUtils.isEmpty(mom_name.getText()) || TextUtils.isEmpty(alamat.getText()) || TextUtils.isEmpty(location_id) ||
                TextUtils.isEmpty(layanan)){
            System.out.println("Ra-Masoook");
        } else {
            System.out.println("Masoook");
        }
    }
}