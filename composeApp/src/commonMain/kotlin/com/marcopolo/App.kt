// App.kt — marco-polo entry point
package com.marcopolo

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.marcopolo.ui.screens.JourneyScreen

@Composable
fun App() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        JourneyScreen()
    }
}
