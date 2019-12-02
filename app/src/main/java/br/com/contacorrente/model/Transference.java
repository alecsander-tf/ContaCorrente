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

    public Transference(){

    }

    protected Transference(Parcel in) {
        id = in.readString();
        id_from = in.readString();
        id_to = in.readString();
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
        dest.writeString(id);
        dest.writeString(id_from);
        dest.writeString(id_to);
        dest.writeString(value);
        dest.writeString(data);
    }
}
