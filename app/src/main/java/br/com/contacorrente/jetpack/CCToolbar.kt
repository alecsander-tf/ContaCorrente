@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.contacorrente.jetpack

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.contacorrente.jetpack.routes.AppDestination
import br.com.contacorrente.jetpack.ui.PreviewContaCorrenteMainTheme

@Preview
@Composable
fun PreviewCustomToolbar() {
    PreviewContaCorrenteMainTheme {
        CustomTopAppBar(
            toolbarTitle = "Olá Bárbara",
            onIconClick = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBackTopBar(onClick: () -> Unit) {
    TopAppBar(title = {},
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    toolbarTitle: String,
    onIconClick: (route: String) -> Unit
) {

    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                fontWeight = FontWeight.Medium
            )
        },
        actions = {
            IconButton(
                onClick = {
                    onIconClick(AppDestination.Account.route)
                }) {
                Icon(
                    imageVector = AppDestination.Account.inactiveIcon,
                    contentDescription = ""
                )
            }
            IconButton(
                onClick = {
                    onIconClick(AppDestination.Settings.route)
                }) {
                Icon(
                    imageVector = AppDestination.Settings.inactiveIcon,
                    contentDescription = ""
                )
            }
        }
    )
}

@Composable
fun CustomBottomAppBar(navController: NavHostController) {

    val items = listOf(
        AppDestination.Home,
        AppDestination.Extract,
        AppDestination.Transference
    )
    AnimatedNavigationBar(
        screens = items,
        navController = navController
    )
    /*SimpleNavigationBar(
        screens = items,
        navController = navController
    )*/
}

@Preview
@Composable
fun PreviewSimpleNavigationBar() {
    PreviewContaCorrenteMainTheme {
        SimpleNavigationBar(
            listOf(
                AppDestination.Home,
                AppDestination.Extract,
                AppDestination.Transference
            ), rememberNavController()
        )
    }
}

@Preview
@Composable
fun PreviewAnimatedNavigationBar() {
    PreviewContaCorrenteMainTheme {
        AnimatedNavigationBar(
            listOf(
                AppDestination.Home,
                AppDestination.Extract,
                AppDestination.Transference
            ),
            rememberNavController()
        )
    }
}

@Composable
fun AnimatedNavigationBar(
    screens: List<AppDestination>,
    navController: NavHostController
) {

    var isSelected: Boolean
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(
        Modifier
            .shadow(
                elevation = 10.dp
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .height(64.dp)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screens.forEach { screen ->
                isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true
                val animatedWeight by animateFloatAsState(
                    targetValue = if (isSelected) 1.5f else 1f, label = ""
                )
                Box(
                    modifier = Modifier.weight(animatedWeight),
                    contentAlignment = Alignment.Center,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    BottomNavItem(
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController.safeNavigation(screen.route)
                        },
                        screen = screen,
                        isSelected = isSelected
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    modifier: Modifier = Modifier,
    screen: AppDestination,
    isSelected: Boolean,
) {
    val animatedHeight by animateDpAsState(
        targetValue = if (isSelected) 32.dp else 24.dp,
        label = ""
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else .5f, label = ""
    )
    val animatedIconSize by animateDpAsState(
        targetValue = 24.dp,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ), label = ""
    )
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .width(64.dp)
                .height(animatedHeight)
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(20.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            FlipIcon(
                isActive = isSelected,
                activeIcon = screen.activeIcon,
                inactiveIcon = screen.inactiveIcon,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .alpha(animatedAlpha)
                    .size(animatedIconSize),
            )
        }
    }
}

@Composable
fun FlipIcon(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    activeIcon: ImageVector,
    inactiveIcon: ImageVector
) {
    val animationRotation by animateFloatAsState(
        targetValue = if (isActive) 360f else 0f,
        animationSpec = spring(
            stiffness = Spring.StiffnessLow,
            dampingRatio = Spring.DampingRatioMediumBouncy
        ), label = ""
    )

    Box(
        modifier = modifier.graphicsLayer { rotationY = animationRotation },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            rememberVectorPainter(
                image = if (animationRotation > 180f) activeIcon else inactiveIcon
            ),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

private fun NavHostController.safeNavigation(route: String) {
    navigate(route = route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}

@Composable
fun SimpleNavigationBar(screens: List<AppDestination>, navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        screens.forEach { screen ->
            CustomNavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                route = screen.route,
                icon = screen.activeIcon,
                navHostController = navController
            )
        }
    }
}

@Composable
fun RowScope.CustomNavigationBarItem(
    selected: Boolean = false,
    route: String,
    icon: ImageVector,
    navHostController: NavHostController
) {
    NavigationBarItem(
        selected = selected,
        onClick = {
            navHostController.safeNavigation(route)
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    )
}