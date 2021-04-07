package com.saranusaibanizam.todo

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (Long)->Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val cal=Calendar.getInstance()
        val year=cal.get(Calendar.YEAR)
        val month=cal.get(Calendar.MONTH)
        val day=cal.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(),this,year,month,day)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal=Calendar.getInstance()
        cal.set(year,month,dayOfMonth)
        listener.invoke(cal.timeInMillis)
    }

}