package br.com.contacorrente.framework

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.jetpack.preferences.THEME
import br.com.contacorrente.jetpack.preferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IThemeProvider {
    suspend fun saveTheme(theme: Int)
    fun readTheme(): Flow<Int>
}

class ThemeProvider(
    val context: Context
) : IThemeProvider {
    override suspend fun saveTheme(theme: Int) {
        context.dataStore.edit {
            it[THEME] = theme
        }
    }

    override fun readTheme(): Flow<Int> {
        return context.dataStore.data.map {
            it[THEME] ?: AppThemeOptions.SYSTEM_DEFAULT.intValue
        }
    }
}