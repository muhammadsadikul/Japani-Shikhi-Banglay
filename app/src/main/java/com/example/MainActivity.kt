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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.SettingsManager
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppScreen
import com.example.ui.screens.FlashcardScreen
import com.example.ui.screens.GrammarScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KanjiScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.VocabularyScreen
import com.example.ui.theme.JapaneseBanglaTheme
import com.example.util.TtsManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var ttsManager: TtsManager
    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ttsManager = TtsManager(this)
        settingsManager = SettingsManager(this)

        setContent {
            val isDarkModePref by settingsManager.isDarkMode.collectAsState()
            val useDarkTheme = isDarkModePref ?: isSystemInDarkTheme()

            JapaneseBanglaTheme(darkTheme = useDarkTheme) {
                MainAppHost(
                    settingsManager = settingsManager,
                    onSpeakJapanese = { text -> ttsManager.speakJapanese(text) }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
    }
}

@Composable
fun MainAppHost(
    settingsManager: SettingsManager,
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSplashVisible by remember { mutableStateOf(true) }

    if (isSplashVisible) {
        SplashScreen(
            onSplashFinished = { isSplashVisible = false }
        )
    } else {
        MainApp(
            settingsManager = settingsManager,
            onSpeakJapanese = onSpeakJapanese,
            modifier = modifier
        )
    }
}

@Composable
fun MainApp(
    settingsManager: SettingsManager,
    onSpeakJapanese: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showEnglishMeaning by settingsManager.showEnglishMeaning.collectAsState()

    // Handle system back navigation to return to Home screen
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        currentScreen = AppScreen.HOME
    }

    val onReportError: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar("রিপোর্ট গ্রহণ করা হয়েছে। ধন্যবাদ!")
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (currentScreen != AppScreen.SETTINGS) {
                AppBottomNavBar(
                    currentScreen = currentScreen,
                    onTabSelected = { selected ->
                        currentScreen = selected
                    }
                )
            }
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
                    onOpenSettings = { currentScreen = AppScreen.SETTINGS },
                    snackbarHostState = snackbarHostState
                )
                AppScreen.VOCABULARY -> VocabularyScreen(
                    showEnglishMeaning = showEnglishMeaning,
                    onSpeakJapanese = onSpeakJapanese,
                    onReportError = onReportError
                )
                AppScreen.FLASHCARD -> FlashcardScreen(
                    showEnglishMeaning = showEnglishMeaning,
                    onSpeakJapanese = onSpeakJapanese,
                    onReportError = onReportError
                )
                AppScreen.QUIZ -> QuizScreen(
                    onSpeakJapanese = onSpeakJapanese
                )
                AppScreen.KANJI -> KanjiScreen(
                    onSpeakJapanese = onSpeakJapanese,
                    snackbarHostState = snackbarHostState
                )
                AppScreen.GRAMMAR -> GrammarScreen(
                    onSpeakJapanese = onSpeakJapanese,
                    snackbarHostState = snackbarHostState
                )
                AppScreen.SETTINGS -> SettingsScreen(
                    settingsManager = settingsManager,
                    onBack = { currentScreen = AppScreen.HOME },
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}
