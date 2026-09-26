package com.example.data

data class SupportedLanguage(
    val code: String,
    val englishName: String,
    val nativeName: String,
    val flag: String
) {
    val displayName: String
        get() = if (englishName.equals(nativeName, ignoreCase = true)) {
            englishName
        } else {
            "$englishName ($nativeName)"
        }
}

object LanguageRepository {
    val languages: List<SupportedLanguage> = listOf(
        SupportedLanguage("bn", "Bengali", "বাংলা", "🇧🇩"),
        SupportedLanguage("en", "English", "English", "🇬🇧"),
        SupportedLanguage("hi", "Hindi", "हिन्दी", "🇮🇳"),
        SupportedLanguage("ur", "Urdu", "اردو", "🇵🇰"),
        SupportedLanguage("es", "Spanish", "Español", "🇪🇸"),
        SupportedLanguage("fr", "French", "Français", "🇫🇷"),
        SupportedLanguage("de", "German", "Deutsch", "🇩🇪"),
        SupportedLanguage("it", "Italian", "Italiano", "🇮🇹"),
        SupportedLanguage("pt", "Portuguese", "Português", "🇧🇷"),
        SupportedLanguage("ru", "Russian", "Русский", "🇷🇺"),
        SupportedLanguage("ar", "Arabic", "العربية", "🇸🇦"),
        SupportedLanguage("zh-CN", "Chinese (Simplified)", "中文 (简体)", "🇨🇳"),
        SupportedLanguage("zh-TW", "Chinese (Traditional)", "中文 (繁體)", "🇹🇼"),
        SupportedLanguage("ja", "Japanese", "日本語", "🇯🇵"),
        SupportedLanguage("ko", "Korean", "한국어", "🇰🇷"),
        SupportedLanguage("vi", "Vietnamese", "Tiếng Việt", "🇻🇳"),
        SupportedLanguage("th", "Thai", "ไทย", "🇹🇭"),
        SupportedLanguage("id", "Indonesian", "Bahasa Indonesia", "🇮🇩"),
        SupportedLanguage("ms", "Malay", "Bahasa Melayu", "🇲🇾"),
        SupportedLanguage("tr", "Turkish", "Türkçe", "🇹🇷"),
        SupportedLanguage("fa", "Persian", "فارسی", "🇮🇷"),
        SupportedLanguage("sw", "Swahili", "Kiswahili", "🇰🇪"),
        SupportedLanguage("ta", "Tamil", "தமிழ்", "🇮🇳"),
        SupportedLanguage("te", "Telugu", "తెలుగు", "🇮🇳"),
        SupportedLanguage("mr", "Marathi", "मराठी", "🇮🇳"),
        SupportedLanguage("gu", "Gujarati", "ગુજરાતી", "🇮🇳"),
        SupportedLanguage("pa", "Punjabi", "ਪੰਜਾਬੀ", "🇮🇳"),
        SupportedLanguage("tl", "Tagalog / Filipino", "Tagalog", "🇵🇭"),
        SupportedLanguage("nl", "Dutch", "Nederlands", "🇳🇱"),
        SupportedLanguage("pl", "Polish", "Polski", "🇵🇱")
    )

    fun getLanguageByCode(code: String): SupportedLanguage {
        return languages.find { it.code.equals(code, ignoreCase = true) }
            ?: languages.first { it.code == "bn" }
    }
}
