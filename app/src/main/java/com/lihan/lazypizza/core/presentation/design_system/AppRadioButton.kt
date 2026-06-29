package com.lihan.lazypizza.core.presentation.design_system

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.primaryGradient

@Composable
fun AppRadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    gradient: Brush = MaterialTheme.colorScheme.primaryGradient,
    unselectedColor: Color = MaterialTheme.colorScheme.secondary,
    size: Int = 16
) {
    val innerCircleSize by animateDpAsState(
        targetValue = if (selected) (size / 2).dp else 0.dp,
        label = "InnerCircleSize"
    )

    Box(
        modifier = modifier
            .size(size.dp)
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.5.dp,
                        brush = gradient,
                        shape = CircleShape
                    )
                } else {
                    Modifier.border(
                        width = 1.5.dp,
                        color = unselectedColor,
                        shape = CircleShape
                    )
                }
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClick() }
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(innerCircleSize)
                    .background(brush = gradient, shape = CircleShape)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AppRadioButtonPreview() {
    LazyPizzaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppRadioButton(
                selected = true,
                onClick = null
            )
            AppRadioButton(
                selected = false,
                onClick = null
            )
        }
    }
}