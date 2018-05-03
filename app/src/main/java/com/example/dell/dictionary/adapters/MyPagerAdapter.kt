package com.example.dell.dictionary.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.dell.dictionary.R

import com.example.dell.dictionary.controllers.FavoriteController
import com.example.dell.dictionary.models.Word

/**
 * Created by DELL on 8/4/2016.
 */
class MyPagerAdapter(
        var words: List<Word>,
        var context: Context) : PagerAdapter() {

    var tts = context as TTS

    override fun getPageWidth(position: Int): Float {
        return 1f
    }

    override fun getCount(): Int {
        return words.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.viewpager, container, false)

        val tvWord = view.findViewById<View>(R.id.tvWord) as TextView
        val tvDetail = view.findViewById<View>(R.id.tvDetail) as TextView
        val imbspeak = view.findViewById<View>(R.id.imbspeak) as ImageButton
        val imbstar = view.findViewById<View>(R.id.imbstar) as ImageButton

        val word = words[position]

        tvWord.text = word.word
        imbstar.isActivated = FavoriteController.contains(word)
        tvDetail.text = word.detail!!.substring(word.word!!.length)

        imbspeak.setOnClickListener { tts.speackOut(position, tvWord.text.toString()) }

        imbstar.setOnClickListener {
            imbstar.isActivated = FavoriteController.toggleAdd(word)
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    interface TTS {
        fun speackOut(position: Int, str: String)
    }
}
