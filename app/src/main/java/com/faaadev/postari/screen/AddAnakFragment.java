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
import com.faaadev.postari.screen.DismisListener;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAnakFragment extends BottomSheetDialogFragment {
    private EditText birthdate, nama;
    private int mYear, mMonth, mDay;
    private String month, day;
    private final Calendar c = Calendar.getInstance();
    private RadioButton rp, rl;
    private String gender;
    private Button btn_add;
    private DismisListener listener;
    private ApiInterface apiInterface;
    String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_add_anak, container, false);

         _init(root);

        return root;
    }

    private void _init (View root){
        birthdate = root.findViewById(R.id.birthdate);
        nama = root.findViewById(R.id.name);
        rp = root.findViewById(R.id.rp);
        rl = root.findViewById(R.id.rl);
        btn_add = root.findViewById(R.id.btn_add);

        if (getArguments() != null) {
            user_id = getArguments().getString("user_id");
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
        birthdate.setText(day + "/" + month + "/" + mYear);

        birthdate.setOnClickListener(v->{
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
                            birthdate.setText(day + "/" + month + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btn_add.setOnClickListener(v -> validation());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void validation(){
        if (rl.isChecked()){
            gender = "Laki-Laki";
        } else if (rp.isChecked()){
            gender = "Perempuan";
        } else {
            gender = "";
        }

        if (TextUtils.isEmpty(nama.getText()) || TextUtils.isEmpty(birthdate.getText()) || TextUtils.isEmpty(gender)) {
            if (TextUtils.isEmpty(nama.getText())){
                nama.setError("Bagian ini harus diisi");
            }
            if (TextUtils.isEmpty(birthdate.getText())){
                nama.setError("Bagian ini harus diisi");
            }
            if (TextUtils.isEmpty(gender)) {
                Toast.makeText(getContext(), "Silahkan pilih jenis kelamin", Toast.LENGTH_LONG).show();
            }
        } else {
            addAnak();
        }
    }

    private void addAnak(){
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addAnak = apiInterface.addAnak(user_id, nama.getText().toString(), birthdate.getText().toString(), gender);
        addAnak.enqueue(new Callback<BasicResponse>() {
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
        listener.onDismisSheet("add_anak");
    }
}