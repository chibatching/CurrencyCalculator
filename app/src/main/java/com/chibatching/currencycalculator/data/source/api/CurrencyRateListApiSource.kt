package com.chibatching.currencycalculator.data.source.api

import com.chibatching.currencycalculator.data.model.CurrencyRate
import com.chibatching.currencycalculator.data.source.CurrencyRateListSource
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CurrencyRateListApiSource(
    private val service: RetrofitProvider.CurrencylayerService
) : CurrencyRateListSource {

    override suspend fun getCurrencyRateList(source: String): List<CurrencyRate> =
        suspendCancellableCoroutine { continuation ->
            val call = service.getCurrencyLiveRateList(source)
            call.enqueue(object : Callback<CurrencyRateListEntity> {
                override fun onFailure(call: Call<CurrencyRateListEntity>, t: Throwable) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<CurrencyRateListEntity>,
                    response: Response<CurrencyRateListEntity>
                ) {
                    response.body()
                        ?.quotes
                        ?.map {
                            val sourceCurrency = it.key.substring(0..2)
                            val targetCurrency = it.key.substring(3..5)
                            CurrencyRate(sourceCurrency, targetCurrency, it.value)
                        }
                        ?.let {
                            continuation.resume(it)
                        }
                        ?: throw UnknownError()
                }
            })
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }

    override suspend fun saveCurrencyRateList(retrieveTimeMills: Long, list: List<CurrencyRate>) {
        throw UnsupportedOperationException()
    }

    override suspend fun lastUpdateTime(source: String): Long {
        throw UnsupportedOperationException()
    }
}
