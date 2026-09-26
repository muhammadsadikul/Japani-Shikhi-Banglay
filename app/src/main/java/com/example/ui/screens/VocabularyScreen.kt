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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VocabularyRepository
import com.example.data.model.VocabularyItem
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
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("সব") }

    val filteredWords = remember(searchQuery, selectedCategory) {
        VocabularyRepository.searchWords(searchQuery, selectedCategory)
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
                        text = "${BengaliUtils.toBengaliDigits(filteredWords.size)}টি শব্দ পাওয়া গেছে",
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
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar at Top
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "বাংলা বা জাপানি শব্দ খুঁজুন...",
                        fontSize = 14.sp,
                        color = Slate400
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "খুঁজুন",
                        tint = JapanRed
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "পরিষ্কার করুন",
                                tint = Slate500
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = JapanRed,
                    unfocusedBorderColor = BorderSubtle,
                    focusedContainerColor = LightBg,
                    unfocusedContainerColor = LightBg
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_text_field")
            )
        }

        // Category Filter Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(VocabularyRepository.categories) { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = {
                        Text(
                            text = category,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = JapanRed,
                        selectedLabelColor = Color.White,
                        containerColor = CardBg,
                        labelColor = Slate700
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = isSelected,
                        borderColor = if (isSelected) JapanRed else BorderSubtle,
                        selectedBorderColor = JapanRed,
                        borderWidth = 1.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Vocabulary List
        if (filteredWords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "কোনো শব্দ খুঁজে পাওয়া যায়নি",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "বানান ঠিক আছে কিনা পরীক্ষা করুন বা ফিল্টার পরিবর্তন করুন।",
                        fontSize = 13.sp,
                        color = Slate500,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().testTag("vocab_list")
            ) {
                items(filteredWords, key = { it.id }) { word ->
                    VocabularyCard(
                        word = word,
                        onSpeak = { onSpeakJapanese(word.japanese) }
                    )
                }
            }
        }
    }
}

@Composable
fun VocabularyCard(
    word: VocabularyItem,
    onSpeak: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
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
                // Furigana reading
                Text(
                    text = word.furigana,
                    fontSize = 14.sp,
                    color = JapanRed,
                    fontWeight = FontWeight.SemiBold
                )

                // Category Tag
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

            // Middle Row: Japanese (Large) + Romaji + Pronunciation Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    // Japanese word (large)
                    Text(
                        text = word.japanese,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    // Romaji
                    Text(
                        text = word.romaji,
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }

                // Audio Button
                IconButton(
                    onClick = onSpeak,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(JapanRedLight)
                        .testTag("speak_btn_${word.id}")
                ) {
                    Icon(
                        imageVector = Icons.Filled.VolumeUp,
                        contentDescription = "${word.japanese} উচ্চারণ শুনুন",
                        tint = JapanRed,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Divider line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BorderSubtle)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom Section: Bengali meaning (bold) and English meaning (small gray)
            Text(
                text = word.bengali,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = word.english,
                fontSize = 13.sp,
                color = Slate400,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
