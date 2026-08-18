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

    // var kirjain1 by remember { mutableStateOf("") }
    // var kirjain2 by remember { mutableStateOf("") }
    // var kirjain3 by remember { mutableStateOf("") }
    // var kirjain4 by remember { mutableStateOf("") }
    // var kirjain5 by remember { mutableStateOf("") }


    var kirjain1_2 by remember { mutableStateOf("") }
    var kirjain2_2 by remember { mutableStateOf("") }
    var kirjain3_2 by remember { mutableStateOf("") }
    var kirjain4_2 by remember { mutableStateOf("") }
    var kirjain5_2 by remember { mutableStateOf("") }

    var kirjain1_3 by remember { mutableStateOf("") }
    var kirjain2_3 by remember { mutableStateOf("") }
    var kirjain3_3 by remember { mutableStateOf("") }
    var kirjain4_3 by remember { mutableStateOf("") }
    var kirjain5_3 by remember { mutableStateOf("") }

    var kirjain1_4 by remember { mutableStateOf("") }
    var kirjain2_4 by remember { mutableStateOf("") }
    var kirjain3_4 by remember { mutableStateOf("") }
    var kirjain4_4 by remember { mutableStateOf("") }
    var kirjain5_4 by remember { mutableStateOf("") }

    var kirjain1_5 by remember { mutableStateOf("") }
    var kirjain2_5 by remember { mutableStateOf("") }
    var kirjain3_5 by remember { mutableStateOf("") }
    var kirjain4_5 by remember { mutableStateOf("") }
    var kirjain5_5 by remember { mutableStateOf("") }

    var kirjain1_6 by remember { mutableStateOf("") }
    var kirjain2_6 by remember { mutableStateOf("") }
    var kirjain3_6 by remember { mutableStateOf("") }
    var kirjain4_6 by remember { mutableStateOf("") }
    var kirjain5_6 by remember { mutableStateOf("") }

    var arvauksienMaara by remember { mutableIntStateOf(0) }

    val sanat = listOf<String>("Koira")

    var sana by remember(sanat) { mutableStateOf(sanat.random()) }

    var nykyKohta by remember { mutableStateOf(0) }

    fun add_kirjain(kirjain: String) {
        kirjaimet1.add(kirjain)
    }

    //val c =  if (kirjain1.length == 1) Color.Gray else White

    fun tarkista() {
        println("moi")
    }


    Column(
        modifier = Modifier.fillMaxWidth().height(550.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val focusManager = LocalFocusManager.current
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
                onValueChange = {
                    // if (it.length == 0) kirjain1 = it
                    // if (it.length == 1) {
                    //    focusManager.moveFocus(
                    //        focusDirection = FocusDirection.Next,
                    //        )
                    //    kirjain1 = it
                  //  }
                },
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
                value = if (kirjaimet1.size >= 2) { kirjaimet1[1] } else "",
                readOnly = true,
                onValueChange = {
                    // if (it.length == 0) kirjain1 = it
                    // if (it.length == 1) {
                    //    focusManager.moveFocus(
                    //        focusDirection = FocusDirection.Next,
                    //        )
                    //    kirjain1 = it
                    //  }
                },
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
                onValueChange = {
                    // if (it.length == 0) kirjain1 = it
                    // if (it.length == 1) {
                    //    focusManager.moveFocus(
                    //        focusDirection = FocusDirection.Next,
                    //        )
                    //    kirjain1 = it
                    //  }
                },
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
                onValueChange = {
                    // if (it.length == 0) kirjain1 = it
                    // if (it.length == 1) {
                    //    focusManager.moveFocus(
                    //        focusDirection = FocusDirection.Next,
                    //        )
                    //    kirjain1 = it
                    //  }
                },
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
                onValueChange = {
                    // if (it.length == 0) kirjain1 = it
                    // if (it.length == 1) {
                    //    focusManager.moveFocus(
                    //        focusDirection = FocusDirection.Next,
                    //        )
                    //    kirjain1 = it
                    //  }
                },
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
                value = kirjain1_2,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain1_2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_2 = it
                    } },
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
                value = kirjain2_2,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain2_2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2_2 = it
                    } },
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
                value = kirjain3_2,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain3_2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3_2 = it
                    } },
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
                value = kirjain4_2,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain4_2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4_2 = it
                    } },
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
                value = kirjain5_2,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 1) kirjain5_2 = it
                     },
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
                value = kirjain1_3,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain1_3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_3 = it
                    } },
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
                value = kirjain2_3,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain2_3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2_3 = it
                    } },
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
                value = kirjain3_3,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain3_3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3_3 = it
                    } },
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
                value = kirjain4_3,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain4_3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4_3 = it
                    } },
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
                value = kirjain5_3,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 1) kirjain5_3 = it
                     },
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
                value = kirjain1_4,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain1_4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_4 = it
                    } },
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
                value = kirjain2_4,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain2_4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2_4 = it
                    } },
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
                value = kirjain3_4,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain3_4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3_4 = it
                    } },
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
                value = kirjain4_4,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain4_4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4_4 = it
                    } },
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
                value = kirjain5_4,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 1) kirjain5_4 = it
                    },
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
                value = kirjain1_5,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain1_5 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_5 = it
                    } },
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
                value = kirjain2_5,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain2_5 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2_5 = it
                    } },
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
                value = kirjain3_5,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain3_5 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3_5 = it
                    } },
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
                value = kirjain4_5,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain4_5 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4_5 = it
                    } },
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
                value = kirjain5_5,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 1) kirjain5_5 = it
                    },
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
                value = kirjain1_6,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain1_6 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_6 = it
                    } },
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
                value = kirjain2_6,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain2_6 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2_6 = it
                    } },
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
                value = kirjain3_6,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain3_6 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3_6 = it
                    } },
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
                value = kirjain4_6,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 0) kirjain4_6 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4_6 = it
                    } },
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
                value = kirjain5_6,
                // readOnly = true,
                onValueChange = {
                    if (it.length == 1) kirjain5_6 = it
                    },
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
                    if (kirjaimet1.isNotEmpty()) {
                        kirjaimet1[kirjaimet1.size - 1] = ""
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
