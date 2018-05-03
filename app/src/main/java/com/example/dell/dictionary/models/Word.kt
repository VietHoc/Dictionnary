package com.example.dell.dictionary.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * Created by DELL on 7/30/2016.
 */

class Word{

    var id: Int = 0

    @SerializedName("code")
    var word: String? = null

    @SerializedName("content")
    var detail: String? = null
}
