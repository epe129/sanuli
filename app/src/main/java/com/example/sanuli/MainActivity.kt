package com.example.sanuli

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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
    var kirjain1 by remember { mutableStateOf("") }
    var kirjain2 by remember { mutableStateOf("") }
    var kirjain3 by remember { mutableStateOf("") }
    var kirjain4 by remember { mutableStateOf("") }
    var kirjain5 by remember { mutableStateOf("") }

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

    Column(
        modifier = Modifier.fillMaxWidth().height(600.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val focusManager = LocalFocusManager.current
        Text("Sanuli",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold),
            fontSize = 20.sp
            )

        // rivi 1
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1,
                onValueChange = {
                    if (it.length == 0) kirjain1 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                            )
                        kirjain1 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2,
                onValueChange = {
                    if (it.length == 0) kirjain2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain2 = it
                    }  },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3,
                onValueChange = {
                    if (it.length == 0) kirjain3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain3 = it
                    } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4,
                onValueChange = {
                    if (it.length == 0) kirjain4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain4 = it
                    } },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5,
                onValueChange = {
                    if (it.length == 1) kirjain5 = it
                     },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }

        // rivi 2
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1_2,
                onValueChange = {
                    if (it.length == 0) kirjain1_2 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_2 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2_2,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3_2,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4_2,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5_2,
                onValueChange = {
                    if (it.length == 1) kirjain5_2 = it
                     },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }

        // rivi 3
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1_3,
                onValueChange = {
                    if (it.length == 0) kirjain1_3 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_3 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2_3,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3_3,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4_3,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5_3,
                onValueChange = {
                    if (it.length == 1) kirjain5_3 = it
                     },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }

        // rivi 4
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1_4,
                onValueChange = {
                    if (it.length == 0) kirjain1_4 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_4 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2_4,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3_4,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4_4,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5_4,
                onValueChange = {
                    if (it.length == 1) kirjain5_4 = it
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }

        // rivi 5
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1_5,
                onValueChange = {
                    if (it.length == 0) kirjain1_5 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_5 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2_5,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3_5,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4_5,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5_5,
                onValueChange = {
                    if (it.length == 1) kirjain5_5 = it
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }

        // rivi 6
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp),
            horizontalArrangement = Arrangement.Center,  // Jakaa tyhjän tilan tasaisesti elementtien väliin rivillä
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 5
        ) {
            OutlinedTextField(
                value = kirjain1_6,
                onValueChange = {
                    if (it.length == 0) kirjain1_6 = it
                    if (it.length == 1) {
                        focusManager.moveFocus(
                            focusDirection = FocusDirection.Next,
                        )
                        kirjain1_6 = it
                    } },
                singleLine = true,
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false, imeAction = ImeAction.Next),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain2_6,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain3_6,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain4_6,
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
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
            OutlinedTextField(
                value = kirjain5_6,
                onValueChange = {
                    if (it.length == 1) kirjain5_6 = it
                    },
                singleLine = true,
                keyboardOptions = KeyboardOptions(showKeyboardOnFocus = false),
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .width(50.dp)
                    .background(Color.White)
                    .border(2.dp, Color.LightGray, shape = RoundedCornerShape(5.dp)),
            )
        }
    }
}
