package br.com.contacorrente.di

import br.com.contacorrente.jetpack.menu.usecase.*
import br.com.contacorrente.jetpack.login.usecase.*
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

}