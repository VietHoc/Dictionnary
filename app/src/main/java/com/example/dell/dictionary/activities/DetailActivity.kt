package com.example.dell.dictionary.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.example.dell.dictionary.adapters.DetailPagerAdapter
import com.example.dell.dictionary.R
import com.example.dell.dictionary.activities.bases.AbstractSoundPlayActivity

import com.example.dell.dictionary.controllers.WordController

/**
 * Created by DELL on 8/4/2016.
 */
class DetailActivity : AbstractSoundPlayActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_viewpager)

        val bundle = intent.extras
        val word = WordController.getById(bundle!!.getInt("word_id"))

        val pagerAdapter = DetailPagerAdapter(WordController.words, this)
        val pager = findViewById<View>(R.id.viewPager) as ViewPager
        pager.adapter = pagerAdapter
        pager.currentItem = WordController.words.indexOf(word)

    }
}
