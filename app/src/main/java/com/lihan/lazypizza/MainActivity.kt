package com.lihan.lazypizza

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import kotlin.text.set



//@Serializable
//data class Product(
//    val id: String = "",
//    val name: String = "",
//    val price: Double = 0.0,
//    val category: String = "",
//    val imageUrl: String = "",
//    val ingredients: List<String> = listOf()
//)
//
//
//@Serializable
//data class ToppingDto(
//    val id: String = "",
//    val name: String = "",
//    val price: Double = 0.0,
//    val imageUrl: String = "",
//)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LazyPizzaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}