package com.chibatching.currencycalculator.data.source

import com.chibatching.currencycalculator.data.model.Currency

interface SupportedCurrencyListSource {

    suspend fun getSupportedCurrencyList(): List<Currency>

    suspend fun saveSupportedCurrencyList(retrieveTime: Long, list: List<Currency>)

    suspend fun lastUpdateTime(): Long
}
