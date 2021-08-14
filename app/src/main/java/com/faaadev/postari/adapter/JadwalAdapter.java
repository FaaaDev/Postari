package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.model.Lokasi;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    Context mContext;
    List<Jadwal> mData;

    public JadwalAdapter(Context mContext, List<Jadwal> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_jadwal, parent,false);
        final JadwalAdapter.ViewHolder myViewHolder = new JadwalAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Jadwal data = mData.get(position);
        holder.tanggal.setText(data.getTanggal());
        holder.nama_posyandu.setText(data.getNamaPosyandu());
        holder.kegiatan.setText(data.getKegiatan());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, nama_posyandu, kegiatan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_posyandu = itemView.findViewById(R.id.nama_posyandu);
            kegiatan = itemView.findViewById(R.id.kegiatan);
            tanggal = itemView.findViewById(R.id.tanggal);
        }
    }
}
