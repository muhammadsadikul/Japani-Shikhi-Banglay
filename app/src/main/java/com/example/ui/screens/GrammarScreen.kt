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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.GrammarRepository
import com.example.data.model.GrammarItem
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
import kotlinx.coroutines.launch

@Composable
fun GrammarScreen(
    onSpeakJapanese: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    var selectedGrammar by remember { mutableStateOf<GrammarItem?>(null) }
    val coroutineScope = rememberCoroutineScope()

    if (selectedGrammar != null) {
        GrammarDetailView(
            grammarItem = selectedGrammar!!,
            onBack = { selectedGrammar = null },
            onSpeakJapanese = onSpeakJapanese,
            onReportError = { message ->
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("রিপোর্ট গ্রহণ করা হয়েছে। ধন্যবাদ!")
                }
            }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightBg)
                .testTag("grammar_screen")
        ) {
            // Top Bar
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
                            text = "N5 ব্যাকরণ (Grammar)",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "মৌলিক ৫টি বাক্যের গঠন ও কণার বিশ্লেষণ",
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
                            text = "৫টি প্যাটার্ন",
                            color = JapanRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            // List of Grammar Patterns
            LazyColumn(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().testTag("grammar_list")
            ) {
                items(GrammarRepository.n5GrammarList, key = { it.id }) { item ->
                    GrammarCard(
                        grammarItem = item,
                        onClick = { selectedGrammar = item }
                    )
                }
            }
        }
    }
}

@Composable
fun GrammarCard(
    grammarItem: GrammarItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            .testTag("grammar_card_${grammarItem.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Pattern Title in Japanese
                Text(
                    text = grammarItem.pattern,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = JapanRed
                )
                Text(
                    text = grammarItem.patternRomaji,
                    fontSize = 12.sp,
                    color = Slate400
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Meaning in Bengali
                Text(
                    text = grammarItem.titleBengali,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900
                )
                Text(
                    text = grammarItem.meaningBengali,
                    fontSize = 13.sp,
                    color = Slate500
                )
            }

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(JapanRedLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "বিস্তারিত দেখুন",
                    tint = JapanRed,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun GrammarDetailView(
    grammarItem: GrammarItem,
    onBack: () -> Unit,
    onSpeakJapanese: (String) -> Unit,
    onReportError: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
            .verticalScroll(scrollState)
            .testTag("grammar_detail_view")
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "ফিরে যান",
                    tint = Slate900
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "ব্যাকরণ প্যাটার্ন বিস্তারিত",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Main Pattern Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = JapanRedLight
                    ) {
                        Text(
                            text = "প্যাটার্ন #${grammarItem.id}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = JapanRed,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = grammarItem.pattern,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Slate900
                    )
                    Text(
                        text = grammarItem.patternRomaji,
                        fontSize = 14.sp,
                        color = Slate500
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "অর্থ: ${grammarItem.meaningBengali}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = JapanRed
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = grammarItem.explanationBengali,
                        fontSize = 14.sp,
                        color = Slate700,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Example Sentence Section
            Text(
                text = "উদাহরণ বাক্য (Example Sentence)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, JapanRedBorder, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = grammarItem.exampleJapanese,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )

                        IconButton(
                            onClick = { onSpeakJapanese(grammarItem.exampleJapanese) },
                            modifier = Modifier
                                .size(40.dp)
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

                    Text(
                        text = grammarItem.exampleReading,
                        fontSize = 13.sp,
                        color = JapanRed,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = grammarItem.exampleBengali,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    Text(
                        text = grammarItem.exampleEnglish,
                        fontSize = 13.sp,
                        color = Slate500
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sentence Breakdown Section
            Text(
                text = "বাক্যের অংশের ব্যাখ্যা (Breakdown)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    grammarItem.breakdowns.forEachIndexed { index, part ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = JapanRedLight,
                                modifier = Modifier.width(72.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = part.part,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = JapanRed
                                    )
                                    Text(
                                        text = part.romaji,
                                        fontSize = 10.sp,
                                        color = Slate500
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = part.roleBengali,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = part.explanation,
                                    fontSize = 12.sp,
                                    color = Slate700,
                                    lineHeight = 18.sp
                                )
                            }
                        }

                        if (index < grammarItem.breakdowns.size - 1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(BorderSubtle)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Report Error button at bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ReportErrorButton(
                    itemName = "গ্রামার: ${grammarItem.pattern}",
                    onReportSubmitted = onReportError
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
