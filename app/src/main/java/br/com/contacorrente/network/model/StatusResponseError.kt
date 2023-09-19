package br.com.contacorrente.network.model

import com.google.gson.annotations.SerializedName

data class StatusResponseError(
    @SerializedName("timestamp")
    var timestamp: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("error")
    var error: String? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("path")
    var path: String? = null
)