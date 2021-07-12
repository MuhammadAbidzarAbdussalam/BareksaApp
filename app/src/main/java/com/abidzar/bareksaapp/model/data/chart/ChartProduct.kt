package com.abidzar.bareksaapp.model.data.chart

data class ChartProduct(
    val code: Int,
    val `data`: Map<String, Hashmap>,
    val error: String,
    val message: String,
    val total_data: Int
)