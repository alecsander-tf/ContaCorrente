package br.com.contacorrente.infrastructure.repository

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.base.collect
import br.com.contacorrente.framework.helper.IMultipartHelper
import br.com.contacorrente.framework.network.provider.IProviderClientNetwork
import br.com.contacorrente.framework.network.provider.IProviderTransferNetwork
import br.com.contacorrente.model.Status
import br.com.contacorrente.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface INetworkRepository {
    fun login(email: String, password: String): Flow<CustomState<Status>>
    fun getUser(userEmail: String): Flow<CustomState<User>>
    fun transfer(clientIdSender: String, clientEmailReceiver: String, value: String): Flow<CustomState<Status>>
}

class NetworkRepositoryImpl(
    private val providerNetwork: IProviderClientNetwork,
    private val transferenceNetwork: IProviderTransferNetwork,
    private val multipartHelper: IMultipartHelper
) : INetworkRepository {
    override fun login(email: String, password: String): Flow<CustomState<Status>> {
        return providerNetwork.login(
            multipartHelper.execute("email", email),
            multipartHelper.execute("password", password)
        ).map {
            it.collect { status ->
                status
            }
        }
    }

    override fun getUser(userEmail: String): Flow<CustomState<User>> {
        return providerNetwork.getUser(userEmail)
    }

    override fun transfer(clientIdSender: String, clientEmailReceiver: String, value: String): Flow<CustomState<Status>> {
        return transferenceNetwork.transfer(
            multipartHelper.execute("clientIdSender", clientIdSender),
            multipartHelper.execute("clientEmailReceiver", clientEmailReceiver),
            multipartHelper.execute("value", value)
        ).map {
            it.collect { status ->
                status
            }
        }
    }
}