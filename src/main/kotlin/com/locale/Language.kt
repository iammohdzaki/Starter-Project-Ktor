package com.locale

enum class Language(
    val displayName: String,
    val code: String
) {

    ENGLISH("English", "en"),
    ARABIC("Arabic", "ar")
    ;

    companion object {
        fun getLanguages() = entries
    }
}