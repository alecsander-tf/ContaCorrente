package br.com.contacorrente.jetpack.settings.usecase

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.infrastructure.repository.IThemeRepository
import kotlinx.coroutines.flow.Flow

interface IReadThemeUseCase {
    fun execute(): Flow<CustomState<String>>
}

class ReadThemeUseCase(
    private val themeRepository: IThemeRepository
) : IReadThemeUseCase {
    override fun execute(): Flow<CustomState<String>> {
        return themeRepository.readTheme()
    }
}