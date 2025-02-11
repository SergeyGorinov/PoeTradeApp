package com.sgorinov.exilehelper.charts_feature.presentation.models

import com.github.mikephil.charting.data.LineDataSet

internal data class HistoryModel(
    val name: String,
    val type: String?,
    val icon: String?,
    val tradeId: String?,
    val sellingGraphData: LineDataSet?,
    val buyingGraphData: LineDataSet?,
    val sellingValue: Float?,
    val buyingValue: Float,
    val sellingSparkLine: LineDataSet?,
    val buyingSparkLine: LineDataSet,
    val sellingTotalChange: Float?,
    val buyingTotalChange: Float
)