package com.alroy.viewpager.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class QuestionContentModel (
    var lvl :Int = 0,
    var branch : Int = 0,
    val thumbnail:String,
    val lvlHeader:String = "",
    val textMessage:String = "",
    val link:String = "",
    val image:Int = -1,
    var correctAnswer:String,
    var answeredCorrectly:Int = 0,
    val thumbnailBgColor:String = "",
    val hints:ArrayList<String>?=null
):Parcelable