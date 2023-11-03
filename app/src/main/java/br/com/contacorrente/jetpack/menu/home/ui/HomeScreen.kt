@file:OptIn(ExperimentalMaterialApi::class)

package br.com.contacorrente.jetpack.menu.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NorthEast
import androidx.compose.material.icons.outlined.SouthWest
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.jetpack.CustomTopAppBar
import br.com.contacorrente.jetpack.ui.PreviewContaCorrenteMainTheme
import br.com.contacorrente.util.Utility
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    userEmail: String,
    homeViewModel: HomeViewModel = koinViewModel(),
    onIconClick: (String) -> Unit
) {

    val homeUiState by homeViewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeUiState.isRefreshing, onRefresh = {
            homeViewModel.loadInformationFromSwipe(userEmail)
        })

    HomeLayout(homeUiState, pullRefreshState, onIconClick)

    LaunchedEffect(Unit) {
        homeViewModel.loadInformation(userEmail)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    iconContentDescription: String = "",
    onClick: () -> Unit,
    imageVector: ImageVector,
    mainText: String,
    descriptionText: String,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                ),
                imageVector = imageVector,
                contentDescription = iconContentDescription
            )
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                text = mainText,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                text = descriptionText,
                fontWeight = FontWeight.Medium
            )
        }
    )
}

@Preview
@Composable
fun CardLayoutPreview() {
    PreviewContaCorrenteMainTheme {
        CardLayout()
    }
}

@Composable
fun CardLayout(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        CustomCard(
            modifier = Modifier.weight(1f),
            onClick = {},
            imageVector = Icons.Outlined.NorthEast,
            mainText = "Pagar alguém",
            descriptionText = "Para alguma carteira, banco ou celular"
        )

        Spacer(modifier = Modifier.padding(8.dp))

        CustomCard(
            modifier = Modifier.weight(1f),
            onClick = {},
            imageVector = Icons.Outlined.SouthWest,
            mainText = "Solicitar dinheiro",
            descriptionText = "Solicitar dinheiro de clientes do aplicativo"
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeLayout(
    homeUiState: HomeUiState,
    pullRefreshState: PullRefreshState,
    onIconClick: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())
    ) {

        Column {

            CustomTopAppBar(
                toolbarTitle = "Olá ${homeUiState.userName}",
                onIconClick = onIconClick
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = Utility.currencyFormat(homeUiState.userBalance),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = "Coisas para você fazer...",
                    fontWeight = FontWeight.Medium
                )

                CardLayout(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        PullRefreshIndicator(
            refreshing = homeUiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}