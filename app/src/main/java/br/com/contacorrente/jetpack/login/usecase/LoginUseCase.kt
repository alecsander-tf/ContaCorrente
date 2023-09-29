package br.com.contacorrente.jetpack.login.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.infrastructure.repository.INetworkRepository
import br.com.contacorrente.infrastructure.repository.IPreferencesRepository
import br.com.contacorrente.model.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ILoginUseCase {
    fun execute(email: String, password: String): Flow<CustomState<Status>>
}

class LoginUseCase(
    private val networkRepository: INetworkRepository,
    private val preferencesRepository: IPreferencesRepository
) : ILoginUseCase {
    override fun execute(email: String, password: String): Flow<CustomState<Status>> {
        return networkRepository.login(email, password).map { state ->
            state.doIfSuccess {
                preferencesRepository.saveLoggedUser()
            }
            state
        }
    }
}