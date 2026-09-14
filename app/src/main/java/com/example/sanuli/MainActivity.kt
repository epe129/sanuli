package com.example.sanuli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sanuli.ui.theme.SanuliTheme
import kotlin.collections.mutableListOf
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.DarkGray) { innerPadding -> Game(modifier = Modifier.padding(innerPadding), context = this) }
            }
        }
    }
}

object DataManager {
    var data = ""
    fun loadAssetsFromFile(context : Context) {
        val inputStream = context.assets.open("sanat.json")
        val size : Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, charset = Charsets.UTF_8)
        data = json
    }
}

@Composable
fun Game(context: Context, modifier: Modifier) {
    var showContent by remember { mutableStateOf(true) }
    var isPopupOpen by remember { mutableStateOf(false) }
    var isRight by remember { mutableStateOf("") }
    val kaikkiKirjaimet = remember { mutableStateListOf<String>() }
    var arvauksienMaara by remember { mutableIntStateOf(0) }
    var nykyKohta by remember { mutableIntStateOf(0) }
    val palautetut = remember { mutableStateListOf<String>() }
    val palautettuKirjaimet = remember { mutableStateListOf<String>() }
    val paikat = remember { mutableStateListOf<String>() }
    val kirjaimet = listOf("Q", "W", "E","R","T","Y","U","I","O","P","Å","A","S","D","F","G","H","J","K","L","Ö","Ä","Z","X","C","V","B","N","M")
    val kaydytNumerot = remember { mutableStateListOf<String>() }
    val kaydytKirjaimet = remember { mutableStateListOf<String>() }
    val kayttajaSanat = remember { mutableStateListOf<String>() }
    val nakyvatKirjaimet = remember { mutableStateListOf<String>("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","") }
    val nakyvatKirjaimetKAIKKI = remember { mutableStateListOf<String>("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","") }
    var nakyvatKirjaimetKohtaKAKKI by remember { mutableIntStateOf(0) }
    var nakyvatKirjaimetKohta by remember { mutableIntStateOf(0) }
    var huijausClickt by remember { mutableStateOf(false) }
    var huijausClicktText by remember { mutableStateOf("") }
    var checkClickt by remember { mutableStateOf(false) }
    val varitTextfieldeka = remember { mutableStateListOf<String>() }
    val varitTextfieldtoka = remember { mutableStateListOf<String>() }
    val varitTextfieldkolmas = remember { mutableStateListOf<String>() }
    val varitTextfieldneljas = remember { mutableStateListOf<String>() }
    val varitTextfieldviides = remember { mutableStateListOf<String>() }
    val varitTextfieldkuudes = remember { mutableStateListOf<String>() }


    // makes the json to list and gets the data from json using object data
    DataManager.loadAssetsFromFile(context)
    val sanatTOlist =  remember { DataManager.data.replace("""[{}:"]""".toRegex(), "").replace("sanat", "").replace("]", "").replace("[", "").lowercase().trim().split(",") .map { it.trim() } }

    // gets the random word from sanatTOList
    var sana by remember(sanatTOlist) { mutableStateOf(sanatTOlist.random().trim()) }

    // adds kirjain to the list's
    fun addKirjain(kirjain: String) {
        if (kaikkiKirjaimet.size == 5) {
            return
        }
        nakyvatKirjaimetKAIKKI.add(nakyvatKirjaimetKohtaKAKKI, kirjain)
        kaikkiKirjaimet.add(nykyKohta, kirjain)
        nakyvatKirjaimet.add(nakyvatKirjaimetKohta, kirjain)
        nykyKohta += 1
        nakyvatKirjaimetKohta += 1
        nakyvatKirjaimetKohtaKAKKI += 1
    }

    // check if user got the word correct
    fun tarkista() {
        nakyvatKirjaimet.clear()
        checkClickt = false
        if (kaikkiKirjaimet.size < 5) {
            return
        }
        val sub = kaikkiKirjaimet.joinToString("").lowercase().trim()
        if (sub !in sanatTOlist) {
            checkClickt = true
            return
        }
        kayttajaSanat.addAll(kaikkiKirjaimet)
        kaydytNumerot.clear()
        kaydytKirjaimet.clear()
        // check if right
        if (sub.length < 5) {
            return
        } else {
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (sub == sana) {
                isRight = "ARVASIT OIKEIN!!"
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
                // if a letter has been checked, move on to the next letter
                if (i.toString() in kaydytNumerot || (i + 1).toString() in kaydytNumerot || k in kaydytKirjaimet) {
                    continue
                }
                // loops trough sana and check if kirjaimet are in same place in sana and what user has typed
                // if are adds index's to the paikat list
                for ((index, value) in sana.withIndex()) {
                    if (value.uppercase() == k && kaikkiKirjaimet[index].uppercase() == k) {
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
        for (kaikki in nakyvatKirjaimetKAIKKI) {
            nakyvatKirjaimet.add(kaikki)
        }
        arvauksienMaara += 1
        palautetut.add(arvauksienMaara.toString())
        nykyKohta = 0
        nakyvatKirjaimetKohta = 0

        for (k in 0..31) {
            if ("1" in palautetut && kayttajaSanat.isNotEmpty() && k <= 4) {
                if (kayttajaSanat[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add(k, "vihrea")
                } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add(k, "keltanen")
                } else varitTextfieldeka.add(k, "harmaa")
                if (k == 4) {break}
            }
            if ("2" in palautetut && kayttajaSanat.isNotEmpty() && k >= 5 && k <= 9) {
                for (s in 0..4){
                    if (kayttajaSanat[k].lowercase() == sana[s].lowercase()) {
                        varitTextfieldeka.add(k, "vihrea")
                    } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                        varitTextfieldeka.add(k, "keltanen")
                    } else varitTextfieldeka.add(k, "harmaa")
                }
                if (k == 9) {break}
            }
            /*if () {
                for (s in 0..4){
                    if (kayttajaSanat[k].lowercase() == sana[s].lowercase()) {
                        varitTextfieldeka.add(k, "vihrea")
                    } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                        varitTextfieldeka.add(k, "keltanen")
                    } else varitTextfieldeka.add(k, "harmaa")
                }
                if (k ) {break}
            }
            if () {
                for (s in 0..4){
                    if (kayttajaSanat[k].lowercase() == sana[s].lowercase()) {
                        varitTextfieldeka.add(k, "vihrea")
                    } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                        varitTextfieldeka.add(k, "keltanen")
                    } else varitTextfieldeka.add(k, "harmaa")
                }
                if (k == ) {break}
            }
            if () {
                for (s in 0..4){
                    if (kayttajaSanat[k].lowercase() == sana[s].lowercase()) {
                        varitTextfieldeka.add(k, "vihrea")
                    } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                        varitTextfieldeka.add(k, "keltanen")
                    } else varitTextfieldeka.add(k, "harmaa")
                }
                if (k == ) {break}
            }
            if () {
                for (s in 0..4){
                    if (kayttajaSanat[k].lowercase() == sana[s].lowercase()) {
                        varitTextfieldeka.add(k, "vihrea")
                    } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                        varitTextfieldeka.add(k, "keltanen")
                    } else varitTextfieldeka.add(k, "harmaa")
                }
                if (k == ) {break}
            }*/
        }

        // if user didn't get the word right
        if (arvauksienMaara >= 6 && !isPopupOpen) {
            isRight = "ikävä kyllä tällä kertaa et arvannut oikein!"
            isPopupOpen = true
            showContent = false
            arvauksienMaara = 0
        }
        kaikkiKirjaimet.clear()
    }

    // shows the sanuli game if game is over doesn't show
    if (showContent) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().height(575.dp),
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index <= 4) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index <= 4) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 4) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 4) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedIndicatorColor = White,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index >= 5 && index <= 9) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 5 && index <= 9) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 10) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 10) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedIndicatorColor = White,
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index >= 10 && index <= 15) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 10 && index <= 15) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                  unfocusedContainerColor = if (varitTextfieldeka.size >= 15) {
                                      if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                          Color.Green
                                      } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                          Color.Yellow
                                      } else Color.Gray
                                  } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 15) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                  focusedIndicatorColor = White,
                              ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index >= 15 && index <= 20) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 15 && index <= 20) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 20) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 20) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                  focusedIndicatorColor = White,
                              ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index >= 20 && index <= 25) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 20 && index <= 25) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 25) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 25) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                  focusedIndicatorColor = White,
                              ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
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
                    nakyvatKirjaimetKAIKKI.forEachIndexed { index, item ->
                        OutlinedTextField(
                            value = if (index >= 25 && index <= 30) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 25 && index <= 30) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
                            },
                            readOnly = true,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            ),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 30) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 30) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                  focusedIndicatorColor = White,
                                ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .width(75.dp),
                        )
                    }
                }
                // cheat button what shows the correct word
                Button(
                    onClick = {
                        huijausClicktText = sana
                        huijausClickt = true
                    },
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.Transparent,
                    ),
                ) {}
                // shows the cheating text for 250 milliseconds
                if (huijausClickt) {
                    LaunchedEffect(key1 = Unit){
                        delay(250.milliseconds)
                        huijausClicktText = ""
                        huijausClickt = false
                    }
                    Text(huijausClicktText, color = White, fontSize = 25.sp)
                }
                //if user puts just random characters, and it is not in the sanat list shows text
                if (checkClickt) {
                    Text("Ei sanulistalla.", color = White, fontSize = 25.sp)
                }
            }

            // KEYBOARD /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Column(
                modifier = Modifier.fillMaxWidth().height(200.dp).offset(0.dp, (-25).dp),
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
                        onClick = { addKirjain("Q") },
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
                        onClick = { addKirjain("W") },
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
                        onClick = { addKirjain("E") },
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
                        onClick = { addKirjain("R") },
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
                        onClick = { addKirjain("T") },
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
                        onClick = { addKirjain("Y") },
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
                        onClick = { addKirjain("U") },
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
                        onClick = { addKirjain("I") },
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
                        onClick = { addKirjain("O") },
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
                        onClick = { addKirjain("P") },
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
                        onClick = { addKirjain("Å") },
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
                        onClick = { addKirjain("A") },
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
                        onClick = { addKirjain("S") },
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
                        onClick = { addKirjain("D") },
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
                        onClick = { addKirjain("F") },
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
                        onClick = { addKirjain("G") },
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
                        onClick = { addKirjain("H") },
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
                        onClick = { addKirjain("J") },
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
                        onClick = { addKirjain("K") },
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
                        onClick = { addKirjain("L") },
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
                        onClick = { addKirjain("Ö") },
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
                        onClick = { addKirjain("Ä") },
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
                        onClick = { addKirjain("Z") },
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
                        onClick = { addKirjain("X") },
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
                        onClick = { addKirjain("C") },
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
                        onClick = { addKirjain("V") },
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
                        onClick = { addKirjain("B") },
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
                        onClick = { addKirjain("N") },
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
                        onClick = { addKirjain("M") },
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
                            nakyvatKirjaimetKohtaKAKKI -= 1
                            nakyvatKirjaimet.removeAt(nakyvatKirjaimetKohta)
                            kaikkiKirjaimet.removeAt(nykyKohta)
                            nakyvatKirjaimetKAIKKI.removeAt(nakyvatKirjaimetKohtaKAKKI)
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
                            containerColor = Color(0xFF1B5E20),
                            contentColor = Color(0xFF1B5E20),
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
    if (isPopupOpen) {
        Popup(onDismissRequest = { isPopupOpen = false; showContent = true; sana = sanatTOlist.random().trim() }, isRight, sana)
        kayttajaSanat.clear()
        nakyvatKirjaimet.clear()
        kaikkiKirjaimet.clear()
        palautettuKirjaimet.clear()
        paikat.clear()
        palautetut.clear()
        nakyvatKirjaimetKAIKKI.clear()
        for (lisaa in 0..30) {
            nakyvatKirjaimetKAIKKI.add(lisaa, "")
        }
        arvauksienMaara = 0
        nakyvatKirjaimetKohtaKAKKI = 0
        nakyvatKirjaimetKohta = 0
    }
}
