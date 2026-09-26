package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("জাপানি শিখি বাংলায়", appName)
  }

  @Test
  fun `verify 50 vocabulary words loaded`() {
    val words = com.example.data.VocabularyRepository.n5Words
    assertEquals(50, words.size)
    val firstWord = words.first()
    assertEquals("私", firstWord.japanese)
    assertEquals("わたし", firstWord.furigana)
    assertEquals("আমি", firstWord.bengali)
  }

  @Test
  fun `verify bengali numeral formatting`() {
    val formatted = com.example.util.BengaliUtils.formatCounter(1, 50)
    assertEquals("১ / ৫০", formatted)
  }

  @Test
  fun `verify 10 kanji loaded`() {
    val kanjiList = com.example.data.KanjiRepository.n5KanjiList
    assertEquals(10, kanjiList.size)
    assertEquals("一", kanjiList[0].kanji)
    assertEquals("日", kanjiList[3].kanji)
  }

  @Test
  fun `verify 5 grammar patterns loaded`() {
    val grammarList = com.example.data.GrammarRepository.n5GrammarList
    assertEquals(5, grammarList.size)
    assertEquals("〜は〜です", grammarList[0].pattern)
  }
}
