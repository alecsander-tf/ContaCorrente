package br.com.contacorrente.framework.network.service

import br.com.contacorrente.model.StatusKt
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IUserServiceAPI {
    @Multipart
    @POST("./check-login")
    fun checkLogin(@Part email: MultipartBody.Part, @Part password: MultipartBody.Part): Call<StatusKt>
}