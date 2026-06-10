@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.menu.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.presentation.Phone
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body1Regular
import com.lihan.lazypizza.core.presentation.ui.theme.body3Bold

@Composable
fun MenuTopbar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.body3Bold.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Phone,
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = null
                )
                //User's Phone
                Text(
                    text = "+1 (555) 321-7890",
                    style = MaterialTheme.typography.body1Regular.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun MenuTopbarPreview() {
    LazyPizzaTheme {
        MenuTopbar()
    }
}