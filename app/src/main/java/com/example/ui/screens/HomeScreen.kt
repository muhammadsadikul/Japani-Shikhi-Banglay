package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppScreen
import com.example.ui.components.PandaSpeechBanner
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber600
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.Indigo50
import com.example.ui.theme.Indigo600
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedBorder
import com.example.ui.theme.JapanRedDark
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.LightBg
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import kotlinx.coroutines.launch

data class LevelInfo(
    val level: String,
    val nameBengali: String,
    val description: String,
    val isAvailable: Boolean,
    val wordCountText: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    onNavigate: (AppScreen) -> Unit,
    onOpenSettings: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val levels = listOf(
        LevelInfo("N5", "প্রাথমিক জাপানি", "দৈনন্দিন সাধারণ ভাববিনিময় ও প্রাথমিক ব্যাকরণ", true, "৫০টি শব্দ + কাঞ্জি ও ব্যাকরণ প্রস্তুত"),
        LevelInfo("N4", "সহজ জাপানি", "মৌলিক দৈনন্দিন আলোচনা ও বাক্য গঠন", false, "শীঘ্রই আসছে"),
        LevelInfo("N3", "মধ্যবর্তী জাপানি", "দৈনন্দিন বিষয়ের সুনির্দিষ্ট বোধগম্যতা", false, "শীঘ্রই আসছে"),
        LevelInfo("N2", "উচ্চ মধ্যবর্তী", "সংবাদ, প্রবন্ধ ও স্বাভাবিক গতিতে কথপোকথন", false, "শীঘ্রই আসছে"),
        LevelInfo("N1", "উন্নত জাপানি", "জটিল দর্শন, পেশাদার ও গভীর সাহিত্যিক ভাষা", false, "শীঘ্রই আসছে")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .verticalScroll(scrollState)
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .testTag("home_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Header Row with Title and Settings Gear Icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = JapanRedLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(JapanRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "日本বাংলা • Japani Shikhi",
                        color = JapanRedDark,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Gear Icon for Settings
            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(CardBg)
                    .border(1.dp, BorderSubtle, CircleShape)
                    .testTag("home_settings_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "সেটিংস",
                    tint = Slate900,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // App Title in Bengali
        Text(
            text = "জাপানি শিখি বাংলায়",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Slate900,
            lineHeight = 36.sp,
            modifier = Modifier.testTag("home_app_title")
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Subtitle
        Text(
            text = "N5 থেকে N1 — বাংলায় সহজে জাপানি শিখুন",
            fontSize = 14.sp,
            color = Slate500,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.testTag("home_app_subtitle")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Panda Mascot Greeting Speech Banner
        PandaSpeechBanner(
            text = "চলো আজ নতুন কিছু শিখি! 🐼🌸",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // N5 Progress & Milestone Section
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp))
                .testTag("n5_progress_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "JLPT N5 অগ্রগতি",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "৪২% সম্পন্ন",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = JapanRed
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Linear Progress bar
                LinearProgressIndicator(
                    progress = { 0.42f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = JapanRed,
                    trackColor = BorderSubtle
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Milestone indicators (25% and 50%)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // 25% Milestone (Achieved)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Emerald50,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Emerald600.copy(alpha = 0.3f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = Emerald600,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text("২৫% মাইলস্টোন", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Emerald600)
                                Text("অর্জিত ✓", fontSize = 10.sp, color = Emerald600)
                            }
                        }
                    }

                    // 50% Milestone (In Progress)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Amber50,
                        border = androidx.compose.foundation.BorderStroke(1.dp, Amber600.copy(alpha = 0.3f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Amber600,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text("৫০% মাইলস্টোন", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Amber600)
                                Text("চলমান...", fontSize = 10.sp, color = Amber600)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Modules Grid (শব্দভাণ্ডার, ফ্ল্যাশকার্ড, কুইজ, কাঞ্জি, গ্রামার)
        Text(
            text = "শেখার মডিউলসমূহ",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = Slate900,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModuleShortcutCard(
                title = "শব্দভাণ্ডার",
                subtitle = "৫০টি শব্দ",
                icon = Icons.Filled.MenuBook,
                iconTint = JapanRed,
                iconBg = JapanRedLight,
                onClick = { onNavigate(AppScreen.VOCABULARY) },
                modifier = Modifier.weight(1f)
            )
            ModuleShortcutCard(
                title = "ফ্ল্যাশকার্ড",
                subtitle = "৩ডি উল্টান",
                icon = Icons.Filled.Style,
                iconTint = Amber600,
                iconBg = Amber50,
                onClick = { onNavigate(AppScreen.FLASHCARD) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModuleShortcutCard(
                title = "কুইজ পরীক্ষা",
                subtitle = "৫টি মোড",
                icon = Icons.Filled.Quiz,
                iconTint = Indigo600,
                iconBg = Indigo50,
                onClick = { onNavigate(AppScreen.QUIZ) },
                modifier = Modifier.weight(1f)
            )
            ModuleShortcutCard(
                title = "কাঞ্জি অনুশীলন",
                subtitle = "১০টি কাঞ্জি",
                icon = Icons.Filled.Spellcheck,
                iconTint = Emerald600,
                iconBg = Emerald50,
                onClick = { onNavigate(AppScreen.KANJI) },
                modifier = Modifier.weight(1f)
            )
            ModuleShortcutCard(
                title = "গ্রামার",
                subtitle = "৫টি নিয়ম",
                icon = Icons.Filled.Translate,
                iconTint = JapanRed,
                iconBg = JapanRedLight,
                onClick = { onNavigate(AppScreen.GRAMMAR) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Unlock System Section (পরের লেভেল আনলক করুন)
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp))
                .testTag("unlock_system_section")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "পরের লেভেল আনলক করুন (N4)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                Text(
                    text = "N5 সম্পন্ন করে বিনামূল্যে আনলক করুন অথবা সরাসরি যান",
                    fontSize = 12.sp,
                    color = Slate500
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Free option: Learn step by step
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LightBg,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "ধাপে ধাপে শিখুন (ফ্রি)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Text(
                                text = "N5 শব্দ ও কাঞ্জি অনুশীলন করে অগ্রগতি অর্জন করুন",
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Emerald50
                        ) {
                            Text(
                                text = "৪২% সম্পন্ন",
                                color = Emerald600,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Paid option: Skip (Coming Soon)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LightBg,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("পেইড স্কিপ ফিচারটি শীঘ্রই আসছে!")
                            }
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "স্কিপ করুন (পেইড)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate700
                            )
                            Text(
                                text = "পরীক্ষা বা পেমেন্ট দিয়ে সরাসরি পরবর্তী লেভেলে যান",
                                fontSize = 11.sp,
                                color = Slate500
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Amber50
                        ) {
                            Text(
                                text = "শীঘ্রই আসছে",
                                color = Amber600,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Level Selection Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "JLPT সকল লেভেল",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Emerald50
            ) {
                Text(
                    text = "N5 উন্মুক্ত",
                    color = Emerald600,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 5 Big Buttons: N5, N4, N3, N2, N1
        levels.forEach { levelInfo ->
            LevelCardItem(
                levelInfo = levelInfo,
                onClick = {
                    if (levelInfo.isAvailable) {
                        onNavigate(AppScreen.VOCABULARY)
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                "${levelInfo.level} স্তরটি শীঘ্রই আসছে! বর্তমানে N5 অনুশীলন করুন।"
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ModuleShortcutCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Slate500
            )
        }
    }
}

@Composable
fun LevelCardItem(
    levelInfo: LevelInfo,
    onClick: () -> Unit
) {
    val isN5 = levelInfo.isAvailable

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isN5) CardBg else Color(0xFFF1F5F9)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isN5) 3.dp else 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isN5) 1.5.dp else 1.dp,
                color = if (isN5) JapanRedBorder else BorderSubtle,
                shape = RoundedCornerShape(16.dp)
            )
            .testTag("level_button_${levelInfo.level.lowercase()}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Level Badge Box
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isN5) {
                                Brush.linearGradient(
                                    colors = listOf(JapanRed, JapanRedDark)
                                )
                            } else {
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFFCBD5E1), Color(0xFF94A3B8))
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = levelInfo.level,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = levelInfo.nameBengali,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isN5) Slate900 else Slate700
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (!isN5) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Amber50,
                                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color(0xFFFDE68A))
                            ) {
                                Text(
                                    text = "শীঘ্রই আসছে",
                                    color = Amber600,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = if (isN5) levelInfo.wordCountText else levelInfo.description,
                        fontSize = 13.sp,
                        color = if (isN5) JapanRed else Slate500,
                        fontWeight = if (isN5) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }

            if (isN5) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(JapanRedLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "প্রবেশ করুন",
                        tint = JapanRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "শীঘ্রই আসছে",
                    tint = Slate400,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

