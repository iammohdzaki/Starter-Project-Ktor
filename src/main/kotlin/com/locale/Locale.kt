package com.locale

import com.utils.FileUtils
import kotlinx.serialization.json.jsonObject

object Locale {

    private val locales = HashMap<String, HashMap<String, String>>()

    fun init() {
        Language.getLanguages()
            .forEach {
                locales[it.code] = getLocaleByCode(it.code)
            }
    }

    fun getString(key: String, code: String = Language.ENGLISH.code) = locales[code]?.get(key) ?: ""

    private fun getLocaleByCode(code: String): HashMap<String, String> {
        val locale = FileUtils.readJsonFile(code)
        return locale.jsonObject.toMap().mapValues {
            it.value.toString().replace("\"", "")
        } as HashMap<String, String>
    }
}