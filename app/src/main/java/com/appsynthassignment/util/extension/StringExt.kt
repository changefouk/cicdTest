package com.appsynthassignment.util.extension

import android.annotation.SuppressLint
import java.lang.Exception
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(): String {
    val pattern = "yyyy-MM-d'T'HH:mm:ss.SSS'Z'"
    val formatPattern = "dd MMM yyyy hh:mm a"
    return try {
        val parser = SimpleDateFormat(pattern)
        val formatter = SimpleDateFormat(formatPattern)
        formatter.format(parser.parse(this))
    } catch (e: Exception) {
        this
    }
}



