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
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.RectangleShape
import java.io.IOException
import kotlin.collections.forEachIndexed

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
// data object that is uset to get the json the right way
data class Sanat(
    val sanat: Map<String, String>
)

// GETS THE DATA FROM THE JSON
fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
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
    val kayttajaSanat2 = remember { mutableStateListOf<String>() }
    val kayttajaSanat3 = remember { mutableStateListOf<String>() }
    val kayttajaSanat4 = remember { mutableStateListOf<String>() }
    val kayttajaSanat5 = remember { mutableStateListOf<String>() }
    val kayttajaSanat6 = remember { mutableStateListOf<String>() }

    val nakyvatKirjaimet = remember { mutableStateListOf<String>("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","") }
    val nakyvatKirjaimetKAIKKI = remember { mutableStateListOf<String>("","","","","","","","","","","","","","","","","","","","","","","","","","","","","","") }
    var nakyvatKirjaimetKohtaKAKKI by remember { mutableIntStateOf(0) }
    var nakyvatKirjaimetKohta by remember { mutableIntStateOf(0) }
    var huijausClickt by remember { mutableStateOf(false) }
    var huijausClicktText by remember { mutableStateOf("") }
    var checkClickt by remember { mutableStateOf(false) }
    val varitTextfieldeka = remember { mutableStateListOf<String>() }

    val yleisetSanat = remember { mutableStateListOf<String>() }
    val muutSanat = remember { mutableStateListOf<String>() }
    // gets the json data
    val jsonFileString = getJsonDataFromAsset(context, "sanat.json")
    val gson = Gson()
    val data = gson.fromJson(jsonFileString, Sanat::class.java)
    // loops trought json and adds common words into yleisensanat list and other words to muusanat list
    data.sanat.forEach { (id, word) ->
        if ("y" in id) {
            yleisetSanat.add(word)
            //println("$id = $word")
        } else {
            muutSanat.add(word)
        }
    }

    // gets the random word from sanatTOList
    var sana by remember(yleisetSanat) { mutableStateOf(yleisetSanat.random().trim()) }

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
        if (sub !in yleisetSanat && sub !in muutSanat) {
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
                        paikat[i + 1] = index.toString()
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
        if (arvauksienMaara == 2) {
            kayttajaSanat2.addAll(kaikkiKirjaimet)
        }
        else if (arvauksienMaara == 3) {
            kayttajaSanat3.addAll(kaikkiKirjaimet)
        }
        else if (arvauksienMaara == 4) {
            kayttajaSanat4.addAll(kaikkiKirjaimet)
        }
        else if (arvauksienMaara == 5) {
            kayttajaSanat5.addAll(kaikkiKirjaimet)
        }
        else if (arvauksienMaara == 6) {
            kayttajaSanat6.addAll(kaikkiKirjaimet)
        }
        palautetut.add(arvauksienMaara.toString())
        nykyKohta = 0
        nakyvatKirjaimetKohta = 0
        println(palautetut)
        // SHOULD WORK
        if ("1" in palautetut  && kayttajaSanat.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add( "harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
        }
        if ("2" in palautetut && kayttajaSanat2.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat2[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat2[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add("harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
        }
        if ("3" in palautetut && kayttajaSanat3.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat3[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat3[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add("harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
        }
        if ("4" in palautetut && kayttajaSanat4.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat4[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat4[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add("harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
        }
        if ("5" in palautetut && kayttajaSanat5.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat5[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat5[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add("harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
        }
        if ("6" in palautetut && kayttajaSanat6.isNotEmpty()) {
            for (k in 0..4) {
                if (kayttajaSanat6[k].lowercase() == sana[k].lowercase()) {
                    varitTextfieldeka.add("vihrea")
                } else if (kayttajaSanat6[k].lowercase() in sana.lowercase()) {
                    varitTextfieldeka.add("keltanen")
                } else varitTextfieldeka.add("harmaa")
                if (k == 4) {
                    palautetut.removeAt(0)
                    break
                }
            }
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
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 9) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 9) {
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
                            value = if (index >= 10 && index <= 14) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 10 && index <= 14) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
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
                                  unfocusedContainerColor = if (varitTextfieldeka.size >= 14) {
                                      if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                          Color.Green
                                      } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                          Color.Yellow
                                      } else Color.Gray
                                  } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 14) {
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
                            value = if (index >= 15 && index <= 19) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 15 && index <= 19) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
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
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 19) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 19) {
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
                            value = if (index >= 20 && index <= 24) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 20 && index <= 24) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
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
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 24) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 24) {
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
                            value = if (index >= 25 && index <= 29) { nakyvatKirjaimetKAIKKI[index] } else return@forEachIndexed,
                            onValueChange = {
                                if (index >= 25 && index <= 29) { nakyvatKirjaimetKAIKKI[index] = it  } else return@OutlinedTextField
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
                                unfocusedContainerColor = if (varitTextfieldeka.size >= 29) {
                                    if (varitTextfieldeka[index].lowercase() == "vihrea") {
                                        Color.Green
                                    } else if (varitTextfieldeka[index].lowercase() == "keltanen") {
                                        Color.Yellow
                                    } else Color.Gray
                                } else White,
                                focusedContainerColor = if (varitTextfieldeka.size >= 29) {
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
            // KEYBOARD COLORS DOESN'T WORK
            // KEYBOARD /////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Column(
                modifier = Modifier.fillMaxWidth().height(250.dp).offset(0.dp, (-75).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                // rows
                FlowRow(
                    modifier = Modifier.padding(0.dp).width(420.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(1.dp, Alignment.CenterVertically),
                    ) {
                    kirjaimet.forEachIndexed { index, item ->
                        TextButton(
                            colors = ButtonColors(
                                containerColor =
                                    (if (item in palautettuKirjaimet && paikat.size >= 2) {
                                        if (item in sana.uppercase()) {
                                            if (paikat[index] == "-1" && paikat[index+1] == "-1") {
                                                Color.Blue
                                            }
                                            if (paikat[index].toInt() == paikat[index+1].toInt()) {
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
                            shape = RectangleShape,
                            modifier = Modifier
                                .padding(1.dp)
                                .width(34.dp),
                                //.clip(shape = CircleShape),
                            onClick = { addKirjain(item) },
                        ) {
                            Text(item, fontSize = 25.sp, textAlign = TextAlign.Center)
                        }
                    }
                    // clear one character at time button
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

                FlowRow(
                    modifier = Modifier.padding(0.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    ) {
                    // check button
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
                verticalArrangement = Arrangement.Bottom,)
            {
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
        Popup(onDismissRequest = { isPopupOpen = false; showContent = true; sana = yleisetSanat.random().trim() }, isRight, sana)
        kayttajaSanat.clear()
        kayttajaSanat2.clear()
        kayttajaSanat3.clear()
        kayttajaSanat4.clear()
        kayttajaSanat5.clear()
        kayttajaSanat6.clear()
        nakyvatKirjaimet.clear()
        kaikkiKirjaimet.clear()
        palautettuKirjaimet.clear()
        paikat.clear()
        nakyvatKirjaimetKAIKKI.clear()
        for (lisaa in 0..30) {
            nakyvatKirjaimetKAIKKI.add(lisaa, "")
        }
        palautetut.clear()
        arvauksienMaara = 0
        nakyvatKirjaimetKohtaKAKKI = 0
        nakyvatKirjaimetKohta = 0
        varitTextfieldeka.clear()
    }
}
