package com.example.dell.dictionary.utils

import android.content.Context
import com.example.dell.dictionary.DictionaryApplication
import com.example.dell.dictionary.controllers.WordController
import com.google.gson.Gson
import java.io.*
/**
 * Created by Viethoc on 5/3/18.
 */
object FileUtils{

    private val context by lazy { DictionaryApplication.instance }

    fun exist(path: String):Boolean{
        return File(path).exists();
    }

    fun readAssets(path: String): String{
        return context.assets.open(path)
                .bufferedReader().use{
                    it.readText()
                }
    }

    fun read(path: String): String{
        val file = File(context.filesDir, path)
        if(file.exists()){
            val fileReader = FileReader(file)
            return fileReader.readLines().joinToString()
        }
        return ""
    }

    fun write(path: String, content: String){
        val file = File(context.filesDir, path)

        if(file.exists()){
            file.delete()
        }

        val fileWriter = FileWriter(file)
        fileWriter.write(content)
        fileWriter.close()
    }

    fun write(path: String, content: Any){
        if(content is String)write(path, content)
        else {
            write(path, Gson().toJson(content))
        }
    }
}