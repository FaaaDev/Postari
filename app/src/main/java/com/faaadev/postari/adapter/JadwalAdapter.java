package com.faaadev.postari.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.screen.DeleteFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.screen.AddJadwalPosyanduFragment;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.ViewHolder> {

    Context mContext;
    List<Jadwal> mData;
    FragmentManager mFm;

    public JadwalAdapter(Context mContext, List<Jadwal> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
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
        System.out.println("ROLE === "+Preferences.getRole(mContext));
        if (Preferences.getRole(mContext).equals("ortu")){
            holder.edit.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.edit.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
        }

        holder.edit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            bundle.putBoolean("isUpdate", true);
            AddJadwalPosyanduFragment addJadwalPosyanduFragment = new AddJadwalPosyanduFragment();
            addJadwalPosyanduFragment.setArguments(bundle);
            addJadwalPosyanduFragment.show(mFm, addJadwalPosyanduFragment.getTag());
        });

        holder.delete.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            deleteFragment.setTable("jadwal");
            deleteFragment.setParam("id");
            deleteFragment.setWhere(data.getIdLokasi());
            deleteFragment.setCancelable(false);
            deleteFragment.show(mFm, deleteFragment.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal, nama_posyandu, kegiatan;
        CardView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_posyandu = itemView.findViewById(R.id.nama_posyandu);
            kegiatan = itemView.findViewById(R.id.kegiatan);
            tanggal = itemView.findViewById(R.id.tanggal);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
