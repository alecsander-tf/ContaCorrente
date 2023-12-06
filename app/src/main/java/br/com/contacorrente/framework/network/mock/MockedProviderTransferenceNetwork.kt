package br.com.contacorrente.framework.network.mock

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.network.provider.IProviderTransferNetwork
import br.com.contacorrente.model.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MultipartBody

class MockedProviderTransferenceNetwork: IProviderTransferNetwork {
    override fun transfer(
        clientIdSender: MultipartBody.Part,
        clientEmailReceiver: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        return flowOf(
            CustomState.Success(
                Status(
                    true,
                    ""
                )
            )
        )
    }
}