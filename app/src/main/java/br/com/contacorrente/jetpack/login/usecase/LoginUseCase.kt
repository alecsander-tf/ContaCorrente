package br.com.contacorrente.jetpack.login.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.infrastructure.repository.IRepositoryNetwork
import br.com.contacorrente.model.StatusKt
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    fun execute(email: String, password: String): Flow<CustomState<StatusKt>>
}

class LoginUseCase(
    private val repositoryNetwork: IRepositoryNetwork
) : ILoginUseCase {
    override fun execute(email: String, password: String): Flow<CustomState<StatusKt>> {
        return repositoryNetwork.login(email, password)
    }
}