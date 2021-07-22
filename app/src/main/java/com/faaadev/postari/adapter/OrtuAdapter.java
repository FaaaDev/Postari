package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.model.User;

import java.util.List;

public class OrtuAdapter extends RecyclerView.Adapter<OrtuAdapter.MyViewHolder> {
    private Context mContext;
    private List<Ortu> mData;

    public OrtuAdapter(Context mContext, List<Ortu> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_ortu, parent,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ortu data = mData.get(position);
        holder.userid.setText(data.getUser_id());
        holder.username.setText("Bp. "+data.getDad_name()+" & Ibu "+data.getMom_name());
        holder.posyandu.setText(data.getPosyandu());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userid, username, posyandu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.userid);
            username = itemView.findViewById(R.id.username);
            posyandu = itemView.findViewById(R.id.posyandu);
        }
    }
}


