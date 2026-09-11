package com.example.sanuli

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun Popup(onDismissRequest: () -> Unit, isRight: String, sana: String) {
    // popup opens when the game is over
    Popup(
        // Positions the popup relative to the button
        alignment = Alignment.TopCenter,
        // Shifts the popup by (x, y) in pixels
        offset = IntOffset(0, 370),
        // Hides the popup when it is dismissed, for example, when the user clicks outside it
        onDismissRequest = { onDismissRequest() }
    ) {
        Box(
            Modifier
                .background(Color.LightGray, RoundedCornerShape(4.dp))
                .padding(12.dp)
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
            ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(isRight, textAlign = TextAlign.Center)
                Text(sana)
                Button(
                    // clears all the values
                    onClick = { onDismissRequest() },
                    modifier = Modifier.offset(0.dp, 20.dp),
                    ) {
                        Text("Uudestaan")
                }
            }
        }
    }
}
