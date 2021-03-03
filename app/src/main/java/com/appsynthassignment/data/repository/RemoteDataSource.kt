package com.appsynthassignment.data.repository

import com.appsynthassignment.data.api.ApiService
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.data.model.response.ProfileResponseModel
import com.appsynthassignment.util.ResultResponse

interface RemoteDataSource {
    suspend fun getProfile(): ResultResponse<ProfileResponseModel>
    suspend fun getNotification(userId: String): ResultResponse<List<NotificationResponseModel>>
}

class RemoteDataSourceImp(
    private val api: ApiService
) : RemoteDataSource {
    override suspend fun getProfile(): ResultResponse<ProfileResponseModel> =
        try {
            ResultResponse.success(api.getProfile())
        } catch (e: Exception) {
            ResultResponse.error(e)
        }

    override suspend fun getNotification(userId: String)
            : ResultResponse<List<NotificationResponseModel>> =
        try {
            ResultResponse.success(api.getNotification(userId))
        } catch (e: Exception) {
            ResultResponse.error(e)
        }
}