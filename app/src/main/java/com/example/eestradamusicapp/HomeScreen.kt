package com.example.eestradamusicapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eestradamusicapp.model.Album
import com.example.eestradamusicapp.Services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun HomeScreen(navController: NavController) {
    val BASE_URL = "https://music.juanfrausto.com/api/"
    var albums by remember { mutableStateOf(listOf<Album>()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) { service.getAllAlbums() }
            Log.i("HomeScreen", "Álbumes recibidos: ${result.size}")
            albums = result
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error: ${e.message}")
        } finally {
            loading = false
        }
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF6A1B9A), Color.White)))
                .padding(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Good Morning! Gustavo Estrada",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}
