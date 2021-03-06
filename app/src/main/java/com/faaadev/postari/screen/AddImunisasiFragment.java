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
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Imunisasi;
import com.faaadev.postari.screen.DismisListener;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImunisasiFragment extends BottomSheetDialogFragment {
    private EditText date, ket;
    private TextView title;
    private int mYear, mMonth, mDay;
    private String month, day;
    private final Calendar c = Calendar.getInstance();
    private Button btn_add;
    private DismisListener listener;
    private ApiInterface apiInterface;
    private String id_anak;
    RadioButton rvit, rimunisasi;
    private String type;
    private Imunisasi imunisasi = new Imunisasi();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_imunisasi, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        date = root.findViewById(R.id.date);
        ket = root.findViewById(R.id.ket);
        btn_add = root.findViewById(R.id.btn_add);
        rvit = root.findViewById(R.id.rvit);
        rimunisasi = root.findViewById(R.id.rimunisasi);
        title = root.findViewById(R.id.title);

        if (getArguments() != null) {
            id_anak = getArguments().getString("id");
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

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        btn_add.setOnClickListener(v -> {
            validation(false);
        });

        if (getArguments() != null) {
            if (getArguments().getBoolean("isUpdate")) {
                title.setText("Edit Imunisasi");
                btn_add.setText("Perbarui Data Imunisasi");
                imunisasi = (Imunisasi) getArguments().getSerializable("data");
                id_anak = imunisasi.getIdAnak();
                date.setText(imunisasi.getDate());
                ket.setText(imunisasi.getKeterangan());
                if (imunisasi.getType().equals("Imunisasi")) {
                    rimunisasi.setChecked(true);
                } else if (imunisasi.getType().equals("Vitamin")) {
                    rvit.setChecked(true);
                } else {

                }

                btn_add.setOnClickListener(v -> {
                    validation(true);
                });
            }
        }
    }

    private void validation(boolean update){
        if (rvit.isChecked()){
            type = "Vitamin";
        } else if (rimunisasi.isChecked()){
            type = "Imunisasi";
        } else {
            type = "";
        }

        if (TextUtils.isEmpty(date.getText()) || TextUtils.isEmpty(ket.getText()) || TextUtils.isEmpty(type)){
            if (TextUtils.isEmpty(type)){
                Toast.makeText(getContext(), "Pilih Imunisasi/Vitamin", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(date.getText())){
                date.setError("Silahkan isi tanggal");
            }
            if (TextUtils.isEmpty(ket.getText())){
                ket.setError("Silahkan isi jenis imunisasi/vitamin");
            }
        } else {
            if (update) {
                updateImunisasi();
            } else {
                addImunisasi();
            }
        }
    }

    private void addImunisasi(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addImunisasi = apiInterface.addImunisasi(id_anak, type, ket.getText().toString(), date.getText().toString());
        addImunisasi.enqueue(new Callback<BasicResponse>() {
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

    private void updateImunisasi() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addImunisasi = apiInterface.editImunisasi(id_anak, type, ket.getText().toString(), date.getText().toString(), imunisasi.getId());
        addImunisasi.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                loadingDialog.dismis();
                if (response.body().getStatus()){
                    Toast.makeText(getContext(), "Data berhasil diubah", Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Data gagal diubah", Toast.LENGTH_LONG).show();
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
        listener.onDismisSheet("imunisasi");
    }
}