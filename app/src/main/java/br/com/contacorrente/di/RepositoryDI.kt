package br.com.contacorrente.di


import br.com.contacorrente.infrastructure.repository.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryDI = module {
    singleOf(::NetworkRepositoryImpl) {
        bind<INetworkRepository>()
    }
    singleOf(::PreferencesRepositoryImpl) {
        bind<IPreferencesRepository>()
    }
}