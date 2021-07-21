package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Lokasi;

import java.util.ArrayList;
import java.util.List;

public class LokasiSpinnerAdapter extends ArrayAdapter<Lokasi> {

    public LokasiSpinnerAdapter(Context context, List<Lokasi> mData){
        super(context, 0, mData);

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_spinner, parent, false
            );
        }

        TextView name, alamat;
        name = convertView.findViewById(R.id.name);
        alamat = convertView.findViewById(R.id.alamat);

        Lokasi data = getItem(position);

        if (data!=null){
            name.setText(data.getNamaPosyandu());
            if (data.getAlamat()!=null){
                alamat.setVisibility(View.VISIBLE);
                alamat.setText(data.getAlamat());
            } else {
                alamat.setVisibility(View.GONE);
            }
        }

        return convertView;
    }
}
