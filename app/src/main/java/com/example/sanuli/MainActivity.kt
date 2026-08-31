package com.example.sanuli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.sanuli.ui.theme.SanuliTheme
import kotlin.collections.mutableListOf
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Gray) { innerPadding ->
                    Game(
                        modifier = Modifier.padding(innerPadding),
                        context = this
                    )
                }
            }
        }
    }
}

@Composable
fun Game(context: Context, modifier: Modifier = Modifier) {
    var showContent by remember { mutableStateOf(true) }
    var isPopupOpen by remember { mutableStateOf(false) }
    var isRight by remember { mutableStateOf("ARVASIT OIKEIN!!") }
    val kirjaimet1 = remember { mutableStateListOf<String>() }
    val kirjaimet2 = remember { mutableStateListOf<String>() }
    val kirjaimet3 = remember { mutableStateListOf<String>() }
    val kirjaimet4 = remember { mutableStateListOf<String>() }
    val kirjaimet5 = remember { mutableStateListOf<String>() }
    val kirjaimet6 = remember { mutableStateListOf<String>() }
    val kaikkiKirjaimet = remember { mutableStateListOf<String>() }
    var arvauksienMaara by remember { mutableIntStateOf(0) }
    val sanat = mutableListOf<String>()
    var nykyKohta by remember { mutableIntStateOf(0) }
    var palautettu by remember { mutableStateOf(false) }
    var palautettu2 by remember { mutableStateOf(false) }
    var palautettu3 by remember { mutableStateOf(false) }
    var palautettu4 by remember { mutableStateOf(false) }
    var palautettu5 by remember { mutableStateOf(false) }
    var palautettu6 by remember { mutableStateOf(false) }
    val palautettuKirjaimet = remember { mutableStateListOf<String>() }
    val paikat = remember { mutableStateListOf<String>() }
    val kirjaimet = listOf<String>("Q", "W", "E","R","T","Y","U","I","O","P","Å","A","S","D","F","G","H","J","K","L","Ö","Ä","Z","X","C","V","B","N","M")
    var jsonString = ""

    // gets words from json file
    try {
        jsonString = context.assets.open("sanat.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    }
    // makes the json to list
    val sanatTOlist = jsonString.replace("""[{}:"]""".toRegex(), "").replace("sanat", "").replace("]", "").replace("[", "").lowercase().trim().split(",")
    // add every word to sanat list
    for (c in sanatTOlist) {
        sanat.add(c.trim())
    }
    // gets the random word from sanat list
    var sana by remember(sanat) { mutableStateOf(sanat.random()) }
    // adds kirjain to the list's
    fun add_kirjain(kirjain: String) {
        if (arvauksienMaara == 0) {
            kirjaimet1.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 1) {
            kirjaimet2.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 2) {
            kirjaimet3.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 3) {
            kirjaimet4.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 4) {
            kirjaimet5.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 5) {
            kirjaimet6.add(nykyKohta, kirjain)
            kaikkiKirjaimet.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
    }
    // check if user got the word correct
    fun tarkista() {
        if (arvauksienMaara == 0) {
            if (kirjaimet1.size < 5) {return}
            palautettu = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet1.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu) {
                isPopupOpen = true
                showContent = false
            }
        }
        if (arvauksienMaara == 1) {
            if (kirjaimet2.size < 5) {return}
            palautettu2 = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet2.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu2) {
                isPopupOpen = true
                showContent = false
            }
        }
        if (arvauksienMaara == 2) {
            if (kirjaimet3.size < 5) {return}
            palautettu3 = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet3.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu3) {
                isPopupOpen = true
                showContent = false
            }
        }
        if (arvauksienMaara == 3) {
            if (kirjaimet4.size < 5) {return}
            palautettu4 = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet4.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu4) {
                isPopupOpen = true
                showContent = false
            }
        }
        if (arvauksienMaara == 4) {
            if (kirjaimet5.size < 5) {return}
            palautettu5 = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet5.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu5) {
                isPopupOpen = true
                showContent = false
            }
        }
        if (arvauksienMaara == 5) {
            if (kirjaimet6.size < 5) {return}
            palautettu6 = true
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }

            if (kirjaimet6.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu6) {
                isPopupOpen = true
                showContent = false
            }
        }
        // adds every characters indes to the paikat list even if it's -1
        if (paikat.isEmpty()) {
            for (g in kirjaimet) {
                paikat.add(sana.uppercase().indexOf(g).toString())
                paikat.add(kaikkiKirjaimet.indexOf(g).toString())
            }
        }
        // checks if character is in sana and user has typed the character adds the index of the character to the list
        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Q' && kaikkiKirjaimet[index] =="Q") {
                paikat[0] = index.toString()
                paikat[1] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'W' && kaikkiKirjaimet[index] == "W") {
                paikat[2] = index.toString()
                paikat[3] = index.toString()
                break
            }

        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'E' && kaikkiKirjaimet[index] == "E") {
                paikat[4] = index.toString()
                paikat[5] = index.toString()
                break
            }

        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'R' && kaikkiKirjaimet[index] == "R") {
                paikat[6] = index.toString()
                paikat[7] = index.toString()
                break
            }

        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'T' && kaikkiKirjaimet[index] == "T") {
                paikat[8] = index.toString()
                paikat[9] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Y' && kaikkiKirjaimet[index] == "Y") {
                paikat[10] = index.toString()
                paikat[11] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'U' && kaikkiKirjaimet[index] == "U") {
                paikat[12] = index.toString()
                paikat[13] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'I' && kaikkiKirjaimet[index] == "I") {
                paikat[14] = index.toString()
                paikat[15] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'O' && kaikkiKirjaimet[index] == "O") {
                paikat[16] = index.toString()
                paikat[17] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'P' && kaikkiKirjaimet[index] == "P") {
                paikat[18] = index.toString()
                paikat[19] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Å' && kaikkiKirjaimet[index] == "Å") {
                paikat[20] = index.toString()
                paikat[21] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'A' && kaikkiKirjaimet[index] == "A") {
                paikat[22] = index.toString()
                paikat[23] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'S' && kaikkiKirjaimet[index] == "S") {
                paikat[24] = index.toString()
                paikat[25] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'D' && kaikkiKirjaimet[index] == "D") {
                paikat[26] = index.toString()
                paikat[27] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'F' && kaikkiKirjaimet[index] == "F") {
                paikat[28] = index.toString()
                paikat[29] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'G' && kaikkiKirjaimet[index] == "G") {
                paikat[30] = index.toString()
                paikat[31] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'H' && kaikkiKirjaimet[index] == "H") {
                paikat[32] = index.toString()
                paikat[33] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'J' && kaikkiKirjaimet[index] == "J") {
                paikat[34] = index.toString()
                paikat[35] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'K' && kaikkiKirjaimet[index] == "K") {
                paikat[36] = index.toString()
                paikat[37] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'L' && kaikkiKirjaimet[index] == "L") {
                paikat[38] = index.toString()
                paikat[39] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Ö' && kaikkiKirjaimet[index] == "Ö") {
                paikat[40] = index.toString()
                paikat[41] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Ä' && kaikkiKirjaimet[index] == "Ä") {
                paikat[42] = index.toString()
                paikat[43] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'Z' && kaikkiKirjaimet[index] == "Z") {
                paikat[44] = index.toString()
                paikat[45] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'X' && kaikkiKirjaimet[index] == "X") {
                paikat[46] = index.toString()
                paikat[47] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'C' && kaikkiKirjaimet[index] == "C") {
                paikat[48] = index.toString()
                paikat[49] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'V' && kaikkiKirjaimet[index] == "V") {
                paikat[50] = index.toString()
                paikat[51] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'B' && kaikkiKirjaimet[index] == "B") {
                paikat[52] = index.toString()
                paikat[53] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'N' && kaikkiKirjaimet[index] == "N") {
                paikat[54] = index.toString()
                paikat[55] = index.toString()
                break
            }
        }

        for ((index, value) in sana.withIndex()) {
            if (value.uppercaseChar() == 'M' && kaikkiKirjaimet[index] == "M") {
                paikat[56] = index.toString()
                paikat[57] = index.toString()
                break
            }
        }

        arvauksienMaara += 1
        nykyKohta = 0
        // if user didn't get the word right
        if (arvauksienMaara >= 6 && isPopupOpen == false) {
            isRight = "ikävä kyllä tällä kertaa et arvannut oikein!"
            isPopupOpen = true
            showContent = false
            arvauksienMaara = 0
        }
    }

    // list where every character is in individually
    val d = mutableListOf<String>()
    for (i in sana) {
        d.add(i.toString().lowercase())
    }
    // cheat when testing
    println(sana)

    // shows the sanuli game if game is over doesn't show
    if (showContent) {
        Column(
            modifier = Modifier.fillMaxWidth().height(550.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Sanuli",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().height(35.dp),
                style = TextStyle(color = White, fontWeight = FontWeight.Bold),
                fontSize = 25.sp
                )
            // rivi 1
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet1.size >= 1) { kirjaimet1[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor =  if (palautettu) { if (kirjaimet1[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet1[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor =  if (palautettu) { if (kirjaimet1[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet1[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp),
                )
                OutlinedTextField(
                    value = if (kirjaimet1.size >= 2) { kirjaimet1[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu) { if (kirjaimet1[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet1[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu) { if (kirjaimet1[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet1[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp),
                )
                OutlinedTextField(
                    value = if (kirjaimet1.size >= 3) { kirjaimet1[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu) { if (kirjaimet1[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet1[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu) { if (kirjaimet1[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet1[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp),
                )
                OutlinedTextField(
                    value = if (kirjaimet1.size >= 4) { kirjaimet1[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu) { if (kirjaimet1[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet1[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu) { if (kirjaimet1[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet1[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp),
                )
                OutlinedTextField(
                    value = if (kirjaimet1.size >= 5) { kirjaimet1[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu) { if (kirjaimet1[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet1[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu) { if (kirjaimet1[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet1[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp),
                )
            }

            // rivi 2
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet2.size >= 1) { kirjaimet2[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu2) { if (kirjaimet2[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet2[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu2) { if (kirjaimet2[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet2[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet2.size >= 2) { kirjaimet2[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu2) { if (kirjaimet2[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet2[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu2) { if (kirjaimet2[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet2[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet2.size >= 3) { kirjaimet2[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu2) { if (kirjaimet2[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet2[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu2) { if (kirjaimet2[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet2[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet2.size >= 4) { kirjaimet2[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu2) { if (kirjaimet2[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet2[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu2) { if (kirjaimet2[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet2[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet2.size >= 5) { kirjaimet2[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu2) { if (kirjaimet2[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet2[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu2) { if (kirjaimet2[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet2[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
            }

            // rivi 3
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet3.size >= 1) { kirjaimet3[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu3) { if (kirjaimet3[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet3[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu3) { if (kirjaimet3[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet3[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet3.size >= 2) { kirjaimet3[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu3) { if (kirjaimet3[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet3[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu3) { if (kirjaimet3[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet3[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet3.size >= 3) { kirjaimet3[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu3) { if (kirjaimet3[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet3[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu3) { if (kirjaimet3[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet3[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet3.size >= 4) { kirjaimet3[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu3) { if (kirjaimet3[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet3[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu3) { if (kirjaimet3[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet3[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet3.size >= 5) { kirjaimet3[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu3) { if (kirjaimet3[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet3[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu3) { if (kirjaimet3[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet3[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
            }

            // rivi 4
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet4.size >= 1) { kirjaimet4[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu4) { if (kirjaimet4[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet4[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu4) { if (kirjaimet4[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet4[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet4.size >= 2) { kirjaimet4[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu4) { if (kirjaimet4[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet4[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu4) { if (kirjaimet4[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet4[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet4.size >= 3) { kirjaimet4[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu4) { if (kirjaimet4[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet4[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu4) { if (kirjaimet4[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet4[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet4.size >= 4) { kirjaimet4[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu4) { if (kirjaimet4[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet4[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu4) { if (kirjaimet4[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet4[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet4.size >= 5) { kirjaimet4[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu4) { if (kirjaimet4[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet4[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu4) { if (kirjaimet4[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet4[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
            }

            // rivi 5
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet5.size >= 1) { kirjaimet5[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu5) { if (kirjaimet5[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet5[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu5) { if (kirjaimet5[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet5[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet5.size >= 2) { kirjaimet5[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu5) { if (kirjaimet5[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet5[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu5) { if (kirjaimet5[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet5[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet5.size >= 3) { kirjaimet5[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu5) { if (kirjaimet5[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet5[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu5) { if (kirjaimet5[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet5[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet5.size >= 4) { kirjaimet5[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu5) { if (kirjaimet5[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet5[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu5) { if (kirjaimet5[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet5[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet5.size >= 5) { kirjaimet5[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu5) { if (kirjaimet5[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet5[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu5) { if (kirjaimet5[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet5[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
            }

            // rivi 6
            FlowRow(
                modifier = Modifier
                    .width(390.dp)
                    .height(65.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.Center,
                maxItemsInEachRow = 5
            ) {
                OutlinedTextField(
                    value = if (kirjaimet6.size >= 1) { kirjaimet6[0] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu6) { if (kirjaimet6[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet6[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu6) { if (kirjaimet6[0].lowercase() == d[0].lowercase()) { Color.Green } else if (kirjaimet6[0].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet6.size >= 2) { kirjaimet6[1] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu6) { if (kirjaimet6[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet6[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu6) { if (kirjaimet6[1].lowercase() == d[1].lowercase()) { Color.Green } else if (kirjaimet6[1].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet6.size >= 3) { kirjaimet6[2] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu6) { if (kirjaimet6[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet6[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu6) { if (kirjaimet6[2].lowercase() == d[2].lowercase()) { Color.Green } else if (kirjaimet6[2].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet6.size >= 4) { kirjaimet6[3] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu6) { if (kirjaimet6[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet6[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu6) { if (kirjaimet6[3].lowercase() == d[3].lowercase()) { Color.Green } else if (kirjaimet6[3].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
                OutlinedTextField(
                    value = if (kirjaimet6.size >= 5) { kirjaimet6[4] } else "",
                    readOnly = true,
                    onValueChange = {},
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                    textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize = 24.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = if (palautettu6) { if (kirjaimet6[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet6[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedContainerColor = if (palautettu6) { if (kirjaimet6[4].lowercase() == d[4].lowercase()) { Color.Green } else if (kirjaimet6[4].lowercase() in sana.lowercase()) { Color.Yellow } else Color.Gray } else White,
                        focusedIndicatorColor = White,
                    ),
                    modifier = Modifier
                        .width(75.dp)
                )
            }
        }

        // KEYBOARD /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Column(
            modifier = Modifier.fillMaxWidth().height(750.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            // eka rivi
            FlowRow(
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.spacedBy(1.dp),
            ) {
                TextButton(
                    colors = ButtonColors(containerColor =
                        (if ("Q" in palautettuKirjaimet && paikat.size >= 2) {
                            if ("Q" in sana.uppercase()) {
                                if (paikat[0] == "-1" && paikat[1] == "-1") {
                                    Color.Blue
                                }
                                if (paikat[0].toInt() == paikat[1].toInt()) {
                                    Color.Green
                                } else {
                                    Color.Yellow
                                }
                            } else {
                                Color.Gray
                            }
                        } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Q") },
                    ) {
                    Text("Q", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("W" in palautettuKirjaimet && paikat.size >= 4) {
                        if ("W" in sana.uppercase()) {
                            if (paikat[2].toInt() == paikat[3].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("W") },
                ) {
                    Text("W", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("E" in palautettuKirjaimet && paikat.size >= 6) {
                        if ("E" in sana.uppercase()) {
                            if (paikat[4].toInt() == paikat[5].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("E") },
                ) {
                    Text("E", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("R" in palautettuKirjaimet  && paikat.size >= 8) {
                        if ("R" in sana.uppercase()) {
                            if (paikat[6].toInt() == paikat[7].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("R") },
                ) {
                    Text("R", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("T" in palautettuKirjaimet && paikat.size >= 10) {
                        if ("T" in sana.uppercase()) {
                            if (paikat[8].toInt() == paikat[9].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("T") },
                ) {
                    Text("T", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("Y" in palautettuKirjaimet && paikat.size >= 12) {
                        if ("Y" in sana.uppercase()) {
                            if (paikat[10].toInt() == paikat[11].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Y") },
                ) {
                    Text("Y", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("U" in palautettuKirjaimet && paikat.size >= 14) {
                        if ("U" in sana.uppercase()) {
                            if (paikat[12].toInt() == paikat[13].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("U") },
                ) {
                    Text("U", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("I" in palautettuKirjaimet && paikat.size >= 16) {
                        if ("I" in sana.uppercase()) {
                            println("DSF")
                            println(paikat[14])
                            println(paikat[15])
                            if (paikat[14].toInt() == paikat[15].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("I") },
                ) {
                    Text("I", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("O" in palautettuKirjaimet && paikat.size >= 18) {
                        if ("O" in sana.uppercase()) {
                            if (paikat[16].toInt() == paikat[17].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("O") },
                ) {
                    Text("O", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("P" in palautettuKirjaimet && paikat.size >= 20) {
                        if ("P" in sana.uppercase()) {
                            if (paikat[18].toInt() == paikat[19].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("P") },
                ) {
                    Text("P", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("Å" in palautettuKirjaimet && paikat.size >= 22) {
                        if ("Å" in sana.uppercase()) {
                            if (paikat[20].toInt() == paikat[21].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Å") },
                ) {
                    Text("Å", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
            }

            // rivi 2
            FlowRow(
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.spacedBy(1.dp),
            ) {
                TextButton(
                    colors = ButtonColors(containerColor = (if ("A" in palautettuKirjaimet && paikat.size >= 24) {
                        if ("A" in sana.uppercase()) {
                            if (paikat[22].toInt() == paikat[23].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("A") },
                ) {
                    Text("A", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("S" in palautettuKirjaimet && paikat.size >= 26) {
                        if ("S" in sana.uppercase()) {
                            if (paikat[24].toInt() == paikat[25].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("S") },
                ) {
                    Text("S", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("D" in palautettuKirjaimet && paikat.size >= 28) {
                        if ("D" in sana.uppercase()) {
                            if (paikat[26].toInt() == paikat[27].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("D") },
                ) {
                    Text("D", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("F" in palautettuKirjaimet && paikat.size >= 30) {
                        if ("F" in sana.uppercase()) {
                            if (paikat[28].toInt() == paikat[29].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("F") },
                ) {
                    Text("F", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("G" in palautettuKirjaimet && paikat.size >= 32) {
                        if ("G" in sana.uppercase()) {
                            if (paikat[30].toInt() == paikat[31].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("G") },
                ) {
                    Text("G", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("H" in palautettuKirjaimet && paikat.size >= 34) {
                        if ("H" in sana.uppercase()) {
                            if (paikat[32].toInt() == paikat[33].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("H") },
                ) {
                    Text("H", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("J" in palautettuKirjaimet && paikat.size >= 36) {
                        if ("J" in sana.uppercase()) {
                            if (paikat[34].toInt() == paikat[35].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("J") },
                ) {
                    Text("J", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("K" in palautettuKirjaimet && paikat.size >= 38) {
                        if ("K" in sana.uppercase()) {
                            if (paikat[36].toInt() == paikat[37].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("K") },
                ) {
                    Text("K", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("L" in palautettuKirjaimet && paikat.size >= 40) {
                        if ("L" in sana.uppercase()) {
                            if (paikat[38].toInt() == paikat[39].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("L") },
                ) {
                    Text("L", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("Ö" in palautettuKirjaimet && paikat.size >= 42) {
                        if ("Ö" in sana.uppercase()) {
                            if (paikat[40].toInt() == paikat[41].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Ö") },
                ) {
                    Text("Ö", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("Ä" in palautettuKirjaimet && paikat.size >= 44) {
                        if ("Ä" in sana.uppercase()) {
                            if (paikat[42].toInt() == paikat[43].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Ä") },
                ) {
                    Text("Ä", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
            }

            // rivi 3
            FlowRow(
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    colors = ButtonColors(containerColor = (if ("Z" in palautettuKirjaimet && paikat.size >= 46) {
                        if ("Z" in sana.uppercase()) {
                            if (paikat[44].toInt() == paikat[45].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("Z") },
                ) {
                    Text("Z", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("X" in palautettuKirjaimet && paikat.size >= 48) {
                        if ("X" in sana.uppercase()) {
                            if (paikat[46].toInt() == paikat[47].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("X") },
                ) {
                    Text("X", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("C" in palautettuKirjaimet && paikat.size >= 50) {
                        if ("C" in sana.uppercase()) {
                            if (paikat[48].toInt() == paikat[49].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        //.height(40.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("C") },
                ) {
                    Text("C", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("V" in palautettuKirjaimet && paikat.size >= 52) {
                        if ("V" in sana.uppercase()) {
                            if (paikat[50].toInt() == paikat[51].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("V") },
                ) {
                    Text("V", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("B" in palautettuKirjaimet && paikat.size >= 54) {
                        if ("B" in sana.uppercase()) {
                            if (paikat[52].toInt() == paikat[53].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("B") },
                ) {
                    Text("B", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("N" in palautettuKirjaimet && paikat.size >= 56) {
                        if ("N" in sana.uppercase()) {
                            if (paikat[54].toInt() == paikat[55].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("N") },
                ) {
                    Text("N", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                TextButton(
                    colors = ButtonColors(containerColor = (if ("M" in palautettuKirjaimet && paikat.size >= 58) {
                        if ("M" in sana.uppercase()) {
                            if (paikat[56].toInt() == paikat[57].toInt()) {
                                Color.Green
                            } else {
                                Color.Yellow
                            }
                        } else {
                            Color.Gray
                        }
                    } else Color.Blue), contentColor = White, disabledContainerColor = Color.Blue, disabledContentColor = White),
                    modifier = Modifier
                        .padding(1.dp)
                        .width(34.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    onClick = { add_kirjain("M") },
                ) {
                    Text("M", fontSize = 25.sp, textAlign = TextAlign.Center)
                }
                Button(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(90.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    colors = ButtonColors(containerColor = Color.Red, contentColor = Color.Red, disabledContainerColor = White, disabledContentColor = White),
                    onClick = {
                        if (arvauksienMaara == 0) {
                            // check that is not empty and if the first is empty also returns
                            if (!kirjaimet1.isNotEmpty() || kirjaimet1[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet1.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                        if (arvauksienMaara == 1) {
                            if (!kirjaimet2.isNotEmpty() || kirjaimet2[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet2.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                        if (arvauksienMaara == 2) {
                            if (!kirjaimet3.isNotEmpty() || kirjaimet3[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet3.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                        if (arvauksienMaara == 3) {
                            if (!kirjaimet4.isNotEmpty() || kirjaimet4[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet4.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                        if (arvauksienMaara == 4) {
                            if (!kirjaimet5.isNotEmpty() || kirjaimet5[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet5.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                        if (arvauksienMaara == 5) {
                            if (!kirjaimet6.isNotEmpty() || kirjaimet6[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            kirjaimet6.removeAt(nykyKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                        }
                    },
                ) {
                    Text("tyh", color = White, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
            // tarkista nappi
            FlowRow(
                modifier = Modifier.padding(0.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(180.dp)
                        .clip(RoundedCornerShape(0.dp)),
                    colors = ButtonColors(containerColor = Color.Green, contentColor = Color.Green, disabledContainerColor = White, disabledContentColor = White),
                    onClick = { tarkista() },
                ) {
                    Text("Tarkista", color = White, fontSize = 20.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }

    if (isPopupOpen) {
        Popup(
            // Positions the popup relative to the button
            alignment = Alignment.TopCenter,
            // Shifts the popup by (x, y) in pixels
            offset = IntOffset(0, 370),
            // Hides the popup when it is dismissed, for example, when the user clicks outside it
            onDismissRequest = { isPopupOpen = true }
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
                        onClick = {
                            // clears all the values
                            isPopupOpen = false
                            palautettu=false
                            palautettu2=false
                            palautettu3=false
                            palautettu4=false
                            palautettu5=false
                            palautettu6=false
                            kirjaimet1.clear()
                            kirjaimet2.clear()
                            kirjaimet3.clear()
                            kirjaimet4.clear()
                            kirjaimet5.clear()
                            kirjaimet6.clear()
                            kaikkiKirjaimet.clear()
                            palautettuKirjaimet.clear()
                            paikat.clear()
                            arvauksienMaara = 0
                            showContent = true
                            isRight = "ARVASIT OKEIN!!"
                            sana = sanat.random()
                        },
                        modifier = Modifier.offset(0.dp, 20.dp),
                    ) {
                        Text("Uudestaan")
                    }
                }
            }
        }
    }
}
