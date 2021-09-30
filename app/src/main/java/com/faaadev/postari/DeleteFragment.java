package com.faaadev.postari;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.screen.DismisListener;
import com.faaadev.postari.service.BasicResponse;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteFragment extends BottomSheetDialogFragment {

    DismisListener listener;
    private String from = "";
    private String table, param, where;
    private CardView delete, cancel;
    private TextView txt_delete;
    private ProgressBar loading;
    private ApiInterface apiInterface;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet =  d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delete, container, false);

        _init(root);

        return root;
    }

    private void _init(View root){
        delete = root.findViewById(R.id.delete);
        cancel = root.findViewById(R.id.cancel);
        txt_delete = root.findViewById(R.id.txt_delete);
        loading = root.findViewById(R.id.loading);

        _implement();
    }

    private void _implement(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        cancel.setOnClickListener(v -> dismiss());

        delete.setOnClickListener(v -> doDelete());

        setLoading(false);
    }

    private void setLoading(Boolean load){
        if ( load ){
            delete.setEnabled(false);
            cancel.setEnabled(false);
            loading.setVisibility(View.VISIBLE);
            txt_delete.setText("Menghapus");
        } else {
            delete.setEnabled(true);
            cancel.setEnabled(true);
            loading.setVisibility(View.GONE);
            txt_delete.setText("Hapus Data");
        }
    }

    private void doDelete(){
        setLoading(true);
        Call<BasicResponse> delete = apiInterface.deleteRecord(table, param, where);
        delete.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if (!table.equals("orang_tua")) {
                    setLoading(false);
                }
                if (response.body().getStatus()){
                    if (!table.equals("orang_tua")) {
                        dismiss();
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        deleteAnak();
                    }
                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(getContext(), "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteAnak(){
        setLoading(true);
        Call<BasicResponse> delete = apiInterface.deleteRecord("anak", param, where);
        delete.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                setLoading(false);
                if (response.body().getStatus()){
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                setLoading(false);
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
        listener.onDismisSheet(from);
    }
}