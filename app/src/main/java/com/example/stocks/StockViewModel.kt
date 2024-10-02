package com.example.stocks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


//from repository class
class StockViewModel(private val repository: StockRepository) : ViewModel() {
//init to null until req is made
    var stockResponse by mutableStateOf<StockResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchStock(stockId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            Log.d("StockViewModel", "Fetching stock data for symbol: $stockId")

            try {
                val response: Response<StockResponse> = repository.getStock(stockId)
                Log.d("StockViewModel", "API Response Code: ${response.code()}")

                if (response.isSuccessful) {
                    stockResponse = response.body()
                    Log.d("StockViewModel", "Stock data retrieved successfully: $stockResponse")

                    if (stockResponse == null) {
                        errorMessage = "No data found for symbol: $stockId"
                        Log.d("StockViewModel", errorMessage!!)

                    }
                } else {
                    errorMessage = when (response.code()) {
                        404 -> "Error: Stock symbol not found."
                        400 -> "Error: Bad request."
                        else -> "Error: ${response.message()}"

                    }
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("StockViewModel", "Error code: ${response.code()}, message: $errorBody")
                }
            } catch (e: Exception) {
                errorMessage = "Error: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

}