package com.example.robitcoin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PricePoints(val x: Double? =null,val y :Double? =null) : Parcelable