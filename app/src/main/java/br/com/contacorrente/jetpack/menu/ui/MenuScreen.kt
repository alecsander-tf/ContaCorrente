@file:OptIn(ExperimentalMaterialApi::class)

package br.com.contacorrente.jetpack.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.R
import br.com.contacorrente.util.Utility
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    userEmail: String,
    menuViewModel: MenuViewModel = koinViewModel()
) {

    val menuUiState by menuViewModel.uiState.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = menuUiState.isRefreshing, onRefresh = {
            menuViewModel.loadInformationFromSwipe(userEmail)
        })

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        LaunchedEffect(Unit) {
            menuViewModel.loadInformation(userEmail)
        }

        MenuLayout(menuUiState, pullRefreshState)
    }
}

@Composable
fun MenuLayout(
    menuUiState: MenuUiState,
    pullRefreshState: PullRefreshState,
) {

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .verticalScroll(rememberScrollState())

    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = menuUiState.userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.your_balance),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Text(
                text = Utility.currencyFormat(menuUiState.userBalance),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        PullRefreshIndicator(
            refreshing = menuUiState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}