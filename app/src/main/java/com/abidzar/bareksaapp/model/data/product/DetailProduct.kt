package com.abidzar.bareksaapp.model.data.product

data class DetailProduct(
    val code: Int,
    val `data`: List<Data>,
    val error: String,
    val message: String,
    val total_data: Int
)