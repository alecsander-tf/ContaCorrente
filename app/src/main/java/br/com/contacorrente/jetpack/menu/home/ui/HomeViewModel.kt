package br.com.contacorrente.jetpack.menu.home.ui

import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.base.doIfApiError
import br.com.contacorrente.base.doIfError
import br.com.contacorrente.base.doIfLoading
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.jetpack.menu.home.usecase.ILoadUserInformationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadUserInformationUseCase: ILoadUserInformationUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadInformationFromSwipe(userEmail: String) {
        _uiState.value = _uiState.value.copy(
            isRefreshing = true
        )
        loadInformation(userEmail)
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            isRefreshing = false
        )
    }

    fun loadInformation(userEmail: String) {
        launch {
            loadUserInformationUseCase.execute(userEmail).collect { state ->
                state.doIfSuccess {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        userName = it.name,
                        userBalance = it.balance,
                        isRefreshing = false
                    )
                }
                state.doIfLoading {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                    )
                }
                state.doIfApiError {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                state.doIfError {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }

}