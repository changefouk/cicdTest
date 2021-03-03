package com.appsynthassignment.data.domain

import com.appsynthassignment.data.model.response.ProfileInformationResponseMapper
import com.appsynthassignment.data.repository.DataRepository
import com.appsynthassignment.util.ResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetProfileAndNotificationUseCase {
    suspend fun getProfileInformation(): ResultResponse<ProfileInformationResponseMapper>
}

class GetProfileAndNotificationUseCaseImp(
    private val repository: DataRepository
) : GetProfileAndNotificationUseCase {

    override suspend fun getProfileInformation()
            : ResultResponse<ProfileInformationResponseMapper> = withContext(Dispatchers.IO) {
        val profile = repository.getProfile()
        profile.data?.let { profileData ->
            val notification = repository.getNotification(profileData.userId)
            if (notification.isSuccess && notification.data != null) {
                ResultResponse.success(
                    ProfileInformationResponseMapper(
                        profile = profileData,
                        notification = notification.data
                    )
                )
            } else {
                resultError(notification.exception)
            }
        } ?: resultError(profile.exception)
    }

    private fun resultError(exception: Exception?) =
        ResultResponse.error<ProfileInformationResponseMapper>(exception)
}