package com.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtils {

    fun readJsonFile(code: String): JsonElement {
        val `in` = javaClass.getResourceAsStream("/languages/lang_$code.json")
        val reader = BufferedReader(InputStreamReader(`in`!!))
        val jsonFile = reader.readText()
        return Json.parseToJsonElement(jsonFile)
    }

}
