package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context mContext;
    private List<User> mData;

    public UserAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_user, parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User data = mData.get(position);
        holder.email.setText(data.getUser_id());
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email, username, role;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.email);
            username = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role);
        }
    }
}


