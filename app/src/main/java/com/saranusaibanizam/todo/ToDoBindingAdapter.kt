package com.saranusaibanizam.todo

import android.graphics.Paint
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.saranusaibanizam.todo.databinding.TodoRowBinding
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:setDate")
fun setDate(tv:TextView,date:Long){
    tv.text = SimpleDateFormat("dd/MM/yyyy").format(Date(date));
}

@BindingAdapter("app:setTime")
fun setTime(tv:TextView,time:Long){
    tv.text = SimpleDateFormat("hh:mm a").format(Date(time));
}
@BindingAdapter("app:setBackgroundColor")
fun setBackground(v: View, toDoModel: ToDoModel){
    var colorID:Int?=null
    if(toDoModel.isDone){
        colorID=when(toDoModel.priority){
            HIGH->R.color.highLight
            LOW->R.color.lowLight
            else->R.color.normalLight
        }
    }else {
        colorID=when (toDoModel.priority) {
            HIGH -> R.color.high
            LOW -> R.color.low
            else -> R.color.normal
        }

    }
    v.setBackgroundColor(ContextCompat.getColor(v.context,colorID))
}

@BindingAdapter("app:setTextStyle")
fun setTextStyle(tv:TextView, isDone:Boolean){
    if(isDone){
        tv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        tv.setTypeface(tv.typeface, Typeface.ITALIC)
    }else{
        tv.paintFlags = Paint.ANTI_ALIAS_FLAG
        tv.setTypeface(tv.typeface, Typeface.BOLD)
    }
}