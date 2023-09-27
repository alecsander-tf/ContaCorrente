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
import br.com.contacorrente.jetpack.login.usecase.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val loginUseCase: ILoginUseCase
) : BaseViewModel(), CoroutineScope {


    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()
    var userEmail by mutableStateOf("barbara@gmail.co")
        private set

    var userPassword by mutableStateOf("12345")
        private set


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        launch {
            loginUseCase.execute(userEmail, userPassword).collect {
                it.doIfSuccess {
                    Timber.w("LoginUseCase - OnSuccess")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = ""
                    )
                }

                it.doIfLoading {
                    Timber.w("LoginUseCase - OnLoading")
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                }

                it.doIfApiError { status ->
                    Timber.e("LoginUseCase - ApiError ${status.message}")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = status.message
                    )
                }

                it.doIfError { errorMessage ->
                    Timber.w("LoginUseCase - OnError $errorMessage")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }

    fun updateEmail(email: String) {
        this.userEmail = email
    }

    fun updatePassword(password: String) {
        this.userPassword = password
    }
}