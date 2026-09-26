package com.example.data.model

data class VocabularyItem(
    val id: Int,
    val japanese: String,
    val furigana: String,
    val romaji: String,
    val bengali: String,
    val english: String,
    val category: String,
    val level: String = "N5",
    val exampleJapanese: String = "",
    val exampleReading: String = "",
    val exampleBengali: String = "",
    val exampleEnglish: String = ""
)
