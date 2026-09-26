package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Quiz
import androidx.compose.material.icons.outlined.Spellcheck
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.Slate500

enum class AppScreen(
    val titleBengali: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME("হোম", Icons.Filled.Home, Icons.Outlined.Home, "nav_tab_home"),
    VOCABULARY("শব্দ", Icons.Filled.MenuBook, Icons.Outlined.MenuBook, "nav_tab_vocab"),
    FLASHCARD("কার্ড", Icons.Filled.Style, Icons.Outlined.Style, "nav_tab_flashcard"),
    QUIZ("কুইজ", Icons.Filled.Quiz, Icons.Outlined.Quiz, "nav_tab_quiz"),
    KANJI("কাঞ্জি", Icons.Filled.Spellcheck, Icons.Outlined.Spellcheck, "nav_tab_kanji"),
    GRAMMAR("গ্রামার", Icons.Filled.AutoStories, Icons.Outlined.AutoStories, "nav_tab_grammar"),
    SETTINGS("সেটিংস", Icons.Filled.Home, Icons.Outlined.Home, "nav_tab_settings") // Used internally for settings navigation
}

@Composable
fun AppBottomNavBar(
    currentScreen: AppScreen,
    onTabSelected: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        AppScreen.HOME,
        AppScreen.VOCABULARY,
        AppScreen.FLASHCARD,
        AppScreen.QUIZ,
        AppScreen.KANJI,
        AppScreen.GRAMMAR
    )

    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        navItems.forEach { screen ->
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
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
