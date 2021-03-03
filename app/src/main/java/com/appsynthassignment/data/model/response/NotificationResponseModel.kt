package com.appsynthassignment.data.model.response

import com.appsynthassignment.data.base.BaseResponse
import com.appsynthassignment.data.model.uimodel.NotificationUiModel
import com.google.gson.annotations.SerializedName

data class NotificationResponseModel(
    @SerializedName("created") val created: String,
    @SerializedName("text") val text: String
) : BaseResponse()