package br.com.contacorrente.di

import br.com.contacorrente.framework.mapper.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mapperDI = module {
    singleOf(::ResponseToStatusMapper) {
        bind<IResponseToStatusMapper>()
    }
    singleOf(::IntToAppThemeOptionsMapper) {
        bind<IIntToAppThemeOptionsMapper>()
    }
}