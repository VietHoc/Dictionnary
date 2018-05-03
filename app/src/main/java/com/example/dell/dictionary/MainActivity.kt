package com.example.dell.dictionary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.StringRes
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast

import com.example.dell.dictionary.controllers.WordController

import java.util.Locale

class MainActivity : AppCompatActivity(), MyAdapter.ItemCustomListener, TextToSpeech.OnInitListener {

    private var myadapter: MyAdapter? = null
    var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)

        val listView = findViewById<View>(R.id.listview) as ListView


        myadapter = MyAdapter(WordController.words, this)
        listView.adapter = myadapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            val word = adapterView.getItemAtPosition(position) as Word
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra("word_id", word.id)
            startActivity(intent)
        }


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        setSupportActionBar(toolbar)

        val ed_search = findViewById<View>(R.id.ed_search) as EditText

        ed_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                myadapter!!.filter.filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })


        val drawerToggle = object: ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }
        }
        drawerLayout.setDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val arr_navi = arrayOf("English-Vietnamese", "Favorite list")
        val listView_navigation = findViewById<View>(R.id.listview_navi) as ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arr_navi)
        listView_navigation.adapter = adapter
        listView_navigation.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            when (position) {
                0 -> {
                }
                1 -> {
                }
            }
        }
    }

    override fun onInit(status: Int) {
        if (status != TextToSpeech.ERROR) {
            Log.i("TAG", status.toString())
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

    override fun onSpeak(position: Int, text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }
}