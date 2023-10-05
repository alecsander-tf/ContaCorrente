package br.com.contacorrente.jetpack

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.contacorrente.jetpack.account.ui.AccountScreen
import br.com.contacorrente.jetpack.menu.extract.ui.ExtractScreen
import br.com.contacorrente.jetpack.menu.home.ui.HomeScreen
import br.com.contacorrente.jetpack.menu.transference.ui.TransferenceScreen
import br.com.contacorrente.jetpack.routes.AppDestination
import br.com.contacorrente.jetpack.settings.ui.SettingsScreen

@Composable
fun MenuNavHost(
    userEmail: String,
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            CustomTopAppBar(
                toolbarTitle = "Olá",
                onIconClick = {
                    navController.navigate(it)
                }
            )
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestination.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = AppDestination.Home.route) {
                    HomeScreen(
                        userEmail = userEmail
                    )
                }
                composable(route = AppDestination.Extract.route) {
                    ExtractScreen()
                }
                composable(route = AppDestination.Transference.route) {
                    TransferenceScreen()
                }
                composable(route = AppDestination.Settings.route) {
                    SettingsScreen()
                }
                composable(route = AppDestination.Account.route) {
                    AccountScreen()
                }
            }
        },
        bottomBar = {
            CustomBottomAppBar(navController = navController)
        }
    )
}

/*
private fun NavHostController.navigateToMenu(userEmail: String) {
    this.navigate("${AppDestination.Home.route}/$userEmail")
}*/
