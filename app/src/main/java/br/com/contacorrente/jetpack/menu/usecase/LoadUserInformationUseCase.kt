package br.com.contacorrente.jetpack.menu.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.infrastructure.repository.INetworkRepository
import br.com.contacorrente.model.User
import kotlinx.coroutines.flow.Flow

interface ILoadUserInformationUseCase {
    fun execute(userEmail: String): Flow<CustomState<User>>
}

class LoadUserInformationUseCase(
    private val networkRepository: INetworkRepository
): ILoadUserInformationUseCase {
    override fun execute(userEmail: String): Flow<CustomState<User>> {
        return networkRepository.getUser(userEmail)
    }
}