package com.example.dell.dictionary.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import com.example.dell.dictionary.R
import com.example.dell.dictionary.activities.bases.IPlaySound

import com.example.dell.dictionary.controllers.FavoriteController
import com.example.dell.dictionary.models.Word

import java.util.ArrayList

/**
 * Created by DELL on 7/30/2016.
 */
class MyAdapter(var listdata: List<Word>,
                val context: Context) :
        BaseAdapter(), Filterable {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val itemCustomListener = context as IPlaySound

    private var mOriginalValues: List<Word>? = null

    override fun getCount(): Int {

        return this.listdata.size
    }

    override fun getItem(i: Int): Any {
        return listdata[i]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val holder: ViewHolder
        val word = listdata[position]

        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_item, viewGroup, false)

            holder = ViewHolder()
            holder.word = word
            holder.textView = view.findViewById<View>(R.id.textview) as TextView
            holder.imbspeak = view.findViewById<View>(R.id.imbspeaker) as ImageButton
            holder.imbstar = view.findViewById<View>(R.id.imbstar) as ImageButton

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }


        holder.textView!!.text = word.word
        holder.imbspeak!!.setOnClickListener {
            Log.i("TAG", holder.textView!!.text.toString())

            itemCustomListener.speak(position, holder.textView!!.text.toString())
        }

        holder.imbstar!!.setOnClickListener {
            FavoriteController.toggleAdd(holder.word)
            notifyDataSetChanged()
        }

        holder.imbstar!!.isActivated = FavoriteController.contains(word)

        return view!!
    }


    internal class ViewHolder {
        var textView: TextView? = null
        var imbspeak: ImageButton? = null
        var imbstar: ImageButton? = null
        lateinit var word: Word
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var constraint = constraint
                val results = FilterResults()
                val FilteredArrayList = ArrayList<Word>()

                if (mOriginalValues == null) {
                    mOriginalValues = ArrayList(listdata!!) // saves the original data in mOriginalValues
                }
                if (constraint.isNullOrEmpty()) {
                    results.count = mOriginalValues!!.size
                    results.values = mOriginalValues
                } else {
                    constraint = constraint.toString().toLowerCase()
                    for (i in mOriginalValues!!.indices) {
                        val data = mOriginalValues!![i].word
                        if (data!!.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrayList.add(mOriginalValues!![i])
                        }
                    }

                    results.count = FilteredArrayList.size
                    results.values = FilteredArrayList
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                listdata = results.values as List<Word>
                notifyDataSetChanged()
            }
        }
    }
}
