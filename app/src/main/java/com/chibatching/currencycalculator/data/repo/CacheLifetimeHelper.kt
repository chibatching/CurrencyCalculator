package com.chibatching.currencycalculator.data.repo

import java.util.concurrent.TimeUnit

fun Long.inCacheLifetime() =
    TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - this) < 30
