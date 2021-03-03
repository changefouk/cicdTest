package com.appsynthassignment.data.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileUiModel(
    val userId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    private val _followers: Int = 0,
    private val _following: Int = 0,
    private val _likes: Int = 0
) : Parcelable {
    fun getFullProfileName(): String = "$firstName $lastName"

    fun getFollower(): String = _followers.toString()

    fun getFollowing(): String = _following.toString()

    fun getLikes(): String = _likes.toString()
}