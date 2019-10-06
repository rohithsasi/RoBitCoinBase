package com.example.robitcoin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pricing(val coordinates:List<PricePoints>? =null, val descripton:String?,
                   val name:String?=null,val unit:String?=null , val period:String?=null) : Parcelable