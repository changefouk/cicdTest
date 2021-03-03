package com.appsynthassignment.data.api

import com.appsynthassignment.constant.ApiConstant
import com.appsynthassignment.data.model.response.NotificationResponseModel
import com.appsynthassignment.data.model.response.ProfileResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(ApiConstant.EndPoint.API_PROFILE)
    suspend fun getProfile(): ProfileResponseModel

    @GET(ApiConstant.EndPoint.API_NOTIFICATION)
    suspend fun getNotification(
        @Path(value = ApiConstant.Value.USER_ID) userId: String
    ): List<NotificationResponseModel>
}