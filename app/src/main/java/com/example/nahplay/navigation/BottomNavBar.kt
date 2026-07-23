package com.example.nahplay.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nahplay.R
import com.example.nahplay.ui.theme.Black
import com.example.nahplay.ui.theme.Purple
import com.example.nahplay.ui.theme.TextMuted

@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    currentRoute:String?,
    onItemClick:(Screen)->Unit
){
    NavigationBar(
        containerColor = Black,
        tonalElevation = 0.dp,
        modifier = Modifier.height(64.dp)
    ) {
        items.forEach{item->
            val selected = currentRoute==item.screen.route

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item.screen) },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
//                icon = TODO(),
//                modifier = TODO(),
//                enabled = TODO(),
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                    )
                },
//                alwaysShowLabel = TODO(),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Purple,
                    selectedTextColor = Purple,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted,
                    indicatorColor = Color.Transparent
                ),
//                interactionSource = TODO()
            )
        }
    }
}

