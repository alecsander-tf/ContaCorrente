package br.com.contacorrente.di


import br.com.contacorrente.infrastructure.repository.IRepositoryNetwork
import br.com.contacorrente.infrastructure.repository.RepositoryNetworkImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryDI = module {
    singleOf(::RepositoryNetworkImpl) {
        bind<IRepositoryNetwork>()
    }
}