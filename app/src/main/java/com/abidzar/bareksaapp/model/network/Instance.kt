package com.abidzar.bareksaapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instance {

    companion object {
        val baseURL = "https://ae1cdb19-2532-46fa-9b8f-cce01702bb1e.mock.pstmn.io/takehometest/apps/compare/"

        fun getInstance() : Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

}