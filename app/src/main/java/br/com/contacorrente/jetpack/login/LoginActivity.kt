package br.com.contacorrente.jetpack.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.contacorrente.jetpack.login.ui.LoginScreen
import br.com.contacorrente.jetpack.ui.ApplicationMainTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ApplicationMainTheme {
                LoginScreen()
            }
        }

    }
}