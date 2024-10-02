package com.example.stocks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
class StockViewModelFactory(private val repository: StockRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
//The factory allows passing dependencies (in this case, StockRepository) into the VIEWMODEL.
// Since ViewModelProvider doesnt know how to pass arguments by default,
// the factory is necessary to inject these dependencies.