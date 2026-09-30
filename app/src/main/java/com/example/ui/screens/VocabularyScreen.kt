package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VocabularyRepository
import com.example.data.model.VocabularyItem
import com.example.ui.components.PandaEmptyState
import com.example.ui.components.PronunciationPracticeDialog
import com.example.ui.components.ReportErrorButton
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
import com.example.util.BengaliUtils

@Composable
fun VocabularyScreen(
    showEnglishMeaning: Boolean,
    onSpeakJapanese: (String) -> Unit,
    onReportError: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("সব") }

    // State for Pronunciation Practice Dialog: Triple(targetJapanese, targetReading, targetMeaning)
    var practiceTarget by remember { mutableStateOf<Triple<String, String, String>?>(null) }

    val filteredWords = remember(searchQuery, selectedCategory) {
        VocabularyRepository.searchWords(searchQuery, selectedCategory)
    }

    if (practiceTarget != null) {
        PronunciationPracticeDialog(
            targetJapanese = practiceTarget!!.first,
            targetReading = practiceTarget!!.second,
            targetMeaning = practiceTarget!!.third,
            onSpeakNative = onSpeakJapanese,
            onDismiss = { practiceTarget = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .testTag("vocabulary_screen")
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "JLPT N5 শব্দভাণ্ডার",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = "মোট ${BengaliUtils.toBengaliDigits(VocabularyRepository.n5Words.size)}টি আবশ্যক শব্দ",
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = JapanRedLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
                ) {
                    Text(
                        text = "N5 লেভেল",
                        color = JapanRed,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "জাপানি, রোমাজি বা বাংলায় খুঁজুন...",
                        fontSize = 14.sp,
                        color = Slate400
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "অনুসন্ধান",
                        tint = Slate400
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "পরিষ্কার করুন",
                                tint = Slate400
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("vocab_search_field"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = JapanRed,
                    unfocusedBorderColor = BorderSubtle,
                    focusedContainerColor = LightBg,
                    unfocusedContainerColor = LightBg,
                    focusedTextColor = Slate900,
                    unfocusedTextColor = Slate900
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Category Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(VocabularyRepository.categories) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        label = {
                            Text(
                                text = category,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = JapanRed,
                            selectedLabelColor = Color.White,
                            containerColor = LightBg,
                            labelColor = Slate700
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) JapanRed else BorderSubtle,
                            selectedBorderColor = JapanRed,
                            enabled = true,
                            selected = isSelected
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }
        }

        // Word List
        if (filteredWords.isEmpty()) {
            PandaEmptyState(
                title = "কোনো শব্দ খুঁজে পাওয়া যায়নি!",
                subtitle = "বানান সঠিক কিনা যাচাই করে আবার খুঁজুন।",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("vocab_list")
            ) {
                items(filteredWords, key = { it.id }) { word ->
                    WordItemCard(
                        word = word,
                        showEnglishMeaning = showEnglishMeaning,
                        onSpeak = { onSpeakJapanese(word.japanese) },
                        onSpeakExample = { onSpeakJapanese(word.exampleJapanese) },
                        onPracticeWord = {
                            Toast.makeText(context, "ভয়েস প্র্যাকটিস শীঘ্রই আসছে!", Toast.LENGTH_SHORT).show()
                        },
                        onPracticeExample = {
                            Toast.makeText(context, "ভয়েস প্র্যাকটিস শীঘ্রই আসছে!", Toast.LENGTH_SHORT).show()
                        },
                        onReportError = onReportError
                    )
                }
            }
        }
    }
}

@Composable
fun WordItemCard(
    word: VocabularyItem,
    showEnglishMeaning: Boolean,
    onSpeak: () -> Unit,
    onSpeakExample: () -> Unit,
    onPracticeWord: () -> Unit,
    onPracticeExample: () -> Unit,
    onReportError: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp))
            .testTag("vocab_card_${word.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top Row: Furigana and Category badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = word.furigana,
                    fontSize = 14.sp,
                    color = JapanRed,
                    fontWeight = FontWeight.SemiBold
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = LightBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle)
                ) {
                    Text(
                        text = word.category,
                        fontSize = 11.sp,
                        color = Slate500,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Middle Row: Japanese (Large) + Romaji + Pronunciation Buttons (Speaker & Mic)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = word.japanese,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = word.romaji,
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Speaker Button (TTS)
                    IconButton(
                        onClick = onSpeak,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(JapanRedLight)
                            .testTag("speak_btn_${word.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = "${word.japanese} উচ্চারণ শুনুন",
                            tint = JapanRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Microphone Button (Voice Analysis Practice)
                    IconButton(
                        onClick = onPracticeWord,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(JapanRedLight)
                            .testTag("mic_btn_${word.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = "${word.japanese} উচ্চারণ অনুশীলন করুন",
                            tint = JapanRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bengali meaning (bold) and conditional English meaning
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = word.bengali,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    if (showEnglishMeaning) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = word.english,
                            fontSize = 13.sp,
                            color = Slate400,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                // Report Error (ভুল আছে?) Button
                ReportErrorButton(
                    itemName = "${word.japanese} (${word.bengali})",
                    onReportSubmitted = onReportError
                )
            }

            // Example Sentence Section (Below word and meaning with highlighted target word)
            if (word.exampleJapanese.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LightBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "উদাহরণ বাক্য (Example):",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate500
                            )

                            // Action icons for example sentence (Speaker & Mic)
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(
                                    onClick = onSpeakExample,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.VolumeUp,
                                        contentDescription = "বাক্যের উচ্চারণ শুনুন",
                                        tint = JapanRed,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                                IconButton(
                                    onClick = onPracticeExample,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Mic,
                                        contentDescription = "বাক্য উচ্চারণ অনুশীলন",
                                        tint = JapanRed,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                            }
                        }

                        // Highlighted Target Word inside Japanese Sentence
                        val highlightedSentence = highlightWordInSentence(
                            sentence = word.exampleJapanese,
                            target = word.japanese,
                            highlightColor = JapanRed
                        )

                        Text(
                            text = highlightedSentence,
                            fontSize = 15.sp,
                            lineHeight = 22.sp,
                            color = Slate900
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        // Translation in Bengali
                        Text(
                            text = word.exampleBengali,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate700
                        )

                        if (showEnglishMeaning && word.exampleEnglish.isNotBlank()) {
                            Text(
                                text = word.exampleEnglish,
                                fontSize = 12.sp,
                                color = Slate400
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Highlights and underlines the target word inside a Japanese sentence.
 */
fun highlightWordInSentence(
    sentence: String,
    target: String,
    highlightColor: Color
): AnnotatedString {
    if (target.isBlank() || !sentence.contains(target)) {
        return AnnotatedString(sentence)
    }

    return buildAnnotatedString {
        var startIndex = 0
        while (startIndex < sentence.length) {
            val index = sentence.indexOf(target, startIndex)
            if (index == -1) {
                append(sentence.substring(startIndex))
                break
            }
            if (index > startIndex) {
                append(sentence.substring(startIndex, index))
            }
            val spanStart = length
            append(target)
            val spanEnd = length
            addStyle(
                style = SpanStyle(
                    color = highlightColor,
                    fontWeight = FontWeight.ExtraBold,
                    textDecoration = TextDecoration.Underline
                ),
                start = spanStart,
                end = spanEnd
            )
            startIndex = index + target.length
        }
    }
}
