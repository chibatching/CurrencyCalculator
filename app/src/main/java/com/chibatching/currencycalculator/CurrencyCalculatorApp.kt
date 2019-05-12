package com.chibatching.currencycalculator

import android.app.Application
import com.chibatching.currencycalculator.data.source.local.DatabaseProvider

class CurrencyCalculatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.create(this)
    }
}
