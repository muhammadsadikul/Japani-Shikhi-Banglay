package com.example.data

import com.example.data.model.VocabularyItem

object VocabularyRepository {
    val n5Words: List<VocabularyItem> = listOf(
        VocabularyItem(1, "私", "わたし", "watashi", "আমি", "I, me", "সর্বনাম"),
        VocabularyItem(2, "あなた", "あなた", "anata", "আপনি / তুমি", "you", "সর্বনাম"),
        VocabularyItem(3, "学生", "がくせい", "gakusei", "ছাত্র / ছাত্রী", "student", "বিশেষ্য"),
        VocabularyItem(4, "先生", "せんせい", "sensei", "শিক্ষক / শিক্ষিকা", "teacher", "বিশেষ্য"),
        VocabularyItem(5, "学校", "がっこう", "gakkou", "বিদ্যালয় / স্কুল", "school", "বিশেষ্য"),
        VocabularyItem(6, "食べる", "たべる", "taberu", "খাওয়া", "to eat", "ক্রিয়া"),
        VocabularyItem(7, "飲む", "のむ", "nomu", "পান করা", "to drink", "ক্রিয়া"),
        VocabularyItem(8, "行く", "いく", "iku", "যাওয়া", "to go", "ক্রিয়া"),
        VocabularyItem(9, "来る", "くる", "kuru", "আসা", "to come", "ক্রিয়া"),
        VocabularyItem(10, "見る", "みる", "miru", "দেখা", "to see, watch", "ক্রিয়া"),
        VocabularyItem(11, "聞く", "きく", "kiku", "শোনা / জিজ্ঞাসা করা", "to hear, listen", "ক্রিয়া"),
        VocabularyItem(12, "話す", "はなす", "hanasu", "কথা বলা", "to speak, talk", "ক্রিয়া"),
        VocabularyItem(13, "読む", "よむ", "yomu", "পড়া", "to read", "ক্রিয়া"),
        VocabularyItem(14, "書く", "かく", "kaku", "লেখা", "to write", "ক্রিয়া"),
        VocabularyItem(15, "買う", "かう", "kau", "কেনা", "to buy", "ক্রিয়া"),
        VocabularyItem(16, "大きい", "おおきい", "ookii", "বড়", "big, large", "বিশেষণ"),
        VocabularyItem(17, "小さい", "ちいさい", "chiisai", "ছোট", "small, little", "বিশেষণ"),
        VocabularyItem(18, "新しい", "あたらしい", "atarashii", "নতুন", "new", "বিশেষণ"),
        VocabularyItem(19, "古い", "ふるい", "furui", "পুরাতন / পুরোনো", "old", "বিশেষণ"),
        VocabularyItem(20, "いい", "いい", "ii", "ভালো", "good", "বিশেষণ"),
        VocabularyItem(21, "悪い", "わるい", "warui", "খারাপ", "bad", "বিশেষণ"),
        VocabularyItem(22, "高い", "たかい", "takai", "উঁচু / দামি", "expensive, high", "বিশেষণ"),
        VocabularyItem(23, "安い", "やすい", "yasui", "সস্তা", "cheap, inexpensive", "বিশেষণ"),
        VocabularyItem(24, "人", "ひと", "hito", "মানুষ / ব্যক্তি", "person", "বিশেষ্য"),
        VocabularyItem(25, "日本", "にほん", "nihon", "জাপান", "Japan", "বিশেষ্য"),
        VocabularyItem(26, "日本語", "にほんご", "nihongo", "জাপানি ভাষা", "Japanese language", "বিশেষ্য"),
        VocabularyItem(27, "本", "ほん", "hon", "বই", "book", "বিশেষ্য"),
        VocabularyItem(28, "水", "みず", "mizu", "পানি / জল", "water", "বিশেষ্য"),
        VocabularyItem(29, "ご飯", "ごはん", "gohan", "ভাত / খাবার", "cooked rice, meal", "বিশেষ্য"),
        VocabularyItem(30, "車", "くるま", "kuruma", "গাড়ি", "car, vehicle", "বিশেষ্য"),
        VocabularyItem(31, "家", "いえ", "ie", "বাড়ি / ঘর", "house, home", "বিশেষ্য"),
        VocabularyItem(32, "友達", "ともだち", "tomodachi", "বন্ধু", "friend", "বিশেষ্য"),
        VocabularyItem(33, "今日", "きょう", "kyou", "আজ", "today", "সময়"),
        VocabularyItem(34, "明日", "あした", "ashita", "আগামীকাল", "tomorrow", "সময়"),
        VocabularyItem(35, "昨日", "きのう", "kinou", "গতকাল", "yesterday", "সময়"),
        VocabularyItem(36, "朝", "あさ", "asa", "সকাল", "morning", "সময়"),
        VocabularyItem(37, "夜", "よる", "yoru", "রাত", "night", "সময়"),
        VocabularyItem(38, "起きる", "おきる", "okiru", "ঘুম থেকে ওঠা", "to wake up", "ক্রিয়া"),
        VocabularyItem(39, "寝る", "ねる", "neru", "ঘুমানো", "to sleep, go to bed", "ক্রিয়া"),
        VocabularyItem(40, "会う", "あう", "au", "দেখা করা / সাক্ষাৎ করা", "to meet", "ক্রিয়া"),
        VocabularyItem(41, "待つ", "まつ", "matsu", "অপেক্ষা করা", "to wait", "ক্রিয়া"),
        VocabularyItem(42, "帰る", "かえる", "kaeru", "ফিরে যাওয়া / ফেরা", "to return, go back", "ক্রিয়া"),
        VocabularyItem(43, "暑い", "あつい", "atsui", "গরম (আবহাওয়া)", "hot (weather)", "বিশেষণ"),
        VocabularyItem(44, "寒い", "さむい", "samui", "ঠান্ডা (আবহাওয়া)", "cold (weather)", "বিশেষণ"),
        VocabularyItem(45, "難しい", "むずかしい", "muzukashii", "কঠিন", "difficult", "বিশেষণ"),
        VocabularyItem(46, "易しい", "やさしい", "yasashii", "সহজ", "easy, gentle", "বিশেষণ"),
        VocabularyItem(47, "時間", "じかん", "jikan", "সময়", "time, hour", "সময়"),
        VocabularyItem(48, "お金", "おかね", "okane", "টাকা / অর্থ", "money", "বিশেষ্য"),
        VocabularyItem(49, "好き", "すき", "suki", "পছন্দ / প্রিয়", "like, fond of", "বিশেষণ"),
        VocabularyItem(50, "ありがとう", "ありがとう", "arigatou", "ধন্যবাদ", "thank you", "অভিবাদন")
    )

    val categories: List<String> = listOf("সব", "ক্রিয়া", "বিশেষণ", "বিশেষ্য", "সর্বনাম", "সময়", "অভিবাদন")

    fun searchWords(query: String, category: String = "সব"): List<VocabularyItem> {
        val trimmed = query.trim().lowercase()
        return n5Words.filter { item ->
            val matchesCategory = (category == "সব" || item.category == category)
            val matchesQuery = if (trimmed.isEmpty()) {
                true
            } else {
                item.japanese.contains(trimmed, ignoreCase = true) ||
                item.furigana.contains(trimmed, ignoreCase = true) ||
                item.romaji.contains(trimmed, ignoreCase = true) ||
                item.bengali.contains(trimmed, ignoreCase = true) ||
                item.english.contains(trimmed, ignoreCase = true)
            }
            matchesCategory && matchesQuery
        }
    }
}
