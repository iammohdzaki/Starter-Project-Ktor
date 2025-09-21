package com.llm.koog.configs

import com.llm.koog.KoogClientWrapper

interface KoogConfig {
    fun build(): KoogClientWrapper
}