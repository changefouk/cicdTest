package com.appsynthassignment.util.intercepor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder().apply {
            addHeader("User-Agent", "android:")
            addHeader("Content-Type", "application/json")

            if ("token".isNotEmpty()) {
                addHeader("Authorization", "Bearer ")
            }
        }
        return chain.proceed(request.build())
    }
}