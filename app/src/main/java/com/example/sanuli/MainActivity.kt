package com.example.sanuli

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.sanuli.ui.theme.SanuliTheme
import kotlin.collections.mutableListOf
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SanuliTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    containerColor = Color(0xFFd3d3d3)) { innerPadding ->
                    Game( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Game(modifier: Modifier = Modifier) {
    val kirjaimet1 = remember { mutableStateListOf<String>() }
    val kirjaimet2 = remember { mutableStateListOf<String>() }
    val kirjaimet3 = remember { mutableStateListOf<String>() }
    val kirjaimet4 = remember { mutableStateListOf<String>() }
    val kirjaimet5 = remember { mutableStateListOf<String>() }
    val kirjaimet6 = remember { mutableStateListOf<String>() }
    var arvauksienMaara by remember { mutableIntStateOf(0) }
    val sanat = listOf<String>("koira")
    var sana by remember(sanat) { mutableStateOf(sanat.random()) }
    var nykyKohta by remember { mutableStateOf(0) }
    var palautettu by remember { mutableStateOf(false) }

    fun add_kirjain(kirjain: String) {
        if (arvauksienMaara == 0) {
            kirjaimet1.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 1) {
            kirjaimet2.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 2) {
            kirjaimet3.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 3) {
            kirjaimet4.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
        if (arvauksienMaara == 4) {
            kirjaimet5.add(nykyKohta, kirjain)
            nykyKohta += 1
        }

        if (arvauksienMaara == 5) {
            kirjaimet6.add(nykyKohta, kirjain)
            nykyKohta += 1
        }
    }

    fun tarkista() {
        if (arvauksienMaara == 0) {
            if (kirjaimet1.size < 5) {return}
            palautettu = true
        }
        if (arvauksienMaara == 1) {
            if (kirjaimet2.size < 5) {return}
        }
        if (arvauksienMaara == 2) {
            if (kirjaimet3.size < 5) {return}
        }
        if (arvauksienMaara == 3) {
            if (kirjaimet4.size < 5) {return}
        }
        if (arvauksienMaara == 4) {
            if (kirjaimet5.size < 5) {return}
        }
        if (arvauksienMaara == 5) {
            if (kirjaimet6.size < 5) {return}
        }
        arvauksienMaara += 1
        nykyKohta = 0
    }

    val kirjain1 = if (palautettu) {
        val d = mutableListOf<String>()
        for (i in sana) {
            d.add(i.toString())
        }
        if (kirjaimet1[0].lowercase() == d[0].lowercase()) {
            Color.Green
        }
        else if (kirjaimet1[0].lowercase() in sana.lowercase()) {
            Color.Yellow
        }
        else Color.Gray
    } else Color.Gray

    Column(
        modifier = Modifier.fillMaxWidth().height(550.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Sanuli",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().height(35.dp),
            style = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
            fontSize = 20.sp
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
                   unfocusedContainerColor =  kirjain1,
                   focusedContainerColor =  kirjain1
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
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
                    unfocusedContainerColor = White,
                    focusedContainerColor = White
                ),
                modifier = Modifier
                    .width(75.dp)
            )
        }
    }


    // KEYBOARD /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    Column(
        modifier = Modifier.fillMaxWidth().height(840.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        FlowRow(
            modifier = Modifier.padding(1.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            maxItemsInEachRow = 11,
        ) {
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Q") },
                ) {
                Text("Q", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("W") },
            ) {
                Text("W", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("E") },
            ) {
                Text("E", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("R") },
            ) {
                Text("R", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("T") },
            ) {
                Text("T", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Y") },
            ) {
                Text("Y", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("U") },
            ) {
                Text("U", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("I") },
            ) {
                Text("I", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("O") },
            ) {
                Text("O", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("P") },
            ) {
                Text("P", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Å") },
            ) {
                Text("Å", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("A") },
            ) {
                Text("A", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("S") },
            ) {
                Text("S", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("D") },
            ) {
                Text("D", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("F") },
            ) {
                Text("F", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("G") },
            ) {
                Text("G", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("H") },
            ) {
                Text("H", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("J") },
            ) {
                Text("J", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("K") },
            ) {
                Text("K", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("L") },
            ) {
                Text("L", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Ö") },
            ) {
                Text("Ö", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Ä") },
            ) {
                Text("Ä", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("Z") },
            ) {
                Text("Z", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("X") },
            ) {
                Text("X", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("C") },
            ) {
                Text("C", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("V") },
            ) {
                Text("V", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("B") },
            ) {
                Text("B", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("N") },
            ) {
                Text("N", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                onClick = { add_kirjain("M") },
            ) {
                Text("M", fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                colors = ButtonColors(containerColor = Color.Red, contentColor = Color.Red, disabledContainerColor = White, disabledContentColor = White),
                onClick = {
                    if (arvauksienMaara == 0) {
                        if (!kirjaimet1.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet1[0] == "") {
                            return@Button
                        }
                        if (kirjaimet1.size > 0) {
                            nykyKohta -= 1
                            kirjaimet1[nykyKohta] = ""
                        }
                    }
                    if (arvauksienMaara == 1) {
                        if (!kirjaimet2.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet2[0] == "") {
                            return@Button
                        }
                        if (kirjaimet2.size > 0) {
                            nykyKohta -= 1
                            kirjaimet2[nykyKohta] = ""
                        }
                    }

                    if (arvauksienMaara == 2) {
                        if (!kirjaimet3.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet3[0] == "") {
                            return@Button
                        }
                        if (kirjaimet3.size > 0) {
                            nykyKohta -= 1
                            kirjaimet3[nykyKohta] = ""
                        }
                    }

                    if (arvauksienMaara == 3) {
                        if (!kirjaimet4.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet4[0] == "") {
                            return@Button
                        }
                        if (kirjaimet4.size > 0) {
                            nykyKohta -= 1
                            kirjaimet4[nykyKohta] = ""
                        }
                    }

                    if (arvauksienMaara == 4) {
                        if (!kirjaimet5.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet5[0] == "") {
                            return@Button
                        }
                        if (kirjaimet5.size > 0) {
                            nykyKohta -= 1
                            kirjaimet5[nykyKohta] = ""
                        }
                    }

                    if (arvauksienMaara == 5) {
                        if (!kirjaimet6.isNotEmpty()) {
                            return@Button
                        }
                        if (kirjaimet6[0] == "") {
                            return@Button
                        }
                        if (kirjaimet6.size > 0) {
                            nykyKohta -= 1
                            kirjaimet6[nykyKohta] = ""
                        }
                    }

                },
            ) {
                Text("tyh", color = White, fontSize = 24.sp)
            }
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp)),
                colors = ButtonColors(containerColor = Color.Green, contentColor = Color.Green, disabledContainerColor = White, disabledContentColor = White),
                onClick = { tarkista() },
            ) {
                Text("Tarkista", color = White, fontSize = 24.sp)
            }
        }
    }
}
