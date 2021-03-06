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
import com.faaadev.postari.model.Penimbangan;
import com.faaadev.postari.screen.AddPenimbanganFragment;

import java.util.List;

public class PenimbanganAdapter extends RecyclerView.Adapter<PenimbanganAdapter.ViewHolder>{
    Context mContext;
    List<Penimbangan> mData;
    private FragmentManager mFm;

    public PenimbanganAdapter(Context mContext, List<Penimbangan> mData, FragmentManager mFm) {
        this.mContext = mContext;
        this.mData = mData;
        this.mFm = mFm;
    }

    @NonNull
    @Override
    public PenimbanganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_penimbangan, parent,false);
        final PenimbanganAdapter.ViewHolder myViewHolder = new PenimbanganAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PenimbanganAdapter.ViewHolder holder, int position) {
        Penimbangan data = mData.get(position);
        holder.date.setText(data.getDate());
        holder.tall.setText("Tinggi Badan : "+data.getTall()+" Cm");
        holder.weight.setText("Berat Badan : "+data.getWeight()+" Kg");
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
            AddPenimbanganFragment addPenimbanganFragment = new AddPenimbanganFragment();
            addPenimbanganFragment.setArguments(bundle);
            addPenimbanganFragment.show(mFm, addPenimbanganFragment.getTag());
        });

        holder.delete.setOnClickListener(v -> {
            DeleteFragment deleteFragment = new DeleteFragment();
            deleteFragment.setTable("penimbangan");
            deleteFragment.setFrom("penimbangan");
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
        TextView date, weight, tall;
        CardView edit, delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            weight = itemView.findViewById(R.id.weight);
            tall = itemView.findViewById(R.id.tall);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
        }
    }
}
