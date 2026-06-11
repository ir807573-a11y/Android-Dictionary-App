package com.example.ui

import kotlinx.coroutines.launch
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DictionaryWord
import com.example.data.RecentSearch
import com.example.viewmodel.DictionaryViewModel
import com.example.viewmodel.QuizQuestion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryAppScreen(
    viewModel: DictionaryViewModel,
    onSpeakEnglish: (String) -> Unit,
    onSpeakPashto: (String, String) -> Unit,
    onShareTranslation: (DictionaryWord) -> Unit
) {
    var activeTab by remember { mutableStateOf("search") }
    val scope = rememberCoroutineScope()
    
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val wordsList by viewModel.wordsList.collectAsState()
    val bookmarkedWords by viewModel.bookmarkedWords.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()
    val wordOfTheDay by viewModel.wordOfTheDay.collectAsState()
    val selectedWord by viewModel.selectedWord.collectAsState()
    
    // Quiz states
    val isQuizInProgress by viewModel.isQuizInProgress.collectAsState()
    val quizQuestions by viewModel.quizQuestions.collectAsState()
    val currentQuestionIdx by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val isAnswerChecked by viewModel.isAnswerChecked.collectAsState()
    val quizScore by viewModel.quizScore.collectAsState()
    val isQuizComplete by viewModel.isQuizComplete.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = "App Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pashto Dictionary",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars
            ) {
                NavigationBarItem(
                    selected = activeTab == "search",
                    onClick = { activeTab = "search"; viewModel.selectWord(null) },
                    icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Tab") },
                    label = { Text("Search", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("nav_search")
                )
                NavigationBarItem(
                    selected = activeTab == "bookmarks",
                    onClick = { activeTab = "bookmarks"; viewModel.selectWord(null) },
                    icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Bookmarks Tab") },
                    label = { Text("Bookmarks", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("nav_bookmarks")
                )
                NavigationBarItem(
                    selected = activeTab == "quiz",
                    onClick = { activeTab = "quiz"; viewModel.selectWord(null) },
                    icon = { Icon(imageVector = Icons.Default.Quiz, contentDescription = "Quiz Tab") },
                    label = { Text("Daily Quiz", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("nav_quiz")
                )
                NavigationBarItem(
                    selected = activeTab == "history",
                    onClick = { activeTab = "history"; viewModel.selectWord(null) },
                    icon = { Icon(imageVector = Icons.Default.History, contentDescription = "History Tab") },
                    label = { Text("History", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("nav_history")
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Content based on Tab
            when (activeTab) {
                "search" -> SearchTabContent(
                    searchQuery = searchQuery,
                    selectedCat = selectedCategory,
                    wordsList = wordsList,
                    wordOfTheDay = wordOfTheDay,
                    onSearchQueryChange = { viewModel.updateSearchQuery(it) },
                    onCategorySelect = { viewModel.selectCategory(it) },
                    onWordClick = { viewModel.selectWord(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
                "bookmarks" -> BookmarksTabContent(
                    bookmarkedWords = bookmarkedWords,
                    onWordClick = { viewModel.selectWord(it) },
                    onBookmarkToggle = { viewModel.toggleBookmark(it) }
                )
                "quiz" -> QuizTabContent(
                    isQuizInProgress = isQuizInProgress,
                    quizQuestions = quizQuestions,
                    currentQuestionIdx = currentQuestionIdx,
                    selectedAnswer = selectedAnswer,
                    isAnswerChecked = isAnswerChecked,
                    quizScore = quizScore,
                    isQuizComplete = isQuizComplete,
                    onStartQuiz = { viewModel.startNewQuiz() },
                    onSelectAnswer = { viewModel.selectQuizAnswer(it) },
                    onCheckAnswer = { 
                        viewModel.checkAnswer()
                        val currentQ = quizQuestions.getOrNull(currentQuestionIdx)
                        if (currentQ != null) {
                            // Automatically pronounce word on checked!
                            onSpeakPashto(currentQ.word.pashto, currentQ.word.phoneticEnglish)
                        }
                    },
                    onNextQuestion = { viewModel.nextQuestion() },
                    onExitQuiz = { viewModel.exitQuiz() },
                    onSpeakPashto = onSpeakPashto
                )
                "history" -> HistoryTabContent(
                    recentSearches = recentSearches,
                    onWordIdClick = { id ->
                        // Load word and open details
                        scope.launch {
                            val word = viewModel.getWordById(id)
                            if (word != null) {
                                viewModel.selectWord(word)
                            }
                        }
                    },
                    onDeleteSearch = { viewModel.deleteRecentSearch(it) },
                    onClearAll = { viewModel.clearSearchHistory() }
                )
            }

            // Word Details Overlay Sheet
            AnimatedVisibility(
                visible = selectedWord != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                selectedWord?.let { word ->
                    WordDetailOverlay(
                        word = word,
                        onClose = { viewModel.selectWord(null) },
                        onBookmarkToggle = { viewModel.toggleBookmark(word) },
                        onSpeakEnglish = onSpeakEnglish,
                        onSpeakPashto = onSpeakPashto,
                        onShare = onShareTranslation
                    )
                }
            }
        }
    }
}

// ==========================================
// SEARCH TAB
// ==========================================
@Composable
fun SearchTabContent(
    searchQuery: String,
    selectedCat: String,
    wordsList: List<DictionaryWord>,
    wordOfTheDay: DictionaryWord?,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onWordClick: (DictionaryWord) -> Unit,
    onBookmarkToggle: (DictionaryWord) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search Input Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search Pashto or English...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .testTag("search_bar_field")
        )

        // Suggestion Chips / Category badgeline
        val categories = listOf(
            "ALL" to "All",
            "NOUN" to "Nouns",
            "VERB" to "Verbs",
            "ADJECTIVE" to "Adjectives",
            "PHRASE" to "Phrases"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { (catKey, catLabel) ->
                val isSelected = selectedCat == catKey
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategorySelect(catKey) },
                    label = { Text(catLabel, fontWeight = FontWeight.Medium) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.surface,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = MaterialTheme.colorScheme.surfaceVariant,
                        enabled = true,
                        selected = isSelected
                    ),
                    modifier = Modifier.testTag("category_chip_$catKey")
                )
            }
        }

        // List Scroll Container
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Word of the Day Banner (Only show if search is vacant and category is ALL)
            if (searchQuery.isEmpty() && selectedCat == "ALL" && wordOfTheDay != null) {
                item {
                    WordOfTheDayCard(word = wordOfTheDay, onClick = { onWordClick(wordOfTheDay) })
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Browse Dictionary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 2.dp, bottom = 4.dp)
                    )
                }
            }

            if (wordsList.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.ContentPasteSearch,
                                contentDescription = "Empty list",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "No matches found",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Try typing Pashto script or other English synonyms",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                modifier = Modifier.padding(top = 4.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                items(wordsList, key = { it.id }) { word ->
                    WordItemCard(
                        word = word,
                        onClick = { onWordClick(word) },
                        onBookmarkToggle = { onBookmarkToggle(word) }
                    )
                }
            }
        }
    }
}

@Composable
fun WordOfTheDayCard(word: DictionaryWord, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("word_of_the_day_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Word of the Day",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "WORD OF THE DAY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.2.sp
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = word.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Pashto word in giant RTL block
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = word.pashto,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = 38.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = word.englishTranslate,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "/ ${word.phoneticPashto} /",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = word.definition,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    lineHeight = 20.sp
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Tap to learn details",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Go",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WordItemCard(
    word: DictionaryWord,
    onClick: () -> Unit,
    onBookmarkToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("word_card_${word.id}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = when (word.category) {
                                "VERB" -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                                "NOUN" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                "ADJECTIVE" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                                else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            }
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = word.category.lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (word.category) {
                                "VERB" -> MaterialTheme.colorScheme.tertiary
                                "NOUN" -> MaterialTheme.colorScheme.primary
                                "ADJECTIVE" -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "/ ${word.phoneticPashto} /",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = word.englishTranslate,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = word.definition,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Pashto script rendered prominent in RTL aligned on screen edge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Text(
                            text = word.pashto,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.defaultMinSize(minWidth = 60.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.testTag("bookmark_btn_${word.id}")
                ) {
                    Icon(
                        imageVector = if (word.isBookmarked) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Bookmark button",
                        tint = if (word.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

// ==========================================
// BOOKMARKS TAB
// ==========================================
@Composable
fun BookmarksTabContent(
    bookmarkedWords: List<DictionaryWord>,
    onWordClick: (DictionaryWord) -> Unit,
    onBookmarkToggle: (DictionaryWord) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Your Bookmarks",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp, start = 2.dp)
        )
        Text(
            text = "Keep track of your favorite vocabulary in offline mode",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp, start = 2.dp)
        )

        if (bookmarkedWords.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "No bookmarked entries",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        modifier = Modifier.size(72.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Your library is empty",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Tap the star star icon next to any Pashto word during your studies to list it here for quick offline access.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(bookmarkedWords, key = { it.id }) { word ->
                    WordItemCard(
                        word = word,
                        onClick = { onWordClick(word) },
                        onBookmarkToggle = { onBookmarkToggle(word) }
                    )
                }
            }
        }
    }
}

// ==========================================
// HISTORY TAB
// ==========================================
@Composable
fun HistoryTabContent(
    recentSearches: List<RecentSearch>,
    onWordIdClick: (Int) -> Unit,
    onDeleteSearch: (Int) -> Unit,
    onClearAll: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 4.dp, start = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Searches",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary
            )
            if (recentSearches.isNotEmpty()) {
                TextButton(
                    onClick = onClearAll,
                    modifier = Modifier.testTag("clear_history_btn")
                ) {
                    Text("Clear All", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
        Text(
            text = "Tap on any matching item to view translations instantly",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp, start = 2.dp)
        )

        if (recentSearches.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History empty",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "No search history",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "Words you view will appear here.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(recentSearches, key = { it.id }) { search ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onWordIdClick(search.wordId) }
                            .testTag("search_history_item_${search.id}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = "Recent term",
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = search.queryEnglish,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "•",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                    Text(
                                        text = search.queryPashto,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            IconButton(
                                onClick = { onDeleteSearch(search.id) },
                                modifier = Modifier.size(36.dp).testTag("delete_recent_btn_${search.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove history item",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// DAILY QUIZ TAB
// ==========================================
@Composable
fun QuizTabContent(
    isQuizInProgress: Boolean,
    quizQuestions: List<QuizQuestion>,
    currentQuestionIdx: Int,
    selectedAnswer: String?,
    isAnswerChecked: Boolean,
    quizScore: Int,
    isQuizComplete: Boolean,
    onStartQuiz: () -> Unit,
    onSelectAnswer: (String) -> Unit,
    onCheckAnswer: () -> Unit,
    onNextQuestion: () -> Unit,
    onExitQuiz: () -> Unit,
    onSpeakPashto: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isQuizInProgress && !isQuizComplete) {
            // Intro screen
            Spacer(modifier = Modifier.height(24.dp))
            Icon(
                imageVector = Icons.Default.Quiz,
                contentDescription = "Quiz Logo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pashto Mastery Quiz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Build Fluency Through Challenges",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        "How it works:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    
                    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("•  ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text("Generates 5 random words dynamically from offline database.", fontSize = 13.sp)
                    }
                    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("•  ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text("Prompts with either a Pashto script word or English card translation.", fontSize = 13.sp)
                    }
                    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("•  ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text("Gives immediate visual validation with speaker pronunciation playbacks.", fontSize = 13.sp)
                    }
                    Row(verticalAlignment = Alignment.Top) {
                        Text("•  ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Text("Track your score and test daily to secure terms in long-term memory.", fontSize = 13.sp)
                    }
                }
            }

            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth(0.81f)
                    .height(50.dp)
                    .testTag("start_quiz_btn"),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Play")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start 5-Word Quiz", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        } else if (isQuizInProgress) {
            val currentQuestion = quizQuestions.getOrNull(currentQuestionIdx)
            if (currentQuestion != null) {
                // Quiz session header
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onExitQuiz,
                        modifier = Modifier.testTag("exit_quiz_btn")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Exit Quiz")
                    }
                    Text(
                        text = "Question ${currentQuestionIdx + 1} of ${quizQuestions.size}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    // Mini Score view
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Score: $quizScore",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                // Progress Bar
                LinearProgressIndicator(
                    progress = { (currentQuestionIdx + 1).toFloat() / quizQuestions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                // Question Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Identify the Translation",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = currentQuestion.questionText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp
                        )
                        
                        // Pronounce button for word being quizzed
                        IconButton(
                            onClick = { onSpeakPashto(currentQuestion.word.pashto, currentQuestion.word.phoneticEnglish) },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Hear Pashto word", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Options list
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    currentQuestion.options.forEachIndexed { idx, option ->
                        val isSelected = selectedAnswer == option
                        
                        // Determine background color post check
                        val containerColor = when {
                            isAnswerChecked && option == currentQuestion.correctAnswer -> Color(0xFF2E7D32) // Emerald Green
                            isAnswerChecked && isSelected && option != currentQuestion.correctAnswer -> Color(0xFFC62828) // Deep Rust/Red
                            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surface
                        }

                        val contentColor = when {
                            isAnswerChecked && (option == currentQuestion.correctAnswer || (isSelected && option != currentQuestion.correctAnswer)) -> Color.White
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        val borderAlpha = if (isSelected || (isAnswerChecked && option == currentQuestion.correctAnswer)) 1f else 0.4f
                        val borderColor = when {
                            isAnswerChecked && option == currentQuestion.correctAnswer -> Color(0xFF2E7D32)
                            isAnswerChecked && isSelected -> Color(0xFFC62828)
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        }

                        Surface(
                            onClick = { onSelectAnswer(option) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("quiz_option_${idx}"),
                            shape = RoundedCornerShape(12.dp),
                            color = containerColor,
                            border = BorderStroke(1.5.dp, borderColor.copy(alpha = borderAlpha)),
                            tonalElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected || isAnswerChecked) contentColor.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = ('A' + idx).toString(),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = contentColor
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = option,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = contentColor,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Action controls
                if (!isAnswerChecked) {
                    Button(
                        onClick = onCheckAnswer,
                        enabled = selectedAnswer != null,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(48.dp)
                            .testTag("check_answer_btn"),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text("Check Answer", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = onNextQuestion,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(48.dp)
                            .testTag("next_question_btn"),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAnswer == currentQuestion.correctAnswer) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = if (currentQuestionIdx + 1 == quizQuestions.size) "Finish Quiz" else "Next Question",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        } else if (isQuizComplete) {
            // Summary Screen
            Spacer(modifier = Modifier.height(32.dp))
            Icon(
                imageVector = if (quizScore >= 4) Icons.Default.EmojiEvents else Icons.Default.ImportContacts,
                contentDescription = "Quiz Completed",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(96.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quiz Completed!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            // Score Display
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 36.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("YOUR SCORE", fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$quizScore / 5",
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (quizScore) {
                            5 -> "Mashallah! Perfect Score!"
                            4 -> "Excellent job! Almost perfect."
                            3 -> "Good work! Keep practice."
                            else -> "Keep studying! Try again."
                        },
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth(0.81f)
                    .height(48.dp)
                    .testTag("restart_quiz_btn"),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.Autorenew, contentDescription = "Retry")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Another Quiz", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onExitQuiz,
                modifier = Modifier
                    .fillMaxWidth(0.81f)
                    .height(48.dp)
                    .testTag("quit_quiz_btn"),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Exit to Menu", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// DETAILED OVERLAY SHEET (BOTTOM DRAW SLIDE)
// ==========================================
@Composable
fun WordDetailOverlay(
    word: DictionaryWord,
    onClose: () -> Unit,
    onBookmarkToggle: () -> Unit,
    onSpeakEnglish: (String) -> Unit,
    onSpeakPashto: (String, String) -> Unit,
    onShare: (DictionaryWord) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("word_detail_overlay"),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Details Header Nav Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose, modifier = Modifier.testTag("detail_close_btn")) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close detailed translation",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Row {
                    IconButton(onClick = { onShare(word) }, modifier = Modifier.testTag("detail_share_btn")) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share entry",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onBookmarkToggle, modifier = Modifier.testTag("detail_bookmark_btn")) {
                        Icon(
                            imageVector = if (word.isBookmarked) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Bookmark detailed word",
                            tint = if (word.isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Word Core Translation Slate Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = word.category.uppercase(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                            letterSpacing = 1.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Prominent Pashto layout WITH layout direction constraints
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Text(
                            text = word.pashto,
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            lineHeight = 52.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = word.englishTranslate,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Phonetic: / ${word.phoneticPashto} /",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Audio Pronunciations row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {
                        Button(
                            onClick = { onSpeakPashto(word.pashto, word.phoneticEnglish) },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Hear Pashto")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Speak Pashto", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = { onSpeakEnglish(word.englishTranslate) },
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Hear English")
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Speak English", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            // Definitions Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Definition",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = word.definition,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            // Separator traditional rug asset motif
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.fillMaxWidth())
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    modifier = Modifier.clip(RoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = " ❖ ❖ ❖ ",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }

            // Example Sentence section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Context Example",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                            Text("BILINGUAL SENTENCE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            IconButton(
                                onClick = { onSpeakPashto(word.examplePashto, word.phoneticEnglish) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(Icons.Default.VolumeUp, contentDescription = "Hear sentence speech", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))

                        // Pashto sentence with RTL constraint
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Text(
                                text = word.examplePashto,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.fillMaxWidth(),
                                lineHeight = 26.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = word.exampleEnglish,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Verb Conjugations Section (Only show if category is VERB)
            if (word.category == "VERB" && word.verbConjugation != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Verb Conjugations (Present Tense)",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Text(
                            text = word.verbConjugation,
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(16.dp),
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Cultural Note Section (Only show if available)
            if (word.culturalNotes != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Cultural insight",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Cultural Context Note",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                        shape = RoundedCornerShape(14.dp)
                     ) {
                        Text(
                            text = word.culturalNotes,
                            fontSize = 13.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
