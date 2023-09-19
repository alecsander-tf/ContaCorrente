package br.com.contacorrente.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Client implements Parcelable {

    @SerializedName("clientId")
    private Long clientId;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("profile")
    private String profile;

    @SerializedName("password")
    private String password;

    @SerializedName("balance")
    private String balance;

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Client(Long id, String name, String email, String profile, String balance) {
        this.clientId = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.balance = balance;
    }

    protected Client(Parcel in) {
        clientId = in.readLong();
        name = in.readString();
        email = in.readString();
        profile = in.readString();
        password = in.readString();
        balance = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(clientId);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(profile);
        dest.writeString(password);
        dest.writeString(balance);
    }
}
