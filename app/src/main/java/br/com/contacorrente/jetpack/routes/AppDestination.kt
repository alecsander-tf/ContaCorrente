package br.com.contacorrente.jetpack.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppDestination(
    val route: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
) {
    data object Login : AppDestination(
        route = "login",
        activeIcon = Icons.Filled.Login,
        inactiveIcon = Icons.Outlined.Login,
    )

    data object Home : AppDestination(
        route = "home",
        activeIcon = Icons.Filled.Home,
        inactiveIcon = Icons.Outlined.Home
    )

    data object Extract : AppDestination(
        route = "extract",
        activeIcon = Icons.Filled.Wallet,
        inactiveIcon = Icons.Outlined.Wallet
    )

    data object Transference : AppDestination(
        route = "transference",
        activeIcon = Icons.Filled.AttachMoney,
        inactiveIcon = Icons.Outlined.AttachMoney
    )

    data object Settings : AppDestination(
        route = "settings",
        activeIcon = Icons.Filled.Settings,
        inactiveIcon = Icons.Outlined.Settings
    )

    data object Account : AppDestination(
        route = "account",
        activeIcon = Icons.Filled.Face,
        inactiveIcon = Icons.Outlined.Face
    )
}