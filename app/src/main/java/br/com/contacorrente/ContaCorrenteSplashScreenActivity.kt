package br.com.contacorrente

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.constants.Singleton
import br.com.contacorrente.jetpack.login.LoginActivity
import br.com.contacorrente.jetpack.settings.usecase.IReadThemeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

@SuppressLint("CustomSplashScreen")
class ContaCorrenteSplashScreenActivity : ComponentActivity() {

    private var keepSplashOnScreen = true
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val readThemeUseCase = KoinJavaComponent.get<IReadThemeUseCase>(
        IReadThemeUseCase::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            keepSplashOnScreen
        }

        scope.launch {
            readThemeUseCase.execute().collect {
                it.doIfSuccess { theme ->
                    Singleton.AppTheme = shouldUseDarkTheme(theme)
                    keepSplashOnScreen = false
                }
            }
        }

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun shouldUseDarkTheme(theme: String): AppThemeOptions = when (theme) {
        getString(R.string.light) -> AppThemeOptions.LIGHT
        getString(R.string.dark) -> AppThemeOptions.DARK
        else -> AppThemeOptions.SYSTEM_DEFAULT
    }

}