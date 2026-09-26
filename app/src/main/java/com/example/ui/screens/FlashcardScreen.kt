package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VocabularyRepository
import com.example.data.model.VocabularyItem
import com.example.ui.components.PronunciationPracticeDialog
import com.example.ui.components.ReportErrorButton
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
import com.example.util.BengaliUtils

@Composable
fun FlashcardScreen(
    showEnglishMeaning: Boolean,
    onSpeakJapanese: (String) -> Unit,
    onReportError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var words by remember { mutableStateOf(VocabularyRepository.n5Words) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val masteredIds = remember { mutableStateListOf<Int>() }

    // State for Pronunciation Practice: Triple(japanese, reading, meaning)
    var practiceTarget by remember { mutableStateOf<Triple<String, String, String>?>(null) }

    val currentWord = words.getOrNull(currentIndex) ?: return

    if (practiceTarget != null) {
        PronunciationPracticeDialog(
            targetJapanese = practiceTarget!!.first,
            targetReading = practiceTarget!!.second,
            targetMeaning = practiceTarget!!.third,
            onSpeakNative = onSpeakJapanese,
            onDismiss = { practiceTarget = null }
        )
    }

    fun goNext() {
        isFlipped = false
        if (currentIndex < words.size - 1) {
            currentIndex++
        } else {
            currentIndex = 0
        }
    }

    fun goPrevious() {
        isFlipped = false
        if (currentIndex > 0) {
            currentIndex--
        } else {
            currentIndex = words.size - 1
        }
    }

    fun shuffleCards() {
        isFlipped = false
        words = words.shuffled()
        currentIndex = 0
    }

    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "card_flip_rotation"
    )

    val density = LocalDensity.current.density
    var dragAccumulator by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("flashcard_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Header
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ফ্ল্যাশকার্ড অনুশীলন",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "কার্ড ${BengaliUtils.toBengaliDigits(currentIndex + 1)} / ${BengaliUtils.toBengaliDigits(words.size)}",
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { shuffleCards() },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(CardBg)
                            .border(1.dp, BorderSubtle, CircleShape)
                            .testTag("shuffle_cards_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Shuffle,
                            contentDescription = "শাফল করুন",
                            tint = Slate700,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = JapanRedLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
                    ) {
                        Text(
                            text = "N5 শব্দ",
                            color = JapanRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar
            val progress = (currentIndex + 1).toFloat() / words.size.toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = JapanRed,
                trackColor = BorderSubtle
            )
        }

        // 3D Flippable Flashcard with Horizontal Swipe Gesture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 10.dp)
                .pointerInput(currentIndex) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (dragAccumulator < -80f) {
                                goNext() // swipe left -> next
                            } else if (dragAccumulator > 80f) {
                                goPrevious() // swipe right -> previous
                            }
                            dragAccumulator = 0f
                        },
                        onDragCancel = {
                            dragAccumulator = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            dragAccumulator += dragAmount
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Card(
                onClick = { isFlipped = !isFlipped },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(390.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .border(1.5.dp, if (isFlipped) JapanRedBorder else BorderSubtle, RoundedCornerShape(20.dp))
                    .testTag("interactive_flashcard")
            ) {
                if (rotation > 90f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationY = 180f }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlashcardBack(
                            word = currentWord,
                            showEnglishMeaning = showEnglishMeaning,
                            onSpeak = { onSpeakJapanese(currentWord.japanese) },
                            onPracticeWord = {
                                practiceTarget = Triple(currentWord.japanese, currentWord.furigana, currentWord.bengali)
                            },
                            onSpeakExample = { onSpeakJapanese(currentWord.exampleJapanese) },
                            onPracticeExample = {
                                practiceTarget = Triple(currentWord.exampleJapanese, currentWord.exampleReading, currentWord.exampleBengali)
                            },
                            onReportError = onReportError
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlashcardFront(
                            word = currentWord,
                            category = currentWord.category
                        )
                    }
                }
            }
        }

        // Action Controls (Flip button, Navigation, Mastery toggle)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { isFlipped = !isFlipped },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = JapanRed),
                border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("flip_action_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Flip,
                    contentDescription = "কার্ড উল্টান",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isFlipped) "জাপানি শব্দ দেখুন" else "অর্থ ও উদাহরণ দেখুন (উল্টান)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Previous and Next Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { goPrevious() },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("prev_card_button"),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "আগের শব্দ",
                        tint = Slate700,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "আগের শব্দ",
                        color = Slate900,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Button(
                    onClick = { goNext() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("next_card_button")
                ) {
                    Text(
                        text = "পরের শব্দ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "পরের শব্দ",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Learned/Mastered Toggle
            val isMastered = masteredIds.contains(currentWord.id)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        if (isMastered) {
                            masteredIds.remove(currentWord.id)
                        } else {
                            masteredIds.add(currentWord.id)
                        }
                    }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isMastered) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = if (isMastered) Emerald600 else Slate400,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isMastered) "শেখা সম্পন্ন হয়েছে ✓" else "শেখা হিসেবে মার্ক করুন",
                    fontSize = 12.sp,
                    color = if (isMastered) Emerald600 else Slate500,
                    fontWeight = if (isMastered) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun FlashcardFront(
    word: VocabularyItem,
    category: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = JapanRedLight,
            border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
        ) {
            Text(
                text = category,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = JapanRed,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Text(
            text = word.japanese,
            fontSize = 44.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Slate900,
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Flip,
                contentDescription = null,
                tint = Slate400,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "অর্থ ও বাক্য দেখতে স্পর্শ করুন",
                fontSize = 13.sp,
                color = Slate400,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun FlashcardBack(
    word: VocabularyItem,
    showEnglishMeaning: Boolean,
    onSpeak: () -> Unit,
    onPracticeWord: () -> Unit,
    onSpeakExample: () -> Unit,
    onPracticeExample: () -> Unit,
    onReportError: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Row: Furigana and Audio Speaker + Mic Icons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = word.furigana,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = JapanRed
            )

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Speaker Button
                IconButton(
                    onClick = onSpeak,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(JapanRedLight)
                        .testTag("flashcard_speak_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "উচ্চারণ শুনুন",
                        tint = JapanRed,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Microphone Button
                IconButton(
                    onClick = onPracticeWord,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(JapanRedLight)
                        .testTag("flashcard_mic_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = "উচ্চারণ পরীক্ষা",
                        tint = JapanRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Middle Section: Romaji & Bengali Meaning (Bold)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            Text(
                text = word.romaji,
                fontSize = 14.sp,
                color = Slate500,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = word.bengali,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900,
                textAlign = TextAlign.Center
            )

            if (showEnglishMeaning) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = word.english,
                    fontSize = 13.sp,
                    color = Slate500,
                    textAlign = TextAlign.Center
                )
            }

            // Example Sentence Section
            if (word.exampleJapanese.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LightBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "উদাহরণ বাক্য:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate500
                            )

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = onSpeakExample,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.VolumeUp,
                                        contentDescription = "শুনুন",
                                        tint = JapanRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                IconButton(
                                    onClick = onPracticeExample,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Mic,
                                        contentDescription = "অনুশীলন",
                                        tint = JapanRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        val highlighted = highlightWordInSentence(
                            sentence = word.exampleJapanese,
                            target = word.japanese,
                            highlightColor = JapanRed
                        )

                        Text(
                            text = highlighted,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = word.exampleBengali,
                            fontSize = 12.sp,
                            color = Slate700,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottom Row: Reference Japanese and Report Error button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LightBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
            ) {
                Text(
                    text = word.japanese,
                    fontSize = 12.sp,
                    color = Slate700,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            ReportErrorButton(
                itemName = "ফ্ল্যাশকার্ড: ${word.japanese}",
                onReportSubmitted = onReportError
            )
        }
    }
}
