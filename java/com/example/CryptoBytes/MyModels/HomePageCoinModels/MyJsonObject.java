package com.example.CryptoBytes.MyModels.HomePageCoinModels;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyJsonObject implements Serializable
{
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("data")
    @Expose
    private List<DataMain> data = null;
    private final static long serialVersionUID = -4369048252305703014L;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public List<DataMain> getData() {
        return data;
    }
    public void setData(List<DataMain> data) {
        this.data = data;
    }
}