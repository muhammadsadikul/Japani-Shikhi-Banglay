package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.MotionPhotosOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VocabularyRepository
import com.example.data.model.VocabularyItem
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

enum class QuizMode(val titleBengali: String) {
    MULTIPLE_CHOICE("বহুনির্বাচনী"),
    TYPING("টাইপিং"),
    MATCHING("জোড়া মেলানো"),
    LISTENING("শ্রবণ পরীক্ষা"),
    SENTENCE("বাক্য গঠন")
}

@Composable
fun QuizScreen(
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedMode by remember { mutableStateOf(QuizMode.MULTIPLE_CHOICE) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBg)
            .testTag("quiz_screen")
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardBg)
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = "N5 কুইজ ও দক্ষতা যাচাই",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Text(
                text = "৫টি ভিন্ন উপায়ে নিজের প্রস্তুতি যাচাই করুন",
                fontSize = 13.sp,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quiz Mode Tabs
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(QuizMode.entries) { mode ->
                    val isSelected = selectedMode == mode
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedMode = mode },
                        label = {
                            Text(
                                text = mode.titleBengali,
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
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) JapanRed else BorderSubtle,
                            selectedBorderColor = JapanRed
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Active Quiz Mode Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedMode) {
                QuizMode.MULTIPLE_CHOICE -> MultipleChoiceQuizContent()
                QuizMode.TYPING -> TypingQuizContent()
                QuizMode.MATCHING -> MatchingQuizContent()
                QuizMode.LISTENING -> ListeningQuizContent(onSpeakJapanese = onSpeakJapanese)
                QuizMode.SENTENCE -> SentenceBuildingQuizContent()
            }
        }
    }
}

// ----------------------------------------------------
// 1. Multiple Choice Quiz
// ----------------------------------------------------
@Composable
fun MultipleChoiceQuizContent() {
    val quizQuestions = remember {
        listOf(
            Triple("食べる", "খাওয়া", listOf("খাওয়া", "পান করা", "যাওয়া", "আসা")),
            Triple("学校", "বিদ্যালয় / স্কুল", listOf("বিদ্যালয় / স্কুল", "বাড়ি / ঘর", "গাড়ি", "বই")),
            Triple("新しい", "নতুন", listOf("নতুন", "পুরাতন / পুরোনো", "বড়", "ছোট")),
            Triple("水", "পানি / জল", listOf("পানি / জল", "ভাত / খাবার", "টাকা / অর্থ", "সময়")),
            Triple("先生", "শিক্ষক / শিক্ষিকা", listOf("শিক্ষক / শিক্ষিকা", "ছাত্র / ছাত্রী", "বন্ধু", "মানুষ"))
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }

    if (isFinished) {
        QuizResultCard(
            score = score,
            total = quizQuestions.size,
            onRestart = {
                currentIndex = 0
                score = 0
                selectedAnswer = null
                isSubmitted = false
                isFinished = false
            }
        )
    } else {
        val currentQ = quizQuestions[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "প্রশ্ন ${BengaliUtils.toBengaliDigits(currentIndex + 1)} / ${BengaliUtils.toBengaliDigits(quizQuestions.size)}",
                        fontWeight = FontWeight.Bold,
                        color = Slate700
                    )
                    Text(
                        text = "স্কোর: ${BengaliUtils.toBengaliDigits(score)}",
                        fontWeight = FontWeight.Bold,
                        color = JapanRed
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / quizQuestions.size.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = JapanRed,
                    trackColor = BorderSubtle
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Question Card
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
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "সঠিক বাংলা অর্থ কোনটি?",
                            fontSize = 13.sp,
                            color = Slate500
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentQ.first,
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Options
                currentQ.third.forEach { option ->
                    val isSelected = selectedAnswer == option
                    val isCorrect = option == currentQ.second

                    val (containerColor, borderColor, textColor) = when {
                        isSubmitted && isCorrect -> Triple(Emerald50, Emerald600, Emerald600)
                        isSubmitted && isSelected && !isCorrect -> Triple(Color(0xFFFFF1F2), JapanRed, JapanRed)
                        isSelected -> Triple(JapanRedLight, JapanRed, JapanRed)
                        else -> Triple(CardBg, BorderSubtle, Slate900)
                    }

                    Card(
                        onClick = {
                            if (!isSubmitted) {
                                selectedAnswer = option
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            if (isSubmitted && isCorrect) {
                                Icon(Icons.Filled.Check, null, tint = Emerald600)
                            } else if (isSubmitted && isSelected && !isCorrect) {
                                Icon(Icons.Filled.Close, null, tint = JapanRed)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button
            Button(
                onClick = {
                    if (!isSubmitted) {
                        if (selectedAnswer != null) {
                            isSubmitted = true
                            if (selectedAnswer == currentQ.second) {
                                score++
                            }
                        }
                    } else {
                        if (currentIndex < quizQuestions.size - 1) {
                            currentIndex++
                            selectedAnswer = null
                            isSubmitted = false
                        } else {
                            isFinished = true
                        }
                    }
                },
                enabled = selectedAnswer != null,
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (!isSubmitted) "উত্তর জমা দিন" else if (currentIndex < quizQuestions.size - 1) "পরবর্তী প্রশ্ন" else "ফলাফল দেখুন",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ----------------------------------------------------
// 2. Typing Quiz
// ----------------------------------------------------
@Composable
fun TypingQuizContent() {
    val questions = remember {
        listOf(
            Pair("আমি (I, me)", "watashi"),
            Pair("খাওয়া (to eat)", "taberu"),
            Pair("স্কুল / বিদ্যালয় (school)", "gakkou"),
            Pair("নতুন (new)", "atarashii"),
            Pair("পানি (water)", "mizu")
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var typedAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }

    if (isFinished) {
        QuizResultCard(score = score, total = questions.size, onRestart = {
            currentIndex = 0
            score = 0
            typedAnswer = ""
            showResult = false
            isFinished = false
        })
    } else {
        val currentQ = questions[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "রোমাজি টাইপ করুন (${BengaliUtils.toBengaliDigits(currentIndex + 1)} / ${BengaliUtils.toBengaliDigits(questions.size)})",
                    fontSize = 14.sp,
                    color = Slate700,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(14.dp))

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
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "নিচের শব্দের সঠিক রোমাজি লিখুন:", fontSize = 13.sp, color = Slate500)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = currentQ.first,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = typedAnswer,
                    onValueChange = { if (!showResult) typedAnswer = it },
                    placeholder = { Text("ইংরেজি হরফে লিখুন (যেমন: $${currentQ.second.first()}...)") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = JapanRed,
                        unfocusedBorderColor = BorderSubtle,
                        focusedContainerColor = CardBg,
                        unfocusedContainerColor = CardBg
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (showResult) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isCorrect) Emerald50 else JapanRedLight,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isCorrect) Emerald600 else JapanRed)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Filled.Check else Icons.Filled.Close,
                                contentDescription = null,
                                tint = if (isCorrect) Emerald600 else JapanRed
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isCorrect) "চমৎকার! সঠিক হয়েছে।" else "সঠিক উত্তর: ${currentQ.second}",
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) Emerald600 else JapanRed,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (!showResult) {
                        showResult = true
                        isCorrect = typedAnswer.trim().equals(currentQ.second, ignoreCase = true)
                        if (isCorrect) score++
                    } else {
                        if (currentIndex < questions.size - 1) {
                            currentIndex++
                            typedAnswer = ""
                            showResult = false
                        } else {
                            isFinished = true
                        }
                    }
                },
                enabled = typedAnswer.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (!showResult) "যাচাই করুন" else if (currentIndex < questions.size - 1) "পরবর্তী শব্দ" else "ফলাফল দেখুন",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ----------------------------------------------------
// 3. Matching Quiz
// ----------------------------------------------------
@Composable
fun MatchingQuizContent() {
    val pairs = remember {
        listOf(
            Pair("私", "আমি"),
            Pair("食べる", "খাওয়া"),
            Pair("水", "পানি"),
            Pair("本", "বই")
        )
    }

    val japaneseWords = remember { pairs.map { it.first }.shuffled() }
    val bengaliWords = remember { pairs.map { it.second }.shuffled() }

    var selectedJapanese by remember { mutableStateOf<String?>(null) }
    var selectedBengali by remember { mutableStateOf<String?>(null) }
    val matchedPairs = remember { mutableStateListOf<String>() }

    var isFinished by remember { mutableStateOf(false) }

    LaunchedEffect(matchedPairs.size) {
        if (matchedPairs.size == pairs.size) {
            isFinished = true
        }
    }

    if (isFinished) {
        QuizResultCard(
            score = 4,
            total = 4,
            onRestart = {
                matchedPairs.clear()
                selectedJapanese = null
                selectedBengali = null
                isFinished = false
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "জোড়া মিলিয়ে সম্পূর্ণ করুন",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900
            )
            Text(
                text = "প্রথমে একটি জাপানি শব্দ স্পর্শ করুন, তারপর তার সঠিক বাংলা অর্থ স্পর্শ করুন।",
                fontSize = 13.sp,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Japanese Column
                Column(modifier = Modifier.weight(1f)) {
                    japaneseWords.forEach { word ->
                        val isMatched = matchedPairs.contains(word)
                        val isSelected = selectedJapanese == word

                        Card(
                            onClick = {
                                if (!isMatched) {
                                    selectedJapanese = word
                                    if (selectedBengali != null) {
                                        // check match
                                        val correct = pairs.any { it.first == word && it.second == selectedBengali }
                                        if (correct) {
                                            matchedPairs.add(word)
                                            selectedJapanese = null
                                            selectedBengali = null
                                        } else {
                                            selectedBengali = null
                                        }
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isMatched) Emerald50 else if (isSelected) JapanRedLight else CardBg
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .border(
                                    1.5.dp,
                                    if (isMatched) Emerald600 else if (isSelected) JapanRed else BorderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = word,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMatched) Emerald600 else if (isSelected) JapanRed else Slate900
                                )
                            }
                        }
                    }
                }

                // Bengali Column
                Column(modifier = Modifier.weight(1f)) {
                    bengaliWords.forEach { meaning ->
                        val matchedWord = pairs.find { it.second == meaning }?.first
                        val isMatched = matchedWord != null && matchedPairs.contains(matchedWord)
                        val isSelected = selectedBengali == meaning

                        Card(
                            onClick = {
                                if (!isMatched) {
                                    selectedBengali = meaning
                                    if (selectedJapanese != null) {
                                        // check match
                                        val correct = pairs.any { it.first == selectedJapanese && it.second == meaning }
                                        if (correct) {
                                            matchedPairs.add(selectedJapanese!!)
                                            selectedJapanese = null
                                            selectedBengali = null
                                        } else {
                                            selectedJapanese = null
                                        }
                                    }
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isMatched) Emerald50 else if (isSelected) JapanRedLight else CardBg
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .border(
                                    1.5.dp,
                                    if (isMatched) Emerald600 else if (isSelected) JapanRed else BorderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = meaning,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMatched) Emerald600 else if (isSelected) JapanRed else Slate900
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = JapanRedLight,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "মেলেছে: ${BengaliUtils.toBengaliDigits(matchedPairs.size)} / ৪",
                    color = JapanRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

// ----------------------------------------------------
// 4. Listening Quiz (Uses Android TextToSpeech)
// ----------------------------------------------------
@Composable
fun ListeningQuizContent(onSpeakJapanese: (String) -> Unit) {
    val questions = remember {
        listOf(
            Triple("ありがとう", "ধন্যবাদ", listOf("ধন্যবাদ", "শুভ সকাল", "বিদায়", "স্বাগতম")),
            Triple("学校", "বিদ্যালয় / স্কুল", listOf("বিদ্যালয় / স্কুল", "বাড়ি", "গাড়ি", "হাসপাতাল")),
            Triple("飲む", "পান করা", listOf("পান করা", "খাওয়া", "যাওয়া", "পড়া")),
            Triple("大きい", "বড়", listOf("বড়", "ছোট", "নতুন", "পুরাতন"))
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }
    var isFinished by remember { mutableStateOf(false) }

    val currentQ = questions[currentIndex]

    // Play word audio automatically on question change
    LaunchedEffect(currentIndex) {
        onSpeakJapanese(currentQ.first)
    }

    if (isFinished) {
        QuizResultCard(score = score, total = questions.size, onRestart = {
            currentIndex = 0
            score = 0
            selectedOption = null
            isSubmitted = false
            isFinished = false
        })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "মনোযোগ দিয়ে শুনুন (${BengaliUtils.toBengaliDigits(currentIndex + 1)} / ${BengaliUtils.toBengaliDigits(questions.size)})",
                    fontSize = 14.sp,
                    color = Slate700,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Big Audio Play Button
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { onSpeakJapanese(currentQ.first) },
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(JapanRed)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.VolumeUp,
                                contentDescription = "শুনুন",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "পুনরায় শুনতে চাপুন",
                            fontSize = 13.sp,
                            color = Slate500,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Options
                currentQ.third.forEach { option ->
                    val isSelected = selectedOption == option
                    val isCorrect = option == currentQ.second

                    val (containerColor, borderColor, textColor) = when {
                        isSubmitted && isCorrect -> Triple(Emerald50, Emerald600, Emerald600)
                        isSubmitted && isSelected && !isCorrect -> Triple(Color(0xFFFFF1F2), JapanRed, JapanRed)
                        isSelected -> Triple(JapanRedLight, JapanRed, JapanRed)
                        else -> Triple(CardBg, BorderSubtle, Slate900)
                    }

                    Card(
                        onClick = { if (!isSubmitted) selectedOption = option },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            if (isSubmitted && isCorrect) {
                                Icon(Icons.Filled.Check, null, tint = Emerald600)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isSubmitted) {
                        isSubmitted = true
                        if (selectedOption == currentQ.second) score++
                    } else {
                        if (currentIndex < questions.size - 1) {
                            currentIndex++
                            selectedOption = null
                            isSubmitted = false
                        } else {
                            isFinished = true
                        }
                    }
                },
                enabled = selectedOption != null,
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (!isSubmitted) "উত্তর যাচাই করুন" else if (currentIndex < questions.size - 1) "পরবর্তী শব্দ" else "ফলাফল দেখুন",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ----------------------------------------------------
// 5. Sentence Building Quiz
// ----------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SentenceBuildingQuizContent() {
    val sentences = remember {
        listOf(
            Pair("আমি একজন ছাত্র।", listOf("わたし", "は", "学生", "です")),
            Pair("আমি ভাত খাই।", listOf("ご飯", "を", "食べます")),
            Pair("তানাকা সাহেব শিক্ষক নন।", listOf("田中さん", "は", "先生", "ではありません"))
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    val currentSentence = sentences[currentIndex]

    val availableWords = remember(currentIndex) {
        mutableStateListOf<String>().apply { addAll(currentSentence.second.shuffled()) }
    }
    val builtSentence = remember(currentIndex) { mutableStateListOf<String>() }

    var isChecked by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var isFinished by remember { mutableStateOf(false) }

    if (isFinished) {
        QuizResultCard(score = sentences.size, total = sentences.size, onRestart = {
            currentIndex = 0
            isChecked = false
            isFinished = false
        })
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "শব্দ সাজিয়ে বাক্য গঠন করুন (${BengaliUtils.toBengaliDigits(currentIndex + 1)} / ${BengaliUtils.toBengaliDigits(sentences.size)})",
                    fontSize = 14.sp,
                    color = Slate700,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Target Bengali Sentence
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
                            .padding(18.dp)
                    ) {
                        Text(text = "টার্গেট বাক্য (বাংলা):", fontSize = 12.sp, color = Slate500)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentSentence.first,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate900
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Built Sentence Slot
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = LightBg,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, JapanRedBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text("আপনার সাজানো বাক্য:", fontSize = 12.sp, color = Slate500)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (builtSentence.isEmpty()) {
                            Text("নিচের শব্দগুলোতে চাপ দিয়ে সাজান...", fontSize = 14.sp, color = Slate400)
                        } else {
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                builtSentence.forEach { word ->
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = JapanRed,
                                        modifier = Modifier.clickable {
                                            if (!isChecked) {
                                                builtSentence.remove(word)
                                                availableWords.add(word)
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "$word ✕",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Available Words Bank
                Text("উপলব্ধ শব্দসমূহ:", fontSize = 13.sp, color = Slate700, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    availableWords.forEach { word ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = CardBg,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderSubtle),
                            modifier = Modifier.clickable {
                                if (!isChecked) {
                                    availableWords.remove(word)
                                    builtSentence.add(word)
                                }
                            }
                        ) {
                            Text(
                                text = word,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                if (isChecked) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isCorrect) Emerald50 else JapanRedLight
                    ) {
                        Text(
                            text = if (isCorrect) "অভিনন্দন! বাক্যটি নিখুঁত হয়েছে!" else "সঠিক ক্রম: ${currentSentence.second.joinToString(" ")}",
                            color = if (isCorrect) Emerald600 else JapanRed,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isChecked) {
                        isChecked = true
                        isCorrect = builtSentence.toList() == currentSentence.second
                    } else {
                        if (currentIndex < sentences.size - 1) {
                            currentIndex++
                            isChecked = false
                        } else {
                            isFinished = true
                        }
                    }
                },
                enabled = builtSentence.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (!isChecked) "বাক্য পরীক্ষা করুন" else if (currentIndex < sentences.size - 1) "পরবর্তী বাক্য" else "ফলাফল দেখুন",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun QuizResultCard(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderSubtle, RoundedCornerShape(20.dp))
            .testTag("quiz_result_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PandaAvatar(size = 84.dp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "কুইজ সম্পন্ন হয়েছে! 🎉",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Slate900
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "আপনার প্রাপ্ত স্কোর",
                fontSize = 14.sp,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(14.dp),
                color = JapanRedLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder)
            ) {
                Text(
                    text = "${BengaliUtils.toBengaliDigits(score)} / ${BengaliUtils.toBengaliDigits(total)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = JapanRed,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("পুনরায় অনুশীলন করুন", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
