package br.com.contacorrente.di

import br.com.contacorrente.framework.mapper.IResponseToStatusMapper
import br.com.contacorrente.framework.mapper.ResponseToStatusMapper
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mapperDI = module {
    singleOf(::ResponseToStatusMapper) {
        bind<IResponseToStatusMapper>()
    }
}