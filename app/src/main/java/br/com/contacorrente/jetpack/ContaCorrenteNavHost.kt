package br.com.contacorrente.jetpack

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.contacorrente.jetpack.login.ui.LoginScreen
import br.com.contacorrente.jetpack.menu.ui.MenuScreen
import br.com.contacorrente.jetpack.routes.AppDestination
import timber.log.Timber

@Composable
fun ContaCorrenteNavHost(
    startDestination: String = "login",
    navController: NavHostController = rememberNavController()
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = AppDestination.Login.route
        ) {
            LoginScreen { userEmail ->
                navController.navigateToMenu(userEmail)
            }
        }
        composable(
            route = "${AppDestination.Menu.route}/{userEmail}",
            arguments = listOf(
                navArgument("userEmail") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) {
            val userEmail = it.arguments?.getString("userEmail")
            MenuScreen(userEmail = userEmail!!)
        }
    }
}

private fun NavHostController.navigateToMenu(userEmail: String) {
    this.navigate("${AppDestination.Menu.route}/$userEmail")
}