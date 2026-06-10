package com.lihan.lazypizza.menu.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.components.ProductCard
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.menu.presentation.ProductType.Companion.toTypeName
import com.lihan.lazypizza.menu.presentation.components.MenuTopbar
import com.lihan.lazypizza.menu.presentation.components.ProductSearchbar
import com.lihan.lazypizza.menu.presentation.components.ProductTypeChip
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuRoot(
    viewModel: MenuViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MenuScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onAction: (MenuAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MenuTopbar()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(150.dp),
                painter = painterResource(R.drawable.banner),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(Modifier.height(16.dp))
            ProductSearchbar(
                textFieldState = TextFieldState(),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProductType.entries.forEach { productType ->
                    ProductTypeChip(
                        type = productType,
                        onClick = {

                        }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.items.forEach { (productType, productUis) ->
                    item {
                        Text(
                            text = productType.toTypeName(),
                            style = MaterialTheme.typography.label2SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    items(
                        items = productUis,
                        key = { productUi ->
                            productUi.id
                        }
                    ){ productUi ->
                        ProductCard(
                            productUi = productUi,
                            onPlusClick = {},
                            onMinusClick = {},
                            onItemClick = {
                                if (productUi.type != ProductType.Pizza){
                                    return@ProductCard
                                }
                            },
                            onDeleteClick = {},
                            onAddToCartClick = {},
                        )
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
        MenuScreen(
            state = MenuState(),
            onAction = {}
        )
    }
}