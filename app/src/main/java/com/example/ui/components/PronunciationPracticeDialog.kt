package com.example.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
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

enum class SpeechPracticeState {
    PERMISSION_REQUIRED,
    READY,
    LISTENING,
    PROCESSING,
    PASSED,
    FAILED,
    ERROR
}

@Composable
fun PronunciationPracticeDialog(
    targetJapanese: String,
    targetReading: String,
    targetMeaning: String,
    onSpeakNative: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var practiceState by remember { mutableStateOf(SpeechPracticeState.READY) }
    var recognizedText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var speechRecognizer by remember { mutableStateOf<SpeechRecognizer?>(null) }

    fun checkMatch(spoken: String, target: String, reading: String): Boolean {
        val cleanSpoken = spoken.trim().replace(" ", "").replace("、", "").replace("。", "").lowercase()
        val cleanTarget = target.trim().replace(" ", "").replace("、", "").replace("。", "").lowercase()
        val cleanReading = reading.trim().replace(" ", "").replace("、", "").replace("。", "").lowercase()

        if (cleanSpoken.isEmpty() || cleanTarget.isEmpty()) return false
        if (cleanSpoken == cleanTarget || cleanSpoken == cleanReading) return true
        if (cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken)) return true
        if (cleanReading.isNotEmpty() && (cleanSpoken.contains(cleanReading) || cleanReading.contains(cleanSpoken))) return true

        return false
    }

    fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            practiceState = SpeechPracticeState.ERROR
            errorMessage = "এই ডিভাইসে ভয়েস রিকগনিশন সার্ভিস সক্রিয় নয়।"
            return
        }

        try {
            speechRecognizer?.destroy()
            val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer = recognizer

            recognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    practiceState = SpeechPracticeState.LISTENING
                }

                override fun onBeginningOfSpeech() {
                    practiceState = SpeechPracticeState.LISTENING
                }

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    practiceState = SpeechPracticeState.PROCESSING
                }

                override fun onError(error: Int) {
                    practiceState = SpeechPracticeState.FAILED
                    errorMessage = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> "কোনো শব্দ শনাক্ত করা যায়নি।"
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "কোনো কথা শোনা যায়নি।"
                        SpeechRecognizer.ERROR_AUDIO -> "মাইক্রোফোন সংযোগে সমস্যা।"
                        else -> "উচ্চারণ সঠিকভাবে শনাক্ত করা যায়নি।"
                    }
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val bestMatch = matches?.firstOrNull() ?: ""
                    recognizedText = bestMatch

                    val isMatch = matches?.any { candidate ->
                        checkMatch(candidate, targetJapanese, targetReading)
                    } == true

                    practiceState = if (isMatch) SpeechPracticeState.PASSED else SpeechPracticeState.FAILED
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ja-JP")
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ja-JP")
                putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "ja-JP")
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
            }

            recognizer.startListening(intent)
            practiceState = SpeechPracticeState.LISTENING
        } catch (e: Exception) {
            practiceState = SpeechPracticeState.ERROR
            errorMessage = e.localizedMessage ?: "অজ্ঞাত ত্রুটি ঘটেছে"
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startListening()
        } else {
            practiceState = SpeechPracticeState.PERMISSION_REQUIRED
        }
    }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            startListening()
        } else {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            speechRecognizer?.destroy()
        }
    }

    // Pulse animation for listening state
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, BorderSubtle, RoundedCornerShape(24.dp))
                .testTag("pronunciation_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Header with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "উচ্চারণ অনুশীলন (Voice Practice)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "বন্ধ করুন",
                            tint = Slate400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Target Japanese Word Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LightBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, JapanRedBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = targetReading,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = JapanRed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = targetJapanese,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Slate900,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = targetMeaning,
                            fontSize = 13.sp,
                            color = Slate500
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Audio button to hear pronunciation
                        OutlinedButton(
                            onClick = { onSpeakNative(targetJapanese) },
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, JapanRed)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.VolumeUp,
                                contentDescription = null,
                                tint = JapanRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "সঠিক উচ্চারণ শুনুন",
                                color = JapanRed,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Dynamic Practice State UI
                when (practiceState) {
                    SpeechPracticeState.PERMISSION_REQUIRED -> {
                        Text(
                            text = "ভয়েস বিশ্লেষণের জন্য মাইক্রোফোনের অনুমতি প্রয়োজন।",
                            fontSize = 13.sp,
                            color = Slate700,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { permissionLauncher.launch(Manifest.permission.RECORD_AUDIO) },
                            colors = ButtonDefaults.buttonColors(containerColor = JapanRed)
                        ) {
                            Text("মাইক্রোফোন অনুমতি দিন", color = Color.White)
                        }
                    }

                    SpeechPracticeState.READY, SpeechPracticeState.LISTENING -> {
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(JapanRedLight)
                                .border(2.dp, JapanRed, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "শুনছি",
                                tint = JapanRed,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "শুনছি... বলুন 🎤",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = JapanRed
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "জাপানি শব্দটি স্পষ্ট স্বরে উচ্চারণ করুন",
                            fontSize = 12.sp,
                            color = Slate500
                        )
                    }

                    SpeechPracticeState.PROCESSING -> {
                        Text(
                            text = "বিশ্লেষণ করা হচ্ছে... ⏳",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate700
                        )
                    }

                    SpeechPracticeState.PASSED -> {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Emerald50),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "পাস",
                                tint = Emerald600,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "পাস! (Pass!) 🎉",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Emerald600
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "চমৎকার! আপনার উচ্চারণ নির্ভুল হয়েছে।",
                            fontSize = 13.sp,
                            color = Slate700
                        )
                        if (recognizedText.isNotBlank()) {
                            Text(
                                text = "শনাক্ত: $recognizedText",
                                fontSize = 12.sp,
                                color = Slate500
                            )
                        }
                    }

                    SpeechPracticeState.FAILED, SpeechPracticeState.ERROR -> {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFEE2E2)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "ফেল",
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "ফেল! আবার চেষ্টা করুন। (Fail! Try again.)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDC2626)
                        )
                        if (recognizedText.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "আপনার উচ্চারণ শোনা গেছে: \"$recognizedText\"",
                                fontSize = 13.sp,
                                color = Slate700
                            )
                        } else if (errorMessage.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 12.sp,
                                color = Slate500
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons at Bottom
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (practiceState == SpeechPracticeState.PASSED) {
                        Button(
                            onClick = onDismiss,
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald600),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f).height(46.dp)
                        ) {
                            Text("দারুণ! সম্পন্ন", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { startListening() },
                            colors = ButtonDefaults.buttonColors(containerColor = JapanRed),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f).height(46.dp).testTag("retry_speech_button")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("আবার বলুন (Retry)", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
