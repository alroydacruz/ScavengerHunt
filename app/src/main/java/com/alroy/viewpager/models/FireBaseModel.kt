package com.alroy.viewpager.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FireBaseModel(var currentLevel: Int = -1, var currentBranch: Int = -1):Parcelable{
    constructor(): this(-1,-1)
}