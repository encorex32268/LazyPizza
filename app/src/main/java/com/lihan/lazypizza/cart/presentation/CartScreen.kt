@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.cart.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.ui.theme.body1Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import org.koin.androidx.compose.koinViewModel

@Composable
fun CartRoot(
    viewModel: CartViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CartScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CartScreen(
    state: CartState,
    onAction: (CartAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cart),
                        style = MaterialTheme.typography.body1Medium.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                    )
                }
            )
        },
        containerColor = Color.Transparent
    ) {  paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1)
            ) {
                item{
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(R.string.recommended_to_add_your_order),
                        style = MaterialTheme.typography.label2SemiBold.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                }
                item {
                    LazyHorizontalGrid(
                        rows = GridCells.Adaptive(160.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {


                    }
                }
            }


        }


    }

}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    CartScreen(
        state = CartState(),
        onAction = {}
    )
}