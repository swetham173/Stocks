package com.example.stocks

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
// actual work of wht it should fetch using methods like(get) when api called
interface StockApiService {
    @GET("query")
    suspend fun getStockData(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String
    ):  Response<StockResponse>
//the response will be in the format of data model i.e StockResponse
}
