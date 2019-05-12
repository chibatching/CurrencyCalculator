package com.chibatching.currencycalculator.data.model

data class CurrencyRate(
    val source: String,
    val target: String,
    val rate: Float
)
