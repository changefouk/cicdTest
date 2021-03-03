package com.appsynthassignment.di

import android.content.Context
import com.appsynthassignment.constant.ApiConstant
import com.appsynthassignment.data.api.ApiService
import com.appsynthassignment.util.intercepor.InternetConnectionInterceptor
import com.appsynthassignment.util.intercepor.ResponseConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { provideHttpLoggingInterceptor() }
    single { provideInternetConnectionInterceptor(androidContext()) }
    single { provideResponseConnectionInterceptor() }
    single { provideOkHttpClient(get(), get(), get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

private fun provideInternetConnectionInterceptor(context: Context): InternetConnectionInterceptor =
    InternetConnectionInterceptor(context)

private fun provideResponseConnectionInterceptor(): ResponseConnectionInterceptor =
    ResponseConnectionInterceptor()

private fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    connectionInterceptor: InternetConnectionInterceptor,
    responseConnectionInterceptor: ResponseConnectionInterceptor
): OkHttpClient = OkHttpClient()
    .newBuilder()
    .connectTimeout(30L, TimeUnit.SECONDS)
    .readTimeout(30L, TimeUnit.SECONDS)
    .addNetworkInterceptor(httpLoggingInterceptor)
    .addInterceptor(connectionInterceptor)
    .addInterceptor(responseConnectionInterceptor)
    .build()

private fun provideRetrofit(okHttpClient: OkHttpClient) =
    Retrofit.Builder().baseUrl(ApiConstant.EndPoint.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)

