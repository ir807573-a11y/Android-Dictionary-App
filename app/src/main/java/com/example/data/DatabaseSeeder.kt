package com.example.data

object DatabaseSeeder {
    fun getSeedWords(): List<DictionaryWord> {
        return listOf(
            // --- NOUNS ---
            DictionaryWord(
                pashto = "ميلمهستيا",
                englishTranslate = "Hospitality",
                phoneticPashto = "Mīlmastiyā",
                phoneticEnglish = "meel-mas-tyah",
                definition = "The practice of hosting guests with unconditional generosity and protection. One of the primary pillars of the Pashtunwali code of honor.",
                category = "NOUN",
                examplePashto = "ميلمهستيا د پښتنو يو مهم صفت دی.",
                exampleEnglish = "Hospitality is an important quality of the Pashtun people.",
                culturalNotes = "Melmastia requires offering guests the best food, comfort, and protection, regardless of their nationality, race, or religion, without expecting anything in return."
            ),
            DictionaryWord(
                pashto = "پښتونوالى",
                englishTranslate = "Pashtunwali",
                phoneticPashto = "Pax̌tūnwalī",
                phoneticEnglish = "pukh-toon-wah-lee",
                definition = "The traditional non-written ethical and social code that governs individual and communal life among the Pashtun people.",
                category = "NOUN",
                examplePashto = "پښتونوالی د عزت، ميلمهستيا او جرګې لار ده.",
                exampleEnglish = "Pashtunwali is the path of honor, hospitality, and council.",
                culturalNotes = "Key tenets include Melmastia (hospitality), Nanawatai (asylum/sanctuary), Badal (justice), and Jirga (council of elders)."
            ),
            DictionaryWord(
                pashto = "جرګه",
                englishTranslate = "Jirga / Assembly",
                phoneticPashto = "Jirga",
                phoneticEnglish = "jeer-gah",
                definition = "A traditional assembly of leaders, elders, or community members who gather to make decisions, resolve disputes, and maintain social harmony by consensus.",
                category = "NOUN",
                examplePashto = "مشرانو په جرګه کې پرېکړه وکړه.",
                exampleEnglish = "The elders made a decision in the council.",
                culturalNotes = "Jirgas are a cornerstone of Pashtun democratic tradition, dating back centuries. Even major legal disputes and local treaties are negotiated through this cooperative assembly."
            ),
            DictionaryWord(
                pashto = "ملګری",
                englishTranslate = "Friend / Companion",
                phoneticPashto = "Malgaray",
                phoneticEnglish = "mahl-guh-ray",
                definition = "A person with whom one has a bond of mutual affection and support (masculine form; feminine form is ملګرې - Malgarē).",
                category = "NOUN",
                examplePashto = "هغه زما ډېر ښه ملګری دی.",
                exampleEnglish = "He is a very good friend of mine.",
                culturalNotes = "Loyalty among friends is highly celebrated in Pashto folklore and poetry."
            ),
            DictionaryWord(
                pashto = "مینه",
                englishTranslate = "Love / Affection",
                phoneticPashto = "Mīna",
                phoneticEnglish = "mee-nah",
                definition = "A strong feeling of affection, care, attachment, or romantic devotion.",
                category = "NOUN",
                examplePashto = "مينه د ژوند رڼا ده.",
                exampleEnglish = "Love is the light of life.",
                culturalNotes = "Pashto love poetry (landys) is famous worldwide for its expressive, concise, and deeply emotional two-line verses."
            ),
            DictionaryWord(
                pashto = "کور",
                englishTranslate = "House / Home",
                phoneticPashto = "Kōr",
                phoneticEnglish = "kor",
                definition = "A building for human habitation; the place where one's family lives.",
                category = "NOUN",
                examplePashto = "دا زما کور دی، ښه راغلاست!",
                exampleEnglish = "This is my home, welcome!",
                culturalNotes = "In Pashtun culture, a 'Kor' represents family privacy, safety, and ultimate sanctuary."
            ),
            DictionaryWord(
                pashto = "اووبه",
                englishTranslate = "Water",
                phoneticPashto = "Oubə",
                phoneticEnglish = "oo-buh",
                definition = "A colorless, transparent, odorless liquid that forms the seas, lakes, rivers, and rain and is the basis of fluids of living organisms.",
                category = "NOUN",
                examplePashto = "مهرباني وکړه، ما ته اووبه راکړه.",
                exampleEnglish = "Please, give me some water."
            ),
            DictionaryWord(
                pashto = "شین چای",
                englishTranslate = "Green Tea",
                phoneticPashto = "Šīn Čāy",
                phoneticEnglish = "sheen chai",
                definition = "Traditional green tea brewed with cardamom and sugar, widely consumed across Pashtun communities.",
                category = "NOUN",
                examplePashto = "راځئ چې چای وڅښو.",
                exampleEnglish = "Let's drink some tea.",
                culturalNotes = "Green tea is an omnipresent gesture of hospitality. It is served immediately upon a guest's arrival in a guest room (Hujra) alongside almonds, sweets, or raisins."
            ),
            DictionaryWord(
                pashto = "ډوډۍ",
                englishTranslate = "Bread / Food / Meal",
                phoneticPashto = "Dōḍəi",
                phoneticEnglish = "doh-day",
                definition = "Flatbread baked in a tandoor oven, or a general term referencing a meal.",
                category = "NOUN",
                examplePashto = "ډوډۍ تياره ده، راځئ چې ډوډۍ وخورو.",
                exampleEnglish = "The food is ready, let's eat.",
                culturalNotes = "Pashto flatbread (Naan) is a central element of every meal, traditionally broken by hand and shared among family members on a dining mat ('Dastarkhwan') spread on the floor."
            ),
            DictionaryWord(
                pashto = "لمر",
                englishTranslate = "Sun",
                phoneticPashto = "Lmar",
                phoneticEnglish = "lmar",
                definition = "The star around which the earth revolves and from which it receives light and warmth.",
                category = "NOUN",
                examplePashto = "لمر په ختيځ کې راخيژي.",
                exampleEnglish = "The sun rises in the east."
            ),
            DictionaryWord(
                pashto = "غر",
                englishTranslate = "Mountain",
                phoneticPashto = "Ghar",
                phoneticEnglish = "ghar",
                definition = "A large natural elevation of the earth's surface rising abruptly from the surrounding level.",
                category = "NOUN",
                examplePashto = "دغه غر ډېر جګ دی.",
                exampleEnglish = "This mountain is very high.",
                culturalNotes = "Mountains symbolize resilience, strength, and freedom in Pashto literature, reflecting the rugged Hindu Kush geography."
            ),
            DictionaryWord(
                pashto = "ننګ",
                englishTranslate = "Honor / Dignity",
                phoneticPashto = "Nang",
                phoneticEnglish = "nahng",
                definition = "A sense of dignity, chivalry, integrity, and honor in defending oneself, family, community, or homeland.",
                category = "NOUN",
                examplePashto = "هغه د ننګ او غيرت بېلګه ده.",
                exampleEnglish = "He is an example of honor and courage.",
                culturalNotes = "Nang is a primary virtue in Pashtunwali, necessitating immediate defense against any perceived dishonor or disrespect to women, family, or territory."
            ),
            DictionaryWord(
                pashto = "پوهنتون",
                englishTranslate = "University",
                phoneticPashto = "Pohantūn",
                phoneticEnglish = "poh-han-toon",
                definition = "An institution of higher education and research which grants academic degrees.",
                category = "NOUN",
                examplePashto = "زه په پوهنتون کې درس لولم.",
                exampleEnglish = "I study at the university."
            ),
            DictionaryWord(
                pashto = "ښوونځی",
                englishTranslate = "School",
                phoneticPashto = "X̌owanzay",
                phoneticEnglish = "shwan-zay",
                definition = "An institution for educating children or students.",
                category = "NOUN",
                examplePashto = "هغه خپلو ماشومانو ته ښوونځي کې درس ورکوي.",
                exampleEnglish = "She teaches her children in school."
            ),
            DictionaryWord(
                pashto = "ستوری",
                englishTranslate = "Star",
                phoneticPashto = "Stōray",
                phoneticEnglish = "stoh-ray",
                definition = "A fixed luminous point in the night sky which is a large, remote, incandescent body like the sun.",
                category = "NOUN",
                examplePashto = "نن په اسمان کې ډېر ستوري دي.",
                exampleEnglish = "There are many stars in the sky tonight."
            ),
            DictionaryWord(
                pashto = "پلار",
                englishTranslate = "Father",
                phoneticPashto = "Plār",
                phoneticEnglish = "plahr",
                definition = "A male parent of a child.",
                category = "NOUN",
                examplePashto = "زما پلار زما لارښود دی.",
                exampleEnglish = "My father is my guide."
            ),
            DictionaryWord(
                pashto = "مور",
                englishTranslate = "Mother",
                phoneticPashto = "Mōr",
                phoneticEnglish = "mohr",
                definition = "A female parent of a child.",
                category = "NOUN",
                examplePashto = "مور د ماشوم لومړی ښوونځی دی.",
                exampleEnglish = "Mother is the first school of a child."
            ),
            DictionaryWord(
                pashto = "ورور",
                englishTranslate = "Brother",
                phoneticPashto = "Wrōr",
                phoneticEnglish = "wror",
                definition = "A man or boy in relation to other sons and daughters of his parents.",
                category = "NOUN",
                examplePashto = "هغه زما مشر ورور دی.",
                exampleEnglish = "He is my older brother."
            ),
            DictionaryWord(
                pashto = "خور",
                englishTranslate = "Sister",
                phoneticPashto = "Xōr",
                phoneticEnglish = "khor",
                definition = "A woman or girl in relation to other sons and daughters of her parents.",
                category = "NOUN",
                examplePashto = "زما خور ډېره باسيکاله ده.",
                exampleEnglish = "My sister is very hardworking."
            ),

            // --- VERBS ---
            DictionaryWord(
                pashto = "تلل",
                englishTranslate = "To go",
                phoneticPashto = "Ktal / Tlal",
                phoneticEnglish = "tlal",
                definition = "To move from one place to another; depart.",
                category = "VERB",
                examplePashto = "زه ښار ته ځم.",
                exampleEnglish = "I am going to the city.",
                verbConjugation = "Present Tense:\n• زه ځم (I go)\n• ته ځې (You go)\n• موږ ځو (We go)\n• تاسو ځئ (You plural go)\n• هغه ځي (He/She/They go)"
            ),
            DictionaryWord(
                pashto = "خوړل",
                englishTranslate = "To eat",
                phoneticPashto = "Xōṛal",
                phoneticEnglish = "khor-al",
                definition = "To put food into the mouth and chew and swallow it.",
                category = "VERB",
                examplePashto = "زه ډوډۍ خورم.",
                exampleEnglish = "I am eating food.",
                verbConjugation = "Present Tense:\n• زه خورم (I eat)\n• ته خورې (You eat)\n• موږ خورو (We eat)\n• تاسو خورئ (You plural eat)\n• هغه خوري (He/She/They eat)"
            ),
            DictionaryWord(
                pashto = "څښل",
                englishTranslate = "To drink",
                phoneticPashto = "Cx̌al",
                phoneticEnglish = "tskhal",
                definition = "To take liquid into the mouth and swallow it.",
                category = "VERB",
                examplePashto = "هغه وم صفا اووبه څښي.",
                exampleEnglish = "He drinks clean water.",
                verbConjugation = "Present Tense:\n• زه څښم (I drink)\n• ته څښې (You drink)\n• موږ څښو (We drink)\n• تاسو څښئ (You plural drink)\n• هغه څښي (He/She/They drink)"
            ),
            DictionaryWord(
                pashto = "کول",
                englishTranslate = "To do / To make",
                phoneticPashto = "Kawal",
                phoneticEnglish = "kah-wal",
                definition = "To perform an action, task, or activity.",
                category = "VERB",
                examplePashto = "زه خپل کار کوم.",
                exampleEnglish = "I am doing my work.",
                verbConjugation = "Present Tense:\n• زه کوم (I do)\n• ته کوې (You do)\n• موږ کوو (We do)\n• تاسو کوئ (You plural do)\n• هغه کوي (He/She/They do)"
            ),
            DictionaryWord(
                pashto = "غوښتل",
                englishTranslate = "To want / request",
                phoneticPashto = "Ghōṣ̌tal",
                phoneticEnglish = "ghosh-tal",
                definition = "To have a desire for something; wish for.",
                category = "VERB",
                examplePashto = "زه غواړم پښتو زده کړم.",
                exampleEnglish = "I want to learn Pashto.",
                verbConjugation = "Present Tense:\n• زه غواړم (I want)\n• ته غواړې (You want)\n• موږ غواړو (We want)\n• تاسو غواړئ (You plural want)\n• هغه غواړي (He/She/They want)"
            ),
            DictionaryWord(
                pashto = "ليکل",
                englishTranslate = "To write",
                phoneticPashto = "Līkal",
                phoneticEnglish = "lee-kal",
                definition = "To mark letters, words, or symbols on paper or computer to express thoughts or record information.",
                category = "VERB",
                examplePashto = "زه يو مکتوب ليکم.",
                exampleEnglish = "I am writing a letter.",
                verbConjugation = "Present Tense:\n• زه ليکم (I write)\n• ته ليکې (You write)\n• موږ ليکو (We write)\n• هغه ليکي (He/She/They write)"
            ),
            DictionaryWord(
                pashto = "کتل",
                englishTranslate = "To look / watch",
                phoneticPashto = "Katal",
                phoneticEnglish = "kah-tal",
                definition = "To direct one's gaze toward someone or something.",
                category = "VERB",
                examplePashto = "موږ يو فلم ګورو.",
                exampleEnglish = "We are watching a movie.",
                verbConjugation = "Present Tense:\n• زه ګورم (I watch)\n• ته ګورې (You watch)\n• موږ ګورو (We watch)\n• هغه ګوري (He/She/They watch)"
            ),
            DictionaryWord(
                pashto = "خبرې کول",
                englishTranslate = "To speak / talk",
                phoneticPashto = "Xabarē kawal",
                phoneticEnglish = "khah-buh-ray kah-wal",
                definition = "To communicate with spoken voice or express feelings orally.",
                category = "VERB",
                examplePashto = "زه په ارامۍ خبرې کوم.",
                exampleEnglish = "I speak calmly.",
                verbConjugation = "Present Tense:\n• زه خبرې کوم (I talk)\n• ته خبرې کوې (You talk)\n• هغه خبرې کوي (He/She/They talk)"
            ),

            // --- ADJECTIVES ---
            DictionaryWord(
                pashto = "ښکلی",
                englishTranslate = "Beautiful / Prepossessing",
                phoneticPashto = "X̌kulay",
                phoneticEnglish = "khkoo-lay",
                definition = "Having quality that alignment of features which gives high aesthetic pleasure.",
                category = "ADJECTIVE",
                examplePashto = "دا ډېر ښکلی ګل دی.",
                exampleEnglish = "This is a very beautiful flower."
            ),
            DictionaryWord(
                pashto = "لوی",
                englishTranslate = "Large / Big / Great",
                phoneticPashto = "Lway",
                phoneticEnglish = "lway",
                definition = "Of considerable or relatively great size, quantity, power, or importance.",
                category = "ADJECTIVE",
                examplePashto = "موږ په لوی کور کې اوسېږو.",
                exampleEnglish = "We live in a large house."
            ),
            DictionaryWord(
                pashto = "کوچنی",
                englishTranslate = "Small / Tiny",
                phoneticPashto = "Kōčnay",
                phoneticEnglish = "koch-nay",
                definition = "Of a size that is less than normal or usual.",
                category = "ADJECTIVE",
                examplePashto = "هغه کوچنی کتاب غواړي.",
                exampleEnglish = "He wants the small book."
            ),
            DictionaryWord(
                pashto = "خوږ",
                englishTranslate = "Sweet / Pleasant",
                phoneticPashto = "Xōg",
                phoneticEnglish = "khog",
                definition = "Having the pleasant taste of sugar, or satisfying/affectionate in character.",
                category = "ADJECTIVE",
                examplePashto = "دا خوږه ډوډۍ ده.",
                exampleEnglish = "This is a sweet bread/cake."
            ),
            DictionaryWord(
                pashto = "سخت",
                englishTranslate = "Hard / Difficult",
                phoneticPashto = "Saxt",
                phoneticEnglish = "sukht",
                definition = "Not easy; requiring great effort, skill, or determination.",
                category = "ADJECTIVE",
                examplePashto = "دا کار ډېر سخت دی.",
                exampleEnglish = "This work is very difficult."
            ),
            DictionaryWord(
                pashto = "اسانه",
                englishTranslate = "Easy / Simple",
                phoneticPashto = "Asāna",
                phoneticEnglish = "ah-sah-nah",
                definition = "Achieved without great effort; simple to accomplish.",
                category = "ADJECTIVE",
                examplePashto = "پښتو زده کول اسان کار دی.",
                exampleEnglish = "Learning Pashto is an easy job."
            ),
            DictionaryWord(
                pashto = "سړی",
                englishTranslate = "Cold",
                phoneticPashto = "Soṛ",
                phoneticEnglish = "sor",
                definition = "Of or at a low temperature, especially when compared with the human body.",
                category = "ADJECTIVE",
                examplePashto = "نن ورځ ډېره سړه ده.",
                exampleEnglish = "Today is a very cold day."
            ),
            DictionaryWord(
                pashto = "تود",
                englishTranslate = "Hot / Warm",
                phoneticPashto = "Taud",
                phoneticEnglish = "towd",
                definition = "Of or at a high temperature, or emotionally intense.",
                category = "ADJECTIVE",
                examplePashto = "چای ډېر تود دی، صبر وکړه.",
                exampleEnglish = "The tea is very hot, please wait."
            ),
            DictionaryWord(
                pashto = "ښه",
                englishTranslate = "Good / Well",
                phoneticPashto = "X̌ah",
                phoneticEnglish = "khah",
                definition = "To be desired or approved of; of a high standard.",
                category = "ADJECTIVE",
                examplePashto = "هغه ډېر ښه هلک دی.",
                exampleEnglish = "He is a very good boy."
            ),
            DictionaryWord(
                pashto = "خراب",
                englishTranslate = "Bad / Broken",
                phoneticPashto = "Xarāb",
                phoneticEnglish = "khuh-rahb",
                definition = "Of poor quality, spoiled, broken, or unethical.",
                category = "ADJECTIVE",
                examplePashto = "زما ساعت خراب دی.",
                exampleEnglish = "My watch is broken."
            ),

            // --- COMMON PHRASES ---
            DictionaryWord(
                pashto = "ښه راغلاست",
                englishTranslate = "Welcome",
                phoneticPashto = "X̌ah rāghlāst",
                phoneticEnglish = "khah rah-glahst",
                definition = "A warm, respectful greeting extended to guests arriving at a home, village, or gathering.",
                category = "PHRASE",
                examplePashto = "ستاسو کور ته ښه راغلاست!",
                exampleEnglish = "Welcome to your home!",
                culturalNotes = "In traditional Pashto greetings, welcoming a guest is routinely accompanied by placing a hand on the heart and bowing slightly to show absolute respect."
            ),
            DictionaryWord(
                pashto = "مننه",
                englishTranslate = "Thank you",
                phoneticPashto = "Manana",
                phoneticEnglish = "mah-nah-nah",
                definition = "An expression of gratitude, appreciation, or polite acknowledgement.",
                category = "PHRASE",
                examplePashto = "د ډوډۍ لپاره ډېره مننه.",
                exampleEnglish = "Thank you very much for the meal."
            ),
            DictionaryWord(
                pashto = "ستاسو نوم څه دی؟",
                englishTranslate = "What is your name?",
                phoneticPashto = "Stāsū nūm tsə day?",
                phoneticEnglish = "stah-soo noom tsah day",
                definition = "A polite standard question used to inquire about someone's name.",
                category = "PHRASE",
                examplePashto = "وروره، ستاسو نوم څه دی؟",
                exampleEnglish = "Brother, what is your name?"
            ),
            DictionaryWord(
                pashto = "زما نوم خان دی.",
                englishTranslate = "My name is Khan.",
                phoneticPashto = "Zəmā nūm Khān day.",
                phoneticEnglish = "zuh-mah noom khahn day",
                definition = "Standard conversational response to declare one's name.",
                category = "PHRASE",
                examplePashto = "ستا هومره عزتمن نوم لرې، زما نوم خان دی.",
                exampleEnglish = "You have such an honorable name, my name is Khan."
            ),
            DictionaryWord(
                pashto = "څنګه يې؟",
                englishTranslate = "How are you?",
                phoneticPashto = "Tsənga yē?",
                phoneticEnglish = "tsuhn-gah yee",
                definition = "A friendly conversational greeting to ask about someone's well-being.",
                category = "PHRASE",
                examplePashto = "سلام ملګريه، څنګه يې؟",
                exampleEnglish = "Hello my friend, how are you?"
            ),
            DictionaryWord(
                pashto = "زه ښه يم، مننه",
                englishTranslate = "I am fine, thank you",
                phoneticPashto = "Zə x̌ah yam, manana",
                phoneticEnglish = "zuh khah yam, mah-nah-nah",
                definition = "A response indicating that one is well and expressing thanks.",
                category = "PHRASE",
                examplePashto = "زه ښه يم، مننه، تاسو څنګه يئ؟",
                exampleEnglish = "I am fine, thank you, how are you? (plural/formal)"
            ),
            DictionaryWord(
                pashto = "خدای پامان",
                englishTranslate = "Goodbye / May God protect you",
                phoneticPashto = "Xudāy pāmān",
                phoneticEnglish = "khu-dai pah-mahn",
                definition = "A parting phrase said when leaving someone, meaning 'Under God's protection'.",
                category = "PHRASE",
                examplePashto = "زه ځم، خدای پامان!",
                exampleEnglish = "I am leaving, goodbye!",
                culturalNotes = "Slightly formal but deeply sentimental, acknowledging that we place each other's life under divine care upon parting."
            ),
            DictionaryWord(
                pashto = "سلام عليکم",
                englishTranslate = "Peace be upon you (Salam)",
                phoneticPashto = "Salām 'alaykum",
                phoneticEnglish = "suh-lahm ah-lay-kum",
                definition = "The universal respectful greeting used when entering any room, meeting someone, or passing by.",
                category = "PHRASE",
                examplePashto = "سلام عليکم، ستاسو روغتيا څنګه ده؟",
                exampleEnglish = "Peace be upon you, how is your health?",
                culturalNotes = "Customarily, the younger person approaches the older person with this greeting, or the one entering a room says it first to show respect."
            ),
            DictionaryWord(
                pashto = "په مخه ښه",
                englishTranslate = "Good luck / Farewell",
                phoneticPashto = "Pə moxa x̌a",
                phoneticEnglish = "puh mo-khah khah",
                definition = "A parting phrase wishing a good journey or fortune ahead, literally translating to 'on your face/way be goodness'.",
                category = "PHRASE",
                examplePashto = "سفر مو ارام شه، په مخه ښه!",
                exampleEnglish = "Have a peaceful journey, farewell!"
            ),
            DictionaryWord(
                pashto = "هو",
                englishTranslate = "Yes",
                phoneticPashto = "Hō",
                phoneticEnglish = "ho",
                definition = "Affirmative answer or assertion.",
                category = "PHRASE",
                examplePashto = "هو، زه پښتو زده کوم.",
                exampleEnglish = "Yes, I am learning Pashto."
            ),
            DictionaryWord(
                pashto = "نه",
                englishTranslate = "No",
                phoneticPashto = "Na",
                phoneticEnglish = "nah",
                definition = "Negative answer or assertion.",
                category = "PHRASE",
                examplePashto = "نه، زه نن نشم تللی.",
                exampleEnglish = "No, I cannot go today."
            ),
            DictionaryWord(
                pashto = "مهرباني وکړه",
                englishTranslate = "Please",
                phoneticPashto = "Mehrabānī wakṛa",
                phoneticEnglish = "meh-ruh-bah-nee wah-krah",
                definition = "A polite expression used to restrict a favor or demand.",
                category = "PHRASE",
                examplePashto = "مهرباني وکړه، دلته کښېنه.",
                exampleEnglish = "Please, sit here."
            ),
            DictionaryWord(
                pashto = "بخښنه غواړم",
                englishTranslate = "I beg your pardon / Sorry",
                phoneticPashto = "Bax̌ṣ̌əna ghwāṛam",
                phoneticEnglish = "bukh-shu-nah ghwah-rum",
                definition = "An expression of regret, apology, or polite entry.",
                category = "PHRASE",
                examplePashto = "بخښنه غواړم، زما پام نه و.",
                exampleEnglish = "I am sorry, I wasn't paying attention."
            )
        )
    }
}
