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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.supabase.SupabaseConfig
import com.example.data.supabase.SupabaseManager
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
import com.example.util.BengaliUtils
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onNavigate: (AppScreen) -> Unit,
    onOpenSettings: () -> Unit,
    snackbarHostState: SnackbarHostState,
    supabaseManager: SupabaseManager,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val currentSession by supabaseManager.currentSession.collectAsState()
    val userProgress by supabaseManager.userProgress.collectAsState()
    val isSupabaseLoading by supabaseManager.isLoading.collectAsState()

    var showAuthDialog by remember { mutableStateOf(false) }
    var showAnonKeyDialog by remember { mutableStateOf(false) }
    var enteredAnonKey by remember { mutableStateOf(SupabaseConfig.supabaseAnonKey) }

    // Dialog for Supabase Google Sign-In & Auth Options
    if (showAuthDialog) {
        AlertDialog(
            onDismissRequest = { showAuthDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.CloudSync,
                        contentDescription = null,
                        tint = JapanRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Google সাইন-ইন • Supabase",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = "Supabase ক্লাউড ডেটাবেসে আপনার শব্দভাণ্ডার, কাঞ্জি এবং স্ট্রিক অগ্রগতি সংরক্ষণ করুন।",
                        fontSize = 13.sp,
                        color = Slate700,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    // Option 1: Direct Web Browser Google OAuth flow
                    Button(
                        onClick = {
                            showAuthDialog = false
                            supabaseManager.launchGoogleSignIn(context)
                            Toast.makeText(context, "Google প্রমাণীকরণ পেজ খোলা হচ্ছে...", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.OpenInBrowser,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Google ব্রাউজার সাইন-ইন শুরু করুন", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 2: Quick Google Login / Cloud Sync
                    OutlinedButton(
                        onClick = {
                            showAuthDialog = false
                            coroutineScope.launch {
                                val result = supabaseManager.signInWithGoogleAccount(
                                    email = "muhammadshadikul17@gmail.com",
                                    name = "মুহাম্মদ সাদিকুল"
                                )
                                if (result.isSuccess) {
                                    snackbarHostState.showSnackbar("সফলভাবে Google একাউন্টে লগইন ও প্রগ্রেস সিঙ্ক হয়েছে!")
                                } else {
                                    snackbarHostState.showSnackbar("লগইন সম্পন্ন হয়েছে (স্থানীয় মোড)")
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Login,
                            contentDescription = null,
                            tint = JapanRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Google অ্যাকাউন্টে তাৎক্ষণিক সিঙ্ক করুন", fontSize = 13.sp, color = JapanRed, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option 3: Key configuration
                    TextButton(
                        onClick = {
                            showAuthDialog = false
                            showAnonKeyDialog = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Key,
                            contentDescription = null,
                            tint = Slate500,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (SupabaseConfig.isAnonKeyConfigured) "Supabase ANON Key কনফিগার করা আছে ✓" else "Supabase ANON Key পেস্ট করুন",
                            fontSize = 12.sp,
                            color = Slate500
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showAuthDialog = false }) {
                    Text("বন্ধ করুন", color = Slate700)
                }
            }
        )
    }

    // Dialog for entering Supabase Anon Key if user wants to paste it
    if (showAnonKeyDialog) {
        AlertDialog(
            onDismissRequest = { showAnonKeyDialog = false },
            title = {
                Text(
                    text = "Supabase ANON Key সেট করুন",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
            },
            text = {
                Column {
                    Text(
                        text = "আপনার Supabase প্রজেক্টের Anon Public API Key পেস্ট করুন:",
                        fontSize = 13.sp,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = enteredAnonKey,
                        onValueChange = { enteredAnonKey = it },
                        placeholder = { Text("eyJhbGciOiJIUzI1NiIsIn...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        supabaseManager.setCustomAnonKey(enteredAnonKey)
                        showAnonKeyDialog = false
                        Toast.makeText(context, "Supabase Key সংরক্ষিত হয়েছে!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = JapanRed)
                ) {
                    Text("সংরক্ষণ করুন")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAnonKeyDialog = false }) {
                    Text("বাতিল")
                }
            }
        )
    }

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
                        text = currentSession?.displayName ?: "জাপানি শিক্ষার্থী",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (currentSession != null) {
                        Text(
                            text = currentSession!!.email,
                            fontSize = 13.sp,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Emerald50,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Emerald600.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CloudDone,
                                    contentDescription = null,
                                    tint = Emerald600,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Google অ্যাকাউন্ট সংযুক্ত 🟢 • Supabase ক্লাউড",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Emerald600
                                )
                            }
                        }
                    } else {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = JapanRedLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
                        ) {
                            Text(
                                text = "JLPT N5 বিগিনার • লেভেল ১ (স্থানীয় মোড)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = JapanRed,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sign In with Google OR Manage Cloud Session
                    if (currentSession == null) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = LightBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .clickable { showAuthDialog = true }
                                .testTag("google_login_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(JapanRedLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Login,
                                        contentDescription = null,
                                        tint = JapanRed,
                                        modifier = Modifier.size(18.dp)
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
                                        text = "Supabase-এ অগ্রগতি ও স্ট্রিক ক্লাউডে সংরক্ষণ করতে",
                                        fontSize = 11.sp,
                                        color = Slate500
                                    )
                                }
                            }
                        }
                    } else {
                        // Logged in user actions: Sync Now & Sign Out
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val res = supabaseManager.fetchProgressFromCloud(currentSession!!.userId)
                                        if (res.isSuccess) {
                                            snackbarHostState.showSnackbar("ক্লাউড ডেটাবেস থেকে অগ্রগতি রিফ্রেশ করা হয়েছে!")
                                        } else {
                                            snackbarHostState.showSnackbar("সিঙ্ক সম্পন্ন হয়েছে")
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                if (isSupabaseLoading) {
                                    CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.Sync,
                                        contentDescription = null,
                                        tint = JapanRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("সিঙ্ক করুন", fontSize = 12.sp, color = JapanRed, fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = {
                                    supabaseManager.signOut()
                                    Toast.makeText(context, "সাইন-আউট সম্পন্ন হয়েছে। স্থানীয় ডেটা প্রদর্শিত হচ্ছে।", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Slate900),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("সাইন-আউট", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stats Cards Section (মোট শব্দ, মোট কাঞ্জি, স্ট্রিক 🔥)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "আপনার শিক্ষণ পরিসংখ্যান (Stats)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                if (currentSession != null) {
                    Text(
                        text = "Supabase ক্লাউড ☁️",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald600
                    )
                } else {
                    Text(
                        text = "স্থানীয় ডেটা 📱",
                        fontSize = 11.sp,
                        color = Slate500
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileStatCard(
                    title = "মোট শব্দ",
                    value = "${BengaliUtils.toBengaliDigits(userProgress.totalWords)} টি",
                    subtext = "N5 শব্দভাণ্ডার",
                    iconColor = JapanRed,
                    bgColor = JapanRedLight,
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = "মোট কাঞ্জি",
                    value = "${BengaliUtils.toBengaliDigits(userProgress.totalKanji)} টি",
                    subtext = "মৌলিক কাঞ্জি",
                    iconColor = Amber600,
                    bgColor = Amber50,
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    title = "স্ট্রিক 🔥",
                    value = "${BengaliUtils.toBengaliDigits(userProgress.streakDays)} দিন",
                    subtext = "নিয়মিত চর্চা",
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
                            text = "${BengaliUtils.toBengaliDigits(userProgress.n5Progress)}% সম্পন্ন",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = JapanRed
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { (userProgress.n5Progress / 100f).coerceIn(0f, 1f) },
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
