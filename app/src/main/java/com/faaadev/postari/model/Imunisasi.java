package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Imunisasi implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("id_anak")
    private String idAnak;
    @SerializedName("type")
    private String type;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("tanggal")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAnak() {
        return idAnak;
    }

    public void setIdAnak(String idAnak) {
        this.idAnak = idAnak;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
