package br.com.contacorrente.framework.helper

import okhttp3.MultipartBody

interface IMultipartHelper {
    fun execute(parameterName: String, value: String): MultipartBody.Part
}

class MultipartHelper : IMultipartHelper {
    override fun execute(parameterName: String, value: String): MultipartBody.Part {
        return MultipartBody.Part.createFormData(parameterName, value)
    }
}