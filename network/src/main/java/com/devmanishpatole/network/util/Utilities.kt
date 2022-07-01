package com.devmanishpatole.network.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response

/**
 * @author Manish Patole, contact@devmanishpatole.com
 * @since 30/06/22
 */

/**
 * Retrofit only gives generic response body when status is Successful.
 * This extension will also parse error body and will give generic response.
 */
inline fun <reified T> Response<T>.getResponse(): T {
    val responseBody = body()
    return if (this.isSuccessful && responseBody != null) {
        responseBody
    } else {
        fromJson<T>(errorBody()!!.string())!!
    }
}

val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

inline fun <reified T> fromJson(json: String) = moshi.adapter(T::class.java).fromJson(json)

inline val <reified T> T.json get() = moshi.adapter(T::class.java).toJson(this)
