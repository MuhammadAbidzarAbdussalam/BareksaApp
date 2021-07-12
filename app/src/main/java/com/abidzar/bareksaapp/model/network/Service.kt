package com.abidzar.bareksaapp.model.network

import com.abidzar.bareksaapp.model.data.chart.ChartProduct
import com.abidzar.bareksaapp.model.data.product.DetailProduct
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("detail")
    fun getProductDetailApi(@Query("productCodes") query: List<String>):Call<DetailProduct>

    @GET("chart")
    fun getChartDetailApi(@Query("productCodes") query: List<String>):Call<ChartProduct>

}