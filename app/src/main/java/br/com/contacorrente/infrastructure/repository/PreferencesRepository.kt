package br.com.contacorrente.infrastructure.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import br.com.contacorrente.jetpack.preferences.dataStore
import br.com.contacorrente.jetpack.preferences.loggedUser

interface IPreferencesRepository {
    suspend fun saveLoggedUser()
    fun isUserLogged(): Boolean
}

class PreferencesRepositoryImpl(
    val context: Context
) : IPreferencesRepository {
    override suspend fun saveLoggedUser() {
        context.dataStore.edit {
            it[loggedUser] = true
        }
    }

    override fun isUserLogged(): Boolean = false
}