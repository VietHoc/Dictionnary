package com.example.dell.dictionary.utils

import com.example.dell.dictionary.DictionaryApplication

/**
 * Created by Viethoc on 5/3/18.
 */
object AssetUtils {

    private val context by lazy { DictionaryApplication.instance }

    fun readAssets(path: String): String{
        return context.assets.open(path)
                .bufferedReader().use{
                    it.readText()
                }
    }
}