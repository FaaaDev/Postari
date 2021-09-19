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
import com.faaadev.postari.R;
import com.faaadev.postari.model.Anak;

import java.util.List;

public class AnakAdapter extends RecyclerView.Adapter<AnakAdapter.ViewHolder>{
    Context mContext;
    List<Anak> mData;
    FragmentManager mFm;
    String type;


    public AnakAdapter(Context mContext, List<Anak> mData, FragmentManager supportFragmentManager, String type) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = supportFragmentManager;
        this.type = type;

    }

    @NonNull
    @Override
    public AnakAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_anak, parent,false);
        final AnakAdapter.ViewHolder myViewHolder = new AnakAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnakAdapter.ViewHolder holder, int position) {
        Anak data = mData.get(position);
        if (type.equals("penimbangan")){
            holder.tag_color.setBackgroundColor(Color.argb(255, 161, 232,100));
        }
        holder.user_id.setText(data.getUser_id());
        holder.nama.setText(data.getNama());
        holder.geder.setText(data.getGender());
        holder.card.setOnClickListener(v -> {
            Intent i = new Intent(mContext, DetailAnakActivity.class);
            i.putExtra("anak", data);
            i.putExtra("type", type);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
        //return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, user_id, geder;
        LinearLayout tag_color;
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_id = itemView.findViewById(R.id.userid);
            nama = itemView.findViewById(R.id.nama);
            geder = itemView.findViewById(R.id.gender);
            tag_color = itemView.findViewById(R.id.tag_color);
            card = itemView.findViewById(R.id.card);
        }
    }
}
