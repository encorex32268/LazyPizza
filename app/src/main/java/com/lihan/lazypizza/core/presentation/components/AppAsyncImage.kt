package com.lihan.lazypizza.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Search
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme

@Composable
fun AppAsyncImage(
    image: Any?,
    modifier: Modifier = Modifier,
    placeholder: Int = R.drawable.logo
) {
    val isInspect = LocalInspectionMode.current
    val painter = rememberAsyncImagePainter(
        model = if (isInspect) placeholder else image
    )

    val state by painter.state.collectAsStateWithLifecycle()

    when (state) {
        is AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                CircularWavyProgressIndicator(
                    modifier = Modifier.size(36.dp)
                )
            }
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = modifier,
                painter = painter,
                contentDescription = null,
            )
        }

        is AsyncImagePainter.State.Error -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true, name = "Success State")
@Composable
private fun AppAsyncImagePreview() {
    LazyPizzaTheme {
        AppAsyncImage(
            image = "",
            modifier = Modifier.size(100.dp)
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun AppAsyncImageLoadingPreview() {
    LazyPizzaTheme {
        // In preview, we bypass loading by using placeholder, 
        // so to test actual UI we can just show the indicator
        Box(
            modifier = Modifier.size(100.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}