package com.example.dell.dictionary.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import com.example.dell.dictionary.R
import com.example.dell.dictionary.activities.DetailActivity
import com.example.dell.dictionary.activities.bases.IPlaySound
import com.example.dell.dictionary.controllers.FavoriteController
import com.example.dell.dictionary.models.Word
import java.util.ArrayList

/**
 * Created by Viethoc on 5/3/18.
 */
class WordAdapter(var words: List<Word>)
    : RecyclerView.Adapter<WordAdapter.WordViewHolder>(),
        Filterable {

    private var mOriginalValues: List<Word>? = null

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var constraint = constraint
                val results = FilterResults()
                val FilteredArrayList = ArrayList<Word>()

                if (mOriginalValues == null) {
                    mOriginalValues = ArrayList(words) // saves the original data in mOriginalValues
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
                words = results.values as List<Word>
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewItem = layoutInflater.inflate(R.layout.row_item, parent, false)
        return WordViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return words.count()
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.word = words[position]
    }

    inner class WordViewHolder(view: View): RecyclerView.ViewHolder(view){
        private lateinit var _word: Word

        private var textView: TextView = view.findViewById<View>(R.id.textview) as TextView
        private var imbspeak: ImageButton = view.findViewById<View>(R.id.imbspeaker) as ImageButton
        private var imbstar: ImageButton = view.findViewById<View>(R.id.imbstar) as ImageButton

        var word: Word
        set(value){
            _word = value
            textView.text = word.word
            imbstar.isActivated = FavoriteController.contains(word)

        }
        get(){
            return _word
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("word_id", word.id)
                itemView.context.startActivity(intent)
            }

            imbspeak.setOnClickListener {

                (itemView.context as IPlaySound).speak(position, textView.text.toString())
            }

            imbstar.setOnClickListener {
                FavoriteController.toggleAdd(word)
                notifyDataSetChanged()
            }
        }
    }

}