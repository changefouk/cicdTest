package com.appsynthassignment.data.model.uimodel

import android.os.Parcelable
import com.appsynthassignment.util.extension.toDateFormat
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationUiModel(
    val _created: String = "",
    val text: String = ""
) : Parcelable {
    fun getCreated(): String = _created.toDateFormat()
}