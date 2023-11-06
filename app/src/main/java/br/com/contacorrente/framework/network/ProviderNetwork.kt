package br.com.contacorrente.framework.network

import br.com.contacorrente.base.BaseServiceNetwork
import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.network.service.IUserServiceAPI
import br.com.contacorrente.model.Status
import br.com.contacorrente.model.User
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface IProviderNetwork {
    fun login(email: MultipartBody.Part, password: MultipartBody.Part): Flow<CustomState<Status>>
    fun transfer(
        from: MultipartBody.Part,
        to: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>>

    fun getUser(userEmail: String): Flow<CustomState<User>>
}

class ProviderNetworkImpl(
    private val userServiceAPI: IUserServiceAPI
) : BaseServiceNetwork(), IProviderNetwork {

    override fun login(
        email: MultipartBody.Part,
        password: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        val checkLoginCall = userServiceAPI.checkLogin(email, password)
        return checkLoginCall.customEnqueue()
    }

    override fun transfer(
        from: MultipartBody.Part,
        to: MultipartBody.Part,
        value: MultipartBody.Part
    ): Flow<CustomState<Status>> {
        val transferCall = userServiceAPI.transfer(from, to, value)
        return transferCall.customEnqueue()
    }

    override fun getUser(userEmail: String): Flow<CustomState<User>> {
        val getUserCall = userServiceAPI.getClient(userEmail)
        return getUserCall.customEnqueue()
    }
}
