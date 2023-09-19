package br.com.contacorrente.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transference implements Parcelable {

    @SerializedName("transferenceId")
    private Long transferenceId;

    @SerializedName("clientIdSender")
    private Long clientIdSender;

    @SerializedName("clientIdReceiver")
    private Long clientIdReceiver;

    @SerializedName("value")
    private String value;

    @SerializedName("data")
    private String data;

    private Client clientRelated;

    public Transference() {

    }

    protected Transference(Parcel in) {
        transferenceId = in.readLong();
        clientIdSender = in.readLong();
        clientIdReceiver = in.readLong();
        value = in.readString();
        data = in.readString();
    }

    public static final Creator<Transference> CREATOR = new Creator<Transference>() {
        @Override
        public Transference createFromParcel(Parcel in) {
            return new Transference(in);
        }

        @Override
        public Transference[] newArray(int size) {
            return new Transference[size];
        }
    };

    public Client getClientRelated() {
        return clientRelated;
    }

    public void setClientRelated(Client clientRelated) {
        this.clientRelated = clientRelated;
    }

    public Long getTransferenceId() {
        return transferenceId;
    }

    public void setTransferenceId(Long transferenceId) {
        this.transferenceId = transferenceId;
    }

    public Long getClientIdSender() {
        return clientIdSender;
    }

    public void setClientIdSender(Long clientIdSender) {
        this.clientIdSender = clientIdSender;
    }

    public Long getClientIdReceiver() {
        return clientIdReceiver;
    }

    public void setClientIdReceiver(Long clientIdReceiver) {
        this.clientIdReceiver = clientIdReceiver;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(transferenceId);
        dest.writeLong(clientIdSender);
        dest.writeLong(clientIdReceiver);
        dest.writeString(value);
        dest.writeString(data);
    }
}
