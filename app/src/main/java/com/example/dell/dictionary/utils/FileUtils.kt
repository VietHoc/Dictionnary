package com.example.dell.dictionary.utils

import com.example.dell.dictionary.DictionaryApplication
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Created by Viethoc on 5/3/18.
 */
object FileUtils{

    private val context by lazy { DictionaryApplication.instance }

    fun read(path: String): String{
        val file = File(context.filesDir, path)
        if(file.exists()){
            val fileReader = FileReader(file)
            return fileReader.readLines().joinToString()
        }
        return ""
    }

    private fun write(path: String, content: String){
        val file = File(context.filesDir, path)

        if(file.exists()){
            file.delete()
        }

        val fileWriter = FileWriter(file)
        fileWriter.write(content)
        fileWriter.close()
    }

    fun write(path: String, content: Any){
        if(content is String){
            write(path, content)
        }
        else {
            write(path, Gson().toJson(content))
        }
    }
}