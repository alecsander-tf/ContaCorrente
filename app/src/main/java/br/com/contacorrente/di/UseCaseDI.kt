package br.com.contacorrente.di

import br.com.contacorrente.jetpack.login.usecase.*
import br.com.contacorrente.jetpack.menu.home.usecase.ILoadUserInformationUseCase
import br.com.contacorrente.jetpack.menu.home.usecase.LoadUserInformationUseCase
import br.com.contacorrente.jetpack.settings.usecase.IReadThemeUseCase
import br.com.contacorrente.jetpack.settings.usecase.ISetThemeUseCase
import br.com.contacorrente.jetpack.settings.usecase.ReadThemeUseCase
import br.com.contacorrente.jetpack.settings.usecase.SetThemeUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseDI = module {

    singleOf(::LoginUseCase) {
        bind<ILoginUseCase>()
    }

    singleOf(::LoadUserInformationUseCase) {
        bind<ILoadUserInformationUseCase>()
    }

    singleOf(::SetThemeUseCase) {
        bind<ISetThemeUseCase>()
    }

    singleOf(::ReadThemeUseCase) {
        bind<IReadThemeUseCase>()
    }

}