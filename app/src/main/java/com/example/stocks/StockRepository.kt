package com.example.stocks

import retrofit2.Response

//abstraction layer between ViewModel and API Service to maintain clean code
//also we can handle the data logic here for example checking if cache exist if not do an api call store it in cache
class StockRepository(private val apiService: StockApiService) {

    suspend fun getStock(stockId: String): Response<StockResponse> {
        return apiService.getStockData(symbol=stockId, apiKey = "YM2PXPMOB4FK71PZ")
    }
}