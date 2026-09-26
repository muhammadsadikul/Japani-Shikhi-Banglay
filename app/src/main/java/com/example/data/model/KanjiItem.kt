package com.example.data.model

data class KanjiItem(
    val id: Int,
    val kanji: String,
    val meaningBengali: String,
    val meaningEnglish: String,
    val onyomi: String,
    val kunyomi: String,
    val strokeCount: Int,
    val strokeSteps: List<String>,
    val exampleWord: String,
    val exampleReading: String,
    val exampleMeaning: String
)
