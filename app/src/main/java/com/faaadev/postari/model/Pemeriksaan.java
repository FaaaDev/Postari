package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pemeriksaan implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("tekanan_darah")
    private String tekanan;
    @SerializedName("bb_ibu")
    private String weight;
    @SerializedName("umur_hamil")
    private int umur;
    @SerializedName("tinggi_fundus")
    private String tinggiFundus;
    @SerializedName("letak_janin")
    private String letakJanin;
    @SerializedName("denyut_janin")
    private String denyutJanin;
    @SerializedName("keluhan")
    private String keluhan;
    @SerializedName("kaki_bengkak")
    private String kakiBengkak;
    @SerializedName("pem_laboratorium")
    private String pemeriksaanLab;
    @SerializedName("tindakan")
    private String tindakan;
    @SerializedName("nasihat")
    private String nasihat;
    @SerializedName("tanggal_periksa_kembali")
    private String periksaKembali;
    @SerializedName("pemeriksa")
    private String pemeriksa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTekanan() {
        return tekanan;
    }

    public void setTekanan(String tekanan) {
        this.tekanan = tekanan;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    public String getTinggiFundus() {
        return tinggiFundus;
    }

    public void setTinggiFundus(String tinggiFundus) {
        this.tinggiFundus = tinggiFundus;
    }

    public String getLetakJanin() {
        return letakJanin;
    }

    public void setLetakJanin(String letakJanin) {
        this.letakJanin = letakJanin;
    }

    public String getDenyutJanin() {
        return denyutJanin;
    }

    public void setDenyutJanin(String denyutJanin) {
        this.denyutJanin = denyutJanin;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getKakiBengkak() {
        return kakiBengkak;
    }

    public void setKakiBengkak(String kakiBengkak) {
        this.kakiBengkak = kakiBengkak;
    }

    public String getPemeriksaanLab() {
        return pemeriksaanLab;
    }

    public void setPemeriksaanLab(String pemeriksaanLab) {
        this.pemeriksaanLab = pemeriksaanLab;
    }

    public String getTindakan() {
        return tindakan;
    }

    public void setTindakan(String tindakan) {
        this.tindakan = tindakan;
    }

    public String getNasihat() {
        return nasihat;
    }

    public void setNasihat(String nasihat) {
        this.nasihat = nasihat;
    }

    public String getPeriksaKembali() {
        return periksaKembali;
    }

    public void setPeriksaKembali(String periksaKembali) {
        this.periksaKembali = periksaKembali;
    }

    public String getPemeriksa() {
        return pemeriksa;
    }

    public void setPemeriksa(String pemeriksa) {
        this.pemeriksa = pemeriksa;
    }
}
