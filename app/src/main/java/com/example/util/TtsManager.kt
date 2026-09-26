package com.example.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsManager(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TtsManager", "Japanese language is not supported or missing data on this device")
                tts?.setLanguage(Locale.US)
            }
            isInitialized = true
        } else {
            Log.e("TtsManager", "TTS initialization failed with status $status")
        }
    }

    fun speakJapanese(text: String) {
        if (isInitialized) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_japanese_${System.currentTimeMillis()}")
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
        } catch (e: Exception) {
            Log.e("TtsManager", "Error shutting down TTS", e)
        }
    }
}
