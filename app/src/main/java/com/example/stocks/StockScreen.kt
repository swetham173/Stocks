package com.example.stocks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StockScreen(viewModel: StockViewModel) {
    val stockResponse = viewModel.stockResponse
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage

    val stockId = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Stock Info Finder",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color(0xFF1A237E),
            modifier = Modifier.padding(bottom= 24.dp)
        )

        TextField(
            value = stockId.value,
            onValueChange = { stockId.value = it },
            label = { Text("Enter Stock Symbol") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom= 16.dp),
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
        )

        Button(
            onClick = { viewModel.fetchStock(stockId.value) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical= 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1A237E))
        ) {
        Text(
            text = "Get Stock Info",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator(color = Color(0xFF1A237E))
            }
            stockResponse != null -> {
                val data = stockResponse.globalQuote
                if (data != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        elevation = 8.dp,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Symbol: ${data.symbol}", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            StockInfoRow(label = "Open", value = data.open)
                            StockInfoRow(label = "High", value = data.high)
                            StockInfoRow(label = "Low", value = data.low)
                            StockInfoRow(label = "Price", value = data.price)
                            StockInfoRow(label = "Volume", value = data.volume)
                            StockInfoRow(label = "Latest Trading Day", value = data.latestTradingDay)
                            StockInfoRow(label = "Previous Close", value = data.previousClose)
                            StockInfoRow(label = "Change", value = data.change)
                            StockInfoRow(label = "Change Percent", value = data.changePercent)
                        }
                    }
                } else {
                    Text(
                        text = "No data found for the stock.",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            errorMessage != null -> {
                Text(
                    text = "Error: $errorMessage",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun StockInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label:", fontWeight = FontWeight.SemiBold)
        Text(text = value, fontWeight = FontWeight.Normal)
    }
}
