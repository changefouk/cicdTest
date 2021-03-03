package com.appsynthassignment.data.model.response

import com.appsynthassignment.data.base.BaseResponse
import com.appsynthassignment.data.model.uimodel.ProfileUiModel
import com.google.gson.annotations.SerializedName

data class ProfileResponseModel(
    @SerializedName("userId") val userId: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("avatar") val avatar: String,
    @SerializedName("followers") val followers: Int,
    @SerializedName("following") val following: Int,
    @SerializedName("likes") val likes: Int
) : BaseResponse()