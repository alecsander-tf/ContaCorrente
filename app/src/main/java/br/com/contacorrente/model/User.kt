package br.com.contacorrente.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("profile") var profile: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("balance") var balance: String
)