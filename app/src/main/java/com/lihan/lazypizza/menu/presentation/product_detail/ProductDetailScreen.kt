 package com.lihan.lazypizza.menu.presentation.product_detail import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProductDetailRoot(
    viewModel: ProductDetailViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    ProductDetailScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ProductDetailScreen(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    LazyPizzaTheme {
        ProductDetailScreen(
            state = ProductDetailState(),
            onAction = {}
        )
    }
}