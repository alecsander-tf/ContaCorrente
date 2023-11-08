package br.com.contacorrente.framework.network

import br.com.contacorrente.base.BaseServiceNetwork
import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.network.service.ITransferServiceApi
import br.com.contacorrente.model.Status
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface IProviderTransferNetwork {
    fun transfer(
        from: MultipartBody.Part,
        to: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>>
}

class ProviderTransferNetwork(
    private val transferServiceApi : ITransferServiceApi
) : BaseServiceNetwork(), IProviderTransferNetwork {
    override fun transfer(
        from: MultipartBody.Part,
        to: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        val transferCall = transferServiceApi.transfer(from, to, value)
        return transferCall.customEnqueue()
    }

}