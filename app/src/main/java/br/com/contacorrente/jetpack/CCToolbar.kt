@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.contacorrente.jetpack

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

@Preview
@Composable
fun PreviewCustomToolbar() {
    ContaCorrenteMainTheme {
        CustomTopAppBar("Olá Bárbara,")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(toolbarTitle: String) {

    TopAppBar(
        title = {
            Text(
                text = toolbarTitle,
                fontWeight = FontWeight.Medium
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Face,
                    contentDescription = ""
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
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
    AnimatedNavigationBar(screens = items)
    /*SimpleNavigationBar(
        listOf(
            AppDestination.Home,
            AppDestination.Extract,
            AppDestination.Transference
        ), navController
    )*/
}

@Preview
@Composable
fun PreviewSimpleNavigationBar() {
    ContaCorrenteMainTheme(useDarkTheme = false) {
        SimpleNavigationBar(
            listOf(
                AppDestination.Home,
                AppDestination.Extract,
                AppDestination.Transference
            ), rememberNavController()
        )
    }
}

@Composable
fun SimpleNavigationBar(items: List<AppDestination>, navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
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

@Preview
@Composable
fun PreviewAnimatedNavigationBar() {
    ContaCorrenteMainTheme(useDarkTheme = false) {
        AnimatedNavigationBar(
            listOf(
                AppDestination.Home,
                AppDestination.Extract,
                AppDestination.Transference
            )
        )
    }
}

@Composable
fun AnimatedNavigationBar(
    screens: List<AppDestination>
) {
    var selectedScreen by remember { mutableIntStateOf(0) }
    Box(
        Modifier
            .shadow(
                elevation = 10.dp
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .height(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (screen in screens) {
                val isSelected = screen == screens[selectedScreen]
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
                            selectedScreen = screens.indexOf(screen)
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
fun RowScope.CustomNavigationBarItem(
    selected: Boolean = false,
    route: String,
    icon: ImageVector,
    navHostController: NavHostController
) {
    NavigationBarItem(
        selected = selected,
        onClick = {
            navHostController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
    )
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
    val animatedElevation by animateDpAsState(
        targetValue = if (isSelected) 15.dp else 0.dp,
        label = ""
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else .5f, label = ""
    )
    val animatedIconSize by animateDpAsState(
        targetValue = if (isSelected) 26.dp else 20.dp,
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
                .shadow(
                    elevation = animatedElevation,
                    shape = RoundedCornerShape(20.dp)
                )
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
                image = if (animationRotation > 90f) activeIcon else inactiveIcon
            ),
            contentDescription = null,
            tint = if (isActive) {
                MaterialTheme.colorScheme.onSecondaryContainer
            } else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}