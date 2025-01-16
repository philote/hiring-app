package com.josephhopson.hiringapp

import android.app.Application
import com.josephhopson.hiringapp.data.AppContainer
import com.josephhopson.hiringapp.data.AppDataContainer

class HiringAppApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}