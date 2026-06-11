package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.DictionaryRepository
import com.example.data.DictionaryWord
import com.example.data.RecentSearch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class QuizQuestion(
    val word: DictionaryWord,
    val questionText: String,
    val correctAnswer: String,
    val options: List<String>
)

class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("ALL")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Dynamically query database as search or filter changes
    @OptIn(ExperimentalCoroutinesApi::class)
    val wordsList: StateFlow<List<DictionaryWord>> = combine(_searchQuery, _selectedCategory) { query, category ->
        Pair(query, category)
    }.flatMapLatest { (query, category) ->
        if (query.trim().isEmpty()) {
            if (category == "ALL") {
                repository.allWords
            } else {
                repository.getWordsByCategory(category)
            }
        } else {
            repository.searchWords(query.trim())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedWord = MutableStateFlow<DictionaryWord?>(null)
    val selectedWord: StateFlow<DictionaryWord?> = _selectedWord.asStateFlow()

    val bookmarkedWords: StateFlow<List<DictionaryWord>> = repository.bookmarkedWords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val recentSearches: StateFlow<List<RecentSearch>> = repository.recentSearches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _wordOfTheDay = MutableStateFlow<DictionaryWord?>(null)
    val wordOfTheDay: StateFlow<DictionaryWord?> = _wordOfTheDay.asStateFlow()

    // Quiz States
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    private val _isAnswerChecked = MutableStateFlow(false)
    val isAnswerChecked: StateFlow<Boolean> = _isAnswerChecked.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _isQuizComplete = MutableStateFlow(false)
    val isQuizComplete: StateFlow<Boolean> = _isQuizComplete.asStateFlow()

    private val _isQuizInProgress = MutableStateFlow(false)
    val isQuizInProgress: StateFlow<Boolean> = _isQuizInProgress.asStateFlow()

    init {
        loadWordOfTheDay()
    }

    private fun loadWordOfTheDay() {
        viewModelScope.launch {
            _wordOfTheDay.value = repository.getWordOfTheDay()
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun selectWord(word: DictionaryWord?) {
        _selectedWord.value = word
        if (word != null) {
            viewModelScope.launch {
                repository.addRecentSearch(word)
            }
        }
    }

    fun toggleBookmark(word: DictionaryWord) {
        viewModelScope.launch {
            repository.toggleBookmark(word.id, word.isBookmarked)
            // If the selected word is updated, update the detailed state as well
            if (_selectedWord.value?.id == word.id) {
                _selectedWord.value = _selectedWord.value?.copy(isBookmarked = !word.isBookmarked)
            }
            // Update word of the day in place if needed
            if (_wordOfTheDay.value?.id == word.id) {
                _wordOfTheDay.value = _wordOfTheDay.value?.copy(isBookmarked = !word.isBookmarked)
            }
        }
    }

    fun deleteRecentSearch(id: Int) {
        viewModelScope.launch {
            repository.deleteRecentSearch(id)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    suspend fun getWordById(id: Int): DictionaryWord? {
        return repository.getWordById(id)
    }

    // --- QUIZ LOGIC ---
    fun startNewQuiz() {
        viewModelScope.launch {
            val words = repository.getQuizWords(5)
            if (words.size < 4) {
                // Not enough words in the DB to generate multiple choices
                return@launch
            }

            val allWordsList = wordsList.value.ifEmpty { 
                // Fallback: load directly from flow if empty
                val seedList = com.example.data.DatabaseSeeder.getSeedWords()
                seedList
            }

            val questions = words.map { currentWord ->
                // Generate wrong options
                val incorrectOptions = allWordsList
                    .filter { it.id != currentWord.id }
                    .shuffled()
                    .take(3)
                    .map { it.englishTranslate }

                val correctAns = currentWord.englishTranslate
                val combinedOptions = (incorrectOptions + correctAns).shuffled()

                // Randomly choose question wording to make it interesting
                val isPashtoQuestion = Math.random() > 0.5
                val questionText = if (isPashtoQuestion) {
                    "What is the English translation for '${currentWord.pashto}'?"
                } else {
                    "Which word translates to '${currentWord.englishTranslate}'?"
                }

                QuizQuestion(
                    word = currentWord,
                    questionText = questionText,
                    correctAnswer = correctAns,
                    options = combinedOptions
                )
            }

            _quizQuestions.value = questions
            _currentQuestionIndex.value = 0
            _selectedAnswer.value = null
            _isAnswerChecked.value = false
            _quizScore.value = 0
            _isQuizComplete.value = false
            _isQuizInProgress.value = true
        }
    }

    fun selectQuizAnswer(answer: String) {
        if (!_isAnswerChecked.value) {
            _selectedAnswer.value = answer
        }
    }

    fun checkAnswer() {
        if (_selectedAnswer.value == null || _isAnswerChecked.value) return
        
        _isAnswerChecked.value = true
        val currentQuestion = _quizQuestions.value.getOrNull(_currentQuestionIndex.value) ?: return
        
        if (_selectedAnswer.value == currentQuestion.correctAnswer) {
            _quizScore.value += 1
        }
    }

    fun nextQuestion() {
        val nextIdx = _currentQuestionIndex.value + 1
        if (nextIdx < _quizQuestions.value.size) {
            _currentQuestionIndex.value = nextIdx
            _selectedAnswer.value = null
            _isAnswerChecked.value = false
        } else {
            _isQuizComplete.value = true
            _isQuizInProgress.value = false
        }
    }

    fun exitQuiz() {
        _isQuizInProgress.value = false
        _isQuizComplete.value = false
        _quizQuestions.value = emptyList()
    }
}

class DictionaryViewModelFactory(private val repository: DictionaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
