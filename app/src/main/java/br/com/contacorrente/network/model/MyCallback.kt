package br.com.contacorrente.network.model

import android.text.TextUtils
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets

abstract class MyCallback<T>: Callback<T> {

     override fun onResponse(call: Call<T>, response: Response<T>) {
         //if (response.isSuccessful) onResponse(response) else onFailure()
     }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }

    abstract fun onResponse(response: Response<T>)

    abstract fun onFailure(response: StatusResponseError)

    private fun getStatusError(responseBody: ResponseBody?): StatusResponseError? {
        if (responseBody != null) {
            try {
                val source = responseBody.source()
                if (source != null) {
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    val buffer = source.buffer
                    var charset = StandardCharsets.UTF_8
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(StandardCharsets.UTF_8)
                    }
                    val string = buffer.clone().readString(charset!!)
                    if (!TextUtils.isEmpty(string)) {
                        val gsonBuilder = GsonBuilder()
                        val gson = gsonBuilder.create()
                        return gson.fromJson(string, StatusResponseError::class.java)
                    }
                }
            } catch (e: Exception) {
            }
        }
        return null
    }
}