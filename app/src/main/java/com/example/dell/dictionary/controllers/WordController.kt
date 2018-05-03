package com.example.dell.dictionary.controllers

import com.example.dell.dictionary.models.Word
import com.example.dell.dictionary.utils.AssetUtils
import com.google.gson.Gson

/**
 * Created by Viethoc on 5/3/18.
 */
object WordController {
    private const val fileName = "dictionary.json"
    var words = listOf<Word>()

    init {
        val jsonString = AssetUtils.readAssets(fileName)
        words = Gson().fromJson(jsonString, Array<Word>::class.java).toMutableList()
    }

    fun getById(id: Int): Word {
        return words.first { it.id == id }
    }
}