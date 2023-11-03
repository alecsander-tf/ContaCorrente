package br.com.contacorrente.jetpack.settings.usecase

import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.infrastructure.repository.IThemeRepository

interface ISetThemeUseCase {
    suspend fun execute(theme: AppThemeOptions)
}

class SetThemeUseCase(
    private val themeRepository: IThemeRepository
) : ISetThemeUseCase {
    override suspend fun execute(theme: AppThemeOptions) {
        themeRepository.saveTheme(theme.intValue)
    }

}