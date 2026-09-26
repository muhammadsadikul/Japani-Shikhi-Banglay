package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardBg
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate900

enum class AppScreen(
    val titleBengali: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("হোম", Icons.Filled.Home, Icons.Outlined.Home, "nav_tab_home"),
    VOCABULARY("শব্দভাণ্ডার", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "nav_tab_vocab"),
    FLASHCARD("ফ্ল্যাশকার্ড", Icons.Filled.Style, Icons.Outlined.Style, "nav_tab_flashcard")
}

@Composable
fun AppBottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        AppScreen.entries.forEach { screen ->
            val isSelected = screen == currentScreen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(screen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = screen.titleBengali
                    )
                },
                label = {
                    Text(
                        text = screen.titleBengali,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = JapanRed,
                    selectedTextColor = JapanRed,
                    indicatorColor = JapanRedLight,
                    unselectedIconColor = Slate500,
                    unselectedTextColor = Slate500
                ),
                modifier = Modifier.testTag(screen.testTag)
            )
        }
    }
}
