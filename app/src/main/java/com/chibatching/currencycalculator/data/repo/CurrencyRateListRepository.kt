package com.chibatching.currencycalculator.data.repo

import com.chibatching.currencycalculator.data.model.CurrencyRate
import com.chibatching.currencycalculator.data.source.CurrencyRateListSource
import com.chibatching.currencycalculator.data.source.api.CurrencyRateListApiSource
import com.chibatching.currencycalculator.data.source.api.RetrofitProvider
import com.chibatching.currencycalculator.data.source.local.CurrencyRateListLocalSource
import com.chibatching.currencycalculator.data.source.local.DatabaseProvider

class CurrencyRateListRepository {

    private val apiSource: CurrencyRateListSource =
        CurrencyRateListApiSource(RetrofitProvider.service)

    private val localSource: CurrencyRateListSource =
        CurrencyRateListLocalSource(DatabaseProvider.cacheDatabase.currencyRateListDao())

    suspend fun getCurrencyRateList(source: String): List<CurrencyRate> {
        if (localSource.lastUpdateTime(source).inCacheLifetime()) {
            val local = localSource.getCurrencyRateList(source)
            if (local.isNotEmpty()) {
                return local
            }
        }
        return try {
            apiSource.getCurrencyRateList(source).also {
                localSource.saveCurrencyRateList(System.currentTimeMillis(), it)
            }
        } catch (e: Exception) {
            localSource.getCurrencyRateList(source)
        }
    }
}
