package br.com.contacorrente.jetpack.settings.ui

import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.jetpack.settings.usecase.IReadThemeUseCase
import br.com.contacorrente.jetpack.settings.usecase.ISetThemeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val setThemeUseCase: ISetThemeUseCase,
    private val readThemeUseCase: IReadThemeUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setTheme(theme: String) {
        launch {
            setThemeUseCase.execute(theme)
        }
    }

    fun readTheme() {
        launch {
            readThemeUseCase.execute().collect {
                it.doIfSuccess { theme ->
                    _uiState.value = _uiState.value.copy(
                        theme = theme
                    )
                }
            }
        }
    }

}