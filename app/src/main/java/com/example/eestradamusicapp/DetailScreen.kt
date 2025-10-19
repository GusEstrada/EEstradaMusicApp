
package com.example.eestradamusicapp


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import com.example.eestradamusicapp.model.Album
import com.example.eestradamusicapp.Services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun DetailScreen(id: String, navController: NavController) {
    val BASE_URL = "https://music.juanfrausto.com/api/"
    val album = remember { mutableStateOf<Album?>(null) }
    val loading = remember { mutableStateOf(true) }
    var isPlaying by remember { mutableStateOf(false) }


    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            album.value = withContext(Dispatchers.IO) { service.getAlbumById(id) }
        } catch (e: Exception) {
            Log.e("DetailScreen", e.toString())
        } finally {
            loading.value = false
        }
    }


    if (loading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        album.value?.let { a ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
            ) {
                Box(modifier = Modifier.height(300.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(a.image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(0xAA6A1B9A))
                    )
                    IconButton(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = a.title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = a.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Row {
                            IconButton(onClick = { isPlaying = true }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                            }
                            IconButton(onClick = { /* shuffle */ }) {
                                Icon(Icons.Default.Shuffle, contentDescription = "Shuffle", tint = Color.White)
                            }
                        }
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "About this album",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = a.description,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                    }
                }


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "Artist: ${a.artist}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(16.dp)
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))


                val tracks = List(10) { i -> "${a.title} • Track ${i + 1}" }


                tracks.forEach { track ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(a.image),
                                contentDescription = track,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(text = track, style = MaterialTheme.typography.bodyLarge)
                                Text(text = a.artist, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))


                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = a.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                            Text(
                                text = a.artist,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }


                        IconButton(onClick = { isPlaying = !isPlaying }) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause" else "Play",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
