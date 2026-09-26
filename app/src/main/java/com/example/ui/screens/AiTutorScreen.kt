package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PandaAvatar
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
import com.example.ui.theme.JapanRed
import com.example.ui.theme.JapanRedBorder
import com.example.ui.theme.JapanRedLight
import com.example.ui.theme.LightBg
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import kotlinx.coroutines.launch

data class LessonCardData(
    val word: String,
    val furigana: String,
    val romaji: String,
    val bengaliMeaning: String,
    val exampleJapanese: String,
    val exampleReading: String,
    val exampleBengali: String
)

data class ChatMessage(
    val id: String,
    val text: String,
    val isUser: Boolean,
    val timestamp: String,
    val lessonCard: LessonCardData? = null
)

@Composable
fun AiTutorScreen(
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputText by remember { mutableStateOf("") }

    // User requested welcome message and placeholder Lesson Card structure
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                text = "কোননিচিওয়া! আমি তোমার জাপানি AI শিক্ষক। আমি এখনো শেখার মোডে আছি, শীঘ্রই আমি সম্পূর্ণভাবে তোমার সাথে কথা বলতে পারব! 🌸",
                isUser = false,
                timestamp = "এখন",
                lessonCard = LessonCardData(
                    word = "日本語",
                    furigana = "にほんご",
                    romaji = "nihongo",
                    bengaliMeaning = "জাপানি ভাষা (Japanese Language)",
                    exampleJapanese = "日本語を勉強します。",
                    exampleReading = "nihongo o benkyou shimasu.",
                    exampleBengali = "আমি জাপানি ভাষা শিখছি।"
                )
            )
        )
    }

    val quickActions = listOf(
        Pair("শব্দ শেখাও", Icons.Filled.MenuBook),
        Pair("গ্রামার বুঝিয়ে দাও", Icons.Filled.School),
        Pair("কুইজ নাও", Icons.Filled.Quiz),
        Pair("উচ্চারণ শেখাও", Icons.Filled.Hearing)
    )

    fun handleSend(text: String) {
        if (text.isBlank()) return
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = text,
            isUser = true,
            timestamp = "এখন"
        )
        messages.add(userMsg)
        inputText = ""

        coroutineScope.launch {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .testTag("ai_tutor_screen")
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                PandaAvatar(size = 46.dp)
                // Mode indicator badge (Amber/learning mode)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF59E0B))
                        .border(2.dp, CardBg, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "জাপানি AI শিক্ষক",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = JapanRed,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "শেখার মোডে আছে 🌸 (শিঘ্রই লাইভ চ্যাট যুক্ত হবে)",
                    fontSize = 12.sp,
                    color = Slate500
                )
            }
        }

        // Informational Training Notice
        Surface(
            shape = RoundedCornerShape(0.dp),
            color = JapanRedLight,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = JapanRed,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Gemini AI শিক্ষক মডেল তৈরি হচ্ছে। নিচে ইন্টারফেসটির ডিজাইন ও লেসন কার্ড দেখতে পারেন।",
                    fontSize = 11.sp,
                    color = JapanRed,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp
                )
            }
        }

        // Chat Message History
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg,
                    onSpeakJapanese = onSpeakJapanese
                )
            }
        }

        // Quick Action Buttons (Above input bar)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickActions) { (label, icon) ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = JapanRedLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { handleSend(label) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = JapanRed,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = JapanRed
                        )
                    }
                }
            }
        }

        // Bottom Input Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "জাপানি বা বাংলায় কিছু লিখুন...",
                        fontSize = 14.sp,
                        color = Slate400
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_chat_input"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = JapanRed,
                    unfocusedBorderColor = BorderSubtle,
                    focusedContainerColor = LightBg,
                    unfocusedContainerColor = LightBg,
                    focusedTextColor = Slate900,
                    unfocusedTextColor = Slate900
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { handleSend(inputText) },
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(JapanRed)
                    .testTag("ai_send_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "পাঠান",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    onSpeakJapanese: (String) -> Unit
) {
    val isUser = message.isUser

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isUser) {
            PandaAvatar(size = 32.dp)
            Spacer(modifier = Modifier.width(6.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 310.dp)
        ) {
            // Main Bubble
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                color = if (isUser) JapanRed else CardBg,
                border = if (isUser) null else androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = if (isUser) Color.White else Slate900,
                        fontWeight = FontWeight.Normal
                    )

                    // Lesson Card inside bubble
                    if (message.lessonCard != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        LessonCardView(
                            card = message.lessonCard,
                            onSpeak = { onSpeakJapanese(message.lessonCard.word) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                color = Slate400,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun LessonCardView(
    card: LessonCardData,
    onSpeak: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = LightBg),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, JapanRedBorder, RoundedCornerShape(14.dp))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row: Furigana and Speaker Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = card.furigana,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = JapanRed
                    )
                    Text(
                        text = card.word,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Slate900
                    )
                    Text(
                        text = card.romaji,
                        fontSize = 12.sp,
                        color = Slate500
                    )
                }

                IconButton(
                    onClick = onSpeak,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(JapanRedLight)
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "শুনুন",
                        tint = JapanRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Bengali Meaning
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = JapanRedLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "অর্থ: ${card.bengaliMeaning}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = JapanRed,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Example Sentence
            Text(
                text = "উদাহরণ বাক্য:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Slate500
            )
            Text(
                text = card.exampleJapanese,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Slate900
            )
            Text(
                text = card.exampleBengali,
                fontSize = 12.sp,
                color = Slate700
            )
        }
    }
}
