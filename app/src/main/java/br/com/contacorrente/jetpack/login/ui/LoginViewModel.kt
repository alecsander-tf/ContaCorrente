package br.com.contacorrente.jetpack.login.ui

import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.base.doIfError
import br.com.contacorrente.base.doIfLoading
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.jetpack.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel(), CoroutineScope {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        launch {
            loginUseCase.execute(email, password).collect {
                it.doIfSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }

                it.doIfLoading {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }

                it.doIfError { errorMessage ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
}