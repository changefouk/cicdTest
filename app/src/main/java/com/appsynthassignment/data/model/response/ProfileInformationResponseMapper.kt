package com.appsynthassignment.data.model.response

import com.appsynthassignment.data.base.BaseResponse
import com.appsynthassignment.data.model.uimodel.NotificationUiModel
import com.appsynthassignment.data.model.uimodel.ProfileUiModel

data class ProfileInformationResponseMapper(
    val profile: ProfileResponseModel,
    val notification: List<NotificationResponseModel>
) : BaseResponse() {

    fun getProfileUi(): ProfileUiModel =
        ProfileUiModel(
            userId = profile.userId,
            firstName = profile.firstName,
            lastName = profile.lastName,
            avatar = profile.avatar,
            _followers = profile.followers,
            _following = profile.following,
            _likes = profile.likes
        )

    fun getNotificationUiModel()
            : List<NotificationUiModel> {
        return if (!notification.isNullOrEmpty()) {
            notification.map {
                NotificationUiModel(
                    _created = it.created,
                    text = it.text
                )
            }
        } else {
            listOf()
        }
    }
}