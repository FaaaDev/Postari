package com.faaadev.postari.screen;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Pemeriksaan;
import com.faaadev.postari.screen.DismisListener;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPemeriksaanFragment extends BottomSheetDialogFragment {

    private EditText date, periksa_kembali, tekanan, weight, umur, tinggi_fundus, letak_janin,
            denyut_janin, keluhan, pem_lab, tindakan, nasihat;
    private RadioButton ryes, rno;
    private TextView title;
    private Button btn_add;
    private int mYear, mMonth, mDay;
    private String month, day;
    private final Calendar c = Calendar.getInstance();
    private ApiInterface apiInterface;
    private String bengkak, user_id;
    private DismisListener listener;
    private Pemeriksaan pemeriksaan = new Pemeriksaan();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_pemeriksaan, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        date = root.findViewById(R.id.date);
        periksa_kembali = root.findViewById(R.id.periksa_kembali);
        tekanan = root.findViewById(R.id.tekanan);
        weight = root.findViewById(R.id.weight);
        umur = root.findViewById(R.id.umur);
        tinggi_fundus = root.findViewById(R.id.tinggi_fundus);
        letak_janin = root.findViewById(R.id.letak_janin);
        denyut_janin = root.findViewById(R.id.denyut_janin);
        keluhan = root.findViewById(R.id.keluhan);
        ryes = root.findViewById(R.id.ryes);
        rno = root.findViewById(R.id.rno);
        pem_lab = root.findViewById(R.id.pem_lab);
        tindakan = root.findViewById(R.id.tindakan);
        nasihat = root.findViewById(R.id.nasihat);
        btn_add = root.findViewById(R.id.btn_add);
        title = root.findViewById(R.id.title);

        if (getArguments() != null) {
            user_id = getArguments().getString("id");
        }

        _implement();
    }

    private void _implement(){
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        if (mMonth < 10){
            month = "0"+(mMonth+1);
        } else {
            month = ""+(mMonth+1);
        }
        if (mDay < 10){
            day = "0"+mDay;
        } else {
            day = ""+mDay;
        }
        date.setText(day + "/" + month + "/" + mYear);

        rno.setChecked(true);

        date.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (monthOfYear+1 < 10){
                                month = "0"+(monthOfYear+1);
                            } else {
                                month = ""+(monthOfYear+1);
                            }
                            if (dayOfMonth < 10){
                                day = "0"+dayOfMonth;
                            } else {
                                day = ""+dayOfMonth;
                            }
                            date.setText(day + "/" + month + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        periksa_kembali.setOnClickListener(v->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            if (monthOfYear+1 < 10){
                                month = "0"+(monthOfYear+1);
                            } else {
                                month = ""+(monthOfYear+1);
                            }
                            if (dayOfMonth < 10){
                                day = "0"+dayOfMonth;
                            } else {
                                day = ""+dayOfMonth;
                            }
                            periksa_kembali.setText(day + "/" + month + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btn_add.setOnClickListener(v -> {
            validation(false);
        });

        if (getArguments() != null) {
            if (getArguments().getBoolean("isUpdate")) {
                title.setText("Edit Pemeriksaan");
                btn_add.setText("Perbarui Data Pemeriksaan");
                pemeriksaan = (Pemeriksaan) getArguments().getSerializable("data");
                user_id = pemeriksaan.getUserId();
                date.setText(pemeriksaan.getTanggal());
                tekanan.setText(pemeriksaan.getTekanan());
                weight.setText(pemeriksaan.getWeight());
                umur.setText(pemeriksaan.getUmur());
                tinggi_fundus.setText(pemeriksaan.getTinggiFundus());
                letak_janin.setText(pemeriksaan.getLetakJanin());
                denyut_janin.setText(pemeriksaan.getDenyutJanin());
                keluhan.setText(pemeriksaan.getKeluhan());
                if (pemeriksaan.getKakiBengkak().equals("Ya")) {
                    ryes.setChecked(true);
                } else if (pemeriksaan.getKakiBengkak().equals("Tidak")) {
                    rno.setChecked(true);
                } else {

                }
                pem_lab.setText(pemeriksaan.getPemeriksaanLab());
                tindakan.setText(pemeriksaan.getTindakan());
                nasihat.setText(pemeriksaan.getNasihat());
                periksa_kembali.setText(pemeriksaan.getPeriksaKembali());

                btn_add.setOnClickListener(v -> {
                    validation(true);
                });
            }
        }
    }

    private void validation(boolean update){
        if(ryes.isChecked()){
            bengkak = "Ya";
        } else {
            bengkak = "Tidak";
        }

        if (TextUtils.isEmpty(tekanan.getText()) || TextUtils.isEmpty(weight.getText())
                || TextUtils.isEmpty(umur.getText()) || TextUtils.isEmpty(tinggi_fundus.getText())
                || TextUtils.isEmpty(letak_janin.getText()) || TextUtils.isEmpty(denyut_janin.getText())
                || TextUtils.isEmpty(periksa_kembali.getText())){
            if (TextUtils.isEmpty(tekanan.getText())){
                tekanan.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(weight.getText())){
                weight.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(umur.getText())){
                umur.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(letak_janin.getText())){
                letak_janin.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(denyut_janin.getText())){
                denyut_janin.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(periksa_kembali.getText())){
                periksa_kembali.setError("Bagian ini wajib diisi");
            }
        } else {
            if (update) {
                updatePemeriksaan();
            } else {
                addPemeriksaan();
            }
        }
    }

    private void addPemeriksaan(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addPemeriksaan = apiInterface.addPemeriksaan(
                user_id,
                date.getText().toString(),
                keluhan.getText().toString(),
                tekanan.getText().toString(),
                weight.getText().toString(),
                umur.getText().toString(),
                tinggi_fundus.getText().toString(),
                letak_janin.getText().toString(),
                denyut_janin.getText().toString(),
                bengkak,
                pem_lab.getText().toString(),
                tindakan.getText().toString(),
                nasihat.getText().toString(),
                Preferences.getUsername(getContext()),
                periksa_kembali.getText().toString()
        );
        addPemeriksaan.enqueue(new Callback<BasicResponse>() {
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
                System.out.println("ERRORR == "+t.toString());
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePemeriksaan() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addPemeriksaan = apiInterface.editPemeriksaan(
                user_id,
                date.getText().toString(),
                keluhan.getText().toString(),
                tekanan.getText().toString(),
                weight.getText().toString(),
                umur.getText().toString(),
                tinggi_fundus.getText().toString(),
                letak_janin.getText().toString(),
                denyut_janin.getText().toString(),
                bengkak,
                pem_lab.getText().toString(),
                tindakan.getText().toString(),
                nasihat.getText().toString(),
                Preferences.getUsername(getContext()),
                periksa_kembali.getText().toString(),
                pemeriksaan.getId()
        );
        addPemeriksaan.enqueue(new Callback<BasicResponse>() {
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
                System.out.println("ERRORR == "+t.toString());
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
        listener.onDismisSheet("pemeriksaan");
    }
}