package com.chibatching.currencycalculator.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.chibatching.currencycalculator.data.model.CurrencyRate

@Dao
interface CurrencyRateDao {

    @Transaction
    suspend fun insertRateList(retrieveTime: Long, rates: List<CurrencyRate>) {
        rates
            .groupBy {
                it.source
            }
            .map {
                val id = _insertRateInfo(CurrencyRateListEntity(it.key, retrieveTime))
                it.value.forEach { rate ->
                    _insertRate(CurrencyRateEntity(rate.source, rate.target, rate.rate))
                }
            }
    }

    @Insert
    suspend fun _insertRateInfo(rateList: CurrencyRateListEntity): Long

    @Insert
    suspend fun _insertRate(rate: CurrencyRateEntity)

    @Query("SELECT source, target, rate FROM currency_rate WHERE source = :source")
    suspend fun getCurrencyRate(source: String): List<CurrencyRate>

    @Query("DELETE FROM currency_rate_list WHERE source = :source")
    suspend fun deleteCurrencyRateList(source: String)

    @Query("SELECT retrieveTimeMillis FROM currency_rate_list WHERE source = :source LIMIT 1")
    suspend fun getRetrieveTimeMillis(source: String): List<Long>
}
