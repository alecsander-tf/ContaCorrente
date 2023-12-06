package br.com.contacorrente.model

import com.google.gson.annotations.SerializedName

data class ErrorStatus(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("status") val status: String,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String,
    @SerializedName("path") val path: String
)