package com.chibatching.currencycalculator.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chibatching.currencycalculator.data.model.Currency

@Dao
interface SupportedCurrencyListDao {

    @Transaction
    suspend fun insertSupportedCurrencyList(retrieveTime: Long, currencies: List<Currency>) {
        val id = _insertListInfo(SupportedCurrencyListEntity(retrieveTime))
        val currencyList = currencies.map { CurrencyEntity(it.currencyCode, it.countryName, id) }
        _insertCurrencyList(currencyList)
    }

    @Insert
    suspend fun _insertListInfo(currencyList: SupportedCurrencyListEntity): Long

    @Insert
    suspend fun _insertCurrencyList(list: List<CurrencyEntity>)

    @Query("DELETE FROM supported_currency_list")
    suspend fun clearSupportedCurrencyList()

    @Query("SELECT currencyCode, countryName FROM supported_currency ORDER BY currencyCode")
    suspend fun getSupportedCurrencyList(): List<Currency>

    @Query("SELECT retrieveTimeMillis FROM supported_currency_list LIMIT 1")
    suspend fun getRetrieveTimeMillis(): List<Long>
}
