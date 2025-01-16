package com.josephhopson.hiringapp.data

import android.util.Log
import com.josephhopson.hiringapp.data.network.FetchApi
import retrofit2.HttpException
import java.io.IOException

/**
 * A Sealed Interface for the Hiring API results
 */
sealed interface HiringApiResult {
    data class Success(val items: Map<Int, List<HiringItem>>): HiringApiResult
    data object Error: HiringApiResult
}

/**
 * Interface for the Hiring Repository
 */
interface HiringRepository {
    suspend fun getHiringResults(): HiringApiResult
}

/**
 * Default implementation of the Hiring Repository
 */
class NetworkHiringRepository: HiringRepository {
    override suspend fun getHiringResults(): HiringApiResult {
        return try {
            val result = FetchApi.retrofitService.getHiringResults()
            val cleanedResult = result
                .filter { !it.name.isNullOrBlank() } // filter out objects with blank and null names
                .sortedBy { it.listId } // get the categories in order
                .groupBy { it.listId } // group by category
                .mapValues { it.value.sortedBy { it.name } } // sort by name inside each category
            if (cleanedResult.isEmpty()) {
                Log.e("FetchApi", "list is empty")
                HiringApiResult.Error
            } else {
                HiringApiResult.Success(cleanedResult)
            }
        } catch (e: IOException) {
            Log.e("FetchApi:IOException", e.message.toString())
            HiringApiResult.Error
        } catch (e: HttpException) {
            Log.e("FetchApi:HttpException", e.message.toString())
            HiringApiResult.Error
        }
    }
}