package br.com.contacorrente.di

import br.com.contacorrente.jetpack.login.ui.LoginViewModel
import br.com.contacorrente.jetpack.menu.home.ui.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelDI = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
}