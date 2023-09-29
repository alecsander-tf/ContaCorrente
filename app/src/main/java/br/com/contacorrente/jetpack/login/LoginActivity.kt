package br.com.contacorrente.jetpack.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.contacorrente.jetpack.ContaCorrenteNavHost
import br.com.contacorrente.jetpack.login.ui.LoginScreen
import br.com.contacorrente.jetpack.menu.ui.MenuScreen
import br.com.contacorrente.jetpack.preferences.dataStore
import br.com.contacorrente.jetpack.preferences.loggedUser
import br.com.contacorrente.jetpack.routes.AppDestination
import br.com.contacorrente.jetpack.ui.ApplicationMainTheme
import kotlinx.coroutines.flow.first

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ApplicationMainTheme {

                val context = LocalContext.current
                var startDestination = AppDestination.Login.route
                LaunchedEffect(null) {
                    if (context.dataStore.data.first()[loggedUser] == true) {
                        startDestination = AppDestination.Menu.route
                    }
                }

                ContaCorrenteNavHost(
                    startDestination = startDestination
                )

            }
        }

    }
}