package br.com.contacorrente.jetpack.routes

sealed class AppDestination(val route: String) {
    data object Login: AppDestination("login")
    data object Menu: AppDestination("menu")
}