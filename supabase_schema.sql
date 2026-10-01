-- Supabase Schema for Japani Shikhi Banglay (জাপানি শিখি বাংলায়)
-- Project URL: https://mdleilnpottaaengffnf.supabase.co
-- Table: user_progress

-- 1. Create table user_progress
CREATE TABLE IF NOT EXISTS public.user_progress (
    user_id UUID PRIMARY KEY,
    total_words INTEGER NOT NULL DEFAULT 50,
    total_kanji INTEGER NOT NULL DEFAULT 10,
    streak_days INTEGER NOT NULL DEFAULT 7,
    n5_progress INTEGER NOT NULL DEFAULT 82,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
);

-- 2. Enable Row Level Security (RLS)
ALTER TABLE public.user_progress ENABLE ROW LEVEL SECURITY;

-- 3. Create policies for read and write
DROP POLICY IF EXISTS "Allow users to read own progress" ON public.user_progress;
CREATE POLICY "Allow users to read own progress"
    ON public.user_progress FOR SELECT
    USING (true);

DROP POLICY IF EXISTS "Allow users to insert own progress" ON public.user_progress;
CREATE POLICY "Allow users to insert own progress"
    ON public.user_progress FOR INSERT
    WITH CHECK (true);

DROP POLICY IF EXISTS "Allow users to update own progress" ON public.user_progress;
CREATE POLICY "Allow users to update own progress"
    ON public.user_progress FOR UPDATE
    USING (true);

-- 4. Sample record for testing (Optional)
-- INSERT INTO public.user_progress (user_id, total_words, total_kanji, streak_days, n5_progress)
-- VALUES ('d3b07384-d113-4f0e-b816-608678000000', 50, 10, 7, 82)
-- ON CONFLICT (user_id) DO NOTHING;
