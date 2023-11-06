package br.com.contacorrente.di

import br.com.contacorrente.framework.IThemeProvider
import br.com.contacorrente.framework.ThemeProvider
import br.com.contacorrente.framework.network.IProviderNetwork
import br.com.contacorrente.framework.network.MockedProviderNetwork
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val providerDI = module {
    single<IProviderNetwork> {
        ProviderNetworkImpl(get(named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)))
        //MockedProviderNetwork()
    }

    singleOf(::ThemeProvider) {
        bind<IThemeProvider>()
    }
}