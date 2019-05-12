package com.chibatching.currencycalculator.data.source.api

import com.chibatching.currencycalculator.data.model.Currency
import com.chibatching.currencycalculator.data.source.SupportedCurrencyListSource
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SupportedCurrencyListApiSource(
    private val service: RetrofitProvider.CurrencylayerService
) : SupportedCurrencyListSource {

    override suspend fun getSupportedCurrencyList() =
        suspendCancellableCoroutine<List<Currency>> { continuation ->
            val call = service.getSupportedCurrencyList()
            call.enqueue(object : Callback<SupportedCurrencyListEntity> {
                override fun onFailure(call: Call<SupportedCurrencyListEntity>, t: Throwable) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(t)
                    }
                }

                override fun onResponse(
                    call: Call<SupportedCurrencyListEntity>,
                    response: Response<SupportedCurrencyListEntity>
                ) {
                    response.body()
                        ?.currencies
                        ?.map {
                            Currency(it.key, it.value)
                        }
                        ?.let {
                            continuation.resume(it)
                        }
                        ?: continuation.resumeWithException(UnknownError())
                }
            })
            continuation.invokeOnCancellation {
                call.cancel()
            }
        }

    override suspend fun saveSupportedCurrencyList(retrieveTime: Long, list: List<Currency>) {
        throw UnsupportedOperationException()
    }

    override suspend fun lastUpdateTime(): Long {
        throw UnsupportedOperationException()
    }
}
