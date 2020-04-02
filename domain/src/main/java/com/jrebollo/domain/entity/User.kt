package com.jrebollo.domain.entity

data class User(
    val name: String,
    val password: String?,
    val token: String?
) : BaseEntity