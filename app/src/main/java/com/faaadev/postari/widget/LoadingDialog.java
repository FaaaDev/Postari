package com.faaadev.postari.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.faaadev.postari.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    ImageView img_loading;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.TransparentDialog);
        LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
        View view = inflater.inflate(R.layout.loading_layout, null);
        img_loading = view.findViewById(R.id.loading_img);
        Glide.with(activity.getApplicationContext()).asGif().load(R.drawable.loading).into(img_loading);
        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()){
                return;
            }
        }
        alertDialog.show();
    }

    public void dismis(){
        if (alertDialog.isShowing()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (activity.isDestroyed()){
                    return;
                }
            }
            alertDialog.dismiss();
        }
    }
}
