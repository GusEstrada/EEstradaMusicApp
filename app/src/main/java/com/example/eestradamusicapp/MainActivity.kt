package com.example.eestradamusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.eestradamusicapp.ui.theme.EEstradaMusicAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EEstradaMusicAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                }
            }
        }
    }
}
