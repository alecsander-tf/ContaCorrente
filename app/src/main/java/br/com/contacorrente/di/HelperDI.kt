package br.com.contacorrente.di

import br.com.contacorrente.framework.helper.IMultipartHelper
import br.com.contacorrente.framework.helper.MultipartHelper
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val helperDI = module {
    singleOf(::MultipartHelper) {
        bind<IMultipartHelper>()
    }
}