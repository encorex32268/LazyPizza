@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.history.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.TrackScreenViewEvent
import com.lihan.lazypizza.core.presentation.components.PlaceholderView
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.history.presentation.components.HistoryItemCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HistoryRoot(
    navigateToMenu: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onAction = { action ->
            when(action){
                HistoryAction.OnNavigateToMenu -> navigateToMenu()
                HistoryAction.OnNavigateToLogin -> navigateToLogin()
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun HistoryScreen(
    state: HistoryState,
    onAction: (HistoryAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    TrackScreenViewEvent("HistoryScreen")
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.order_history),
                        style = MaterialTheme.typography.body1Medium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        ),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            when{
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
                !state.isSignIn ->{
                    PlaceholderView(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 120.dp),
                        title = stringResource(R.string.history_is_empty_title),
                        content = stringResource(R.string.history_is_empty_content),
                        buttonText = stringResource(R.string.sign_in),
                        onClick = {
                            onAction(HistoryAction.OnNavigateToLogin)
                        }
                    )
                }
                state.items.isEmpty() -> {
                    PlaceholderView(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 120.dp),
                        title = stringResource(R.string.history_is_empty_title),
                        content = stringResource(R.string.history_is_empty_content),
                        buttonText = stringResource(R.string.go_to_menu),
                        onClick = {
                            onAction(HistoryAction.OnNavigateToMenu)
                        }
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.items){ orderHistoryUi ->
                            HistoryItemCard(
                                orderNumber = orderHistoryUi.orderNumber,
                                createTime = orderHistoryUi.formatedTime,
                                status = orderHistoryUi.status,
                                details = orderHistoryUi.details,
                                totalAmount = orderHistoryUi.totalAmount
                            )
                        }
                        item {
                            Spacer(Modifier.height(16.dp))
                        }

                    }
                }
            }

        }
    }
}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        HistoryScreen(
            state = HistoryState(),
            onAction = {}
        )
    }
}