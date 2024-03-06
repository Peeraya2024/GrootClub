package com.example.grootclub.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import android.widget.TableRow
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun initDatePickerCurrentDate20(context: Context, editText: TextView, ageTextView: TextView) {
    Locale.setDefault(Locale("en", "EN"))
    val calendar = Calendar.getInstance(Locale("en", "EN"))
    val selectedDate = Calendar.getInstance(Locale("en", "EN"))
    calendar.add(Calendar.YEAR, -20)
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val day = calendar[Calendar.DAY_OF_MONTH]

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val thaiYear = year
        val fmDay = if (dayOfMonth < 10) "0${dayOfMonth}" else dayOfMonth
        val fmMonth = if (month < 9) "0${month + 1}" else month + 1
        val selectedDateStr = "$fmDay/${fmMonth}/$thaiYear"
        editText.text = selectedDateStr
        val dataSelectedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateStr)
        val age = calculateAge(dataSelectedDate!!)
        ageTextView.text = age.toString()
    }

    if (editText.text.isNotEmpty()) {
        val selectedDateStr = editText.text.toString()
        val dataSelectedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateStr)
        if (dataSelectedDate != null) {
            selectedDate.time = dataSelectedDate
        }
    }

    val dialog =
        DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day)
    dialog.updateDate(year, month, day)

    // Set maximum date to current date
    val maxDate = calendar.timeInMillis
    dialog.datePicker.maxDate = maxDate

    val title = "Calendar"
    val titleText = SpannableString(title)
    titleText.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        title.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    dialog.setTitle(titleText)

    dialog.updateDate(
        selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(
            Calendar.DAY_OF_MONTH
        )
    )


    dialog.show()
}

fun calculateAge(birthDate: Date): Int {
    val currentDate = Calendar.getInstance()
    val birthCalendar = Calendar.getInstance()
    birthCalendar.time = birthDate

    var age = currentDate.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    if (currentDate.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    return age
}

fun initDatePickerCurrentDateTomorrow(context: Context, editText: TextView) {
    val calendar = Calendar.getInstance()

    val selectedDate = Calendar.getInstance()

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val thaiYear = year
        val fmDay = if (dayOfMonth < 10) "0${dayOfMonth}" else dayOfMonth
        val fmMonth = if (month < 9) "0${month + 1}" else month + 1
        val selectedDateStr = "$fmDay/${fmMonth}/$thaiYear"
        editText.text = selectedDateStr
    }

    val dialog = DatePickerDialog(
        context, AlertDialog.THEME_HOLO_LIGHT, dateSetListener,
        selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH)
    )

    // Set maximum date to tomorrow
    val maxCalendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }
    dialog.datePicker.maxDate = maxCalendar.timeInMillis

    // Set minimum date to today
    dialog.datePicker.minDate = calendar.timeInMillis

    val title = "Calendar"
    val titleText = SpannableString(title)
    titleText.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        title.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    dialog.setTitle(titleText)

    dialog.show()
}


fun setArrayAdapter(context: Context, arrayAdapter: Array<String>) =
    ArrayAdapter(
        context,
        androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
        arrayAdapter
    )

fun setListAdapter(context: Context, arrayAdapter: Array<List<String>>) =
    ArrayAdapter(
        context,
        androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
        arrayAdapter
    )

fun marginLayoutParams(leftMargin: Int, topMargin: Int, rightMargin: Int, bottomMargin: Int): TableRow.LayoutParams {
    val layoutParams = TableRow.LayoutParams(
        TableRow.LayoutParams.WRAP_CONTENT,
        TableRow.LayoutParams.WRAP_CONTENT
    )
    layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
    return layoutParams
}

fun mapTypeSpotToApiValue(typeSpot: String): String {
    return when (typeSpot) {
        "Tennis" -> "TennisCourt"
        "Table Tennis" -> "TableTennisCourt"
        "Badminton" -> "BadmintonCourt"
        "Yoga" -> "YogaCourt"
        "Aerobic" -> "AerobicCourt"
        else -> "TennisCourt" // default
    }
}

fun mapTypeSpotToValue(typeSpot: String): String {
    return when (typeSpot) {
        "tennis" -> "Tennis"
        "table_tennis" -> "Table Tennis"
        "badminton" -> "Badminton"
        "yoga" -> "Yoga"
        "aerobic_dance" -> "Aerobic Dance"
        else -> "" // default
    }
}
