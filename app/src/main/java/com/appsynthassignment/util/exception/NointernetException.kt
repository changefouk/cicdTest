package com.appsynthassignment.util.exception

import android.content.Context
import com.appsynthassignment.R
import java.io.IOException

class NoInternetException(private val context: Context) : IOException() {
    override val message: String?
        get() = context.getString(R.string.no_internet_exception)
}
