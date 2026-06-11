package com.example.data

import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class DictionaryRepository(private val dictionaryDao: DictionaryDao) {

    val allWords: Flow<List<DictionaryWord>> = dictionaryDao.getAllWords()
    val bookmarkedWords: Flow<List<DictionaryWord>> = dictionaryDao.getBookmarkedWords()
    val recentSearches: Flow<List<RecentSearch>> = dictionaryDao.getRecentSearches()

    fun searchWords(term: String): Flow<List<DictionaryWord>> {
        val wildcardQuery = "%$term%"
        val exactQuery = term
        return dictionaryDao.searchWords(wildcardQuery, exactQuery)
    }

    fun getWordsByCategory(category: String): Flow<List<DictionaryWord>> {
        return dictionaryDao.getWordsByCategory(category)
    }

    suspend fun toggleBookmark(wordId: Int, currentStatus: Boolean) {
        dictionaryDao.setBookmarkStatus(wordId, !currentStatus)
    }

    suspend fun getWordById(id: Int): DictionaryWord? {
        return dictionaryDao.getWordById(id)
    }

    suspend fun getWordOfTheDay(): DictionaryWord? {
        val count = dictionaryDao.getWordCount()
        if (count == 0) return null
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)
        // Combine day and year so it changes every day
        val deterministicOffset = (dayOfYear + year) % count
        return dictionaryDao.getWordAtOffset(deterministicOffset)
    }

    suspend fun getQuizWords(limit: Int = 5): List<DictionaryWord> {
        return dictionaryDao.getRandomWords(limit)
    }

    suspend fun addRecentSearch(word: DictionaryWord) {
        val search = RecentSearch(
            wordId = word.id,
            queryPashto = word.pashto,
            queryEnglish = word.englishTranslate
        )
        dictionaryDao.insertRecentSearch(search)
    }

    suspend fun deleteRecentSearch(id: Int) {
        dictionaryDao.deleteRecentSearch(id)
    }

    suspend fun clearHistory() {
        dictionaryDao.clearRecentSearchHistory()
    }
}
