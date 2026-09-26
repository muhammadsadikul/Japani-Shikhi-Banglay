# জাপানি শিখি বাংলায় (Japani Shikhi Banglay)

বাঙালি শিক্ষার্থীদের জন্য বাংলায় সহজে জাপানি ভাষা (JLPT N5) শেখার আধুনিক অ্যান্ড্রয়েড অ্যাপ্লিকেশন।

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20(M3)-blue.svg)](https://developer.android.com/jetpack/compose)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com)

---

## 🌟 প্রধান ফিচারসমূহ (Key Features)

- 🌸 **অ্যানিমেটেড স্প্ল্যাশ স্ক্রিন:** ঝরে পড়া সাকুরা পাপড়ি ও 日本বাংলা লোগো অ্যানিমেশন।
- 🐼 **পান্ডা ম্যাসকট:** মাথায় সাকুরা ফুল পরা কিউট পান্ডা গাইড।
- 📖 **N5 শব্দভাণ্ডার (Vocabulary):** ৫০টি আসল N5 শব্দ, ফুরিগানা, রোমাজি, বাংলা অর্থ, ইংরেজি অর্থ ও নেটিভ জাপানি অডিও (TTS)।
- 🎴 **৩ডি ফ্ল্যাশকার্ড (Flashcards):** মসৃণ ১৮০° ঘূর্ণন অ্যানিমেশন, সোয়াইপ জেসচার ও খাঁটি বাংলা কাউন্টার (**১ / ৫০**)।
- 📝 **কুইজ পরীক্ষা (Quiz - ৫টি মোড):** বহুনির্বাচনী, টাইপিং, জোড়া মেলানো, শ্রবণ পরীক্ষা (Listening) ও বাক্য গঠন।
- ✍️ **কাঞ্জি অনুশীলন (Kanji):** ১০টি N5 কাঞ্জি, অন-ইয়োমি, কুন-ইয়োমি, স্ট্রোক অর্ডার এবং আঙুল দিয়ে লেখার ড্রয়িং ক্যানভাস।
- 📚 **ব্যাকরণ (Grammar):** ৫টি মৌলিক বাক্যের নিয়ম ও কণার (Particles) বিস্তারিত বাংলা বিশ্লেষণ।
- ⚙️ **সেটিংস ও অফলাইন:** ডার্ক মোড, ইংরেজি অর্থ হাইড/শো অপশন, স্মার্ট নোটিফিকেশন ও অফলাইন প্যাক।

---

## 🛠️ বিল্ড ও রান করার নিয়ম (How to Build)

### প্রয়োজনীয় সফটওয়্যার (Prerequisites):
- **Android Studio** (Ladybug / Iguana বা এর পরবর্তী সংস্করণ)
- **JDK 17 বা 21**
- **Android SDK API 24+** (Recommended Target API: 36)

### ধাপসমূহ (Steps):

1. **রিপোজিটরি ক্লোন করুন:**
   ```bash
   git clone https://github.com/<your-username>/japani-shikhi-banglay.git
   cd japani-shikhi-banglay
   ```

2. **Android Studio-তে ওপেন করুন:**
   - Android Studio চালু করে `Open` চাপুন এবং এই ফোল্ডারটি নির্বাচন করুন।
   - Gradle Sync সম্পূর্ণ হওয়া পর্যন্ত অপেক্ষা করুন।

3. **টার্মিনাল থেকে বিল্ড করতে:**
   ```bash
   # ডিবাগ APK তৈরি করতে
   ./gradlew assembleDebug

   # ইউনিট টেস্ট চালাতে
   ./gradlew testDebugUnitTest
   ```
   *বিল্ড শেষে APK ফাইলটি পাবেন: `app/build/outputs/apk/debug/app-debug.apk`*

---

## 🚀 GitHub-এ পুশ করার নির্দেশিকা (How to Push to GitHub)

নতুন একটি GitHub রিপোজিটরিতে কোড আপলোড করতে আপনার টার্মিনালে নিচের কমান্ডগুলো চালান:

```bash
# ১. গিট ইনিশিয়ালাইজ করুন
git init

# ২. ফাইলগুলো স্টেজিংয়ে যোগ করুন
git add .

# ৩. কমিট করুন
git commit -m "feat: initial commit of Japani Shikhi Banglay v2.0"

# ৪. মেইন ব্রাঞ্চ নির্বাচন করুন
git branch -M main

# ৫. আপনার রিমোট রিপোজিটরির লিংক যুক্ত করুন
git remote add origin https://github.com/<YOUR_USERNAME>/<YOUR_REPOSITORY>.git

# ৬. কোড পুশ করুন
git push -u origin main
```

---

## 📁 প্রজেক্ট আর্কিটেকচার (Project Structure)

```text
├── app/
│   ├── src/main/
│   │   ├── java/com/example/
│   │   │   ├── MainActivity.kt               # প্রধান নেভিগেশন ও অ্যাপ হোস্ট
│   │   │   ├── data/
│   │   │   │   ├── VocabularyRepository.kt   # ৫০টি N5 শব্দভাণ্ডার ডেটা
│   │   │   │   ├── KanjiRepository.kt        # ১০টি N5 কাঞ্জি ডেটা
│   │   │   │   ├── GrammarRepository.kt      # ৫টি N5 গ্রামার প্যাটার্ন
│   │   │   │   └── SettingsManager.kt        # ইউজার প্রিফারেন্সেস (ডার্ক মোড ইত্যাদি)
│   │   │   ├── ui/
│   │   │   │   ├── screens/                  # Splash, Home, Vocab, Flashcard, Quiz, Kanji, Grammar, Settings
│   │   │   │   ├── components/               # BottomNav, PandaMascot, ReportErrorDialog
│   │   │   │   └── theme/                    # Material 3 থিম ও কালার প্যালেট
│   │   │   └── util/                         # BengaliUtils, TtsManager
│   │   ├── res/                              # ড্রয়েবল, আইকন ও লেআউট রিসোর্স
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts                      # অ্যাপ মডিউল ডিপেন্ডেন্সি ও কনফিগারেশন
├── data/n5-vocabulary.json                   # কাঁচা JSON শব্দভাণ্ডার
├── .gitignore                                # Android Studio স্ট্যান্ডার্ড গিট-ইগনোর
└── README.md                                 # প্রজেক্ট ডকুমেন্টেশন
```

---

## 📄 লাইসেন্স (License)

This project is licensed under the MIT License.
