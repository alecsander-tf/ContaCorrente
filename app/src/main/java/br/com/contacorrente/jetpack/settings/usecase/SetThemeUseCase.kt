package br.com.contacorrente.jetpack.settings.usecase

import br.com.contacorrente.infrastructure.repository.IThemeRepository

interface ISetThemeUseCase {
    suspend fun execute(theme: String)
}

class SetThemeUseCase(
    private val themeRepository: IThemeRepository
) : ISetThemeUseCase {
    override suspend fun execute(theme: String) {
        themeRepository.saveTheme(theme)
    }

}