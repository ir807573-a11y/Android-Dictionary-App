package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_words ORDER BY pashto ASC")
    fun getAllWords(): Flow<List<DictionaryWord>>

    @Query("""
        SELECT * FROM dictionary_words 
        WHERE pashto LIKE :query 
           OR englishTranslate LIKE :query 
           OR phoneticPashto LIKE :query 
           OR phoneticEnglish LIKE :query 
           OR definition LIKE :query 
        ORDER BY 
           CASE 
              WHEN pashto LIKE :exactQuery THEN 1
              WHEN englishTranslate LIKE :exactQuery THEN 2
              ELSE 3 
           END, pashto ASC
    """)
    fun searchWords(query: String, exactQuery: String): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE category = :category ORDER BY pashto ASC")
    fun getWordsByCategory(category: String): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE isBookmarked = 1 ORDER BY pashto ASC")
    fun getBookmarkedWords(): Flow<List<DictionaryWord>>

    @Query("SELECT * FROM dictionary_words WHERE id = :id LIMIT 1")
    suspend fun getWordById(id: Int): DictionaryWord?

    @Query("SELECT COUNT(*) FROM dictionary_words")
    suspend fun getWordCount(): Int

    @Query("SELECT * FROM dictionary_words ORDER BY id LIMIT 1 OFFSET :offset")
    suspend fun getWordAtOffset(offset: Int): DictionaryWord?

    @Query("SELECT * FROM dictionary_words ORDER BY RANDOM() LIMIT :limit")
    suspend fun getRandomWords(limit: Int): List<DictionaryWord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<DictionaryWord>)

    @Update
    suspend fun updateWord(word: DictionaryWord)

    @Query("UPDATE dictionary_words SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmarkStatus(id: Int, isBookmarked: Boolean)

    // Recent Search History
    @Query("SELECT * FROM recent_searches ORDER BY timestamp DESC LIMIT 20")
    fun getRecentSearches(): Flow<List<RecentSearch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(search: RecentSearch)

    @Query("DELETE FROM recent_searches WHERE id = :id")
    suspend fun deleteRecentSearch(id: Int)

    @Query("DELETE FROM recent_searches")
    suspend fun clearRecentSearchHistory()
}
