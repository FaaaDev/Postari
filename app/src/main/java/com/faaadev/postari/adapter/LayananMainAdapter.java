package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Layanan;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.screen.DataAnakActivity;
import com.faaadev.postari.screen.DetailAnakActivity;

import java.util.List;

public class LayananMainAdapter extends RecyclerView.Adapter<LayananMainAdapter.ViewHolder>{
    Context mContext;
    List<Layanan> mData;
    Ortu ortu;

    public LayananMainAdapter(Context mContext, List<Layanan> mData, Ortu ortu) {
        this.mContext = mContext;
        this.mData = mData;
        this.ortu = ortu;
    }

    @NonNull
    @Override
    public LayananMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_layanan_main, parent,false);
        final LayananMainAdapter.ViewHolder myViewHolder = new LayananMainAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LayananMainAdapter.ViewHolder holder, int position) {
        Layanan data = mData.get(position);
        if(data.getNama().equals("pemeriksaan")){
            holder.primary.setBackgroundColor(Color.argb(255, 202, 126, 61));
            holder.second.setBackgroundColor(Color.argb(255, 236, 170, 115));
            holder.img.setImageResource(R.drawable.ic__13_maternity_5);
            holder.layanan.setText("Pemeriksaan Ibu Hamil");
            holder.card.setOnClickListener(v -> {
                Intent i = new Intent(mContext, DetailAnakActivity.class);
                i.putExtra("type", "pemeriksaan");
                i.putExtra("ortu", ortu);
                mContext.startActivity(i);
            });
        } else if(data.getNama().equals("penimbangan")){
            holder.primary.setBackgroundColor(Color.argb(255, 161, 232, 100));
            holder.second.setBackgroundColor(Color.argb(255, 236, 170, 115));
            holder.img.setImageResource(R.drawable.ic__31_scale);
            holder.layanan.setText("Penimbangan");
            holder.card.setOnClickListener(v -> {
                Intent i = new Intent(mContext, DataAnakActivity.class);
                i.putExtra("type", "penimbangan");
                mContext.startActivity(i);
            });
        } else {
            holder.primary.setBackgroundColor(Color.argb(255, 43, 152, 140));
            holder.second.setBackgroundColor(Color.argb(255, 103, 195, 185));
            holder.img.setImageResource(R.drawable.ic__09_injection);
            holder.layanan.setText("Imunisasi");
            holder.card.setOnClickListener(v -> {
                Intent i = new Intent(mContext, DataAnakActivity.class);
                i.putExtra("type", "imunisasi");
                mContext.startActivity(i);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView layanan;
        LinearLayout second;
        RelativeLayout primary;
        ImageView img;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layanan = itemView.findViewById(R.id.layanan);
            second = itemView.findViewById(R.id.second);
            primary = itemView.findViewById(R.id.primary);
            img = itemView.findViewById(R.id.img);
            card = itemView.findViewById(R.id.card);
        }
    }
}
