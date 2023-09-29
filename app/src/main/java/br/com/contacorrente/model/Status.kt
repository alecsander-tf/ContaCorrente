package br.com.contacorrente.model

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
)