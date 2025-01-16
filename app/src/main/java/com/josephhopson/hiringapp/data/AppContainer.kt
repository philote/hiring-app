package com.josephhopson.hiringapp.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val hiringRepository: HiringRepository
}

/**
 * [AppContainer] implementation that provides instance of [HiringRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {

    /**
     * Implementation for [HiringRepository]
     */
    override val hiringRepository: HiringRepository by lazy {
        NetworkHiringRepository()
    }
}
