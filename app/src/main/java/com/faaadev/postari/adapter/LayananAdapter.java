package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.model.Lokasi;

import java.util.List;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.ViewHolder>{
    Context mContext;
    List<Layanan> mData;

    public LayananAdapter(Context mContext, List<Layanan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public LayananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_layanan, parent,false);
        final LayananAdapter.ViewHolder myViewHolder = new LayananAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayananAdapter.ViewHolder holder, int position) {
        Layanan data = mData.get(position);
        if(data.getNama().equals("pemeriksaan")){
            holder.layanan.setText("Pemeriksaan Ibu Hamil");
        } else if(data.getNama().equals("penimbangan")){
            holder.layanan.setText("Penimbangan");
        } else {
            holder.layanan.setText("Imunisasi");
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView layanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layanan = itemView.findViewById(R.id.layanan);
        }
    }
}
