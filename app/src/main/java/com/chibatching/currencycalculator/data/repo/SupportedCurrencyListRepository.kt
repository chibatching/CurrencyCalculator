package com.chibatching.currencycalculator.data.repo

import com.chibatching.currencycalculator.data.model.Currency
import com.chibatching.currencycalculator.data.source.SupportedCurrencyListSource
import com.chibatching.currencycalculator.data.source.api.RetrofitProvider
import com.chibatching.currencycalculator.data.source.api.SupportedCurrencyListApiSource
import com.chibatching.currencycalculator.data.source.local.DatabaseProvider
import com.chibatching.currencycalculator.data.source.local.SupportedCurrencyListLocalSource
import java.util.concurrent.TimeUnit

class SupportedCurrencyListRepository {

    private val apiSource: SupportedCurrencyListSource =
        SupportedCurrencyListApiSource(RetrofitProvider.service)

    private val localSource: SupportedCurrencyListSource =
        SupportedCurrencyListLocalSource(DatabaseProvider.cacheDatabase.currencyListDao())

    suspend fun getCurrencyList(): List<Currency> {
        if (localSource.lastUpdateTime().inCacheLifetime()) {
            val local = localSource.getSupportedCurrencyList()
            if (local.isNotEmpty()) {
                return local
            }
        }
        return try {
            apiSource.getSupportedCurrencyList().also {
                localSource.saveSupportedCurrencyList(System.currentTimeMillis(), it)
            }
        } catch (e: Exception) {
            localSource.getSupportedCurrencyList()
        }
    }
}
