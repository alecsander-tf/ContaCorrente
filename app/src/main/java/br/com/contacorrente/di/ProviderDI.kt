package br.com.contacorrente.di

import br.com.contacorrente.framework.IThemeProvider
import br.com.contacorrente.framework.ThemeProvider
import br.com.contacorrente.framework.network.mock.MockedProviderClientNetwork
import br.com.contacorrente.framework.network.mock.MockedProviderTransferenceNetwork
import br.com.contacorrente.framework.network.provider.IProviderClientNetwork
import br.com.contacorrente.framework.network.provider.IProviderTransferNetwork
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val providerDI = module {
    single<IProviderClientNetwork> {
        //ProviderClientNetworkImpl(get(named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)))
        MockedProviderClientNetwork()
    }

    single<IProviderTransferNetwork> {
        //ProviderTransferNetwork(get(named(ContaCorrenteConstants.NamedHttpKoin.TRANSFER_SERVICE_API)))
        MockedProviderTransferenceNetwork()
    }

    singleOf(::ThemeProvider) {
        bind<IThemeProvider>()
    }
}