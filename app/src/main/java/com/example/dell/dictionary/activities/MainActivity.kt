package com.example.dell.dictionary.activities

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.dell.dictionary.adapters.MyAdapter
import com.example.dell.dictionary.R
import com.example.dell.dictionary.activities.bases.AbstractSoundPlayActivity
import com.example.dell.dictionary.adapters.WordAdapter
import com.example.dell.dictionary.controllers.FavoriteController
import com.example.dell.dictionary.models.Word

import com.example.dell.dictionary.controllers.WordController

import java.util.Locale

class MainActivity : AbstractSoundPlayActivity() {

    private var myadapter = WordAdapter(WordController.words)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<View>(R.id.listview) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = myadapter


        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        setSupportActionBar(toolbar)

        val ed_search = findViewById<View>(R.id.ed_search) as EditText

        ed_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                myadapter.filter.filter(charSequence.toString())
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

        listView_navigation.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> showList(WordController.words)
                1 -> showList(FavoriteController.favorites)
            }
            drawerLayout.closeDrawers()
        }
    }

    private fun showList(words: List<Word>){
        myadapter.words = words
        myadapter.notifyDataSetChanged()
    }
}