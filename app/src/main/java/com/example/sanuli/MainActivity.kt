package com.example.sanuli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanuli.ui.theme.SanuliTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),) { innerPadding ->
                    Game( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
    var kirjain1 by remember { mutableStateOf("") }
    var arvauksienMaara by remember { mutableIntStateOf(0) }
    val sanat = listOf<String>("Koira")
    var sana by remember(sanat) { mutableStateOf(sanat.random()) }

    Text("WORDLE FI", color = Color.White)

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 5
    ) {
        OutlinedTextField(
            value = kirjain1,
            onValueChange = { if (it.length <= 1) kirjain1 = it },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(50.dp)
                .background(Color.White)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )
        OutlinedTextField(
            value = kirjain1,
            onValueChange = { if (it.length <= 1) kirjain1 = it },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(50.dp)
                .background(Color.White)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )
        OutlinedTextField(
                value = kirjain1,
            onValueChange = { if (it.length <= 1) kirjain1 = it },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(50.dp)
                .background(Color.White)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )
        OutlinedTextField(
            value = kirjain1,
            onValueChange = { if (it.length <= 1) kirjain1 = it },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(50.dp)
                .background(Color.White)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )
        OutlinedTextField(
            value = kirjain1,
            onValueChange = { if (it.length <= 1) kirjain1 = it },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .width(50.dp)
                .background(Color.White)
                .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
        )
    }

}





//var kirjain = remember(sana) {
//Random.nextInt(sananPituus)
// }

// var sana = remember(sanat) {
// sanat.random()
// }
