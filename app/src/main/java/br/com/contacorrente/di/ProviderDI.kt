package br.com.contacorrente.di

import br.com.contacorrente.constants.ContaCorrenteConstants
import br.com.contacorrente.framework.IThemeProvider
import br.com.contacorrente.framework.ThemeProvider
import br.com.contacorrente.framework.network.provider.IProviderClientNetwork
import br.com.contacorrente.framework.network.provider.IProviderTransferNetwork
import br.com.contacorrente.framework.network.provider.ProviderClientNetworkImpl
import br.com.contacorrente.framework.network.provider.ProviderTransferNetwork
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val providerDI = module {
    single<IProviderClientNetwork> {
        ProviderClientNetworkImpl(get(named(ContaCorrenteConstants.NamedHttpKoin.USER_SERVICE_API)))
        //MockedProviderClientNetwork()
    }

    single<IProviderTransferNetwork> {
        ProviderTransferNetwork(get(named(ContaCorrenteConstants.NamedHttpKoin.TRANSFER_SERVICE_API)))
        //MockedProviderTransferenceNetwork()
    }

    singleOf(::ThemeProvider) {
        bind<IThemeProvider>()
    }
}