package com.example.mohamedashour.test.data.models;

import com.google.gson.annotations.SerializedName;

public class CurrencyModel {
    @SerializedName("key")
    private String key;
    @SerializedName("flag")
    private String flag;
    @SerializedName("val")
    private double val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }
}
