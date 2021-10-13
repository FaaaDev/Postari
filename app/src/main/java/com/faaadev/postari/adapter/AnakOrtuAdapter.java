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
import com.faaadev.postari.model.Anak;
import com.faaadev.postari.screen.AddAnakFragment;

import java.util.List;

public class AnakOrtuAdapter extends RecyclerView.Adapter<AnakOrtuAdapter.ViewHolder>{
    Context mContext;
    List<Anak> mData;
    FragmentManager mFm;

    public AnakOrtuAdapter(Context mContext, List<Anak> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
    }

    @NonNull
    @Override
    public AnakOrtuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_anak_ortu, parent,false);
        final AnakOrtuAdapter.ViewHolder myViewHolder = new AnakOrtuAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnakOrtuAdapter.ViewHolder holder, int position) {
        Anak data = mData.get(position);
        holder.posisi.setText("Data Anak ke-"+(position+1));
        holder.nama_anak.setText("Nama : "+data.getNama());
        holder.birthdate.setText("Tanggal Lahir : "+data.getBirthdate());
        holder.geder.setText("Jenis Kelamin : "+data.getGender());
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
            AddAnakFragment addAnakFragment = new AddAnakFragment();
            addAnakFragment.setArguments(bundle);
            addAnakFragment.show(mFm, addAnakFragment.getTag());
        });

        holder.delete.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            deleteFragment.setFrom("add_anak");
            deleteFragment.setTable("anak");
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
        TextView posisi, nama_anak, birthdate, geder;
        CardView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posisi = itemView.findViewById(R.id.posisi);
            nama_anak = itemView.findViewById(R.id.nama_anak);
            birthdate = itemView.findViewById(R.id.birthdate);
            geder = itemView.findViewById(R.id.gender);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
