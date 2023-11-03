package br.com.contacorrente.infrastructure.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.contacorrente.base.CustomState
import br.com.contacorrente.jetpack.preferences.dataStore
import br.com.contacorrente.jetpack.preferences.LOGGED_USER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IPreferencesRepository {
    suspend fun saveLoggedUser()
    fun isUserLogged(): Flow<Boolean>
}

class PreferencesRepositoryImpl(
    val context: Context
) : IPreferencesRepository {
    override suspend fun saveLoggedUser() {
        context.dataStore.edit {
            it[LOGGED_USER] = true
        }
    }

    override fun isUserLogged(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[LOGGED_USER] ?: false
        }
    }
}