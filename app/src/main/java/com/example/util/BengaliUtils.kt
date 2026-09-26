package com.example.util

object BengaliUtils {
    private val bengaliDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

    fun toBengaliDigits(number: Int): String {
        return number.toString().map { char ->
            if (char in '0'..'9') {
                bengaliDigits[char - '0']
            } else {
                char
            }
        }.joinToString("")
    }

    fun formatCounter(current: Int, total: Int): String {
        return "${toBengaliDigits(current)} / ${toBengaliDigits(total)}"
    }
}
