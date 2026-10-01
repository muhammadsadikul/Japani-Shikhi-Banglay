package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.data.AiRepository
import com.example.ui.components.PandaAvatar
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBg
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    val isError: Boolean = false,
    val lessonCard: LessonCardData? = null
)

data class QuickActionItem(
    val label: String,
    val prompt: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun AiTutorScreen(
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val timeFormatter = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }

    // Initial welcome message with interactive Lesson Card
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                text = "কোননিচিওয়া! আমি তোমার জাপানি AI শিক্ষক 🌸 আমি সরাসরি ক্লাউড এআই এর সাথে যুক্ত। আমাকে যেকোনো জাপানি শব্দ, ব্যাকরণ বা বাক্য সম্পর্কে প্রশ্ন করো!",
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

    // Quick action prompt mappings
    val quickActions = listOf(
        QuickActionItem(
            label = "শব্দ শেখাও",
            prompt = "আমাকে একটা নতুন N5 জাপানি শব্দ শেখাও",
            icon = Icons.Filled.MenuBook
        ),
        QuickActionItem(
            label = "গ্রামার বুঝিয়ে দাও",
            prompt = "একটা N5 গ্রামার পয়েন্ট সহজ বাংলায় বুঝিয়ে দাও",
            icon = Icons.Filled.School
        ),
        QuickActionItem(
            label = "কুইজ নাও",
            prompt = "আমাকে একটা N5 শব্দের কুইজ দাও",
            icon = Icons.Filled.Quiz
        ),
        QuickActionItem(
            label = "উচ্চারণ শেখাও",
            prompt = "একটা জাপানি শব্দের উচ্চারণ শেখাও",
            icon = Icons.Filled.Hearing
        )
    )

    fun handleSend(textToSend: String) {
        val trimmed = textToSend.trim()
        if (trimmed.isBlank() || isLoading) return

        val userMessageTime = timeFormatter.format(Date())
        val userMsg = ChatMessage(
            id = System.currentTimeMillis().toString(),
            text = trimmed,
            isUser = true,
            timestamp = userMessageTime
        )
        messages.add(userMsg)
        inputText = ""
        isLoading = true

        coroutineScope.launch {
            // Scroll to the user message
            listState.animateScrollToItem(messages.size)

            try {
                val reply = AiRepository.sendMessage(message = trimmed, level = "N5")
                val aiMsg = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = reply,
                    isUser = false,
                    timestamp = timeFormatter.format(Date()),
                    isError = false
                )
                messages.add(aiMsg)
            } catch (e: Exception) {
                val errorMsg = ChatMessage(
                    id = (System.currentTimeMillis() + 1).toString(),
                    text = "⚠️ ${e.message ?: "সার্ভার থেকে উত্তর পাওয়া যায়নি। আবার চেষ্টা করুন।"}",
                    isUser = false,
                    timestamp = timeFormatter.format(Date()),
                    isError = true
                )
                messages.add(errorMsg)
            } finally {
                isLoading = false
                listState.animateScrollToItem(messages.size - 1)
            }
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
                // Online indicator badge (Emerald green)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981))
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
                    text = if (isLoading) "AI চিন্তা করছে..." else "অনলাইন 🟢 • Cloudflare AI সক্রিয়",
                    fontSize = 12.sp,
                    color = if (isLoading) JapanRed else Emerald600,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Informational Notice Banner
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
                    text = "লাইভ AI টিউটর সংযুক্ত। যেকোনো জাপানি শব্দ বা ব্যাকরণ প্রশ্ন করুন।",
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

            // Animated Typing Indicator when AI is thinking
            if (isLoading) {
                item(key = "typing_indicator_item") {
                    TypingIndicatorBubble()
                }
            }
        }

        // Quick Action Buttons (Horizontally scrollable LazyRow with prompt mappings)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(quickActions, key = { it.label }) { action ->
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isLoading) Color(0xFFF1F5F9) else JapanRedLight,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isLoading) BorderSubtle else JapanRedBorder
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable(enabled = !isLoading) {
                            handleSend(action.prompt)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = null,
                            tint = if (isLoading) Slate400 else JapanRed,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = action.label,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isLoading) Slate500 else JapanRed
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
                enabled = !isLoading,
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
                enabled = inputText.isNotBlank() && !isLoading,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(if (inputText.isNotBlank() && !isLoading) JapanRed else Slate400)
                    .testTag("ai_send_button")
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
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
}

@Composable
fun TypingIndicatorBubble() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing_dots_transition")
    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = 0, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot1"
    )
    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = 180, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot2"
    )
    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, delayMillis = 360, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot3"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        PandaAvatar(size = 32.dp)
        Spacer(modifier = Modifier.width(6.dp))

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 4.dp,
                bottomEnd = 16.dp
            ),
            color = CardBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
            shadowElevation = 1.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "AI লিখছে",
                    fontSize = 12.sp,
                    color = Slate500,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(JapanRed.copy(alpha = dot1Alpha))
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(JapanRed.copy(alpha = dot2Alpha))
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(JapanRed.copy(alpha = dot3Alpha))
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
    val isError = message.isError

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
                color = when {
                    isUser -> JapanRed
                    isError -> Color(0xFFFEE2E2) // Light red container on error
                    else -> CardBg
                },
                border = when {
                    isUser -> null
                    isError -> androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444))
                    else -> androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                },
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = when {
                            isUser -> Color.White
                            isError -> Color(0xFFB91C1C) // Red text on error
                            else -> Slate900
                        },
                        fontWeight = if (isError) FontWeight.Medium else FontWeight.Normal
                    )

                    // Lesson Card inside bubble (for welcome message)
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
                color = if (isError) Color(0xFFEF4444) else Slate400,
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
