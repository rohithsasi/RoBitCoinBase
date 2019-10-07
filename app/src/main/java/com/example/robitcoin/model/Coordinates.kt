package com.example.robitcoin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates(val x: Double, val y: Double) : Parcelable