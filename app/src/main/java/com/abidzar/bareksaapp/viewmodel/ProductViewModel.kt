package com.abidzar.bareksaapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abidzar.bareksaapp.model.data.chart.ChartProduct
import com.abidzar.bareksaapp.model.data.product.DetailProduct
import com.abidzar.bareksaapp.model.network.Instance
import com.abidzar.bareksaapp.model.network.Service
import retrofit2.Call
import retrofit2.Response

class ProductViewModel: ViewModel() {

    lateinit var detailProductData: MutableLiveData<DetailProduct>
    lateinit var chartProductData: MutableLiveData<ChartProduct>

    init {
        detailProductData = MutableLiveData()
        chartProductData = MutableLiveData()
    }

    fun getDetailProductDataObserver() : MutableLiveData<DetailProduct>{
        return detailProductData
    }

    fun getDetailChartDataObserver() : MutableLiveData<ChartProduct>{
        return chartProductData
    }

    fun makeApiCallGetProduct(productCode : List<String>) {

        val retroInstance = Instance.getInstance().create(Service::class.java)
        val call = retroInstance.getProductDetailApi(productCode)
        call.enqueue(object : retrofit2.Callback<DetailProduct> {
            override fun onResponse(call: Call<DetailProduct>, response: Response<DetailProduct>) {
                if (response.isSuccessful) {
                    detailProductData.postValue(response.body())
                } else {
                    detailProductData.postValue(null)
                }
            }

            override fun onFailure(call: Call<DetailProduct>, t: Throwable) {
              //  Toast.makeText(context, "Error getting data from API", Toast.LENGTH_LONG).show()
                detailProductData.postValue(null)
            }
        })
    }

    fun makeApiCallChartProduct(productCode : List<String>) {
        val retroInstance = Instance.getInstance().create(Service::class.java)
        val call = retroInstance.getChartDetailApi(productCode)
        call.enqueue(object : retrofit2.Callback<ChartProduct> {
            override fun onResponse(call: Call<ChartProduct>, response: Response<ChartProduct>) {
                if (response.isSuccessful) {
                    chartProductData.postValue(response.body())
                } else {
                    chartProductData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ChartProduct>, t: Throwable) {
              //  Toast.makeText(context, "Error getting data from API", Toast.LENGTH_LONG).show()
                chartProductData.postValue(null)
            }
        })
    }

}