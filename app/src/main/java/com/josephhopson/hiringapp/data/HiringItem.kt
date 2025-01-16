package com.josephhopson.hiringapp.data

import kotlinx.serialization.Serializable

@Serializable
data class HiringItem (
    val id : Int = -1,
    val listId : Int = -1,
    val name : String? = null
)