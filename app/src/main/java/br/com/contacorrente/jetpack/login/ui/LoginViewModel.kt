package br.com.contacorrente.jetpack.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.base.doIfApiError
import br.com.contacorrente.base.doIfError
import br.com.contacorrente.base.doIfLoading
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.jetpack.login.usecase.ILoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: ILoginUseCase) : BaseViewModel() {

    var userEmail by mutableStateOf("barbara@gmail.com")
    var userPassword by mutableStateOf("12345")

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {

        if (!validateFields()) return

        launch {
            loginUseCase.execute(userEmail, userPassword).collect {
                it.doIfSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "",
                        onError = false,
                        isLoggedIn = true
                    )
                }

                it.doIfLoading {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        onError = false,
                        isLoggedIn = false
                    )
                }

                it.doIfApiError { status ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = status.message,
                        onError = true,
                        isLoggedIn = false
                    )
                }

                it.doIfError { errorMessage ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = errorMessage,
                        isLoggedIn = false,
                        onError = true
                    )
                }
            }
        }
    }

    private fun validateFields(): Boolean {

        val isEmailOnError = userEmail.isEmpty()
        val isPasswordOnError = userPassword.isEmpty()
        var onError = uiState.value.onError

        if (isEmailOnError || isPasswordOnError) {
            onError = false
        }

        _uiState.value = _uiState.value.copy(
            isEmailOnError = isEmailOnError,
            isPasswordOnError = isPasswordOnError,
            onError = onError
        )

        return !(isEmailOnError || isPasswordOnError)
    }
}