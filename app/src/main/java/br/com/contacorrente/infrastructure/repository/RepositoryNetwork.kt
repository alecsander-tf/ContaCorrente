package br.com.contacorrente.infrastructure.repository

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.base.collect
import br.com.contacorrente.base.doIfError
import br.com.contacorrente.framework.helper.IMultipartHelper
import br.com.contacorrente.framework.mapper.IResponseToStatusMapper
import br.com.contacorrente.framework.network.IProviderNetwork
import br.com.contacorrente.model.Status
import br.com.contacorrente.model.StatusKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IRepositoryNetwork {
    fun login(email: String, password: String): Flow<CustomState<StatusKt>>
}

class RepositoryNetworkImpl(
    private val providerNetwork: IProviderNetwork,
    private val multipartHelper: IMultipartHelper,
    private val responseToStatusMapper: IResponseToStatusMapper
) : IRepositoryNetwork {
    override fun login(email: String, password: String): Flow<CustomState<StatusKt>> {
        return providerNetwork.login(
            multipartHelper.execute("email", email),
            multipartHelper.execute("password", password)
        ).map {
            it.collect { status ->
                status
            }
        }
    }
}