package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Jadwal {
    @SerializedName("id_lokasi")
    private String id;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("nama_posyandu")
    private String namaPosyandu;
    @SerializedName("kegiatan")
    private String kegiatan;

    public Jadwal(String id, String tanggal, String namaPosyandu, String kegiatan) {
        this.id = id;
        this.tanggal = tanggal;
        this.namaPosyandu = namaPosyandu;
        this.kegiatan = kegiatan;
    }

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
}
