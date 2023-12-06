package br.com.contacorrente.framework.network.provider

import br.com.contacorrente.base.BaseServiceNetwork
import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.network.service.ITransferenceServiceApi
import br.com.contacorrente.model.Status
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface IProviderTransferNetwork {
    fun transfer(
        clientIdSender: MultipartBody.Part,
        clientEmailReceiver: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>>
}

class ProviderTransferNetwork(
    private val transferServiceApi : ITransferenceServiceApi
) : BaseServiceNetwork(), IProviderTransferNetwork {
    override fun transfer(
        clientIdSender: MultipartBody.Part,
        clientEmailReceiver: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        val transferCall = transferServiceApi.transfer(clientIdSender, clientEmailReceiver, value)
        return transferCall.customEnqueue()
    }

}