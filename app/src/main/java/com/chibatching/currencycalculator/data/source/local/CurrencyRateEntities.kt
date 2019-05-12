package com.chibatching.currencycalculator.data.source.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currency_rate_list",
    indices = [Index(value = ["source"], unique = true)]
)
data class CurrencyRateListEntity(
    val source: String,
    val retrieveTimeMillis: Long,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(
    tableName = "currency_rate",
    primaryKeys = ["source", "target"],
    foreignKeys = [
        ForeignKey(
            entity = CurrencyRateListEntity::class,
            parentColumns = arrayOf("source"),
            childColumns = arrayOf("source"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CurrencyRateEntity(
    val source: String,
    val target: String,
    val rate: Float
)
