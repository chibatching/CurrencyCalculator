package com.chibatching.currencycalculator.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "supported_currency_list")
data class SupportedCurrencyListEntity(
    val retrieveTimeMillis: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)

@Entity(
    tableName = "supported_currency",
    foreignKeys = [
        ForeignKey(
            entity = SupportedCurrencyListEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("list_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CurrencyEntity(
    @PrimaryKey val currencyCode: String,
    val countryName: String,
    @ColumnInfo(name = "list_id") val listId: Long
)
