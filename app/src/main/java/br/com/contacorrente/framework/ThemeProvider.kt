package br.com.contacorrente.framework

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.preferences.THEME
import br.com.contacorrente.jetpack.preferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IThemeProvider {
    suspend fun saveTheme(theme: String)
    fun readTheme(): Flow<String>
}

class ThemeProvider(
    val context: Context
) : IThemeProvider {
    override suspend fun saveTheme(theme: String) {
        context.dataStore.edit {
            it[THEME] = theme
        }
    }

    override fun readTheme(): Flow<String> {
        return context.dataStore.data.map {
            it[THEME] ?: context.getString(R.string.system_default)
        }
    }
}