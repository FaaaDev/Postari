package com.faaadev.postari.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.faaadev.postari.http.ApiClient;
import com.faaadev.postari.http.ApiInterface;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.screen.LoginActivity;
import com.faaadev.postari.service.BasicResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoreDeleteRecord {
    String table, param, where;
    ApiInterface apiInterface;
    Context context;
    boolean status = true;

    public CoreDeleteRecord(String table, String param, String where, Context context) {
        this.table = table;
        this.param = param;
        this.where = where;
        this.context = context;
    }

    public boolean delete() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        LoadingDialog loadingDialog = new LoadingDialog((Activity) context);
        loadingDialog.startLoading();

        Call<BasicResponse> delete = apiInterface.deleteRecord(table, param, where);
        delete.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                loadingDialog.dismis();
                if (response.body().getStatus()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    status = true;
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    status = false;
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                loadingDialog.dismis();
                Toast.makeText(context, "Kesalahan saat menghubungi server", Toast.LENGTH_LONG).show();
                status = false;
            }
        });
        return status;
    }

}
