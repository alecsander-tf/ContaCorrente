package br.com.contacorrente.jetpack.menu.transference.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.infrastructure.repository.INetworkRepository
import br.com.contacorrente.model.Status
import kotlinx.coroutines.flow.Flow

interface ITransferenceUseCase {
    fun execute(from: String, to: String, value: Double): Flow<CustomState<Status>>
}

class TransferenceUseCase(
    private val networkRepository: INetworkRepository
) : ITransferenceUseCase {
    override fun execute(from: String, to: String, value: Double): Flow<CustomState<Status>> {
        return networkRepository.transfer(from, to, value)
    }
}