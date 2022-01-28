package com.example.jetpackcomposetest


import android.graphics.fonts.FontStyle
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposetest.ui.theme.JetpackComposeTestTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTestTheme {
                //**********
                Column(
                    Modifier
                        .fillMaxSize()
//                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .verticalScroll(rememberScrollState()),
                    Arrangement.SpaceEvenly
                ) {
                    Text(
                        "What is Lorem Ipsum?\n" +
                                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "\n" +
                                "...............",
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(30.dp)
                            .background(Color.Yellow)
                            .requiredSize(200.dp)
                            .verticalScroll(rememberScrollState())
                    )

                    //**********

                    val checkedState = remember { mutableStateOf(true) }
                    var txt = ""
                    Row {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                            modifier = Modifier.padding(5.dp)
                        )
                        if (checkedState.value) {
                            txt = "Выбрано"
                        } else {
                            txt = "Не выбрано"
                        }
                        Text(txt, fontSize = 22.sp)
                    }

                    //**********
                    val state = remember { mutableStateOf(true) }
                    Column(Modifier.selectableGroup()) {
                        Row() {
                            RadioButton(
                                selected = state.value,
                                onClick = { state.value = true },
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Kotlin",
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight()
                            )
                        }
                        Row() {
                            RadioButton(
                                selected = !state.value,
                                onClick = { state.value = false },
                                modifier = Modifier.padding(8.dp)
                            )
                            Text(
                                text = "Java",
                                Modifier
                                    .padding(8.dp)
                                    .fillMaxHeight()
                            )
                        }

                    }
                    //**********

                    val colors = listOf(Color.Red, Color.Green, Color.Blue)
                    val selectedOption = remember { mutableStateOf(colors[0]) }

                    Column(modifier = Modifier.padding(20.dp)) {
                        Row() {
                            Box(
                                Modifier
                                    .padding(10.dp)
                                    .size(100.dp)
                                    .background(color = selectedOption.value)
                            )
                            Text(text = getColorName(selectedOption.value))
                        }


                        colors.forEach { color ->
                            val selected = selectedOption.value == color
                            Box(
                                Modifier
                                    .padding(8.dp)
                                    .size(60.dp)
                                    .background(color = color)
                                    .selectable(
                                        selected = selected,
                                        onClick = { selectedOption.value = color }
                                    )
                                    .border(
                                        width = if (selected) {
                                            2.dp
                                        } else {
                                            0.dp
                                        },
                                        color = Color.Black
                                    )
                            )
                        }
                        //**********
                        val languages = listOf("Kotlin", "Java", "JavaScript", "Ruby")
                        val (selectedOption, onOptionSelected) = remember { mutableStateOf(languages[0]) }

                        Column(Modifier.selectableGroup()) {
                            languages.forEach() { text ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(56.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) })
                                    Text(text = text, fontSize = 22.sp)
                                }
                            }
                        }
                        //**********

                        val count = remember { mutableStateOf(0) }
                        var c: String
                        var bool = true
                        if (count.value < 5) {
                            c = count.value.toString()
                        } else {
                            c = "END!"
                            bool = false
                        }
                        Text(
                            text = "Clicks: ${c} ",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .clickable(bool, null, null) { count.value += 1 }
//                            .clickable { count.value += 1 }
                                .padding(30.dp)
                        )

                        //**********

                        val words = listOf("Kotlin", "Java", "JavaScript", "Scala")
                        Column {
                            for (i in words) {
                                var r = Random.nextInt(0, 100)
                                Text(
                                    text = i,
                                    fontSize = 22.sp,
                                    color = Color.Red,
                                    fontFamily = FontFamily.Cursive,
//                                letterSpacing = r.sp
                                )
                            }

                            //**********

                            val message = remember { mutableStateOf("") }
                            TextField(
                                message.value,
                                { message.value = it },
                                textStyle = TextStyle(fontSize = 28.sp),
                                placeholder = { Text("Hello Work!") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Проверено"
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.Info,
                                        contentDescription = "Дополнительная информация"
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = Color.Red,
                                    backgroundColor = Color.LightGray
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getColorName(value: Color): String {
        when (value) {
            Color.Red -> return "Красный"
            Color.Green -> return "Зеленый"
            Color.Blue -> return "Синий"
        }
        return "Не знаю такой цвет"
    }
}

