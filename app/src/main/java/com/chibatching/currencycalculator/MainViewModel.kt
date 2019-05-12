package com.chibatching.currencycalculator

import androidx.lifecycle.*
import com.chibatching.currencycalculator.data.repo.CurrencyRateListRepository
import com.chibatching.currencycalculator.data.repo.SupportedCurrencyListRepository
import com.chibatching.currencycalculator.util.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import java.text.NumberFormat

class MainViewModel : ViewModel() {

    private val supportedCurrencyRepository = SupportedCurrencyListRepository()
    private val currencyRateRepository = CurrencyRateListRepository()

    val inputData = MutableLiveData<String>()

    val supportedCurrencyList = liveData {
        emit(supportedCurrencyRepository.getCurrencyList().map { it.currencyCode })
    }

    private val selectedCurrency = MutableLiveData<String>()

    private val currencyRates = liveData {
        selectedCurrency.asFlow()
            .filterNotNull()
            .collect {
                emit(currencyRateRepository.getCurrencyRateList(it))
            }
    }

    private fun calculateResult(currencyCode: String): LiveData<String> = Transformations.map(inputData) { inputData ->
        val input = inputData?.toFloatOrNull() ?: return@map null
        val rate = currencyRates.value?.find { it.target == currencyCode }?.rate ?: return@map null
        NumberFormat.getInstance().format(input * rate)
    }

    val currencyCalcResults = Transformations.map(currencyRates) { rates ->
        rates?.map { CurrencyCalcResult(it.target, calculateResult(it.target)) }
    }

    fun onCurrencySelected(position: Int) {
        selectedCurrency.value = supportedCurrencyList.value?.get(position)
    }

    data class CurrencyCalcResult(
        val currencyCode: String,
        val calculateResult: LiveData<String>
    )
}
