package br.com.contacorrente.framework.mapper

import br.com.contacorrente.model.Status
import com.google.gson.Gson

interface IResponseToStatusMapper {
    fun map(responseBodyString: String): Status
}

class ResponseToStatusMapper : IResponseToStatusMapper {
    override fun map(responseBodyString: String): Status =
        Gson().fromJson(responseBodyString, Status::class.java)
}