package com.example.dell.dictionary

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by DELL on 7/30/2016.
 */

class Word : Parcelable {
    var id: Int = 0
    var word: String? = null
    var detail: String? = null

    constructor(word: String, detail: String, id: Int) {
        this.id = id
        this.word = word
        this.detail = detail
    }

    protected constructor(insteam: Parcel) {
        id =insteam.readInt()
        word = insteam.readString()
        detail = insteam.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(id)
        parcel.writeString(word)
        parcel.writeString(detail)
    }

    companion object {

        val CREATOR: Parcelable.Creator<Word> = object : Parcelable.Creator<Word> {
            override fun createFromParcel(inStream: Parcel): Word {
                return Word(inStream)
            }

            override fun newArray(size: Int): Array<Word> {
                return Array(size, {Word("","",0)})
            }
        }
    }
}
