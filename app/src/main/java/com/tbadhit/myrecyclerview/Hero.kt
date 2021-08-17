package com.tbadhit.myrecyclerview

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// (1)
@Parcelize
data class Hero(
    var name: String,
    var description: String,
    var photo: String
) : Parcelable
//-----