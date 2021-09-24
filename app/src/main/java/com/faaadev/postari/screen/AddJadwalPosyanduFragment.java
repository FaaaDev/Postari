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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.adapter.LokasiSpinnerAdapter;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddJadwalPosyanduFragment extends BottomSheetDialogFragment {

    private TextView date, kegiatan, title;
    private Spinner spinner_loc;
    private LokasiSpinnerAdapter adapter;
    private String location_id;
    private ApiInterface apiInterface;
    private List<Lokasi> lokasi;
    private int mYear, mMonth, mDay;
    private final Calendar c = Calendar.getInstance();
    private String month, day;
    private DismisListener listener;
    private Button btn_add;
    String id_jadwal;
    private Jadwal jadwal = new Jadwal();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_jadwal_posyandu, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        spinner_loc = root.findViewById(R.id.spinner_loc);
        date = root.findViewById(R.id.date);
        kegiatan = root.findViewById(R.id.kegiatan);
        btn_add = root.findViewById(R.id.btn_add);
        title = root.findViewById(R.id.title);

        _implement();
    }

    private void _implement(){
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

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

        btn_add.setOnClickListener(v -> _validation(false));

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
                    setUpdate();
                }
            }

            @Override
            public void onFailure(Call<LokasiList> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpdate() {
        if (getArguments() != null) {
            if (getArguments().getBoolean("isUpdate")) {
                title.setText("Edit Anak");
                btn_add.setText("Perbarui Data Anak");
                jadwal = (Jadwal) getArguments().getSerializable("data");
                date.setText(jadwal.getTanggal());
                kegiatan.setText(jadwal.getKegiatan());
                if (lokasi.size() > 0) {
                    for (int x = 0; x < lokasi.size(); x++) {
                        if (lokasi.get(x).getNamaPosyandu().equals(jadwal.getNamaPosyandu())) {
                            spinner_loc.setSelection(x);
                            //return;
                        }
                    }
                }

                btn_add.setOnClickListener(v -> {
                    _validation(true);
                });
            }
        }
    }

    private void _validation(boolean update){
        if (TextUtils.isEmpty(location_id) || TextUtils.isEmpty(date.getText()) || TextUtils.isEmpty(kegiatan.getText())){
            if (TextUtils.isEmpty(location_id)){
                Toast.makeText(getContext(), "Silahkan pilih Lokasi Posyandu", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(date.getText())){
                date.setError("Silahkan pilih tanggal");
            }
            if (TextUtils.isEmpty(kegiatan.getText())){
                date.setError("Bagian ini harus diisi");
            }
        } else {
            if (update) {
                updateJadwal();
            } else {
                addJadwal();
            }
        }
    }

    private void addJadwal(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addJadwal = apiInterface.addJadwal(
                location_id,
                date.getText().toString(),
                kegiatan.getText().toString()
        );
        addJadwal.enqueue(new Callback<BasicResponse>() {
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

    private void updateJadwal() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addJadwal = apiInterface.editJadwal(
                location_id,
                date.getText().toString(),
                kegiatan.getText().toString(),
                jadwal.getId()
        );
        addJadwal.enqueue(new Callback<BasicResponse>() {
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
        listener.onDismisSheet("");
    }
}