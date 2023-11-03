package br.com.contacorrente.framework.network.service

import br.com.contacorrente.model.Status
import br.com.contacorrente.model.User
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface IUserServiceAPI {
    @Multipart
    @POST("./check-login")
    fun checkLogin(
        @Part email: MultipartBody.Part,
        @Part password: MultipartBody.Part
    ): Call<Status>

    @GET("./get-client")
    fun getClient(@Query("email") userEmail: String): Call<User>
}