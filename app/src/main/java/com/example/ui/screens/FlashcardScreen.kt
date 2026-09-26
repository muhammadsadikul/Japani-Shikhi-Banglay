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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Refresh
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

@Composable
fun FlashcardScreen(
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var words by remember { mutableStateOf(VocabularyRepository.n5Words) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }
    val masteredIds = remember { mutableStateListOf<Int>() }

    val currentWord = words.getOrNull(currentIndex) ?: return

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

    val isMastered = masteredIds.contains(currentWord.id)

    // Smooth 3D Flip animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "card_rotation"
    )

    val density = LocalDensity.current.density
    var dragAccumulator by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .testTag("flashcard_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section: Progress & Bengali Counter ("১ / ৫০")
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shuffle Button
                IconButton(
                    onClick = { shuffleCards() },
                    modifier = Modifier.testTag("shuffle_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shuffle,
                        contentDescription = "এলোমেলো করুন",
                        tint = Slate700
                    )
                }

                // Bengali Counter: "১ / ৫০"
                Text(
                    text = BengaliUtils.formatCounter(currentIndex + 1, words.size),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.testTag("flashcard_counter")
                )

                // Mastered / Learned toggle button
                IconButton(
                    onClick = {
                        if (isMastered) {
                            masteredIds.remove(currentWord.id)
                        } else {
                            masteredIds.add(currentWord.id)
                        }
                    },
                    modifier = Modifier.testTag("mastered_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isMastered) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle,
                        contentDescription = if (isMastered) "মুখস্থ হয়েছে" else "মুখস্থ হয়নি",
                        tint = if (isMastered) Emerald600 else Slate400
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Indicator
            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / words.size.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = JapanRed,
                trackColor = BorderSubtle,
            )
        }

        // Center Section: Centered 3D Flippable Flashcard with Swipe Detection
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 16.dp)
                .pointerInput(Unit) {
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
                    .height(340.dp)
                    .graphicsLayer {
                        rotationY = rotation
                        cameraDistance = 12f * density
                    }
                    .border(1.5.dp, if (isFlipped) JapanRedBorder else BorderSubtle, RoundedCornerShape(20.dp))
                    .testTag("interactive_flashcard")
            ) {
                // If rotated more than 90 deg, show Back side (flipped 180 to remain upright)
                if (rotation > 90f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { rotationY = 180f }
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlashcardBack(
                            word = currentWord,
                            onSpeak = { onSpeakJapanese(currentWord.japanese) }
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FlashcardFront(word = currentWord)
                    }
                }
            }
        }

        // Bottom Controls: Flip button, Previous, Next
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Flip Hint button
            OutlinedButton(
                onClick = { isFlipped = !isFlipped },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = JapanRed
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("flip_action_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.Flip,
                    contentDescription = "কার্ড উল্টান",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isFlipped) "জাপানি শব্দ দেখুন" else "অর্থ ও উচ্চারণ দেখুন (উল্টান)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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
                        .height(52.dp)
                        .testTag("prev_card_button"),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "পূর্ববর্তী শব্দ",
                        tint = Slate700,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "পূর্ববর্তী",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate700
                    )
                }

                Button(
                    onClick = { goNext() },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .testTag("next_card_button")
                ) {
                    Text(
                        text = "পরবর্তী",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "পরবর্তী শব্দ",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun FlashcardFront(word: VocabularyItem) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Category Badge
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = JapanRedLight,
            border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
        ) {
            Text(
                text = "${word.level} • ${word.category}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = JapanRed,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }

        // Japanese Word (Large, Centered)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = word.japanese,
                fontSize = 44.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900,
                textAlign = TextAlign.Center
            )
        }

        // Tap to flip hint
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Flip,
                contentDescription = null,
                tint = Slate400,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "অর্থ দেখতে স্পর্শ করুন",
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
    onSpeak: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Row: Furigana and Audio Speaker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = word.furigana,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = JapanRed
            )

            IconButton(
                onClick = onSpeak,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(JapanRedLight)
                    .testTag("flashcard_speak_button")
            ) {
                Icon(
                    imageVector = Icons.Filled.VolumeUp,
                    contentDescription = "উচ্চারণ শুনুন",
                    tint = JapanRed,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Middle Section: Romaji & Bengali Meaning (Bold)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = word.romaji,
                fontSize = 16.sp,
                color = Slate500,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = word.bengali,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = word.english,
                fontSize = 15.sp,
                color = Slate500,
                textAlign = TextAlign.Center
            )
        }

        // Original Japanese reference at bottom
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = LightBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
        ) {
            Text(
                text = "শব্দ: ${word.japanese}",
                fontSize = 13.sp,
                color = Slate700,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}
