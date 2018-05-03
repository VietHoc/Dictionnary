package com.example.dell.dictionary.activities.bases

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.*

/**
 * Created by Viethoc on 5/3/18.
 */
abstract class AbstractSoundPlayActivity :
        AppCompatActivity(),
        IPlaySound,
        TextToSpeech.OnInitListener{


    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)
    }


    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            tts.language = Locale.US
        } else {
            Toast.makeText(applicationContext, "Feature not sopportedin your device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()

        super.onDestroy()
    }

    override fun onPause() {
        tts.stop()
        super.onPause()
    }

    override fun speak(position: Int, wordToSpeak: String) {
        tts.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, null)
    }
}
