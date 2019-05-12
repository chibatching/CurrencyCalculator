package com.chibatching.currencycalculator.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        SupportedCurrencyListEntity::class,
        CurrencyEntity::class,
        CurrencyRateListEntity::class,
        CurrencyRateEntity::class
    ],
    version = 1
)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun currencyListDao(): SupportedCurrencyListDao

    abstract fun currencyRateListDao(): CurrencyRateDao
}
