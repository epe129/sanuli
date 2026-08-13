package com.example.sanuli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Game(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var userinput by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var arvauksienMaara by remember { mutableIntStateOf(0) }

    val sanat = listOf<String>("moi")

    if (sanat.isEmpty()) {
        Text("No words available", color = Color.White)
        return
    }

    // var sana = remember(sanat) {
    // sanat.random()
    // }

    var sana by remember(sanat) { mutableStateOf(sanat.random()) }

    var sananPituus by remember { mutableIntStateOf(sana.length)}

    var n by remember { mutableStateOf("")}

    var x by remember { mutableIntStateOf(0) }

    //var kirjain = remember(sana) {
    //Random.nextInt(sananPituus)
    // }


    var kirjain by remember(sana) { mutableStateOf(Random.nextInt(sananPituus)) }

    // luodaan vihje jossa on yksi sanan kirjain
    while (x < sananPituus) {
        if (x == kirjain) {
            n += " " + sana[kirjain] + " "
        } else {
            n += " _ "
        }
        x += 1
    }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 1
    ) {

        Text("Arvaa sana:", color = Color(0xFFFFFFFF))

        Text(n, color = Color(0xFFFFFFFF), modifier = Modifier
            .padding(top = 10.dp)
            .padding(bottom = 3.dp))

        FlowRow (
            modifier = Modifier
                .height(75.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.Center,
            itemVerticalAlignment = Alignment.CenterVertically,
            maxItemsInEachRow = 2
        ) {

            TextField(
                value = userinput,
                // maksimi merkkien määrä on 20 merkkiä
                onValueChange = { if (it.length <= 20 ) userinput = it },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    cursorColor = Color.Black,
                ),
                maxLines = 1,
                modifier = Modifier
                    .heightIn(min = 30.dp, max = 50.dp)
                    .widthIn(min = 220.dp, max = 250.dp),
                shape = RoundedCornerShape(16.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )

            Button(onClick = {
                if (userinput != sana) {
                    text = "Väärin"
                    arvauksienMaara += 1
                } else {
                    arvauksienMaara += 1
                    showDialog = true
                    userinput = ""
                    text = ""
                } },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xff888888),
                    containerColor = Color(0xff444444)
                )
            ) {
                Text("Arvaa", color = Color(0xFFFFFFFF))
            }
        }
        Text(text, color = Color(0xFFFFFFFF))
    }

}
