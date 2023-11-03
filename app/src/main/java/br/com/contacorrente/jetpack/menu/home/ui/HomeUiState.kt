package br.com.contacorrente.jetpack.menu.home.ui

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "",
    val userBalance: String = "0",
    val isRefreshing: Boolean = false
)
