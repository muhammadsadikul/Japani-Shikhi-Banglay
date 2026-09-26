package com.example.data

import com.example.data.model.KanjiItem

object KanjiRepository {
    val n5KanjiList: List<KanjiItem> = listOf(
        KanjiItem(
            id = 1,
            kanji = "一",
            meaningBengali = "এক (১)",
            meaningEnglish = "One",
            onyomi = "イチ (ichi), イツ (itsu)",
            kunyomi = "ひと-つ (hito-tsu)",
            strokeCount = 1,
            strokeSteps = listOf("বাম থেকে ডানে অনুভূমিক রেখা (Horizontal line left to right)"),
            exampleWord = "一つ",
            exampleReading = "ひとつ (hitotsu)",
            exampleMeaning = "একটি"
        ),
        KanjiItem(
            id = 2,
            kanji = "二",
            meaningBengali = "দুই (২)",
            meaningEnglish = "Two",
            onyomi = "ニ (ni), ジ (ji)",
            kunyomi = "ふた-つ (futa-tsu)",
            strokeCount = 2,
            strokeSteps = listOf("উপরের ছোট অনুভূমিক রেখা", "নিচের লম্বা অনুভূমিক রেখা"),
            exampleWord = "二つ",
            exampleReading = "ふたつ (futatsu)",
            exampleMeaning = "দুটি"
        ),
        KanjiItem(
            id = 3,
            kanji = "三",
            meaningBengali = "তিন (৩)",
            meaningEnglish = "Three",
            onyomi = "サン (san)",
            kunyomi = "মি-つ (mit-tsu)",
            strokeCount = 3,
            strokeSteps = listOf("উপরের রেখা", "মাঝের ছোট রেখা", "নিচের লম্বা রেখা"),
            exampleWord = "三日",
            exampleReading = "みっか (mikka)",
            exampleMeaning = "তিন তারিখ / তিন দিন"
        ),
        KanjiItem(
            id = 4,
            kanji = "日",
            meaningBengali = "দিন / সূর্য",
            meaningEnglish = "Day, Sun",
            onyomi = "ニチ (nichi), ジツ (jitsu)",
            kunyomi = "ひ (hi), -か (ka)",
            strokeCount = 4,
            strokeSteps = listOf("বাম পাশের উল্লম্ব রেখা", "উপর ও ডান পাশের কোণা", "মাঝের অনুভূমিক রেখা", "নিচের রেখা বন্ধ করা"),
            exampleWord = "日本",
            exampleReading = "にほん (nihon)",
            exampleMeaning = "জাপান (সূর্যোদয়ের দেশ)"
        ),
        KanjiItem(
            id = 5,
            kanji = "月",
            meaningBengali = "চাঁদ / মাস",
            meaningEnglish = "Moon, Month",
            onyomi = "ゲツ (getsu), ガツ (gatsu)",
            kunyomi = "つき (tsuki)",
            strokeCount = 4,
            strokeSteps = listOf("বাম পাশের বাঁকা উল্লম্ব রেখা", "উপর থেকে ডানে গিয়ে নিচের বাঁক", "মাঝের প্রথম রেখা", "মাঝের দ্বিতীয় রেখা"),
            exampleWord = "一月",
            exampleReading = "いちがつ (ichigatsu)",
            exampleMeaning = "জানুয়ারি মাস"
        ),
        KanjiItem(
            id = 6,
            kanji = "木",
            meaningBengali = "গাছ / কাঠ",
            meaningEnglish = "Tree, Wood",
            onyomi = "ボク (boku), モク (moku)",
            kunyomi = "き (ki), こ- (ko-)",
            strokeCount = 4,
            strokeSteps = listOf("অনুভূমিক রেখা", "মাঝের উল্লম্ব রেখা", "বাম পাশের তির্যক ঢাল", "ডান পাশের তির্যক ঢাল"),
            exampleWord = "木曜日",
            exampleReading = "もくようび (mokuyoubi)",
            exampleMeaning = "বৃহস্পতিবার"
        ),
        KanjiItem(
            id = 7,
            kanji = "山",
            meaningBengali = "পাহাড় / পর্বত",
            meaningEnglish = "Mountain",
            onyomi = "サン (san), セン (sen)",
            kunyomi = "やま (yama)",
            strokeCount = 3,
            strokeSteps = listOf("মাঝের সবচেয়ে উঁচু উল্লম্ব রেখা", "বাম উল্লম্ব ও অনুভূমিক বাঁক", "ডান পাশের উল্লম্ব রেখা"),
            exampleWord = "富士山",
            exampleReading = "ふじさん (fujisan)",
            exampleMeaning = "ফুজি পর্বত"
        ),
        KanjiItem(
            id = 8,
            kanji = "川",
            meaningBengali = "নদী",
            meaningEnglish = "River",
            onyomi = "セン (sen)",
            kunyomi = "かわ (kawa)",
            strokeCount = 3,
            strokeSteps = listOf("বাম পাশের হালকা বাঁকা রেখা", "মাঝের ছোট উল্লম্ব রেখা", "ডান পাশের সোজা উল্লম্ব রেখা"),
            exampleWord = "小川",
            exampleReading = "おがわ (ogawa)",
            exampleMeaning = "ছোট নদী / ঝরনা"
        ),
        KanjiItem(
            id = 9,
            kanji = "人",
            meaningBengali = "মানুষ / ব্যক্তি",
            meaningEnglish = "Person",
            onyomi = "ジン (jin), ニン (nin)",
            kunyomi = "ひと (hito)",
            strokeCount = 2,
            strokeSteps = listOf("বাম দিকে ঢালু প্রথম স্ট্রোক", "ডান দিকে ঢালু দ্বিতীয় সহায়ক স্ট্রোক"),
            exampleWord = "日本人",
            exampleReading = "にほんじん (nihonjin)",
            exampleMeaning = "জাপানি ব্যক্তি"
        ),
        KanjiItem(
            id = 10,
            kanji = "水",
            meaningBengali = "পানি / জল",
            meaningEnglish = "Water",
            onyomi = "スイ (sui)",
            kunyomi = "みず (mizu)",
            strokeCount = 4,
            strokeSteps = listOf("মাঝের সোজা উল্লম্ব রেখা নিচের হুকসহ", "বাম পাশের ছোট বাঁকা রেখা", "ডান পাশের ঊর্ধ্বমুখী স্ট্রোক", "ডান পাশের নিম্নমুখী তির্যক স্ট্রোক"),
            exampleWord = "水曜日",
            exampleReading = "すいようび (suiyoubi)",
            exampleMeaning = "বুধবার"
        )
    )
}
