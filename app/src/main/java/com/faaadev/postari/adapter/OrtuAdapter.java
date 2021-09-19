package com.faaadev.postari.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.screen.DetailAnakActivity;
import com.faaadev.postari.screen.DetailOrtuActivity;
import com.faaadev.postari.R;
import com.faaadev.postari.model.Ortu;

import java.util.List;

public class OrtuAdapter extends RecyclerView.Adapter<OrtuAdapter.MyViewHolder> {
    private Context mContext;
    private List<Ortu> mData;
    private FragmentManager mFm;
    private Boolean pemeriksaan = false;

    public OrtuAdapter(Context mContext, List<Ortu> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
    }

    public OrtuAdapter(Context mContext, List<Ortu> mData, FragmentManager mFm, Boolean pemeriksaan) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
        this.pemeriksaan = pemeriksaan;
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
        holder.posyandu.setText(data.getPosyandu());
        if (pemeriksaan){
            holder.username.setText("Ibu "+data.getMom_name());
            holder.tag_color.setBackgroundColor(Color.argb(255,202,126,61));
            holder.card.setOnClickListener(v -> {
                Intent i = new Intent(mContext, DetailAnakActivity.class);
                i.putExtra("type", "pemeriksaan");
                i.putExtra("ortu", data);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            });
            holder.delete.setVisibility(View.GONE);
        } else {
            holder.username.setText("Bp. "+data.getDad_name()+" & Ibu "+data.getMom_name());
            holder.card.setOnClickListener(v -> {
                Intent i = new Intent(mContext, DetailOrtuActivity.class);
                i.putExtra("ortu", data);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            });
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userid, username, posyandu;
        CardView card, delete;
        LinearLayout tag_color;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            userid = itemView.findViewById(R.id.userid);
            username = itemView.findViewById(R.id.username);
            posyandu = itemView.findViewById(R.id.posyandu);
            card = itemView.findViewById(R.id.card);
            tag_color = itemView.findViewById(R.id.tag_color);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}


