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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color.DarkGray) { innerPadding ->
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
    val kaikkiKirjaimet = remember { mutableStateListOf<String>() }
    var arvauksienMaara by remember { mutableIntStateOf(0) }
    var nykyKohta by remember { mutableIntStateOf(0) }
    val palautetut = remember { mutableStateListOf<String>() }
    val palautettuKirjaimet = remember { mutableStateListOf<String>() }
    val paikat = remember { mutableStateListOf<String>() }
    val kirjaimet = listOf<String>("Q", "W", "E","R","T","Y","U","I","O","P","Å","A","S","D","F","G","H","J","K","L","Ö","Ä","Z","X","C","V","B","N","M")
    var jsonString = ""
    val kaydytNumerot = remember { mutableStateListOf<String>() }
    val kaydytKirjaimet = remember { mutableStateListOf<String>() }
    var KayttajaSanat = remember { emptyArray<String>() }
    val nakyvatKirjaimet = remember { mutableStateListOf<String>() }
    var nakyvatKirjaimetKohta by remember { mutableIntStateOf(0) }

    // gets words from json file
    try {
        jsonString = context.assets.open("sanat.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    }

    // makes the json to list
    val sanatTOlist = remember { jsonString.replace("""[{}:"]""".toRegex(), "").replace("sanat", "").replace("]", "").replace("[", "").lowercase().trim().split(",") }

    // gets the random word from sanat sanatTOList
    var sana by remember(sanatTOlist) { mutableStateOf(sanatTOlist.random().trim()) }

    // adds kirjain to the list's
    fun add_kirjain(kirjain: String) {
        if (kaikkiKirjaimet.size == 5) {
            return
        }
        kaikkiKirjaimet.add(nykyKohta, kirjain)
        nakyvatKirjaimet.add(kirjain)
        nykyKohta += 1
        nakyvatKirjaimetKohta += 1
    }

    // check if user got the word correct
    fun tarkista() {
        if (kaikkiKirjaimet.size < 5) {
            return
        }
        KayttajaSanat += kaikkiKirjaimet
        println("\n")
        println(KayttajaSanat[0][0])
        kaydytNumerot.clear()
        kaydytKirjaimet.clear()
        val sub = kaikkiKirjaimet.toList().subList(0, 5)
        // check if right
        if (sub.size < 5) {
            return
        } else {
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (sub.joinToString(separator = "").replace(",", "").lowercase().trim() == sana) {
                isPopupOpen = true
                showContent = false
            }
        }
        // adds every characters index to the paikat list even if it's -1
        if (paikat.isEmpty()) {
            for (g in kirjaimet) {
                paikat.add(sana.uppercase().indexOf(g).toString())
                paikat.add(kaikkiKirjaimet.indexOf(g).toString())
            }
        }
        // checks if character is in sana and user has typed the character adds the index of the character to the list
        for (i in 0..57) {
            for (k in kirjaimet) {
                // if a letter has been check, move on to the next letter
                if (i.toString() in kaydytNumerot || (i + 1).toString() in kaydytNumerot || k in kaydytKirjaimet) {
                    continue
                }
                // loops trough sana and check if kirjaimet are in same place in sana and what user has typed
                for ((index, value) in sana.withIndex()) {
                    if (value.uppercase() == k && kaikkiKirjaimet[index].uppercase() == k) {
                        //println("$i" + k.toString())
                        //println((i + 1).toString() + k.toString())
                        paikat[i] = index.toString()
                        paikat[i+1] = index.toString()
                        break
                    }
                }
                kaydytNumerot.add(i.toString())
                kaydytNumerot.add((i + 1).toString())
                kaydytKirjaimet.add(k)
            }
        }
        arvauksienMaara += 1
        palautetut.add(arvauksienMaara.toString())
        nykyKohta = 0
        // if user didn't get the word right
        if (arvauksienMaara >= 6 && isPopupOpen == false) {
            isRight = "ikävä kyllä tällä kertaa et arvannut oikein!"
            isPopupOpen = true
            showContent = false
            arvauksienMaara = 0
        }
        kaikkiKirjaimet.clear()
    }

    // list where every character is in individually
    val d = mutableListOf<String>()
    for (i in sana) {
        d.add(i.toString().lowercase())
    }
    // cheat when testing
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println("\n")
    println(sana)

    // shows the sanuli game if game is over doesn't show
    if (showContent) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().height(550.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Sanuli",
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
                        value = if (nakyvatKirjaimet.size >= 1) { nakyvatKirjaimet[0] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[0].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[0].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[0].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[0].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp),
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 2) { nakyvatKirjaimet[1] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[1].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[1].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[1].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[1].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp),
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 3) { nakyvatKirjaimet[2] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[2].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[2].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[2].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[2].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp),
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 4) { nakyvatKirjaimet[3] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[3].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[3].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[3].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[3].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp),
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 5) { nakyvatKirjaimet[4] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[4].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[4].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("1" in palautetut && KayttajaSanat.size >= 1) {
                                if (KayttajaSanat[4].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[4].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
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
                        value = if (nakyvatKirjaimet.size >= 6) { nakyvatKirjaimet[5] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            showKeyboardOnFocus = false,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[5].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[5].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[5].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[5].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 7) { nakyvatKirjaimet[6] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[6].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[6].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[6].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[6].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 8) { nakyvatKirjaimet[7] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[7].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[7].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[7].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[7].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 9) { nakyvatKirjaimet[8] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[8].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[8].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[8].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[8].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 10) { nakyvatKirjaimet[9] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[9].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[9].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("2" in palautetut) {
                                if (KayttajaSanat[9].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[9].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
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
                        value = if (nakyvatKirjaimet.size >= 11) { nakyvatKirjaimet[10] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            showKeyboardOnFocus = false,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[10].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[10].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[10].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[10].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 12) { nakyvatKirjaimet[11] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[11].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[11].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[11].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[11].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 13) { nakyvatKirjaimet[12] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[12].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[12].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[12].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[12].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 14) { nakyvatKirjaimet[13] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[13].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[13].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[13].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[13].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 15) { nakyvatKirjaimet[14] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[14].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[14].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("3" in palautetut) {
                                if (KayttajaSanat[14].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[14].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
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
                        value = if (nakyvatKirjaimet.size >= 16) { nakyvatKirjaimet[15] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            showKeyboardOnFocus = false,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[15].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[15].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[15].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[15].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 17) { nakyvatKirjaimet[16] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[16].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[16].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[16].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[16].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 18) { nakyvatKirjaimet[17] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[17].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[17].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[17].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[17].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 19) { nakyvatKirjaimet[18] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[18].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[18].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[18].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[18].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 20) { nakyvatKirjaimet[19] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[19].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[19].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("4" in palautetut) {
                                if (KayttajaSanat[19].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[19].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
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
                        value = if (nakyvatKirjaimet.size >= 21) { nakyvatKirjaimet[20] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            showKeyboardOnFocus = false,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[20].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[20].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[20].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[20].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 22) { nakyvatKirjaimet[21] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[21].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[21].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[21].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[21].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 23) { nakyvatKirjaimet[22] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[22].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[22].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[22].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[22].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 24) { nakyvatKirjaimet[23] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[23].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[23].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[23].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[23].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 25) { nakyvatKirjaimet[24] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[24].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[24].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("5" in palautetut) {
                                if (KayttajaSanat[24].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[24].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
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
                        value = if (nakyvatKirjaimet.size >= 26) { nakyvatKirjaimet[25] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            showKeyboardOnFocus = false,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[25].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[25].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[25].lowercase() == d[0].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[25].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 27) { nakyvatKirjaimet[26] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[26].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[26].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[26].lowercase() == d[1].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[26].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 28) { nakyvatKirjaimet[27] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[27].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[27].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[27].lowercase() == d[2].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[27].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 29) { nakyvatKirjaimet[28] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[28].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[28].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[28].lowercase() == d[3].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[28].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                    OutlinedTextField(
                        value = if (nakyvatKirjaimet.size >= 30) { nakyvatKirjaimet[29] } else "",
                        readOnly = true,
                        onValueChange = {},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp
                        ),
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[29].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[29].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedContainerColor = if ("6" in palautetut) {
                                if (KayttajaSanat[29].lowercase() == d[4].lowercase()) {
                                    Color.Green
                                } else if (KayttajaSanat[29].lowercase() in sana.lowercase()) {
                                    Color.Yellow
                                } else Color.Gray
                            } else White,
                            focusedIndicatorColor = White,
                        ),
                        modifier = Modifier
                            .width(75.dp)
                    )
                }
            }

            // KEYBOARD /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Column(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                // eka rivi
                FlowRow(
                    modifier = Modifier.padding(0.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.spacedBy(1.dp),
                ) {
                    TextButton(
                        colors = ButtonColors(
                            containerColor =
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
                                } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("Q") },
                    ) {
                        Text("Q", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("W" in palautettuKirjaimet && paikat.size >= 4) {
                                if ("W" in sana.uppercase()) {
                                    if (paikat[2].toInt() == paikat[3].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("W") },
                    ) {
                        Text("W", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("E" in palautettuKirjaimet && paikat.size >= 6) {
                                if ("E" in sana.uppercase()) {
                                    if (paikat[4].toInt() == paikat[5].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("E") },
                    ) {
                        Text("E", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("R" in palautettuKirjaimet && paikat.size >= 8) {
                                if ("R" in sana.uppercase()) {
                                    if (paikat[6].toInt() == paikat[7].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("R") },
                    ) {
                        Text("R", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("T" in palautettuKirjaimet && paikat.size >= 10) {
                                if ("T" in sana.uppercase()) {
                                    if (paikat[8].toInt() == paikat[9].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("T") },
                    ) {
                        Text("T", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("Y" in palautettuKirjaimet && paikat.size >= 12) {
                                if ("Y" in sana.uppercase()) {
                                    if (paikat[10].toInt() == paikat[11].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("Y") },
                    ) {
                        Text("Y", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("U" in palautettuKirjaimet && paikat.size >= 14) {
                                if ("U" in sana.uppercase()) {
                                    if (paikat[12].toInt() == paikat[13].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("U") },
                    ) {
                        Text("U", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("I" in palautettuKirjaimet && paikat.size >= 16) {
                                if ("I" in sana.uppercase()) {
                                    if (paikat[14].toInt() == paikat[15].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("I") },
                    ) {
                        Text("I", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("O" in palautettuKirjaimet && paikat.size >= 18) {
                                if ("O" in sana.uppercase()) {
                                    if (paikat[16].toInt() == paikat[17].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("O") },
                    ) {
                        Text("O", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("P" in palautettuKirjaimet && paikat.size >= 20) {
                                if ("P" in sana.uppercase()) {
                                    if (paikat[18].toInt() == paikat[19].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("P") },
                    ) {
                        Text("P", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("Å" in palautettuKirjaimet && paikat.size >= 22) {
                                if ("Å" in sana.uppercase()) {
                                    if (paikat[20].toInt() == paikat[21].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
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
                        colors = ButtonColors(
                            containerColor = (if ("A" in palautettuKirjaimet && paikat.size >= 24) {
                                if ("A" in sana.uppercase()) {
                                    if (paikat[22].toInt() == paikat[23].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("A") },
                    ) {
                        Text("A", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("S" in palautettuKirjaimet && paikat.size >= 26) {
                                if ("S" in sana.uppercase()) {
                                    if (paikat[24].toInt() == paikat[25].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("S") },
                    ) {
                        Text("S", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("D" in palautettuKirjaimet && paikat.size >= 28) {
                                if ("D" in sana.uppercase()) {
                                    if (paikat[26].toInt() == paikat[27].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("D") },
                    ) {
                        Text("D", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("F" in palautettuKirjaimet && paikat.size >= 30) {
                                if ("F" in sana.uppercase()) {
                                    if (paikat[28].toInt() == paikat[29].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("F") },
                    ) {
                        Text("F", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("G" in palautettuKirjaimet && paikat.size >= 32) {
                                if ("G" in sana.uppercase()) {
                                    if (paikat[30].toInt() == paikat[31].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("G") },
                    ) {
                        Text("G", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("H" in palautettuKirjaimet && paikat.size >= 34) {
                                if ("H" in sana.uppercase()) {
                                    if (paikat[32].toInt() == paikat[33].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("H") },
                    ) {
                        Text("H", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("J" in palautettuKirjaimet && paikat.size >= 36) {
                                if ("J" in sana.uppercase()) {
                                    if (paikat[34].toInt() == paikat[35].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("J") },
                    ) {
                        Text("J", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("K" in palautettuKirjaimet && paikat.size >= 38) {
                                if ("K" in sana.uppercase()) {
                                    if (paikat[36].toInt() == paikat[37].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("K") },
                    ) {
                        Text("K", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("L" in palautettuKirjaimet && paikat.size >= 40) {
                                if ("L" in sana.uppercase()) {
                                    if (paikat[38].toInt() == paikat[39].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("L") },
                    ) {
                        Text("L", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("Ö" in palautettuKirjaimet && paikat.size >= 42) {
                                if ("Ö" in sana.uppercase()) {
                                    if (paikat[40].toInt() == paikat[41].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("Ö") },
                    ) {
                        Text("Ö", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("Ä" in palautettuKirjaimet && paikat.size >= 44) {
                                if ("Ä" in sana.uppercase()) {
                                    if (paikat[42].toInt() == paikat[43].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
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
                        colors = ButtonColors(
                            containerColor = (if ("Z" in palautettuKirjaimet && paikat.size >= 46) {
                                if ("Z" in sana.uppercase()) {
                                    if (paikat[44].toInt() == paikat[45].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("Z") },
                    ) {
                        Text("Z", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("X" in palautettuKirjaimet && paikat.size >= 48) {
                                if ("X" in sana.uppercase()) {
                                    if (paikat[46].toInt() == paikat[47].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("X") },
                    ) {
                        Text("X", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("C" in palautettuKirjaimet && paikat.size >= 50) {
                                if ("C" in sana.uppercase()) {
                                    if (paikat[48].toInt() == paikat[49].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
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
                        colors = ButtonColors(
                            containerColor = (if ("V" in palautettuKirjaimet && paikat.size >= 52) {
                                if ("V" in sana.uppercase()) {
                                    if (paikat[50].toInt() == paikat[51].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("V") },
                    ) {
                        Text("V", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("B" in palautettuKirjaimet && paikat.size >= 54) {
                                if ("B" in sana.uppercase()) {
                                    if (paikat[52].toInt() == paikat[53].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("B") },
                    ) {
                        Text("B", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("N" in palautettuKirjaimet && paikat.size >= 56) {
                                if ("N" in sana.uppercase()) {
                                    if (paikat[54].toInt() == paikat[55].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
                        modifier = Modifier
                            .padding(1.dp)
                            .width(34.dp)
                            .clip(RoundedCornerShape(0.dp)),
                        onClick = { add_kirjain("N") },
                    ) {
                        Text("N", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    TextButton(
                        colors = ButtonColors(
                            containerColor = (if ("M" in palautettuKirjaimet && paikat.size >= 58) {
                                if ("M" in sana.uppercase()) {
                                    if (paikat[56].toInt() == paikat[57].toInt()) {
                                        Color.Green
                                    } else {
                                        Color.Yellow
                                    }
                                } else {
                                    Color.Gray
                                }
                            } else Color.Blue),
                            contentColor = White,
                            disabledContainerColor = Color.Blue,
                            disabledContentColor = White
                        ),
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
                        colors = ButtonColors(
                            containerColor = Color.Red,
                            contentColor = Color.Red,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        onClick = {
                            // check that is not empty and if the first is empty also returns
                            if (!kaikkiKirjaimet.isNotEmpty() || kaikkiKirjaimet[0] == "") {
                                return@Button
                            }
                            nykyKohta -= 1
                            nakyvatKirjaimetKohta -= 1
                            nakyvatKirjaimet.removeAt(nakyvatKirjaimetKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
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
                        colors = ButtonColors(
                            containerColor = Color.Green,
                            contentColor = Color.Green,
                            disabledContainerColor = White,
                            disabledContentColor = White
                        ),
                        onClick = { tarkista() },
                    ) {
                        Text(
                            "Tarkista",
                            color = White,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            // instructions how to play
            Column(
                modifier = Modifier.fillMaxWidth().height(500.dp).padding(0.dp, 0.dp, 0.dp, 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Text("How to play",  color = White, fontSize = 25.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp))
                Text(
                        "All words are finnish words. \n" +
                        "\n" +
                        "You have six tries to guess the word correct and the word length is always 5 character.\n" +
                        "\n" +
                        "Green on the keyboard key or in the character box means the character is in the right place and it is in the word.\n" +
                        "\n" +
                        "Yellow on the keyboard key or in the character box means the character is not in the right place but the character is in the word.\n" +
                        "\n" +
                        "Gray on the keyboard key or in the character box means the character is not in the right place and it is not in the word.\n",
                        color = White, fontSize = 20.sp, textAlign = TextAlign.Center
                    )
            }
        }
    }

    // popup opens when the game is over
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
                        // clears all the values
                        onClick = {
                            isPopupOpen = false
                            nakyvatKirjaimetKohta = 0
                            KayttajaSanat = emptyArray<String>()
                            nakyvatKirjaimet.clear()
                            kaikkiKirjaimet.clear()
                            palautettuKirjaimet.clear()
                            paikat.clear()
                            arvauksienMaara = 0
                            showContent = true
                            isRight = "ARVASIT OKEIN!!"
                            sana = sanatTOlist.random().trim()
                            palautetut.clear()
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
