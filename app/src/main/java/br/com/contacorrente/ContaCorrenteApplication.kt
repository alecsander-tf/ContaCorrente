package br.com.contacorrente

import android.app.Application
import br.com.contacorrente.di.helperDI
import br.com.contacorrente.di.httpClientDI
import br.com.contacorrente.di.httpTransferenceDI
import br.com.contacorrente.di.mapperDI
import br.com.contacorrente.di.providerDI
import br.com.contacorrente.di.repositoryDI
import br.com.contacorrente.di.useCaseDI
import br.com.contacorrente.di.viewModelDI
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
                    httpClientDI,
                    httpTransferenceDI,
                    helperDI,
                    mapperDI
                )
            )
        }
    }

}