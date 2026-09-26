package com.example.data.model

data class GrammarBreakdown(
    val part: String,
    val romaji: String,
    val roleBengali: String,
    val explanation: String
)

data class GrammarItem(
    val id: Int,
    val pattern: String,
    val patternRomaji: String,
    val titleBengali: String,
    val meaningBengali: String,
    val explanationBengali: String,
    val exampleJapanese: String,
    val exampleReading: String,
    val exampleBengali: String,
    val exampleEnglish: String,
    val breakdowns: List<GrammarBreakdown>
)
