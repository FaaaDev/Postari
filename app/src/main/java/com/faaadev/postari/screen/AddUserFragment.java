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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.faaadev.postari.R;
import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.widget.LoadingDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddUserFragment extends BottomSheetDialogFragment {

    private EditText userid, username, password;
    private RadioButton rpetugas, rortu;
    private Button btn_add;
    private ApiInterface apiInterface;
    private RadioGroup rgrole;
    private String role;
    DismisListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_user, container, false);

        _init(root);

        return root;
    }

    private void _init(View root) {
        userid = root.findViewById(R.id.userid);
        username = root.findViewById(R.id.username);
        password = root.findViewById(R.id.password);
        rortu = root.findViewById(R.id.rortu);
        rpetugas = root.findViewById(R.id.rpetugas);
        btn_add = root.findViewById(R.id.btn_add);
        rgrole = root.findViewById(R.id.rgrole);

        _implement();
    }

    private void _implement() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        
        btn_add.setOnClickListener(v -> {
            _validation();
        });
    }

    private void _validation() {
        if (rortu.isChecked()) {
            role = "ortu";
        } else if (rpetugas.isChecked()){
            role = "petugas";
        } else {
            role = "";
        }

        if (TextUtils.isEmpty(userid.getText()) || TextUtils.isEmpty(username.getText())
                || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(role)) {
            if (TextUtils.isEmpty(userid.getText())) {
                userid.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(username.getText())) {
                username.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(password.getText())) {
                password.setError("Bagian ini wajib diisi");
            }
            if (TextUtils.isEmpty(role)) {
                Toast.makeText(getContext(), "Silahkan pilih Petugas/Orang Tua", Toast.LENGTH_LONG).show();
            }
        } else {
            addAccount();
        }
    }

    private void addAccount() {
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoading();

        Call<BasicResponse> addUser = apiInterface.addUser(
                userid.getText().toString(),
                username.getText().toString(),
                password.getText().toString(),
                role,
                "");
        addUser.enqueue(new Callback<BasicResponse>() {
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
        listener.onDismisSheet();
    }

    public interface DismisListener{
        void onDismisSheet();
    }
}