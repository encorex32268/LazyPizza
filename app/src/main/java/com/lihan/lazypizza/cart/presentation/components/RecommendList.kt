package com.lihan.lazypizza.cart.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.domain.formatToTwoDecimals
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.menu.presentation.MenuState
import com.lihan.lazypizza.menu.presentation.model.ProductUi
import kotlin.random.Random


fun LazyGridScope.RecommendListRow(
    recommendItems: List<ProductUi>,
    onAddClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (recommendItems.isNotEmpty()) {
        item {
            RecommendList(
                recommendItems = recommendItems,
                onAddClick = onAddClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun RecommendList(
    recommendItems: List<ProductUi>,
    onAddClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        modifier = modifier
            .fillMaxWidth()
            .height(216.dp)
            .animateContentSize(),
        rows = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = recommendItems,
            key = {productUi -> "recommend_${productUi.id}" }
        ) { productUi ->
            RecommendProductCard(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(width = 160.dp)
                    .animateItem(),
                image = productUi.imageUrl,
                name = productUi.name,
                price = "$${productUi.price.formatToTwoDecimals()}",
                onAddClick = {
                    onAddClick(productUi.id)
                }
            )

        }
    }
}

@Preview
@Composable
private fun RecommendListPreview() {
    LazyPizzaTheme {
        RecommendList(
            recommendItems = MenuState.fakeProductUiList.shuffled(random = Random(4)),
            onAddClick = {}
        )
    }

}
