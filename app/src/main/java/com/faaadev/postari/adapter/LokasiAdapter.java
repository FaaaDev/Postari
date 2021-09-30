package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.DeleteFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Lokasi;
import com.faaadev.postari.screen.AddJadwalPosyanduFragment;
import com.faaadev.postari.screen.AddLocationPosyanduFragment;

import java.util.List;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.ViewHolder> {

    Context mContext;
    List<Lokasi> mData;
    FragmentManager mFm;

    public LokasiAdapter(Context mContext, List<Lokasi> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
        //this.mlokasi = adapter;
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
        if (Preferences.getRole(mContext).equals("ortu")){
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }

        holder.card.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(data.getUrl())) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(data.getUrl()));
                mContext.startActivity(intent);
            }
        });

        holder.edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            bundle.putBoolean("isUpdate", true);
            AddLocationPosyanduFragment addLocationPosyanduFragment = new AddLocationPosyanduFragment();
            addLocationPosyanduFragment.setArguments(bundle);
            addLocationPosyanduFragment.show(mFm, addLocationPosyanduFragment.getTag());
        });

        holder.delete.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            deleteFragment.setTable("lokasi_posyandu");
            deleteFragment.setFrom("lokasi");
            deleteFragment.setParam("id");
            deleteFragment.setWhere(data.getId());
            deleteFragment.setCancelable(false);
            deleteFragment.show(mFm, deleteFragment.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_posyandu, lokasi;
        CardView edit, delete, card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_posyandu = itemView.findViewById(R.id.nama_posyandu);
            lokasi = itemView.findViewById(R.id.lokasi);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
            card = itemView.findViewById(R.id.card);
        }
    }
}
