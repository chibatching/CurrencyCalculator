package com.chibatching.currencycalculator.data.source.local

import com.chibatching.currencycalculator.data.model.Currency
import com.chibatching.currencycalculator.data.source.SupportedCurrencyListSource

class SupportedCurrencyListLocalSource(
    private val database: SupportedCurrencyListDao
) : SupportedCurrencyListSource {

    override suspend fun getSupportedCurrencyList(): List<Currency> {
        return database.getSupportedCurrencyList()
    }

    override suspend fun saveSupportedCurrencyList(retrieveTime: Long, list: List<Currency>) {
        database.clearSupportedCurrencyList()
        database.insertSupportedCurrencyList(retrieveTime, list)
    }

    override suspend fun lastUpdateTime(): Long {
        return database.getRetrieveTimeMillis().firstOrNull() ?: 0
    }
}
