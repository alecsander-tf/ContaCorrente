package br.com.contacorrente.infrastructure.repository

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.framework.IThemeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IThemeRepository {
    suspend fun saveTheme(theme: String)
    fun readTheme(): Flow<CustomState<String>>

}

class ThemeRepositoryImpl(
    private val themeProvider: IThemeProvider
) : IThemeRepository {
    override suspend fun saveTheme(theme: String) {
        themeProvider.saveTheme(theme)
    }

    override fun readTheme(): Flow<CustomState<String>> {
        return themeProvider.readTheme().map {
            CustomState.Success(it)
        }
    }
}