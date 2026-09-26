package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppScreen
import com.example.ui.screens.FlashcardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.VocabularyScreen
import com.example.ui.theme.JapaneseBanglaTheme
import com.example.util.TtsManager

class MainActivity : ComponentActivity() {
    private lateinit var ttsManager: TtsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ttsManager = TtsManager(this)

        setContent {
            JapaneseBanglaTheme {
                MainApp(onSpeakJapanese = { text ->
                    ttsManager.speakJapanese(text)
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
    }
}

@Composable
fun MainApp(
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Hardware/System back button handling
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        currentScreen = AppScreen.HOME
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            AppBottomNavBar(
                currentScreen = currentScreen,
                onTabSelected = { selected ->
                    currentScreen = selected
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_transition",
            modifier = Modifier.padding(innerPadding)
        ) { screen ->
            when (screen) {
                AppScreen.HOME -> HomeScreen(
                    onNavigate = { target -> currentScreen = target },
                    snackbarHostState = snackbarHostState
                )
                AppScreen.VOCABULARY -> VocabularyScreen(
                    onSpeakJapanese = onSpeakJapanese
                )
                AppScreen.FLASHCARD -> FlashcardScreen(
                    onSpeakJapanese = onSpeakJapanese
                )
            }
        }
    }
}
