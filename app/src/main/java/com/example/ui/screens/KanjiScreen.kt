package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.KanjiRepository
import com.example.data.model.KanjiItem
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
fun KanjiScreen(
    onSpeakJapanese: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    var selectedKanji by remember { mutableStateOf<KanjiItem?>(null) }
    val coroutineScope = rememberCoroutineScope()

    if (selectedKanji != null) {
        KanjiDetailView(
            kanjiItem = selectedKanji!!,
            onBack = { selectedKanji = null },
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
                .testTag("kanji_screen")
        ) {
            // Header
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
                            text = "N5 কাঞ্জি (১০টি)",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "অর্থ, উচ্চারণ ও আঁকা অনুশীলন করুন",
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
                            text = "JLPT N5",
                            color = JapanRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            // Grid of 10 Kanji Cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize().testTag("kanji_grid")
            ) {
                items(KanjiRepository.n5KanjiList, key = { it.id }) { item ->
                    KanjiCard(
                        kanjiItem = item,
                        onClick = { selectedKanji = item }
                    )
                }
            }
        }
    }
}

@Composable
fun KanjiCard(
    kanjiItem: KanjiItem,
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
            .testTag("kanji_card_${kanjiItem.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Kanji Symbol
            Text(
                text = kanjiItem.kanji,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Meaning in Bengali
            Text(
                text = kanjiItem.meaningBengali,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = JapanRed,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Kun-yomi / On-yomi preview
            Text(
                text = kanjiItem.onyomi.split(",").firstOrNull() ?: "",
                fontSize = 12.sp,
                color = Slate500,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = LightBg
            ) {
                Text(
                    text = "${kanjiItem.strokeCount} স্ট্রোক",
                    fontSize = 11.sp,
                    color = Slate700,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun KanjiDetailView(
    kanjiItem: KanjiItem,
    onBack: () -> Unit,
    onSpeakJapanese: (String) -> Unit,
    onReportError: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    // Finger drawing state
    val drawingPaths = remember { mutableStateListOf<List<Offset>>() }
    var currentPath by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBg)
            .verticalScroll(scrollState)
            .testTag("kanji_detail_view")
    ) {
        // Top Bar
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
                text = "কাঞ্জি বিস্তারিত ও অনুশীলন",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            // Main Kanji Hero Card
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(18.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = kanjiItem.kanji,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Slate900
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = kanjiItem.meaningBengali,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = JapanRed
                    )
                    Text(
                        text = kanjiItem.meaningEnglish,
                        fontSize = 14.sp,
                        color = Slate500
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // On-yomi and Kun-yomi row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("অন-ইয়োমি (音読み)", fontSize = 12.sp, color = Slate500, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(kanjiItem.onyomi, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Slate900)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("কুন-ইয়োমি (訓読み)", fontSize = 12.sp, color = Slate500, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(kanjiItem.kunyomi, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Slate900)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Example word card
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = JapanRedLight,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "উদাহরণ: ${kanjiItem.exampleWord} (${kanjiItem.exampleReading})",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate900
                                )
                                Text(
                                    text = "অর্থ: ${kanjiItem.exampleMeaning}",
                                    fontSize = 13.sp,
                                    color = JapanRed,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            IconButton(onClick = { onSpeakJapanese(kanjiItem.exampleWord) }) {
                                Icon(
                                    imageVector = Icons.Filled.VolumeUp,
                                    contentDescription = "শুনুন",
                                    tint = JapanRed
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Stroke Order Section
            Text(
                text = "স্ট্রোক অর্ডার (Stroke Order): ${kanjiItem.strokeCount}টি স্ট্রোক",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    kanjiItem.strokeSteps.forEachIndexed { index, step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(JapanRed),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = step,
                                fontSize = 13.sp,
                                color = Slate700
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Practice Drawing Canvas Header & Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = JapanRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "হাতে লিখে অনুশীলন করুন",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                }

                Button(
                    onClick = {
                        drawingPaths.clear()
                        currentPath = emptyList()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = LightBg),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "মুছুন",
                        tint = Slate700,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("মুছে ফেলুন", color = Slate700, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Interactive Drawing Canvas with Genkouyoushi Calligraphy Grid
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .border(2.dp, JapanRedBorder, RoundedCornerShape(16.dp))
                    .testTag("kanji_drawing_canvas")
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Calligraphy Grid & Watermark
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Outer border
                        drawRect(color = Color(0xFFFDE8E8), style = Stroke(width = 2.dp.toPx()))

                        // Cross guidelines (dashed horizontal and vertical)
                        drawLine(
                            color = Color(0xFFFCA5A5),
                            start = Offset(w * 0.5f, 0f),
                            end = Offset(w * 0.5f, h),
                            strokeWidth = 1.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFFFCA5A5),
                            start = Offset(0f, h * 0.5f),
                            end = Offset(w, h * 0.5f),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Faint Background Kanji Guide Watermark
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = kanjiItem.kanji,
                            fontSize = 110.sp,
                            color = Color(0xFFFEE2E2).copy(alpha = 0.65f),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // User Touch Drawing Layer
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { offset ->
                                        currentPath = listOf(offset)
                                    },
                                    onDragEnd = {
                                        if (currentPath.isNotEmpty()) {
                                            drawingPaths.add(currentPath)
                                            currentPath = emptyList()
                                        }
                                    },
                                    onDragCancel = {
                                        currentPath = emptyList()
                                    },
                                    onDrag = { change, _ ->
                                        change.consume()
                                        currentPath = currentPath + change.position
                                    }
                                )
                            }
                    ) {
                        val strokeStyle = Stroke(
                            width = 12f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )

                        // Draw finished strokes
                        drawingPaths.forEach { points ->
                            if (points.size > 1) {
                                val path = Path().apply {
                                    moveTo(points.first().x, points.first().y)
                                    for (i in 1 until points.size) {
                                        lineTo(points[i].x, points[i].y)
                                    }
                                }
                                drawPath(path, color = Color(0xFF0F172A), style = strokeStyle)
                            }
                        }

                        // Draw ongoing active stroke
                        if (currentPath.size > 1) {
                            val activePath = Path().apply {
                                moveTo(currentPath.first().x, currentPath.first().y)
                                for (i in 1 until currentPath.size) {
                                    lineTo(currentPath[i].x, currentPath[i].y)
                                }
                            }
                            drawPath(activePath, color = Color(0xFFE11D48), style = strokeStyle)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Report Error button at bottom
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ReportErrorButton(
                    itemName = "কাঞ্জি: ${kanjiItem.kanji}",
                    onReportSubmitted = onReportError
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
