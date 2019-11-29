package br.com.contacorrente.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transference implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("id_from")
    private String id_from;

    @SerializedName("id_to")
    private String id_to;

    @SerializedName("value")
    private String value;

    @SerializedName("data")
    private String data;

    private User userRelated;

    public User getUserRelated() {
        return userRelated;
    }

    public void setUserRelated(User userRelated) {
        this.userRelated = userRelated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_from() {
        return id_from;
    }

    public void setId_from(String id_from) {
        this.id_from = id_from;
    }

    public String getId_to() {
        return id_to;
    }

    public void setId_to(String id_to) {
        this.id_to = id_to;
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

    }
}
