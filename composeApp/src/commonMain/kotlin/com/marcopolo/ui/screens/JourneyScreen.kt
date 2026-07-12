// JourneyScreen.kt — travel log dashboard with map
package com.marcopolo.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcopolo.model.Journey

@Composable
fun JourneyScreen() {
    // TODO: load from persistence
    val journeys = remember { mutableStateListOf<Journey>() }
    val totalKm = journeys.sumOf { it.totalKm }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Marco Polo Moto 🏍", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            // Stats banner
            Surface(
                Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Row(Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatItem("旅程", "${journeys.size}")
                    StatItem("里程", "${"%.0f".format(totalKm)} km")
                    StatItem("照片", "${journeys.sumOf { it.photos.size }}")
                }
            }

            if (journeys.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🏍", fontSize = 48.sp)
                        Spacer(Modifier.height(12.dp))
                        Text("还没有旅程记录", color = Color.Gray)
                        Text("开始你的第一次骑行吧！", color = Color.Gray.copy(alpha = 0.6f), fontSize = 13.sp)
                    }
                }
            } else {
                LazyColumn(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    items(journeys.sortedByDescending { it.startDate }) { journey ->
                        JourneyCard(journey)
                    }
                }
            }
        }
    }
}

@Composable
fun JourneyCard(journey: Journey) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(journey.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(4.dp))
            Text("${"%.1f".format(journey.totalKm)} km · ${journey.route.size} 个航点",
                color = Color.Gray, fontSize = 14.sp)
            if (journey.notes.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(journey.notes, color = Color.DarkGray, fontSize = 13.sp, maxLines = 2)
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}
