package com.dripemporium.weather.ui.screens

import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dripemporium.weather.ui.viewmodels.LocationViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LocationListScreen(
    navController: NavHostController,
    viewModel: LocationViewModel = hiltViewModel(),
    onDateSelected: (Any) -> Unit
) {
    val locations = viewModel.locations.collectAsState()

    Column {
        TextField(
            value = "",
            onValueChange = { viewModel.fetchLocations(it) },
            label = { Text("Search Locations") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(locations.value) { location ->
                Text(
                    text = "${location.name}, ${location.country}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("location_details/${location.id}") }
                        .padding(16.dp),
                    style = MaterialTheme.typography.h6
                )
            }
        }

        Column {
            CalendarScreen(onDateSelected = onDateSelected)
        }
        }
    }

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun InlineCalendar(
//    onDateSelected: (LocalDate) -> Unit
//) {
//    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
//    var openPicker by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier
//        .fillMaxWidth()
//        .padding(16.dp)) {
//        // Show the calendar inline
//        DatePicker(
//            state = rememberDatePickerState(
//                initialSelectedDateMillis = selectedDate.toEpochDay() * 24 * 60 * 60 * 1000L
//            ),
//            onDateChange = { epochMillis ->
//                selectedDate = Instant.ofEpochMilli(epochMillis)
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate()
//                onDateSelected(selectedDate)
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Display selected date
//        Text(
//            text = "Selected Date: ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))}",
//            style = MaterialTheme.typography.body1
//        )
//    }
//
//
//}
//@Composable
//fun CalendarScreen() {
//    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
//    val currentMonth = remember { YearMonth.now() }
//    val today = remember { LocalDate.now() }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Selected Date: ${selectedDate ?: "None"}",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Calendar View
//        CalendarView(
//            month = currentMonth,
//            selectedDate = selectedDate,
//            onDateSelected = { selectedDate = it },
//            today = today
//        )
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(onDateSelected: (Any) -> Unit) {
    var selectedDate: MutableState<LocalDate?> = remember { mutableStateOf(null) }
    val currentMonth = remember { YearMonth.now() }
    val today = remember { LocalDate.now() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Selected Date: ${selectedDate.value ?: "None"}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Calendar View
        CalendarView(
            month = currentMonth,
            selectedDate = selectedDate.value,
            onDateSelected = {
                selectedDate.value = it
                onDateSelected(it.toString())
                             },
            today = today
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    month: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit,
    today: LocalDate
) {
    val firstDayOfMonth = month.atDay(1)
    val daysInMonth = month.lengthOfMonth()
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust to start with Sunday
    val days = (1..daysInMonth).toList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Month Title
        Text(
            text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + month.year,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Weekday Headers
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(text = it, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Calendar Grid
        val rows = (days.size + startDayOfWeek - 1) / 7 + 1
        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0..6) {
                    val index = row * 7 + col - startDayOfWeek + 1
                    if (index in days) {
                        val date = firstDayOfMonth.plusDays((index - 1).toLong())
                        DayCell(
                            date = date,
                            isSelected = date == selectedDate,
                            isToday = date == today,
                            onClick = onDateSelected
                        )
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCell(date: LocalDate, isSelected: Boolean, isToday: Boolean, onClick: (LocalDate?) -> Unit) {
    val backgroundColor = when {
        isSelected -> Color.Blue
        isToday -> Color.Green
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(backgroundColor)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            fontSize = 14.sp,
            color = if (isSelected || isToday) Color.White else Color.Black,
            modifier = Modifier.clickable { onClick(date) }
        )
    }
}