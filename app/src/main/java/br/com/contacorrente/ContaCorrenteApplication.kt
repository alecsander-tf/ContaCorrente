package br.com.contacorrente

import android.app.Application
import br.com.contacorrente.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class ContaCorrenteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@ContaCorrenteApplication)
            modules(
                listOf(
                    useCaseDI,
                    viewModelDI,
                    repositoryDI,
                    providerDI,
                    httpDI,
                    helperDI,
                    mapperDI
                )
            )
        }
    }

}