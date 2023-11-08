package br.com.contacorrente.framework.network.service

import br.com.contacorrente.model.Status
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ITransferServiceApi {
    @Multipart
    @POST("./transfer-by-email")
    fun transfer(
        clientIdSender: MultipartBody.Part,
        clientEmailReceiver: MultipartBody.Part,
        value: MultipartBody.Part
    ): Call<Status>
}