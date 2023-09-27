package br.com.contacorrente.framework.network

import br.com.contacorrente.base.BaseServiceNetwork
import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.network.service.IUserServiceAPI
import br.com.contacorrente.model.StatusKt
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface IProviderNetwork {
    fun login(email: MultipartBody.Part, password: MultipartBody.Part): Flow<CustomState<StatusKt>>
}

class ProviderNetworkImpl(
    private val userServiceAPI: IUserServiceAPI
) : BaseServiceNetwork(), IProviderNetwork {

    override fun login(
        email: MultipartBody.Part,
        password: MultipartBody.Part
    ): Flow<CustomState<StatusKt>> {
        val checkLogin = userServiceAPI.checkLogin(email, password)
        return checkLogin.customEnqueue()
    }
}
