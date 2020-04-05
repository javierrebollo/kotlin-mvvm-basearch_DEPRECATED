package com.jrebollo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jrebollo.domain.entity.User

@Entity(tableName = "user")
data class UserRoom(
    @PrimaryKey val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val locationLatitude: Double?,
    val locationLongitude: Double?,
    val smallAvatar: String?,
    val bigAvatar: String?
) : BaseEntityRoom {
    companion object {
        fun from(user: User): UserRoom {
            return UserRoom(
                id = user.id,
                name = user.name,
                lastName = user.lastName,
                email = user.email,
                gender = user.gender,
                locationLatitude = user.locationLatitude,
                locationLongitude = user.locationLongitude,
                smallAvatar = user.smallAvatar,
                bigAvatar = user.bigAvatar
            )
        }
    }

    fun toUser(): User {
        return User(
            id = id,
            name = name,
            lastName = lastName,
            email = email,
            gender = gender,
            locationLatitude = locationLatitude,
            locationLongitude = locationLongitude,
            smallAvatar = smallAvatar,
            bigAvatar = bigAvatar
        )
    }
}