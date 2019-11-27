package br.com.contacorrente.model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
