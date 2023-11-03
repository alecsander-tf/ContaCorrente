package br.com.contacorrente.jetpack.settings.ui

import br.com.contacorrente.constants.AppThemeOptions

data class SettingsUiState(
    val theme: AppThemeOptions = AppThemeOptions.SYSTEM_DEFAULT
)