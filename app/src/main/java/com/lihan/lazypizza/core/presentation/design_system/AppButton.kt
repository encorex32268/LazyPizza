package com.lihan.lazypizza.core.presentation.design_system

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.primary8
import com.lihan.lazypizza.core.presentation.ui.theme.primaryGradient
import com.lihan.lazypizza.core.presentation.ui.theme.textPrimary8
import com.lihan.lazypizza.core.presentation.ui.theme.title3


enum class ButtonType {
    Filled, Outline, Text
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    type: ButtonType,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    when (type) {
        ButtonType.Filled -> {
            val gradientBrush = if (enabled) {
                MaterialTheme.colorScheme.primaryGradient
            } else {
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.textPrimary8,
                        MaterialTheme.colorScheme.textPrimary8
                    )
                )
            }
            Box(
                modifier = modifier
                    .clip(RoundedCornerShape(100))
                    .background(gradientBrush)
                    .clickable(
                        enabled = enabled,
                        onClick = onClick
                    )
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.title3,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
                    }
                )
            }
        }

        ButtonType.Outline -> {

            OutlinedButton(
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent,
                    disabledContentColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f),
                    disabledContainerColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = if (enabled){
                        MaterialTheme.colorScheme.primary8
                    }else{
                        MaterialTheme.colorScheme.outline
                    }
                )
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.title3.copy(
                        color = if (enabled){
                            MaterialTheme.colorScheme.primary
                        }else{
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
                        }
                    )
                )
            }


        }
        ButtonType.Text -> {
            TextButton(
                onClick = onClick,
                enabled = enabled,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent,
                    disabledContentColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f),
                    disabledContainerColor = Color.Transparent
                )
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.title3.copy(
                        color = if (enabled){
                            MaterialTheme.colorScheme.primary
                        }else{
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
                        }
                    )
                )
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    LazyPizzaTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppButton(
                text = "FilledButton",
                onClick = {},
                type = ButtonType.Filled,
                enabled = true
            )
            AppButton(
                text = "FilledButton",
                onClick = {},
                type = ButtonType.Filled,
                enabled = false
            )
            AppButton(
                text = "OutlineButton",
                onClick = {},
                type = ButtonType.Outline,
                enabled = true
            )

            AppButton(
                text = "OutlineButton",
                onClick = {},
                type = ButtonType.Outline,
                enabled = false
            )

            AppButton(
                text = "TextButton",
                onClick = {},
                type = ButtonType.Text,
                enabled = true
            )

            AppButton(
                text = "TextButton",
                onClick = {},
                type = ButtonType.Text,
                enabled = false
            )

        }
    }
}