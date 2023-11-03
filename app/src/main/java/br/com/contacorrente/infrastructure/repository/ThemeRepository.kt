package br.com.contacorrente.infrastructure.repository

import br.com.contacorrente.base.CustomState
import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.framework.IThemeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IThemeRepository {
    suspend fun saveTheme(theme: Int)
    fun readTheme(): Flow<Int>

}

class ThemeRepositoryImpl(
    private val themeProvider: IThemeProvider
) : IThemeRepository {
    override suspend fun saveTheme(theme: Int) {
        themeProvider.saveTheme(theme)
    }

    override fun readTheme(): Flow<Int> {
        return themeProvider.readTheme()
    }
}