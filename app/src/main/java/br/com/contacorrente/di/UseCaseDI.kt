package br.com.contacorrente.di

import br.com.contacorrente.jetpack.login.usecase.ILoginUseCase
import br.com.contacorrente.jetpack.login.usecase.LoginUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseDI = module {

    singleOf(::LoginUseCase) {
        bind<ILoginUseCase>()
    }

}