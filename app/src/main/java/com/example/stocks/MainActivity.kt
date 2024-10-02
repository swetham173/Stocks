package com.example.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stocks.ui.theme.StocksTheme

class MainActivity : ComponentActivity() {
    private val stockViewModel: StockViewModel by viewModels {
        StockViewModelFactory(StockRepository(RetrofitInstance.api))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StocksTheme {
                StockScreen(viewModel = stockViewModel)
            }
        }
    }
}
