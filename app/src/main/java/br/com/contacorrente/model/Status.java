package br.com.contacorrente.model;

import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("error")
    private String error;

    @SerializedName("status")
    private boolean status;

    public String getError() {
        return error;
    }

    public boolean isStatus() {
        return status;
    }
}
