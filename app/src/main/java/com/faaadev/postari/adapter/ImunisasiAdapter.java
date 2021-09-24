package com.faaadev.postari.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.DeleteFragment;
import com.faaadev.postari.R;
import com.faaadev.postari.http.Preferences;
import com.faaadev.postari.model.Imunisasi;
import com.faaadev.postari.model.Penimbangan;
import com.faaadev.postari.screen.AddAnakFragment;
import com.faaadev.postari.screen.AddImunisasiFragment;

import java.util.List;

public class ImunisasiAdapter extends RecyclerView.Adapter<ImunisasiAdapter.ViewHolder>{
    Context mContext;
    List<Imunisasi> mData;
    FragmentManager mFm;

    public ImunisasiAdapter(Context mContext, List<Imunisasi> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
    }

    @NonNull
    @Override
    public ImunisasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_imunisasi, parent,false);
        final ImunisasiAdapter.ViewHolder myViewHolder = new ImunisasiAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImunisasiAdapter.ViewHolder holder, int position) {
        Imunisasi data = mData.get(position);
        holder.date.setText(data.getDate());
        holder.jenis.setText(data.getKeterangan());
        holder.type.setText(data.getType());
        if (data.getType().equals("Vitamin")){
            holder.tag_type.setBackgroundColor(Color.argb(255,237,120,120));
        }
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
            AddImunisasiFragment addImunisasiFragment = new AddImunisasiFragment();
            addImunisasiFragment.setArguments(bundle);
            addImunisasiFragment.show(mFm, addImunisasiFragment.getTag());
        });

        holder.delete.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            deleteFragment.setTable("imunisasi");
            deleteFragment.setParam("id");
            deleteFragment.setWhere(data.getId());
            deleteFragment.setCancelable(false);
            deleteFragment.show(mFm, deleteFragment.getTag());
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
        //return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, jenis, type;
        LinearLayout tag_type;
        CardView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            jenis = itemView.findViewById(R.id.jenis);
            type = itemView.findViewById(R.id.type);
            tag_type = itemView.findViewById(R.id.tag_type);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
