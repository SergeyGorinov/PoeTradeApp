package com.poe.tradeapp.charts_feature.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class CurrenciesOverviewResponse(
    val lines: List<CurrencyOverview>,
    val currencyDetails: List<CurrencyDetail>,
    val language: Language
)

@Serializable
internal data class CurrencyOverview(
    val currencyTypeName: String,
    val pay: Info?,
    val receive: Info,
    val paySparkLine: SparkLine,
    val receiveSparkLine: SparkLine,
    val chaosEquivalent: Float,
    val lowConfidencePaySparkLine: SparkLine,
    val lowConfidenceReceiveSparkLine: SparkLine,
    val detailsId: String
)

@Serializable
internal data class Info(
    val id: Int,
    val league_id: Int,
    val pay_currency_id: Int,
    val get_currency_id: Int,
    val sample_time_utc: String,
    val count: Int,
    val value: Double,
    val data_point_count: Int,
    val includes_secondary: Boolean,
    val listing_count: Int
)

@Serializable
internal data class SparkLine(
    val data: List<Float?>,
    val totalChange: Float
)

@Serializable
internal data class CurrencyDetail(
    val id: Int,
    val icon: String,
    val name: String,
    val tradeId: String?
)

@Serializable
internal data class Language(
    val name: String,
    val translations: Map<String, String>
)
