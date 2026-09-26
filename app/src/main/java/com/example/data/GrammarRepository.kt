package com.example.data

import com.example.data.model.GrammarBreakdown
import com.example.data.model.GrammarItem

object GrammarRepository {
    val n5GrammarList: List<GrammarItem> = listOf(
        GrammarItem(
            id = 1,
            pattern = "〜は〜です",
            patternRomaji = "~ wa ~ desu",
            titleBengali = "A হল B (পরিচয় বা অবস্থা প্রকাশ)",
            meaningBengali = "A হল B / A is B",
            explanationBengali = "জাপানি ভাষার সবচেয়ে মৌলিক বাক্য গঠন। কোনো ব্যক্তি বা বস্তুর পরিচয়, পেশা বা জাতীয়তা জানাতে এটি ব্যবহৃত হয়।",
            exampleJapanese = "わたしは学生です。",
            exampleReading = "わたし は がくせい です (Watashi wa gakusei desu)",
            exampleBengali = "আমি একজন ছাত্র।",
            exampleEnglish = "I am a student.",
            breakdowns = listOf(
                GrammarBreakdown("わたし", "watashi", "বিষয় (Topic)", "আমি — সর্বনাম"),
                GrammarBreakdown("は", "wa", "বিষয় নির্দেশক কণা (Topic Particle)", "বাক্যের মূল আলোচ্য বিষয় নির্দেশ করে। লেখা হয় 'ha' কিন্তু উচ্চারণ 'wa'"),
                GrammarBreakdown("学生", "gakusei", "বিধেয় (Predicate Noun)", "ছাত্র / ছাত্রী"),
                GrammarBreakdown("です", "desu", "ভদ্র সহায়ক ক্রিয়া (Polite Copula)", "হওয়া (am / is / are)-এর কাজ করে এবং বাক্য সমাপ্ত করে")
            )
        ),
        GrammarItem(
            id = 2,
            pattern = "〜は〜じゃありません / ではありません",
            patternRomaji = "~ wa ~ ja arimasen / dewa arimasen",
            titleBengali = "A, B নয় (নেতিবাচক রূপ)",
            meaningBengali = "A, B নয় / A is not B",
            explanationBengali = "'です' এর নেতিবাচক রূপ। সাধারণ কথ্য ভাষায় 'じゃありません' এবং লিখিত বা ফরমাল ভাষায় 'ではありません' ব্যবহৃত হয়।",
            exampleJapanese = "田中さんは先生ではありません。",
            exampleReading = "たなかさん は せんせい ではありません (Tanaka-san wa sensei dewa arimasen)",
            exampleBengali = "তানাকা সাহেব শিক্ষক নন।",
            exampleEnglish = "Mr. Tanaka is not a teacher.",
            breakdowns = listOf(
                GrammarBreakdown("田中さん", "Tanaka-san", "ব্যক্তি (Subject)", "তানাকা সাহেব ('-সান' হলো সম্মানসূচক পদবী)"),
                GrammarBreakdown("は", "wa", "বিষয় নির্দেশক কণা", "তানাকা সাহেবকে আলোচনার মূল বিষয় হিসেবে নির্দিষ্ট করে"),
                GrammarBreakdown("先生", "sensei", "পেশা (Noun)", "শিক্ষক / শিক্ষিকা"),
                GrammarBreakdown("ではありません", "dewa arimasen", "নেতিবাচক সমাপ্তি", "'নয়' বা 'নন' বোঝায় (ভদ্র রীতি)")
            )
        ),
        GrammarItem(
            id = 3,
            pattern = "〜も〜です",
            patternRomaji = "~ mo ~ desu",
            titleBengali = "A-ও B ('ও' বা Also বোঝাতে)",
            meaningBengali = "A-ও B / A is also B",
            explanationBengali = "যখন আগের বিষয়ের মত নতুন বিষয়ের ক্ষেত্রেও একই তথ্য প্রযোজ্য হয়, তখন 'は' কণার পরিবর্তে 'も' কণা বসে।",
            exampleJapanese = "私もバングラデシュ人です。",
            exampleReading = "わたし も バングラデシュじん です (Watashi mo Banguradeshu-jin desu)",
            exampleBengali = "আমিও বাংলাদেশি।",
            exampleEnglish = "I am also Bangladeshi.",
            breakdowns = listOf(
                GrammarBreakdown("私", "watashi", "বিষয়", "আমি"),
                GrammarBreakdown("も", "mo", "সংযোজক কণা (Also Particle)", "বাংলায় 'ও' অর্থ দেয় (আমি + ও = আমিও)"),
                GrammarBreakdown("バングラデシュ人", "Banguradeshu-jin", "জাতীয়তা (Noun + じん)", "বাংলাদেশি নাগরিক"),
                GrammarBreakdown("です", "desu", "সহায়ক ক্রিয়া", "হয় / am")
            )
        ),
        GrammarItem(
            id = 4,
            pattern = "〜を〜ます",
            patternRomaji = "~ o ~ masu",
            titleBengali = "কর্মবাচক কণা 'を' ও বর্তমান ক্রিয়াপদ",
            meaningBengali = "কিছু করা / খাওয়া / পড়া (Object + Verb)",
            explanationBengali = "সকর্মক ক্রিয়ার (Transitive Verb) সাথে কর্ম বা Object নির্দেশ করতে 'を' (উচ্চারণ 'o') কণা ব্যবহৃত হয়।",
            exampleJapanese = "ご飯を食べます。",
            exampleReading = "ごはん を たべます (Gohan o tabemasu)",
            exampleBengali = "আমি ভাত / খাবার খাই।",
            exampleEnglish = "I eat rice / meal.",
            breakdowns = listOf(
                GrammarBreakdown("ご飯", "gohan", "কর্ম (Direct Object)", "ভাত অথবা খাবার"),
                GrammarBreakdown("を", "o (wo)", "কর্ম নির্দেশক কণা (Object Marker)", "কোন জিনিসের উপর ক্রিয়ার প্রভাব পড়ছে তা চিহ্নিত করে"),
                GrammarBreakdown("食べます", "tabemasu", "ভদ্র বর্তমান ক্রিয়া (Polite Verb)", "'食べる' ক্রিয়ার মার্জিত বর্তমান রূপ (খাই / খাব)")
            )
        ),
        GrammarItem(
            id = 5,
            pattern = "〜へ行きます / 来ます / 帰ります",
            patternRomaji = "~ e ikimasu / kimasu / kaerimasu",
            titleBengali = "দিকনির্দেশক কণা 'へ' (গন্তব্যে যাওয়া/আসা/ফেরা)",
            meaningBengali = "কোনো স্থানে যাওয়া / আসা / বাড়ি ফেরা",
            explanationBengali = "কোনো গন্তব্য বা লক্ষ্যের দিকে চলনশীল ক্রিয়া বোঝাতে স্থানটির পরে 'へ' (উচ্চারণ 'e') কণা বসে।",
            exampleJapanese = "学校へ行きます。",
            exampleReading = "がっこう へ いきます (Gakkou e ikimasu)",
            exampleBengali = "আমি স্কুলে যাই।",
            exampleEnglish = "I go to school.",
            breakdowns = listOf(
                GrammarBreakdown("学校", "gakkou", "গন্তব্য স্থান (Place)", "বিদ্যালয় / স্কুল"),
                GrammarBreakdown("へ", "e (he)", "দিক নির্দেশক কণা (Direction Particle)", "লেখা হয় 'he' কিন্তু উচ্চারণ 'e'। অর্থ: দিকে বা অভিমুখে"),
                GrammarBreakdown("行きます", "ikimasu", "চলন ক্রিয়া (Verb of Motion)", "'行く' ক্রিয়ার মার্জিত রূপ (যাই / যাব)")
            )
        )
    )
}
