package br.com.contacorrente.jetpack.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.jetpack.login.ui.LoginScreen
import br.com.contacorrente.jetpack.menu.MenuActivity
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContaCorrenteMainTheme {
                LoginScreen { userEmail ->
                    startMenuActivity(userEmail)
                }
            }
        }
    }

    private fun startMenuActivity(userEmail: String) {
        startActivity(
            Intent(this, MenuActivity::class.java).apply {
                putExtra(ContaCorrenteConstants.IntentExtras.USER_EMAIL, userEmail)
            }
        )
    }

}