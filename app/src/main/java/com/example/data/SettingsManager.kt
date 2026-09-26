package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("app_settings_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_DARK_MODE = "key_dark_mode"
        private const val KEY_SHOW_ENGLISH = "key_show_english"
        private const val KEY_NOTIFICATIONS = "key_notifications"
        private const val KEY_REMINDER_TIME = "key_reminder_time"
        private const val KEY_APP_LANGUAGE = "key_app_language"
        private const val KEY_LANGUAGE_SELECTED = "key_language_selected"
    }

    private val _appLanguage = MutableStateFlow(
        prefs.getString(KEY_APP_LANGUAGE, "bn") ?: "bn"
    )
    val appLanguage: StateFlow<String> = _appLanguage.asStateFlow()

    private val _hasSelectedLanguage = MutableStateFlow(
        prefs.getBoolean(KEY_LANGUAGE_SELECTED, false)
    )
    val hasSelectedLanguage: StateFlow<Boolean> = _hasSelectedLanguage.asStateFlow()

    private val _isDarkMode = MutableStateFlow<Boolean?>(
        if (prefs.contains(KEY_DARK_MODE)) prefs.getBoolean(KEY_DARK_MODE, false) else null
    )
    val isDarkMode: StateFlow<Boolean?> = _isDarkMode.asStateFlow()

    private val _showEnglishMeaning = MutableStateFlow(
        prefs.getBoolean(KEY_SHOW_ENGLISH, true)
    )
    val showEnglishMeaning: StateFlow<Boolean> = _showEnglishMeaning.asStateFlow()

    private val _smartNotifications = MutableStateFlow(
        prefs.getBoolean(KEY_NOTIFICATIONS, true)
    )
    val smartNotifications: StateFlow<Boolean> = _smartNotifications.asStateFlow()

    private val _reminderTime = MutableStateFlow(
        prefs.getString(KEY_REMINDER_TIME, "রাত ৮:০০") ?: "রাত ৮:০০"
    )
    val reminderTime: StateFlow<String> = _reminderTime.asStateFlow()

    fun setDarkMode(enabled: Boolean?) {
        _isDarkMode.value = enabled
        prefs.edit().apply {
            if (enabled == null) {
                remove(KEY_DARK_MODE)
            } else {
                putBoolean(KEY_DARK_MODE, enabled)
            }
            apply()
        }
    }

    fun setShowEnglishMeaning(show: Boolean) {
        _showEnglishMeaning.value = show
        prefs.edit().putBoolean(KEY_SHOW_ENGLISH, show).apply()
    }

    fun setSmartNotifications(enabled: Boolean) {
        _smartNotifications.value = enabled
        prefs.edit().putBoolean(KEY_NOTIFICATIONS, enabled).apply()
    }

    fun setReminderTime(time: String) {
        _reminderTime.value = time
        prefs.edit().putString(KEY_REMINDER_TIME, time).apply()
    }

    fun setAppLanguage(lang: String) {
        _appLanguage.value = lang
        _hasSelectedLanguage.value = true
        prefs.edit()
            .putString(KEY_APP_LANGUAGE, lang)
            .putBoolean(KEY_LANGUAGE_SELECTED, true)
            .apply()
    }
}
