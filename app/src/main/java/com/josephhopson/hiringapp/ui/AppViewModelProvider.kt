package com.josephhopson.hiringapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.josephhopson.hiringapp.HiringAppApplication
import com.josephhopson.hiringapp.ui.home.HomeViewModel

/**
 * Factory for app view models
 */
object AppViewModelProvider {
    // Initializer for HomeViewModel
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                hiringApplication().container.hiringRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [HiringAppApplication].
 */
fun CreationExtras.hiringApplication(): HiringAppApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HiringAppApplication)