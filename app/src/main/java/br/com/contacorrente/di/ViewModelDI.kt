package br.com.contacorrente.di

import br.com.contacorrente.jetpack.login.ui.LoginViewModel
import br.com.contacorrente.jetpack.menu.home.ui.HomeViewModel
import br.com.contacorrente.jetpack.menu.extract.ui.ExtractViewModel
import br.com.contacorrente.jetpack.menu.transference.ui.TransferenceViewModel
import br.com.contacorrente.jetpack.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelDI = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ExtractViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::TransferenceViewModel)
}