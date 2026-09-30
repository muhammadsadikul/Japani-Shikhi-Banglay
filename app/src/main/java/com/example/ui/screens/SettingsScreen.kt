package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LanguageRepository
import com.example.data.SettingsManager
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
fun SettingsScreen(
    settingsManager: SettingsManager,
    onBack: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val isDarkMode by settingsManager.isDarkMode.collectAsState()
    val showEnglishMeaning by settingsManager.showEnglishMeaning.collectAsState()
    val smartNotifications by settingsManager.smartNotifications.collectAsState()
    val appLanguage by settingsManager.appLanguage.collectAsState()

    var showLanguageSelector by remember { mutableStateOf(false) }

    if (showLanguageSelector) {
        LanguageSelectionScreen(
            initialLanguage = appLanguage,
            onLanguageSelected = { newLang ->
                settingsManager.setAppLanguage(newLang)
                showLanguageSelector = false
                if (newLang != "bn") {
                    val comingSoonNotice = "Translation for this language is coming soon! UI will remain in Bengali for now."
                    Toast.makeText(context, comingSoonNotice, Toast.LENGTH_LONG).show()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(comingSoonNotice)
                    }
                } else {
                    val bnNotice = "ভাষা বাংলায় সেট করা হয়েছে।"
                    Toast.makeText(context, bnNotice, Toast.LENGTH_SHORT).show()
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(bnNotice)
                    }
                }
            },
            onBack = { showLanguageSelector = false },
            modifier = modifier
        )
        return
    }

    val downloadLevels = listOf(
        Pair("JLPT N5 প্যাকেজ", true),
        Pair("JLPT N4 প্যাকেজ", false),
        Pair("JLPT N3 প্যাকেজ", false),
        Pair("JLPT N2 প্যাকেজ", false),
        Pair("JLPT N1 প্যাকেজ", false)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .testTag("settings_screen")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.testTag("settings_back_button")) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "হোমে ফিরে যান",
                    tint = Slate900
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "সেটিংস ও পছন্দসমূহ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // General Settings Section
            Text(
                text = "সাধারণ সেটিংস",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Slate500,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // App Language Selector
                    val currentLangObj = LanguageRepository.getLanguageByCode(appLanguage)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { showLanguageSelector = true }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(JapanRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Language,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "অ্যাপের ভাষা (App Language)",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "${currentLangObj.flag} ${currentLangObj.displayName}",
                                    fontSize = 13.sp,
                                    color = JapanRed,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = JapanRedLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "পরিবর্তন",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = JapanRed
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Dark Mode Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(JapanRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.DarkMode,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ডার্ক মোড (Dark Mode)",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = if (isDarkMode == true) "ডার্ক থিম চালু" else "লাইট থিম চালু",
                                    fontSize = 12.sp,
                                    color = Slate500
                                )
                            }
                        }

                        Switch(
                            checked = isDarkMode == true,
                            onCheckedChange = { checked ->
                                settingsManager.setDarkMode(checked)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = JapanRed
                            ),
                            modifier = Modifier.testTag("dark_mode_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Show/Hide English Meaning Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(JapanRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Translate,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ইংরেজি অর্থ প্রদর্শন",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = if (showEnglishMeaning) "কার্ডে ইংরেজি অর্থ দৃশ্যমান" else "শুধুমাত্র বাংলা অর্থ প্রদর্শিত",
                                    fontSize = 12.sp,
                                    color = Slate500
                                )
                            }
                        }

                        Switch(
                            checked = showEnglishMeaning,
                            onCheckedChange = { checked ->
                                settingsManager.setShowEnglishMeaning(checked)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = JapanRed
                            ),
                            modifier = Modifier.testTag("show_english_switch")
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Smart Notifications Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(JapanRedLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Notifications,
                                    contentDescription = null,
                                    tint = JapanRed,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "দৈনিক স্মার্ট নোটিফিকেশন",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "প্রতিদিন রাত ৮:০০ টায় অনুশীলনের বার্তা",
                                    fontSize = 12.sp,
                                    color = Slate500
                                )
                            }
                        }

                        Switch(
                            checked = smartNotifications,
                            onCheckedChange = { checked ->
                                settingsManager.setSmartNotifications(checked)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (checked) "স্মার্ট নোটিফিকেশন চালু করা হয়েছে।" else "নোটিফিকেশন বন্ধ করা হয়েছে।"
                                    )
                                }
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = JapanRed
                            ),
                            modifier = Modifier.testTag("smart_notifications_switch")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Offline Download UI Section
            Text(
                text = "ডাউনলোড ও অফলাইন প্যাক",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Slate500,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    downloadLevels.forEachIndexed { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = item.first,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = if (item.second) "অফলাইনে প্রস্তুত (৫০টি শব্দ + কাঞ্জি)" else "ক্লাউড প্যাকেজ (শীঘ্রই আসছে)",
                                    fontSize = 12.sp,
                                    color = Slate500
                                )
                            }

                            if (item.second) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Emerald50,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Emerald600.copy(alpha = 0.4f))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = Emerald600,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "ইনস্টল করা আছে",
                                            color = Emerald600,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            } else {
                                OutlinedButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                "${item.first} শীঘ্রই অফলাইন ডাউনলোডের জন্য উপলব্ধ হবে!"
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = JapanRed),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                        horizontal = 10.dp,
                                        vertical = 4.dp
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Download,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("ডাউনলোড করুন", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        if (index < downloadLevels.size - 1) {
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderSubtle))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // About Section
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "জাপানি শিখি বাংলায় (日本বাংলা)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "সংস্করণ ২.০.০ • প্যাকেজ: com.sadikul.japanishikhibanglay",
                        fontSize = 12.sp,
                        color = Slate500
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "বাঙালি শিক্ষার্থীদের জন্য সহজ জাপানি ভাষা শিক্ষার পূর্ণাঙ্গ প্ল্যাটফর্ম।",
                        fontSize = 12.sp,
                        color = Slate700,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
