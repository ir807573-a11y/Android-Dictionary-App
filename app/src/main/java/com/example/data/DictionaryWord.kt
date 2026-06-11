package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_words")
data class DictionaryWord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pashto: String,
    val englishTranslate: String,
    val phoneticPashto: String,
    val phoneticEnglish: String,
    val definition: String,
    val category: String, // VERB, NOUN, ADJECTIVE, PHRASE
    val examplePashto: String,
    val exampleEnglish: String,
    val verbConjugation: String? = null,
    val culturalNotes: String? = null,
    val isBookmarked: Boolean = false
)
