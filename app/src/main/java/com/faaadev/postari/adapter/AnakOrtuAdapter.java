package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Anak;

import java.util.List;

public class AnakOrtuAdapter extends RecyclerView.Adapter<AnakOrtuAdapter.ViewHolder>{
    Context mContext;
    List<Anak> mData;

    public AnakOrtuAdapter(Context mContext, List<Anak> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
        //return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView posisi, nama_anak, birthdate, geder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            posisi = itemView.findViewById(R.id.posisi);
            nama_anak = itemView.findViewById(R.id.nama_anak);
            birthdate = itemView.findViewById(R.id.birthdate);
            geder = itemView.findViewById(R.id.gender);
        }
    }
}
