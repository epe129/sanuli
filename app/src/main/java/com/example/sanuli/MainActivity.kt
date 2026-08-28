package com.example.sanuli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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

    try {
        jsonString = context.assets.open("sanat.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    }

    val sanatTOlist = jsonString.replace("""[{}:"]""".toRegex(), "").replace("sanat", "").replace("]", "").replace("[", "").lowercase().trim().split(",")

    for (c in sanatTOlist) {
        sanat.add(c.trim())
    }

    var sana by remember(sanat) { mutableStateOf(sanat.random()) }

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

    fun tarkista() {
        if (arvauksienMaara == 0) {
            if (kirjaimet1.size < 5) {return}
            palautettu = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet1.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu) {
                println(kirjaimet1.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
                isPopupOpen = true
            }
        }
        if (arvauksienMaara == 1) {
            if (kirjaimet2.size < 5) {return}
            palautettu2 = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet2.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu2) {
                isPopupOpen = true
                println(kirjaimet2.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
            }
        }
        if (arvauksienMaara == 2) {
            if (kirjaimet3.size < 5) {return}
            palautettu3 = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet3.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu3) {
                isPopupOpen = true
                println(kirjaimet3.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
            }
        }
        if (arvauksienMaara == 3) {
            if (kirjaimet4.size < 5) {return}
            palautettu4 = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet4.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu4) {
                isPopupOpen = true
                println(kirjaimet4.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
            }
        }
        if (arvauksienMaara == 4) {
            if (kirjaimet5.size < 5) {return}
            palautettu5 = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet5.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu5) {
                isPopupOpen = true
                println(kirjaimet5.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
            }
        }
        if (arvauksienMaara == 5) {
            if (kirjaimet6.size < 5) {return}
            palautettu6 = true
            println(kaikkiKirjaimet)
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }

            if (kirjaimet6.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu6) {
                isPopupOpen = true
                println(kirjaimet6.joinToString(separator = "").replace(",", "").lowercase().trim())
                println(sana)
            }
        }

        if (paikat.isEmpty()) {
            for (g in kirjaimet) {
                paikat.add(sana.uppercase().indexOf(g).toString())
                paikat.add(kaikkiKirjaimet.indexOf(g).toString())
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Q"))
            if (sana.uppercase().indexOf("Q") == index && value == "Q") {
                paikat[0] = sana.uppercase().indexOf("Q").toString()
                paikat[1] = index.toString()
                break
            }

        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("W"))
            if (sana.uppercase().indexOf("W") == index && value == "W") {
                paikat[2] = sana.uppercase().indexOf("W").toString()
                paikat[3] = index.toString()
                break
            }

        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("E"))
            if (sana.uppercase().indexOf("E") == index && value == "E") {
                paikat[4] = sana.uppercase().indexOf("E").toString()
                paikat[5] = index.toString()
                break
            }

        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("R"))
            if (sana.uppercase().indexOf("R") == index && value == "R") {
                paikat[6] = sana.uppercase().indexOf("R").toString()
                paikat[7] = index.toString()
                break
            }

        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("T"))
            if (sana.uppercase().indexOf("T") == index && value == "T") {
                paikat[8] = sana.uppercase().indexOf("T").toString()
                paikat[9] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Y"))
            if (sana.uppercase().indexOf("Y") == index && value == "Y") {
                paikat[10] = sana.uppercase().indexOf("Y").toString()
                paikat[11] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("U"))
            if (sana.uppercase().indexOf("U") == index && value == "U") {
                paikat[12] = sana.uppercase().indexOf("U").toString()
                paikat[13] = kaikkiKirjaimet.indexOf("U").toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("I"))
            if (sana.uppercase().indexOf("I") == index && value == "I") {
                paikat[14] = sana.uppercase().indexOf("I").toString()
                paikat[15] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("O"))
            if (sana.uppercase().indexOf("O") == index && value == "O") {
                paikat[16] = sana.uppercase().indexOf("O").toString()
                paikat[17] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("P"))
            if (sana.uppercase().indexOf("P") == index && value == "P") {
                paikat[18] = sana.uppercase().indexOf("P").toString()
                paikat[19] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Å"))
            if (sana.uppercase().indexOf("Å") == index && value == "Å") {
                paikat[20] = sana.uppercase().indexOf("Å").toString()
                paikat[21] = index.toString()
                break
            }
        }
        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("A"))
            if (sana.uppercase().indexOf("A") == index && value == "A") {
                paikat[22] = sana.uppercase().indexOf("A").toString()
                paikat[23] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("S"))
            if (sana.uppercase().indexOf("S") == index && value == "S") {
                paikat[24] = sana.uppercase().indexOf("S").toString()
                paikat[25] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("D"))
            if (sana.uppercase().indexOf("D") == index && value == "D") {
                paikat[26] = sana.uppercase().indexOf("D").toString()
                paikat[27] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("F"))
            if (sana.uppercase().indexOf("F") == index && value == "F") {
                paikat[28] = sana.uppercase().indexOf("F").toString()
                paikat[29] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("G"))
            if (sana.uppercase().indexOf("G") == index && value == "G") {
                paikat[30] = sana.uppercase().indexOf("G").toString()
                paikat[31] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("H"))
            if (sana.uppercase().indexOf("H") == index && value == "H") {
                paikat[32] = sana.uppercase().indexOf("H").toString()
                paikat[33] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("J"))
            if (sana.uppercase().indexOf("J") == index && value == "J") {
                paikat[34] = sana.uppercase().indexOf("J").toString()
                paikat[35] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("K"))
            if (sana.uppercase().indexOf("K") == index && value == "K") {
                paikat[36] = sana.uppercase().indexOf("K").toString()
                paikat[37] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("L"))
            if (sana.uppercase().indexOf("L") == index && value == "L") {
                paikat[38] = sana.uppercase().indexOf("L").toString()
                paikat[39] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Ö"))
            if (sana.uppercase().indexOf("Ö") == index && value == "Ö") {
                paikat[40] = sana.uppercase().indexOf("Ö").toString()
                paikat[41] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Ä"))
            if (sana.uppercase().indexOf("Ä") == index && value == "Ä") {
                paikat[42] = sana.uppercase().indexOf("Ä").toString()
                paikat[43] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("Z"))
            if (sana.uppercase().indexOf("Z") == index && value == "Z") {
                paikat[44] = sana.uppercase().indexOf("Z").toString()
                paikat[45] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("X"))
            if (sana.uppercase().indexOf("X") == index && value == "X") {
                paikat[46] = sana.uppercase().indexOf("X").toString()
                paikat[47] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("C"))
            if (sana.uppercase().indexOf("C") == index && value == "C") {
                paikat[48] = sana.uppercase().indexOf("C").toString()
                paikat[49] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("V"))
            if (sana.uppercase().indexOf("V") == index && value == "V") {
                paikat[50] = sana.uppercase().indexOf("V").toString()
                paikat[51] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("B"))
            if (sana.uppercase().indexOf("B") == index && value == "B") {
                paikat[52] = sana.uppercase().indexOf("B").toString()
                paikat[53] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("N"))
            if (sana.uppercase().indexOf("N") == index && value == "N") {
                paikat[54] = sana.uppercase().indexOf("N").toString()
                paikat[55] = index.toString()
                break
            }
        }

        for ((index, value) in kaikkiKirjaimet.withIndex()) {
            println(value)
            println(index)
            println(sana.uppercase().indexOf("M"))
            if (sana.uppercase().indexOf("M") == index && value == "M") {
                paikat[56] = sana.uppercase().indexOf("M").toString()
                paikat[57] = index.toString()
                break
            }
        }

        arvauksienMaara += 1
        nykyKohta = 0
        if (arvauksienMaara >= 6 && !isPopupOpen) {
            isRight = "ikävä kyllä tällä kertaa et arvannut oikein!"
            isPopupOpen = true
        }

    }

    // lista jossa jokainen kirjain on yksitellen
    val d = mutableListOf<String>()
    for (i in sana) {
        d.add(i.toString().lowercase())
    }

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
                    .width(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column() {
                    Text(isRight, textAlign = TextAlign.Center)
                    Button(
                        onClick = {
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
                        },
                        modifier = Modifier.offset(0.dp, 30.dp)
                    ) {
                        Text("Uudestaan")
                    }
                }
            }
        }
    }
}
