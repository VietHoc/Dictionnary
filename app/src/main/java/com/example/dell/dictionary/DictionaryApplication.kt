package com.example.dell.dictionary

import android.app.Application

class DictionaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DictionaryApplication.instance = this
    }

    companion object{
        lateinit var instance: DictionaryApplication
    }
}