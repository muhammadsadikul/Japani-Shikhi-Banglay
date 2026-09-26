# Japanese for Bangla (日本বাংলা / জাপানি শিখি বাংলায়)

A mobile application designed to help Bengali speakers learn Japanese effortlessly from JLPT N5 through N1, with interactive vocabulary cards, Japanese Kanji/Furigana/Romaji, Bengali and English translations, and 3D animated flashcards.

## 📱 Features

- **হোম (Home Screen):**
  - Title: **"জাপানি শিখি বাংলায়"**
  - Subtitle: **"N5 থেকে N1 — বাংলায় সহজে জাপানি শিখুন"**
  - JLPT Level Selection: N5 (Active & complete), N4–N1 with "শীঘ্রই আসছে" (Coming Soon) status.
  - Quick action buttons to jump straight to Vocabulary or Flashcard practice.

- **শব্দভাণ্ডার (N5 Vocabulary Screen):**
  - 50 authentic JLPT N5 Japanese vocabulary words.
  - Full details for each card:
    - Japanese Kanji/Kana (Large)
    - Furigana (reading in Hiragana)
    - Romaji (Hepburn)
    - Bengali Meaning (Bold)
    - English Meaning (Subtle gray)
    - Category tag (সর্বনাম, বিশেষ্য, ক্রিয়া, বিশেষণ, সময়, অভিবাদন)
    - Native Audio pronunciation using Text-to-Speech!
  - Real-time search by Bengali or Japanese/Romaji.
  - Category filter chips (সব, ক্রিয়া, বিশেষণ, বিশেষ্য, সময়).

- **ফ্ল্যাশকার্ড (N5 Flashcard Screen):**
  - Interactive 3D flip card with realistic Y-axis perspective rotation.
  - Front: Large Japanese word with tap-to-flip indicator.
  - Back: Furigana, Romaji, Bengali meaning, English meaning, and audio pronunciation.
  - Bengali Counter: **"১ / ৫০"** to **"৫০ / ৫০"** with progress bar.
  - Horizontal swipe gestures and Previous/Next buttons.
  - Shuffle / Randomize mode.
  - Mastered toggle (মুখস্থ হয়েছে).

- **Native Android + Capacitor Ready:**
  - Native Jetpack Compose implementation with Material 3 styling (#F8FAFC soft background, #E11D48 Japan crimson accent, 16dp rounded cards).
  - Includes `/data/n5-vocabulary.json` and `capacitor.config.json`.

## 🛠️ Build & Run Instructions

### Native Android (AI Studio & Android Studio)
1. In Android Studio, open the project root directory.
2. Ensure Android SDK 34+ is installed.
3. Build the project using Gradle:
   ```bash
   ./gradlew assembleDebug
   ```
4. Run on an Android device or emulator (API 24+).

### Capacitor / Hybrid Web Deployment
1. If compiling for Capacitor:
   ```bash
   npm install @capacitor/core @capacitor/cli @capacitor/android
   npx cap sync android
   npx cap open android
   ```
2. The `capacitor.config.json` is pre-configured with `appId: com.aistudio.japanesebangla.jpbd`.

## 📁 Project Structure
- `/data/n5-vocabulary.json` - Complete JLPT N5 vocabulary data with Bengali translations
- `/app/src/main/java/com/example/`
  - `MainActivity.kt` - Main navigation and scaffold
  - `data/model/VocabularyItem.kt` - Data model
  - `data/VocabularyRepository.kt` - 50 N5 words repository
  - `ui/theme/Color.kt` & `Theme.kt` - Japan crimson red & soft slate theme
  - `ui/screens/HomeScreen.kt` - Home level dashboard
  - `ui/screens/VocabularyScreen.kt` - Searchable vocabulary list
  - `ui/screens/FlashcardScreen.kt` - 3D flipping flashcards with Bengali counter
  - `util/BengaliUtils.kt` - Number to Bengali digits converter
  - `util/TtsManager.kt` - Japanese speech engine
- `/capacitor.config.json` - Capacitor configuration
