package com.example.mytodos.utils

import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatTextView
import com.example.mytodos.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun showTimePicker(context: Context, onTimeSet: (String, selectedHour: Int, selectedMinute: Int) -> Unit) {
    val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, selectedHour: Int, selectedMinute: Int) {
                val time = "$selectedHour:$selectedMinute"
                var fmt = SimpleDateFormat("HH:mm")
                var date: Date? = null
                try {
                    date = fmt.parse(time)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val fmtOut = SimpleDateFormat("hh:mm aa")
                val formattedTime = fmtOut.format(date)
                onTimeSet.invoke(formattedTime, selectedHour, selectedMinute)

            }
        }
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("hh:mm aa")

    val timePicker: TimePickerDialog = TimePickerDialog(
        context,
        timePickerDialogListener,
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false
    )
    timePicker.show()
}