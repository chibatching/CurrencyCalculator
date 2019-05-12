package com.chibatching.currencycalculator.data.source.api

import kotlinx.serialization.Serializable

@Serializable
data class SupportedCurrencyListEntity(
    val currencies: Map<String, String>
)

@Serializable
data class CurrencyRateListEntity(
    val timestamp: Long,
    val quotes: Map<String, Float>
)
