package com.faaadev.postari.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faaadev.postari.R;
import com.faaadev.postari.model.Pemeriksaan;
import com.faaadev.postari.model.Penimbangan;

import java.util.List;

public class PemeriksaanAdapter extends RecyclerView.Adapter<PemeriksaanAdapter.ViewHolder>{
    Context mContext;
    List<Pemeriksaan> mData;

    public PemeriksaanAdapter(Context mContext, List<Pemeriksaan> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public PemeriksaanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.item_pemeriksaan, parent,false);
        final PemeriksaanAdapter.ViewHolder myViewHolder = new PemeriksaanAdapter.ViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PemeriksaanAdapter.ViewHolder holder, int position) {
        Pemeriksaan data = mData.get(position);
        holder.date.setText(data.getTanggal());
        holder.tekanan.setText("Tekanan Darah : "+ data.getTekanan()+ " mmHg");
        holder.weight.setText("Berat Badan : "+data.getWeight()+" Kg");
        holder.umur.setText("Umur Hamil : "+data.getUmur()+" Bulan");
        holder.tinggi_fundus.setText("Tinggi Fundus : "+data.getTinggiFundus());
        holder.letak_janin.setText("Letak Janin : "+data.getLetakJanin());
        holder.denyut_janin.setText("Denyut Janin : "+data.getDenyutJanin());
        holder.kaki_bengkak.setText("Kaki Bengkak : "+data.getKakiBengkak());
        holder.keluhan.setText(data.getKeluhan().isEmpty() ? "Keluhan : -" : "Keluhan : "+data.getKeluhan());
        holder.pem_lab.setText(data.getPemeriksaanLab().isEmpty() ? "Pemeriksaan Laboratorium : -" : "Pemeriksaan Laboratorium : "+data.getPemeriksaanLab());
        holder.tindakan.setText(data.getTindakan().isEmpty() ? "Tindakan : -" : "Tindakan : "+data.getTindakan());
        holder.nasihat.setText(data.getKeluhan().isEmpty() ? "Nasihat : -" : "Nasihat : "+data.getKeluhan());
        holder.pemeriksa.setText("Pemeriksa : "+data.getPemeriksa());
        holder.periksa_kembali.setText("Tanggal Periksa Kembali : "+data.getPeriksaKembali());
    }

    @Override
    public int getItemCount() {
        return mData.size();
        //return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, periksa_kembali, tekanan, weight, umur, tinggi_fundus, letak_janin,
                denyut_janin, keluhan, pem_lab, tindakan, nasihat, kaki_bengkak, pemeriksa;
        public ViewHolder(@NonNull View root) {
            super(root);

            date = root.findViewById(R.id.date);
            periksa_kembali = root.findViewById(R.id.periksa_kembali);
            tekanan = root.findViewById(R.id.tekanan);
            weight = root.findViewById(R.id.weight);
            umur = root.findViewById(R.id.umur);
            tinggi_fundus = root.findViewById(R.id.tinggi_fundus);
            letak_janin = root.findViewById(R.id.letak_janin);
            denyut_janin = root.findViewById(R.id.denyut_janin);
            keluhan = root.findViewById(R.id.keluhan);
            pem_lab = root.findViewById(R.id.pem_lab);
            tindakan = root.findViewById(R.id.tindakan);
            nasihat = root.findViewById(R.id.nasihat);
            kaki_bengkak = root.findViewById(R.id.kaki_bengkak);
            pemeriksa = root.findViewById(R.id.pemeriksa);
        }
    }
}
