package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AppScreen
import com.example.ui.components.PandaAvatar
import com.example.ui.theme.Amber50
import com.example.ui.theme.Amber600
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
import com.example.ui.theme.Emerald50
import com.example.ui.theme.Emerald600
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedBorder
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.LightBg
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onNavigate: (AppScreen) -> Unit,
    onOpenSettings: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .verticalScroll(scrollState)
            .testTag("profile_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "আমার প্রোফাইল",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LightBg)
                    .testTag("profile_settings_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "সেটিংস খুলুন",
                    tint = Slate900,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Avatar & Info Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Circle
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .background(JapanRedLight)
                            .border(3.dp, JapanRed, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        PandaAvatar(size = 72.dp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "জাপানি শিক্ষার্থী",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = JapanRedLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
                    ) {
                        Text(
                            text = "JLPT N5 বিগিনার • লেভেল ১",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = JapanRed,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Google Login Button (Placeholder UI)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = LightBg,
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Google সাইন-ইন সেবা শীঘ্রই চালু হবে!")
                                }
                            }
                            .testTag("google_login_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(JapanRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Login,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Google অ্যাকাউন্ট দিয়ে সাইন-ইন করুন",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "অগ্রগতি ও স্ট্রিক ক্লাউডে সংরক্ষণ করতে",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats Cards Section (মোট শব্দ শেখা, মোট কাঞ্জি, ৭ দিনের স্ট্রিক 🔥)
            Text(
                text = "আপনার শিক্ষণ পরিসংখ্যান (Stats)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatCard(
                    title = "মোট শব্দ",
                    value = "৫০ টি",
                    subtext = "N5 ১০০%",
                    iconColor = JapanRed,
                    bgColor = JapanRedLight,
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = "মোট কাঞ্জি",
                    value = "১০ টি",
                    subtext = "মৌলিক",
                    iconColor = Amber600,
                    bgColor = Amber50,
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = "স্ট্রিক 🔥",
                    value = "৭ দিন",
                    subtext = "অবিরাম",
                    iconColor = Emerald600,
                    bgColor = Emerald50,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar Section
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.EmojiEvents,
                                contentDescription = null,
                                tint = Amber600,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "N5 সম্পন্ন হার",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                        }
                        Text(
                            text = "৪২% সম্পন্ন",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = JapanRed
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { 0.42f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = JapanRed,
                        trackColor = BorderSubtle
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Navigation Links
            Text(
                text = "অন্যান্য মডিউল ও সাহায্য",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column {
                    ProfileMenuRow(
                        title = "অ্যাপ সেটিংস (থিম, নোটিফিকেশন, অফলাইন)",
                        icon = Icons.Filled.Settings,
                        iconTint = JapanRed,
                        onClick = onOpenSettings
                    )
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    ProfileMenuRow(
                        title = "ফ্ল্যাশকার্ড অনুশীলন (৩ডি সোয়াইপ)",
                        icon = Icons.Filled.Style,
                        iconTint = Amber600,
                        onClick = { onNavigate(AppScreen.FLASHCARD) }
                    )
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    ProfileMenuRow(
                        title = "কাঞ্জি অনুশীলন ও ড্রয়িং",
                        icon = Icons.Filled.Spellcheck,
                        iconTint = Emerald600,
                        onClick = { onNavigate(AppScreen.KANJI) }
                    )
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    ProfileMenuRow(
                        title = "ব্যাকরণ গাইড ও বিশ্লেষণ",
                        icon = Icons.Filled.AutoStories,
                        iconTint = Color(0xFF4F46E5),
                        onClick = { onNavigate(AppScreen.GRAMMAR) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Footer
            Text(
                text = "জাপানি শিখি বাংলায় • সংস্করণ ২.০\nসবার জন্য সহজ ও বাংলায় উন্মুক্ত জাপানি শিক্ষা 🇧🇩🇯🇵",
                fontSize = 12.sp,
                color = Slate400,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun ProfileStatCard(
    title: String,
    value: String,
    subtext: String,
    iconColor: Color,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        modifier = modifier.border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = bgColor,
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "★",
                        fontSize = 16.sp,
                        color = iconColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900
            )

            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Slate500
            )

            Text(
                text = subtext,
                fontSize = 10.sp,
                color = iconColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileMenuRow(
    title: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(iconTint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate900
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Slate400,
            modifier = Modifier.size(16.dp)
        )
    }
}
