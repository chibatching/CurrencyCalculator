package com.chibatching.currencycalculator.data.source.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    lateinit var cacheDatabase: CacheDatabase

    fun create(context: Context) {
        if (::cacheDatabase.isInitialized) {
            return
        }
        cacheDatabase = Room.databaseBuilder(
            context.applicationContext,
            CacheDatabase::class.java,
            "cache-data"
        ).build()
    }
}
