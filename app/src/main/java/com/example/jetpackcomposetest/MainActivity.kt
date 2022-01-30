package com.example.jetpackcomposetest


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

private val TXT_SIZE = 22.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isAdded = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val scaffoldState = rememberScaffoldState()
            val count = remember { mutableStateOf(0) }
            //***For Drawer
            val selectedItem = remember { mutableStateOf("") }
            val items = listOf("Главная", "Контакты", "О приложении")
            //***For Slider

            Scaffold(
                scaffoldState = scaffoldState,
                drawerContent = {
                    for (item in items) {
                        Text(
                            text = item,
                            fontSize = TXT_SIZE,
                            modifier = Modifier.clickable {
                                selectedItem.value = item
                                scope.launch { scaffoldState.drawerState.close() }
                            })
                    }
                },
                topBar = {
                    TopAppBar() {
                        IconButton(onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, "Меню")
                        }
                        Text(text = selectedItem.value, fontSize = TXT_SIZE)
                        Spacer(modifier = Modifier.weight(1f, true))
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Инормация о приложении"
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Поиск")
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar() {
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Меню"
                            )
                        }
                        Spacer(Modifier.weight(1f, true))
                        IconButton(onClick = { }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Поиск"
                            )
                        }
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        content = {
                            if (isAdded.value) Icon(
                                Icons.Filled.Clear,
                                contentDescription = "Удалить"
                            )
                            else Icon(Icons.Filled.Add, contentDescription = "Добавить")
                        },
                        onClick = { isAdded.value = !isAdded.value }
                    )
                },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                content = {
                    Column(
                        Modifier
                            .padding(
                                bottom = it.calculateBottomPadding(),
                                start = dimensionResource(id = R.dimen.padding_small)
                            )
//                        .fillMaxSize()
//                        .background(Color.Cyan)
                            .verticalScroll(rememberScrollState()),
                        Arrangement.SpaceEvenly
                    ) {
                        InfoIconButton()

                        Image(
                            bitmap = ImageBitmap.imageResource(R.drawable.audi),
                            contentDescription = "Audi A5"
                        )

                        MyProgressBars()
                        BasketTextInfo(isAdded.value)
                        MyButtonForSnack(scope, scaffoldState, count)
                        MyButtonForAllert()
                        MyButtonForDropDownMenu()
                        LoremIpsumText()
                        MySwitch()
                        MySlider()
                        IconToggleButton()
                        CheckBox()
                        TwoRadioButtons()
                        MyFlowers()
                        SelectedColorBoxes()
                        LanguagesRadioGoup()
                        ClickableTextWithCounter()
                        MyWords()
                        TextFieldWithIcons()
                        MyFloatingActionButton()
                        MyExtendedFloatingActionButton()
                    }
                }
            )
        }
    }

    @Composable
    private fun MyFlowers() {
        val resources = LocalContext.current.resources
        var quantity = remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .border(3.dp, Color.DarkGray)
//                .padding(8.dp)
//                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = resources.getQuantityString(
                    R.plurals.flowers,
                    if (quantity.value.isNotEmpty()) {
                        quantity.value.toInt()
                    } else 0,
                    if (quantity.value.isNotEmpty()) {
                        quantity.value.toInt()
                    } else 0
                ),
                fontSize = 22.sp
            )
            OutlinedTextField(
                value = quantity.value, { quantity.value = it },
                textStyle = TextStyle(fontSize = 28.sp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }

    @Composable
    private fun MyProgressBars() {
        val scopePB = rememberCoroutineScope()
        var progress by remember { mutableStateOf(0.0f) }
        var isVisible by remember { mutableStateOf(false) }
        Column() {
            Text(text = "Статус: $progress", fontSize = TXT_SIZE)
            Row() {
                OutlinedButton(
                    onClick = {
                        scopePB.launch {
                            isVisible = true
                            progress = 0f
                            while (progress < 1f) {
                                progress += 0.1f
                                delay(1_000L)
                            }
                        }
                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(text = "Run", fontSize = TXT_SIZE)
                }
                OutlinedButton(
                    onClick = {
                        isVisible = false
                        progress = 0f
                    },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(text = "Stop", fontSize = 22.sp)
                }
            }
            if (isVisible) {
                Row() {
                    CircularProgressIndicator(
                        progress = progress,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                    CircularProgressIndicator(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
                }
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
                LinearProgressIndicator(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))
            } else {
            }
        }
    }

    @Composable
    private fun MyButtonForDropDownMenu() {
        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf("") }
        Column() {
            Text(text = "Выбран пункт: $selectedOption")
            Box() {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Показать меню")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 20.dp, y = 10.dp)
                ) {
                    DropdownMenuItem(onClick = { selectedOption = "Copy" }) {
                        Text(text = "Копировать")
                    }
                    DropdownMenuItem(onClick = {
                        selectedOption = "Paste"
                        expanded = false
                    }) {
                        Text(text = "Вставить")
                    }
                    Divider()
                    DropdownMenuItem(onClick = { selectedOption = "Delete" }) {
                        Text(text = "Удалить")
                    }
                }
            }
        }
    }

    @Composable
    private fun MyButtonForAllert() {
        val openDialog = remember { mutableStateOf(false) }
        OutlinedButton(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            onClick = { openDialog.value = true }
        ) {
            Text("Удалить", fontSize = 22.sp)
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false },
                title = { Text(text = "Подтверждение действия") },
                text = { Text("Вы действительно хотите удалить выбранный элемент?") },
                buttons = {
                    Row(
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = { openDialog.value = false },
                            modifier = Modifier
                                .weight(1f)
                                .padding(dimensionResource(id = R.dimen.padding_small))
                        ) {
                            Text(text = "Удалить")
                        }
                        Button(
                            onClick = { openDialog.value = false },
                            modifier = Modifier
                                .weight(1f)
                                .padding(dimensionResource(id = R.dimen.padding_small))
                        ) {
                            Text(text = "Отмена")
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun MySwitch() {
        val checkedState = remember { mutableStateOf(false) }
        val textColor = remember { mutableStateOf(Color.Unspecified) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Зеленый цвет", fontSize = 22.sp, color = textColor.value)
            Switch(checked = checkedState.value, onCheckedChange = {
                checkedState.value = it
                if (checkedState.value) textColor.value = Color(0xff00695C)
                else textColor.value = Color.Unspecified
            })
        }
    }

    @Composable
    private fun MySlider() {
        var sliderPosition by remember { mutableStateOf(0f) }
        Text(text = "Текущее значение: ${sliderPosition.toInt()}", fontSize = 22.sp)
        Slider(
            value = sliderPosition,
            valueRange = 0f..10f,
            steps = 9,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.red),
                activeTrackColor = Color(0xFFEF9A9A),
                inactiveTrackColor = Color(0xFFFFEBEE),
                inactiveTickColor = Color(0xFFEF9A9A),
                activeTickColor = colorResource(id = R.color.red)
            )
        )
    }

    @Composable
    private fun MyButtonForSnack(
        scope: CoroutineScope,
        scaffoldState: ScaffoldState,
        count: MutableState<Int>
    ) {
        Button(
            onClick = {
                scope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        "Count: ${count.value}",
                        "add count"
                    )
                    if (result == SnackbarResult.ActionPerformed) count.value++
                }
            }
        ) {
            Text(text = "Click for Snack!", fontSize = 22.sp)
        }
    }
}

@Composable
private fun MyExtendedFloatingActionButton() {
    val added = remember { mutableStateOf(false) }
    ExtendedFloatingActionButton(
        icon = {
            Icon(
                if (added.value) Icons.Filled.Add else Icons.Filled.Delete,
                contentDescription = "Добавить"
            )
        },
        text = { Text(if (added.value) "Добавить" else "Удалить") },
        onClick = { added.value = !added.value })
}

@Composable
private fun MyFloatingActionButton() {
    FloatingActionButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Add, contentDescription = "Добавить")
    }

}

@Composable
private fun TextFieldWithIcons() {
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

@Composable
private fun MyWords() {
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
    }
}

@Composable
private fun ClickableTextWithCounter() {
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
            .padding(30.dp)
    )
}

@Composable
private fun LanguagesRadioGoup() {
    val languages = listOf("Kotlin", "Java", "JavaScript", "Ruby")
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(
            languages[0]
        )
    }
    Column(Modifier.selectableGroup()) {
        languages.forEach() { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(selected = (text == selectedOption),
                        onClick = { onOptionSelected(text) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = null
                )
                Text(text = text, fontSize = 22.sp)
            }
        }
    }
}

@Composable
private fun SelectedColorBoxes() {
    val colors = listOf(Color.Red, Color.Green, Color.Blue)
    val selectedOption = remember { mutableStateOf(colors[0]) }

    Column(modifier = Modifier.padding(20.dp)) {
        Row() {
            Box(
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .size(100.dp)
                    .background(color = selectedOption.value)
            )
            Text(text = getColorName(selectedOption.value))
        }
        colors.forEach { color ->
            val selected = selectedOption.value == color
            Box(
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
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
    }
}

@Composable
private fun TwoRadioButtons() {
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
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            Text(
                text = "Java",
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .fillMaxHeight()
            )
        }

    }
}

@Composable
private fun CheckBox() {
    val checkedState = remember { mutableStateOf(true) }
    var txt = ""
    Row {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
        if (checkedState.value) {
            txt = "Выбрано"
        } else {
            txt = "Не выбрано"
        }
        Text(txt, fontSize = 22.sp)
    }
}

@Composable
private fun IconToggleButton() {
    val checkedIcon = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconToggleButton(
            checked = checkedIcon.value,
            onCheckedChange = { checkedIcon.value = it }) {
            Icon(
                Icons.Filled.Info,
                contentDescription = "Информация о приложении",
                tint = if (checkedIcon.value) Color(0xFFEC407A) else Color(
                    0xFFB0BEC5
                )
            )
        }
        Text(
            text = if (checkedIcon.value) "Выбрано" else "Не выбрано",
            fontSize = 28.sp
        )
    }
}

@Composable
private fun LoremIpsumText() {
    Text(
        text = stringResource(id = R.string.lorem_ipsum),
        fontSize = 22.sp,
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .background(Color.Yellow)
            .requiredSize(200.dp)
            .verticalScroll(rememberScrollState())
    )
}

@Composable
private fun InfoIconButton() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(Icons.Filled.Info, contentDescription = "Информация  о приложении")
    }
}

@Composable
private fun BasketTextInfo(isAdded: Boolean) {
    Text(
        if (isAdded) "Товар добавлен" else "Корзина пуста",
        fontSize = 28.sp
    )
}

private fun getColorName(value: Color): String {
    when (value) {
        Color.Red -> return "Красный"
        Color.Green -> return "Зеленый"
        Color.Blue -> return "Синий"
    }
    return "Не знаю такой цвет"
}
