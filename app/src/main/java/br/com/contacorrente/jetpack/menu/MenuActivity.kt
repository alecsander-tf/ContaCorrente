package br.com.contacorrente.jetpack.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.jetpack.MenuNavHost
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContaCorrenteMainTheme {
                intent.extras?.getString(ContaCorrenteConstants.IntentExtras.USER_EMAIL, "")?.let {
                    MenuNavHost(it)
                }
            }
        }
    }
}