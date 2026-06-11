package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.audio.PronunciationManager
import com.example.data.AppDatabase
import com.example.data.DictionaryRepository
import com.example.data.DictionaryWord
import com.example.ui.DictionaryAppScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.DictionaryViewModel
import com.example.viewmodel.DictionaryViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var pronunciationManager: PronunciationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Speech Pronunciation engine
        pronunciationManager = PronunciationManager(this)

        // Set up database and repository
        val database = AppDatabase.getDatabase(this, lifecycleScope)
        val repository = DictionaryRepository(database.dictionaryDao())
        
        // Retrieve viewmodel with factory
        val viewModel: DictionaryViewModel by viewModels {
            DictionaryViewModelFactory(repository)
        }

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DictionaryAppScreen(
                    viewModel = viewModel,
                    onSpeakEnglish = { englishText ->
                        pronunciationManager.speakEnglish(englishText)
                    },
                    onSpeakPashto = { pashtoTerm, phonetic ->
                        pronunciationManager.speakPashto(pashtoTerm, phonetic)
                    },
                    onShareTranslation = { word ->
                        shareTranslation(word)
                    }
                )
            }
        }
    }

    private fun shareTranslation(word: DictionaryWord) {
        val shareBody = """
            Pashto: ${word.pashto} (${word.phoneticPashto})
            English: ${word.englishTranslate}
            Grammar: ${word.category}
            Definition: ${word.definition}
            
            Example (Pashto): ${word.examplePashto}
            Example (English): ${word.exampleEnglish}
            
            Shared via offline Pashto-English Dictionary.
        """.trimIndent()

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_SUBJECT, "Pashto Translation: ${word.englishTranslate}")
            putExtra(Intent.EXTRA_TEXT, shareBody)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, "Share translation via:")
        startActivity(shareIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        pronunciationManager.shutdown()
    }
}
