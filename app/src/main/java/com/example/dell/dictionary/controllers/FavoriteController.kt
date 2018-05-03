package com.example.dell.dictionary.controllers

import com.example.dell.dictionary.DictionaryApplication
import com.example.dell.dictionary.Word
import com.example.dell.dictionary.utils.FileUtils
import com.google.gson.Gson

object FavoriteController {

    private const val fileName = "favorites.json"

    var favorites = mutableListOf<Word>()

    init {
        val jsonString = FileUtils.read(fileName)
        if(jsonString.isNotEmpty()){
            favorites = Gson().fromJson(jsonString, Array<Word>::class.java).toMutableList()
        }
    }

    fun save(){
        try{
            FileUtils.write(fileName, favorites)
        }catch (err: Exception){
            err.printStackTrace()
        }
    }

    fun add(word: Word){
        if(!contains(word)){
            favorites.add(word)
            save()
        }
    }

    fun contains(word: Word):Boolean{
        return favorites.any { it.id == word.id }
    }

    fun remove(word: Word){
        if(favorites.removeAll { it.id == word.id }){
            save()
        }
    }

    fun toggleAdd(word: Word):Boolean{

        if(contains(word)){
            favorites.removeAll { it.id == word.id }
        }else{
            favorites.add(word)
        }

        save()

        return contains(word)
    }
}