package com.example.dell.dictionary.activities

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.dell.dictionary.adapters.MyPagerAdapter
import com.example.dell.dictionary.R
import com.example.dell.dictionary.models.Word

import com.example.dell.dictionary.controllers.WordController

import java.util.Locale

/**
 * Created by DELL on 8/4/2016.
 */
class DetailActivity : AppCompatActivity(), MyPagerAdapter.TTS, TextToSpeech.OnInitListener {

    private var word: Word? = null

    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_viewpager)

        tts = TextToSpeech(this, this)

        val bundle = intent.extras
        word = WordController.getById(bundle!!.getInt("word_id"))

        init()

    }


    private fun init() {
        val pagerAdapter = MyPagerAdapter(WordController.words, this)
        val pager = findViewById<View>(R.id.viewPager) as ViewPager
        pager.adapter = pagerAdapter
        pager.currentItem = WordController.words.indexOf(word)
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            tts!!.language = Locale.US
        } else {
            Toast.makeText(applicationContext, "Feature not sopportedin your device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onPause() {
        if (tts != null) {
            tts!!.stop()
        }
        super.onPause()
    }


    override fun speackOut(position: Int, str: String) {
        tts!!.speak(str, TextToSpeech.QUEUE_FLUSH, null)
    }
}
