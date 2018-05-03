package com.example.dell.dictionary.utils

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Created by Viethoc on 5/3/18.
 */
object FileUtils{

    fun read(context: Context, path: String): String{
        val file = File(context.filesDir, path)
        if(file.exists()){
            val fileReader = FileReader(file)
            return fileReader.readLines().joinToString()
        }
        return ""
    }

    fun write(context: Context, path: String, content: String){
        val file = File(context.filesDir, path)

        if(file.exists()){
            file.delete()
        }

        val fileWriter = FileWriter(file)
        fileWriter.write(content)
        fileWriter.close()
    }

    fun write(context: Context, path: String, content: Any){
        if(content is String)write(context, path, content)
        else {
            write(context, path, Gson().toJson(content))
        }
    }
}