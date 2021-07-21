package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Lokasi;

import java.util.List;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.ViewHolder> {

    Context mContext;
    List<Lokasi> mData;

    public LokasiAdapter(Context mContext, List<Lokasi> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_lokasi, parent,false);
        final LokasiAdapter.ViewHolder myViewHolder = new LokasiAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lokasi data = mData.get(position);
        holder.nama_posyandu.setText(data.getNamaPosyandu());
        holder.lokasi.setText(data.getAlamat());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_posyandu, lokasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_posyandu = itemView.findViewById(R.id.nama_posyandu);
            lokasi = itemView.findViewById(R.id.lokasi);
        }
    }
}
