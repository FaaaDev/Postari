package com.faaadev.postari.model;

import com.faaadev.postari.ui.home.HomeFragment;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Jadwal implements Serializable {
    @SerializedName("id")
    private String idLokasi;
    @SerializedName("id_lokasi")
    private String id;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("nama_posyandu")
    private String namaPosyandu;
    @SerializedName("kegiatan")
    private String kegiatan;
    @SerializedName("url")
    private String url;

    public Jadwal() {

    }

    public Jadwal(String idLokasi, String id, String tanggal, String namaPosyandu, String kegiatan) {
        this.idLokasi = idLokasi;
        this.id = id;
        this.tanggal = tanggal;
        this.namaPosyandu = namaPosyandu;
        this.kegiatan = kegiatan;
    }

    public String getId() {
        return id;
    }

    public String getIdLokasi() {
        return idLokasi;
    }

    public void setIdLokasi(String idLokasi) {
        this.idLokasi = idLokasi;
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

    public String getNamaPosyandu() {
        return namaPosyandu;
    }

    public void setNamaPosyandu(String namaPosyandu) {
        this.namaPosyandu = namaPosyandu;
    }

    public String getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(String kegiatan) {
        this.kegiatan = kegiatan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
