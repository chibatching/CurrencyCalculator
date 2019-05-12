package com.chibatching.currencycalculator.data.source.local

import com.chibatching.currencycalculator.data.model.CurrencyRate
import com.chibatching.currencycalculator.data.source.CurrencyRateListSource

class CurrencyRateListLocalSource(
    private val database: CurrencyRateDao
) : CurrencyRateListSource {

    override suspend fun getCurrencyRateList(source: String): List<CurrencyRate> {
        return database.getCurrencyRate(source)
    }

    override suspend fun saveCurrencyRateList(retrieveTimeMills: Long, list: List<CurrencyRate>) {
        list
            .distinctBy {
                it.source
            }
            .forEach {
                database.deleteCurrencyRateList(it.source)
            }
        database.insertRateList(retrieveTimeMills, list)
    }

    override suspend fun lastUpdateTime(source: String): Long {
        return database.getRetrieveTimeMillis(source).firstOrNull() ?: 0
    }
}
