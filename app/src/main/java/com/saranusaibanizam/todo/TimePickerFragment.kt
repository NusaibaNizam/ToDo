package com.saranusaibanizam.todo

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(val listener: (Long)->Unit):DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal=Calendar.getInstance()
        val hour=cal.get(Calendar.HOUR_OF_DAY)
        val minute=cal.get(Calendar.MINUTE)
        return TimePickerDialog(requireActivity(),this,hour,minute,false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val cal=Calendar.getInstance()
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),hourOfDay,minute)
        listener.invoke(cal.timeInMillis)
    }
}