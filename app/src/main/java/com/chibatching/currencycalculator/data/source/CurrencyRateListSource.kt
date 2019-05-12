package com.chibatching.currencycalculator.data.source

import com.chibatching.currencycalculator.data.model.CurrencyRate

interface CurrencyRateListSource {

    suspend fun getCurrencyRateList(source: String): List<CurrencyRate>

    suspend fun saveCurrencyRateList(retrieveTimeMills: Long, list: List<CurrencyRate>)

    suspend fun lastUpdateTime(source: String): Long
}
