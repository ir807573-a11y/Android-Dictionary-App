package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class PronunciationManager(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (e: Exception) {
            Log.e("PronunciationManager", "Failed to create TextToSpeech instance", e)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("PronunciationManager", "US English is not supported in TTS")
            } else {
                isInitialized = true
            }
        } else {
            Log.e("PronunciationManager", "TextToSpeech init status error code: $status")
        }
    }

    fun speakEnglish(text: String) {
        if (isInitialized && tts != null) {
            tts?.setLanguage(Locale.US)
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "EnglishSpeech")
        }
    }

    fun speakPashto(pashtoWord: String, phoneticEnglish: String) {
        if (isInitialized && tts != null) {
            val psLocale = Locale("ps")
            val available = tts?.setLanguage(psLocale)
            if (available == TextToSpeech.LANG_MISSING_DATA || available == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Fallback to reading phonetic syllables using English engine
                tts?.setLanguage(Locale.US)
                val friendlySyllables = phoneticEnglish
                    .replace("-", " ")
                    .replace("š", "sh")
                    .replace("Š", "Sh")
                    .replace("x̌", "kh")
                    .replace("X̌", "Kh")
                    .replace("č", "ch")
                    .replace("Č", "Ch")
                    .replace("ə", "uh")
                tts?.speak(friendlySyllables, TextToSpeech.QUEUE_FLUSH, null, "PashtoPhonetic")
            } else {
                // Device supports Pashto TTS speak directly
                tts?.speak(pashtoWord, TextToSpeech.QUEUE_FLUSH, null, "PashtoSpeech")
            }
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
