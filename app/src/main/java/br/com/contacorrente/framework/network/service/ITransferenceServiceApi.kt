package br.com.contacorrente.framework.network.service

import br.com.contacorrente.model.Status
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ITransferenceServiceApi {
    @Multipart
    @POST("./transfer-by-email")
    fun transfer(
        @Part clientIdSender: MultipartBody.Part,
        @Part clientEmailReceiver: MultipartBody.Part,
        @Part value: MultipartBody.Part
    ): Call<Status>
}