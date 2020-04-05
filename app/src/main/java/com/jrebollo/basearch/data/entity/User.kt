package com.jrebollo.basearch.data.entity

data class User(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val locationLatitude: Double?,
    val locationLongitude: Double?,
    val smallAvatar: String?,
    val bigAvatar: String?
) : BaseEntity