package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.ChatRoomActivity;
import com.faaadev.postari.R;
import com.faaadev.postari.model.User;

import java.util.List;

public class PetugasAdapter extends RecyclerView.Adapter<PetugasAdapter.MyViewHolder> {
    private Context mContext;
    private List<User> mData;
    ItemClickListener itemClickListener;

    public PetugasAdapter(Context mContext, List<User> mData, ItemClickListener itemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_petugas, parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User data = mData.get(position);
        holder.username.setText(data.getUsername());
        if (data.getRole().equals("ortu")){
            holder.role.setText("Orang Tua");
        } else if (data.getRole().equals("petugas_posyandu")){
            holder.role.setText("Petugas Posyandu");
        } else if (data.getRole().equals("petugas_kesehatan")){
            holder.role.setText("Petugas Kesehatan");
        } else {
            holder.role.setText("Petugas");
        }
        holder.container.setOnClickListener(v -> {
            Intent i = new Intent(mContext, ChatRoomActivity.class);
            i.putExtra("user", data);
            i.putExtra("from", "new");
            mContext.startActivity(i);
            itemClickListener.onItemClicked("");
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, role;
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role);
            container = itemView.findViewById(R.id.container);
        }
    }
}


