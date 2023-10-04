package br.com.contacorrente.jetpack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.contacorrente.jetpack.menu.extract.ui.ExtractScreen
import br.com.contacorrente.jetpack.menu.home.ui.HomeLayout
import br.com.contacorrente.jetpack.menu.home.ui.HomeScreen
import br.com.contacorrente.jetpack.routes.AppDestination
import br.com.contacorrente.jetpack.menu.transference.ui.TransferenceScreen

@Composable
fun MenuNavHost(
    userEmail: String,
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        topBar = {
            CustomTopAppBar("Olá")
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = AppDestination.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = AppDestination.Home.route) {
                    HomeScreen(
                        navController = navController,
                        userEmail = userEmail
                    )
                }
                composable(route = AppDestination.Extract.route) {
                    ExtractScreen()
                }
                composable(route = AppDestination.Transference.route) {
                    TransferenceScreen()
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
