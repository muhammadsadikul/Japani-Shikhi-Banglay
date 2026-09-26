package com.example.data

import com.example.data.model.VocabularyItem

object VocabularyRepository {
    val n5Words: List<VocabularyItem> = listOf(
        VocabularyItem(1, "私", "わたし", "watashi", "আমি", "I, me", "সর্বনাম", "N5",
            "私は学生です。", "watashi wa gakusei desu.", "আমি একজন শিক্ষার্থী।", "I am a student."),
        VocabularyItem(2, "あなた", "あなた", "anata", "আপনি / তুমি", "you", "সর্বনাম", "N5",
            "あなたのお名前は何ですか？", "anata no onamae wa nan desu ka?", "আপনার নাম কি?", "What is your name?"),
        VocabularyItem(3, "学生", "がくせい", "gakusei", "ছাত্র / ছাত্রী", "student", "বিশেষ্য", "N5",
            "彼は日本語の学生です。", "kare wa nihongo no gakusei desu.", "সে জাপানি ভাষার একজন ছাত্র।", "He is a Japanese language student."),
        VocabularyItem(4, "先生", "せんせい", "sensei", "শিক্ষক / শিক্ষিকা", "teacher", "বিশেষ্য", "N5",
            "田中先生、おはようございます。", "tanaka sensei, ohayou gozaimasu.", "তানাকা স্যার, শুভ সকাল।", "Good morning, Teacher Tanaka."),
        VocabularyItem(5, "学校", "がっこう", "gakkou", "বিদ্যালয় / স্কুল", "school", "বিশেষ্য", "N5",
            "毎日学校へ行きます。", "mainichi gakkou e ikimasu.", "প্রতিদিন স্কুলে যাই।", "I go to school every day."),
        VocabularyItem(6, "食べる", "たべる", "taberu", "খাওয়া", "to eat", "ক্রিয়া", "N5",
            "朝ご飯を食べます。", "asagohan o tabemasu.", "সকালের নাস্তা খাই।", "I eat breakfast."),
        VocabularyItem(7, "飲む", "のむ", "nomu", "পান করা", "to drink", "ক্রিয়া", "N5",
            "冷たい水を飲みます。", "tsumetai mizu o nomimasu.", "ঠান্ডা পানি পান করি।", "I drink cold water."),
        VocabularyItem(8, "行く", "いく", "iku", "যাওয়া", "to go", "ক্রিয়া", "N5",
            "東京へ行く予定です。", "toukyou e iku yotei desu.", "টোকিও যাওয়ার পরিকল্পনা আছে।", "I plan to go to Tokyo."),
        VocabularyItem(9, "来る", "くる", "kuru", "আসা", "to come", "ক্রিয়া", "N5",
            "明日友達がうちに来る。", "ashita tomodachi ga uchi ni kuru.", "আগামীকাল বন্ধু আমার বাড়িতে আসবে।", "Tomorrow a friend is coming to my home."),
        VocabularyItem(10, "見る", "みる", "miru", "দেখা", "to see, watch", "ক্রিয়া", "N5",
            "映画を見るのが好きです。", "eiga o miru no ga suki desu.", "সিনেমা দেখতে পছন্দ করি।", "I like watching movies."),
        VocabularyItem(11, "聞く", "きく", "kiku", "শোনা / জিজ্ঞাসা করা", "to hear, listen", "ক্রিয়া", "N5",
            "日本の音楽を聞きます。", "nihon no ongaku o kikimasu.", "জাপানি গান শুনি।", "I listen to Japanese music."),
        VocabularyItem(12, "話す", "はなす", "hanasu", "কথা বলা", "to speak, talk", "ক্রিয়া", "N5",
            "日本語で話しましょう。", "nihongo de hanashimashou.", "চলুন জাপানি ভাষায় কথা বলি।", "Let's speak in Japanese."),
        VocabularyItem(13, "読む", "よむ", "yomu", "পড়া", "to read", "ক্রিয়া", "N5",
            "毎日日本語の本を読みます。", "mainichi nihongo no hon o yomimasu.", "প্রতিদিন জাপানি বই পড়ি।", "I read Japanese books every day."),
        VocabularyItem(14, "書く", "かく", "kaku", "লেখা", "to write", "ক্রিয়া", "N5",
            "ひらがなで手紙を書く。", "hiragana de tegami o kaku.", "হিরাগানায় চিঠি লিখি।", "Write a letter in Hiragana."),
        VocabularyItem(15, "買う", "かう", "kau", "কেনা", "to buy", "ক্রিয়া", "N5",
            "スーパーでりんごを買う。", "suupaa de ringo o kau.", "সুপারমার্কেট থেকে আপেল কিনি।", "Buy apples at the supermarket."),
        VocabularyItem(16, "大きい", "おおきい", "ookii", "বড়", "big, large", "বিশেষণ", "N5",
            "この家はとても大きいです。", "kono ie wa totemo ookii desu.", "এই বাড়িটি অনেক বড়।", "This house is very big."),
        VocabularyItem(17, "小さい", "ちいさい", "chiisai", "ছোট", "small, little", "বিশেষণ", "N5",
            "これは小さい猫です。", "kore wa chiisai neko desu.", "এটি একটি ছোট বিড়াল।", "This is a small cat."),
        VocabularyItem(18, "新しい", "あたらしい", "atarashii", "নতুন", "new", "বিশেষণ", "N5",
            "新しい車を買いました。", "atarashii kuruma o kaimashita.", "নতুন গাড়ি কিনেছি।", "I bought a new car."),
        VocabularyItem(19, "古い", "ふるい", "furui", "পুরাতন / পুরোনো", "old", "বিশেষণ", "N5",
            "この本は少し古いです。", "kono hon wa sukoshi furui desu.", "এই বইটি কিছুটা পুরোনো।", "This book is a little old."),
        VocabularyItem(20, "いい", "いい", "ii", "ভালো", "good", "বিশেষণ", "N5",
            "今日は天気がとてもいいですね。", "kyou wa tenki ga totemo ii desu ne.", "আজ আবহাওয়া খুব সুন্দর, তাই না?", "The weather is very good today, isn't it?"),
        VocabularyItem(21, "悪い", "わるい", "warui", "খারাপ", "bad", "বিশেষণ", "N5",
            "気分が少し悪い。", "kibun ga sukoshi warui.", "শরীরটা কিছুটা খারাপ লাগছে।", "I feel a little unwell."),
        VocabularyItem(22, "高い", "たかい", "takai", "উঁচু / দামি", "expensive, high", "বিশেষণ", "N5",
            "富士山はとても高い山です。", "fujisan wa totemo takai yama desu.", "ফুজি পাহাড় অনেক উঁচু একটি পর্বত।", "Mount Fuji is a very high mountain."),
        VocabularyItem(23, "安い", "やすい", "yasui", "সস্তা", "cheap, inexpensive", "বিশেষণ", "N5",
            "この店の果物は安いです。", "kono mise no kudamono wa yasui desu.", "এই দোকানের ফলমূল সস্তা।", "The fruit at this shop is cheap."),
        VocabularyItem(24, "人", "ひと", "hito", "মানুষ / ব্যক্তি", "person", "বিশেষ্য", "N5",
            "あの人はとても親切です。", "ano hito wa totemo shinsetsu desu.", "ওই লোকটি খুব দয়ালু।", "That person is very kind."),
        VocabularyItem(25, "日本", "にほん", "nihon", "জাপান", "Japan", "বিশেষ্য", "N5",
            "私は日本に行きたいです。", "watashi wa nihon ni ikitai desu.", "আমি জাপানে যেতে চাই।", "I want to go to Japan."),
        VocabularyItem(26, "日本語", "にほんご", "nihongo", "জাপানি ভাষা", "Japanese language", "বিশেষ্য", "N5",
            "日本語を楽しく勉強しています。", "nihongo o tanoshiku benkyou shiteimasu.", "আনন্দের সাথে জাপানি ভাষা শিখছি।", "I am enjoying studying Japanese."),
        VocabularyItem(27, "本", "ほん", "hon", "বই", "book", "বিশেষ্য", "N5",
            "机の上に本があります。", "tsukue no ue ni hon ga arimasu.", "টেবিলের উপর বই আছে।", "There is a book on the desk."),
        VocabularyItem(28, "水", "みず", "mizu", "পানি / জল", "water", "বিশেষ্য", "N5",
            "お水を一杯ください。", "omizu o ippai kudasai.", "এক গ্লাস পানি দিন, অনুগ্রহ করে।", "Please give me a glass of water."),
        VocabularyItem(29, "ご飯", "ごはん", "gohan", "ভাত / খাবার", "cooked rice, meal", "বিশেষ্য", "N5",
            "一緒にご飯を食べましょう。", "issho ni gohan o tabemashou.", "চলুন একসাথে খাবার খাই।", "Let's eat a meal together."),
        VocabularyItem(30, "車", "くるま", "kuruma", "গাড়ি", "car, vehicle", "বিশেষ্য", "N5",
            "父の車を借りました。", "chichi no kuruma o karimashita.", "বাবার গাড়ি ধার নিয়েছি।", "I borrowed my father's car."),
        VocabularyItem(31, "家", "いえ", "ie", "বাড়ি / ঘর", "house, home", "বিশেষ্য", "N5",
            "私の家は駅の近くにあります。", "watashi no ie wa eki no chikaku ni arimasu.", "আমার বাড়ি স্টেশনের কাছে।", "My house is near the station."),
        VocabularyItem(32, "友達", "ともだち", "tomodachi", "বন্ধু", "friend", "বিশেষ্য", "N5",
            "友達と公園を散歩しました。", "tomodachi to kouen o sanpo shimashita.", "বন্ধুর সাথে পার্কে হেঁটেছি।", "I took a walk in the park with a friend."),
        VocabularyItem(33, "今日", "きょう", "kyou", "আজ", "today", "সময়", "N5",
            "今日は月曜日です。", "kyou wa getsuyoubi desu.", "আজ সোমবার।", "Today is Monday."),
        VocabularyItem(34, "明日", "あした", "ashita", "আগামীকাল", "tomorrow", "সময়", "N5",
            "明日はテストがあります。", "ashita wa tesuto ga arimasu.", "আগামীকাল পরীক্ষা আছে।", "There is a test tomorrow."),
        VocabularyItem(35, "昨日", "きのう", "kinou", "গতকাল", "yesterday", "সময়", "N5",
            "昨日は雨が降りました。", "kinou wa ame ga furimashita.", "গতকাল বৃষ্টি হয়েছিল।", "It rained yesterday."),
        VocabularyItem(36, "朝", "あさ", "asa", "সকাল", "morning", "সময়", "N5",
            "毎朝７時に起きます。", "maiasa shichiji ni okimasu.", "প্রতিদিন সকাল ৭টায় ঘুম থেকে উঠি।", "I wake up at 7 o'clock every morning."),
        VocabularyItem(37, "夜", "よる", "yoru", "রাত", "night", "সময়", "N5",
            "夜は静かに読書します。", "yoru wa shizuka ni dokusho shimasu.", "রাতে শান্তভাবে বই পড়ি।", "At night, I read books quietly."),
        VocabularyItem(38, "起きる", "おきる", "okiru", "ঘুম থেকে ওঠা", "to wake up", "ক্রিয়া", "N5",
            "明日早く起きる必要があります。", "ashita hayaku okiru hitsuyou ga arimasu.", "কাল সকালে জলদি উঠতে হবে।", "I need to wake up early tomorrow."),
        VocabularyItem(39, "寝る", "ねる", "neru", "ঘুমানো", "to sleep, go to bed", "ক্রিয়া", "N5",
            "夜１１時に寝る。", "yoru juuichiji ni neru.", "রাত ১১টায় ঘুমাই।", "I go to sleep at 11 PM."),
        VocabularyItem(40, "会う", "あう", "au", "দেখা করা / সাক্ষাৎ করা", "to meet", "ক্রিয়া", "N5",
            "午後３時に友達と会う。", "gogo sanji ni tomodachi to au.", "দুপুর ৩টায় বন্ধুর সাথে দেখা করব।", "I meet my friend at 3 PM."),
        VocabularyItem(41, "待つ", "まつ", "matsu", "অপেক্ষা করা", "to wait", "ক্রিয়া", "N5",
            "ここでバスを待つ。", "koko de basu o matsu.", "এখানে বাসের জন্য অপেক্ষা করি।", "Wait for the bus here."),
        VocabularyItem(42, "帰る", "かえる", "kaeru", "ফিরে যাওয়া / ফেরা", "to return, go back", "ক্রিয়া", "N5",
            "夕方うちに帰る。", "yuugata uchi ni kaeru.", "সন্ধ্যায় বাড়ি ফিরি।", "Return home in the evening."),
        VocabularyItem(43, "暑い", "あつい", "atsui", "গরম (আবহাওয়া)", "hot (weather)", "বিশেষণ", "N5",
            "今年の夏は本当に暑いです。", "kotoshi no natsu wa hontou ni atsui desu.", "এই বছরের গ্রীষ্ম আসলেই অনেক গরম।", "This year's summer is really hot."),
        VocabularyItem(44, "寒い", "さむい", "samui", "ঠান্ডা (আবহাওয়া)", "cold (weather)", "বিশেষণ", "N5",
            "冬はとても寒いです。", "fuyu wa totemo samui desu.", "শীতকালে অনেক ঠান্ডা পড়ে।", "Winter is very cold."),
        VocabularyItem(45, "難しい", "むずかしい", "muzukashii", "কঠিন", "difficult", "বিশেষণ", "N5",
            "漢字の書き方は少し難しい。", "kanji no kakikata wa sukoshi muzukashii.", "কাঞ্জি লেখার নিয়ম কিছুটা কঠিন।", "Writing kanji is a little difficult."),
        VocabularyItem(46, "易しい", "やさしい", "yasashii", "সহজ", "easy, gentle", "বিশেষণ", "N5",
            "この質問はとても易しいです。", "kono shitsumon wa totemo yasashii desu.", "এই প্রশ্নটি খুবই সহজ।", "This question is very easy."),
        VocabularyItem(47, "時間", "じかん", "jikan", "সময়", "time, hour", "সময়", "N5",
            "今何時ですか？時間がありますか？", "ima nanji desu ka? jikan ga arimasu ka?", "এখন কয়টা বাজে? আপনার সময় আছে কি?", "What time is it now? Do you have time?"),
        VocabularyItem(48, "お金", "おかね", "okane", "টাকা / অর্থ", "money", "বিশেষ্য", "N5",
            "財布にお金がありません。", "saifu ni okane ga arimasen.", "মানিব্যাগে টাকা নেই।", "There is no money in my wallet."),
        VocabularyItem(49, "好き", "すき", "suki", "পছন্দ / প্রিয়", "like, fond of", "বিশেষণ", "N5",
            "私は日本のアニメが好きです。", "watashi wa nihon no anime ga suki desu.", "আমি জাপানি অ্যানিমে পছন্দ করি।", "I like Japanese anime."),
        VocabularyItem(50, "ありがとう", "ありがとう", "arigatou", "ধন্যবাদ", "thank you", "অভিবাদন", "N5",
            "手伝ってくれて、どうもありがとう。", "tetsudatte kurete, doumo arigatou.", "সাহায্য করার জন্য আপনাকে অনেক ধন্যবাদ।", "Thank you very much for helping me.")
    )

    val categories: List<String> = listOf("সব", "ক্রিয়া", "বিশেষণ", "বিশেষ্য", "সর্বনাম", "সময়", "অভিবাদন")

    fun searchWords(query: String, category: String = "সব"): List<VocabularyItem> {
        return n5Words.filter { item ->
            val matchesCategory = (category == "সব" || item.category == category)
            val matchesQuery = query.isBlank() ||
                    item.japanese.contains(query, ignoreCase = true) ||
                    item.furigana.contains(query, ignoreCase = true) ||
                    item.romaji.contains(query, ignoreCase = true) ||
                    item.bengali.contains(query, ignoreCase = true) ||
                    item.english.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }
}
