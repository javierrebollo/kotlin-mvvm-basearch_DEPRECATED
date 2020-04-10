package com.jrebollo.domain.entity

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
) : BaseEntity