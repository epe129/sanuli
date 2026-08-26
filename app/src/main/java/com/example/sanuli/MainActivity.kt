package com.example.sanuli

import android.R
import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.sanuli.ui.theme.SanuliTheme
import java.io.File
import kotlin.collections.mutableListOf
import kotlin.random.Random
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Gray) { innerPadding ->
                    Game( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
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
    val sanat = listOf<String>("qoira")
    var sana by remember(sanat) { mutableStateOf(sanat.random()) }
    var nykyKohta by remember { mutableIntStateOf(0) }
    var palautettu by remember { mutableStateOf(false) }
    var palautettu2 by remember { mutableStateOf(false) }
    var palautettu3 by remember { mutableStateOf(false) }
    var palautettu4 by remember { mutableStateOf(false) }
    var palautettu5 by remember { mutableStateOf(false) }
    var palautettu6 by remember { mutableStateOf(false) }
    val palautettuKirjaimet = remember { mutableStateListOf<String>() }
    val paikat = remember { mutableStateListOf<String>() }

    //var indexK by remember { mutableStateOf(0) }
    //var indexKaikki by remember { mutableStateOf(0) }


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
            for (c in kaikkiKirjaimet) {
                if (c !in palautettuKirjaimet) {
                    palautettuKirjaimet.add(c)
                }
            }
            if (kirjaimet1.joinToString(separator = "").replace(",", "").lowercase().trim() == sana && palautettu) {
                isPopupOpen = true
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
            }
        }
        paikat.clear()
        paikat.add(sana.uppercase().indexOf("Q").toString())
        paikat.add(kaikkiKirjaimet.lastIndexOf("Q").toString())
        arvauksienMaara += 1
        nykyKohta = 0
        if (arvauksienMaara > 5) {
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
                    (if ("Q" in palautettuKirjaimet) {
                        if ("Q" in sana.uppercase()) {
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
                colors = ButtonColors(containerColor = (if ("W" in palautettuKirjaimet) {
                    if ("W" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("W") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("W") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("E" in palautettuKirjaimet) {
                    if ("E" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("E") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("E") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("R" in palautettuKirjaimet) {
                    if ("R" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("R") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("R") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("T" in palautettuKirjaimet) {
                    if ("T" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("T") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("T") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("Y" in palautettuKirjaimet) {
                    if ("Y" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("Y") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("Y") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("U" in palautettuKirjaimet) {
                    if ("U" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("U") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("U") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("I" in palautettuKirjaimet) {
                    if ("I" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("I") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("I") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("O" in palautettuKirjaimet) {
                    if ("O" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("O") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("O") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("P" in palautettuKirjaimet) {
                    if ("P" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("P") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("P") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("Å" in palautettuKirjaimet) {
                    if ("Å" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("Å") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("Å") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("A" in palautettuKirjaimet) {
                    if ("A" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("A") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("A") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("S" in palautettuKirjaimet) {
                    if ("S" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("S") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("S") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("D" in palautettuKirjaimet) {
                    if ("D" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("D") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("D") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("F" in palautettuKirjaimet) {
                    if ("F" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("F") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("F") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("G" in palautettuKirjaimet) {
                    if ("G" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("G") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("G") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("H" in palautettuKirjaimet) {
                    if ("H" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("H") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("H") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("J" in palautettuKirjaimet) {
                    if ("J" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("J") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("J") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("K" in palautettuKirjaimet) {
                    if ("K" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("K") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("K") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("L" in palautettuKirjaimet) {
                    if ("L" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("L") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("L") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("Ö" in palautettuKirjaimet) {
                    if ("Ö" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("Ö") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("Ö") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("Ä" in palautettuKirjaimet) {
                    if ("Ä" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("Ä") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("Ä") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("Z" in palautettuKirjaimet) {
                    if ("Z" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("Z") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("Z") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("X" in palautettuKirjaimet) {
                    if ("X" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("X") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("X") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("C" in palautettuKirjaimet) {
                    if ("C" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("C") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("C") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("V" in palautettuKirjaimet) {
                    if ("V" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("V") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("V") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("B" in palautettuKirjaimet) {
                    if ("B" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("B") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("B") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("N" in palautettuKirjaimet) {
                    if ("N" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("N") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("N") }
                        if (indexKaikki == indexK) {
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
                colors = ButtonColors(containerColor = (if ("M" in palautettuKirjaimet) {
                    if ("M" in sana.uppercase()) {
                        val indexK = remember { sana.uppercase().indexOf("M") }
                        val indexKaikki = remember { kaikkiKirjaimet.indexOf("M") }
                        if (indexKaikki == indexK) {
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
                        kirjaimet1[nykyKohta] = ""
                    }
                    if (arvauksienMaara == 1) {
                        if (!kirjaimet2.isNotEmpty() || kirjaimet2[0] == "") {
                            return@Button
                        }
                        nykyKohta -= 1
                        kirjaimet2[nykyKohta] = ""
                    }
                    if (arvauksienMaara == 2) {
                        if (!kirjaimet3.isNotEmpty() || kirjaimet3[0] == "") {
                            return@Button
                        }
                        nykyKohta -= 1
                        kirjaimet3[nykyKohta] = ""
                    }
                    if (arvauksienMaara == 3) {
                        if (!kirjaimet4.isNotEmpty() || kirjaimet4[0] == "") {
                            return@Button
                        }
                        nykyKohta -= 1
                        kirjaimet4[nykyKohta] = ""
                    }
                    if (arvauksienMaara == 4) {
                        if (!kirjaimet5.isNotEmpty() || kirjaimet5[0] == "") {
                            return@Button
                        }
                        nykyKohta -= 1
                        kirjaimet5[nykyKohta] = ""
                    }
                    if (arvauksienMaara == 5) {
                        if (!kirjaimet6.isNotEmpty() || kirjaimet6[0] == "") {
                            return@Button
                        }
                        nykyKohta -= 1
                        kirjaimet6[nykyKohta] = ""
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
