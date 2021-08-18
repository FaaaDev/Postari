package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ortu implements Serializable {
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("nama_ibu")
    private String mom_name;
    @SerializedName("nama_suami")
    private String dad_name;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("nama_posyandu")
    private String posyandu;
    @SerializedName("posyandu")
    private String idPosyandu;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMom_name() {
        return mom_name;
    }

    public void setMom_name(String mom_name) {
        this.mom_name = mom_name;
    }

    public String getDad_name() {
        return dad_name;
    }

    public void setDad_name(String dad_name) {
        this.dad_name = dad_name;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPosyandu() {
        return posyandu;
    }

    public void setPosyandu(String posyandu) {
        this.posyandu = posyandu;
    }

    public String getIdPosyandu() {
        return idPosyandu;
    }

    public void setIdPosyandu(String idPosyandu) {
        this.idPosyandu = idPosyandu;
    }
}
