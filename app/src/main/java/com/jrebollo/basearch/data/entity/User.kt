package com.jrebollo.basearch.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class User(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val locationLatitude: Double? = null,
    val locationLongitude: Double? = null,
    val smallAvatar: String? = null,
    val bigAvatar: String? = null
) : BaseEntity, Parcelable