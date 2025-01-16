package com.josephhopson.hiringapp.data.network

import com.josephhopson.hiringapp.data.HiringItem
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

private const val BASE_FETCH_URL = "https://fetch-hiring.s3.amazonaws.com/"
// Don't blow up if the API adds extra stuff
private val format = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_FETCH_URL)
    .addConverterFactory(format.asConverterFactory("application/json; charset=UTF8".toMediaType()))
    .build()

/**
 * Interface for the Fetch Hiring API
 */
interface FetchApiService {
    @GET("hiring.json")
    suspend fun getHiringResults(): List<HiringItem>
}

/**
 * Singleton for the Fetch API
 */
object FetchApi {
    val retrofitService: FetchApiService by lazy {
        retrofit.create(FetchApiService::class.java)
    }
}