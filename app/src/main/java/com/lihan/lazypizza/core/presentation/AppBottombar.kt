package com.lihan.lazypizza.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.primary8
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHigher
import com.lihan.lazypizza.core.presentation.ui.theme.title4

@Composable
fun AppBottomBar(
    currentRoute: Route,
    onItemClick: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        containerColor = MaterialTheme.colorScheme.surfaceHigher,
        contentColor = Color.Transparent
    ) {
        bottomBarItems.forEach { bottomBarItem ->
            val isSelected = bottomBarItem.route == currentRoute
            NavigationBarItem(
                selected = false,
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = if (isSelected) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary,
                ),
                alwaysShowLabel = true,
                onClick = {
                    onItemClick(bottomBarItem.route)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (bottomBarItem.badge > 0){
                                Text(
                                    text = bottomBarItem.badge.toString(),
                                    style = MaterialTheme.typography.title4.copy(
                                        color = MaterialTheme.colorScheme.onSecondary
                                    ),
                                )
                            }
                        }
                    ) {
                        Icon(
                            imageVector = bottomBarItem.icon,
                            contentDescription = null
                        )
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected){
                                        MaterialTheme.colorScheme.primary8
                                    }else{
                                        Color.Transparent
                                    }
                                )
                        )

                    }
                },
                label = {
                    Text(
                        text = bottomBarItem.route.toRouteName(),
                        style = MaterialTheme.typography.title4
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun AppBottomBarPreview() {
    LazyPizzaTheme {
        AppBottomBar(
            currentRoute = Route.Menu,
            onItemClick = {}
        )
    }
}