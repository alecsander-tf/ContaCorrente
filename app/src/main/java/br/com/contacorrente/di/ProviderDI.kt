package br.com.contacorrente.di

import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.framework.network.IProviderNetwork
import br.com.contacorrente.framework.network.ProviderNetworkImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val providerDI = module {
    single<IProviderNetwork> {
        ProviderNetworkImpl(get(named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)))
    }
}