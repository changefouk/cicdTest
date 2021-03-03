package com.appsynthassignment.data.model.uimodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileInformationUiModel(
    val profile: ProfileUiModel = ProfileUiModel(),
    val notification: List<NotificationUiModel> = listOf()
) : Parcelable