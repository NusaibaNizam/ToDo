package com.saranusaibanizam.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.util.*


class ToDoViewModel(context: Application):AndroidViewModel(context) {
    private val todoRep: ToDoRepository= ToDoRepository(context)
    fun getCorretctTime(time:Long,date:Long):Long{
        val timeCal= Calendar.getInstance();
        timeCal.timeInMillis=time
        val dateCal=Calendar.getInstance()
        dateCal.timeInMillis = date
        val cal= Calendar.getInstance();
        cal.set(dateCal.get(Calendar.YEAR),dateCal.get(Calendar.MONTH),dateCal.get(Calendar.DAY_OF_MONTH)
            ,timeCal.get(Calendar.HOUR_OF_DAY),timeCal.get(Calendar.MINUTE))
        return cal.timeInMillis
    }
    fun addToDo(toDoModel: ToDoModel){
        todoRep.insertTodo(toDoModel)
    }
    fun updateToDo(toDoModel: ToDoModel){
        todoRep.updateTodo(toDoModel)
    }
    fun removeToDo(toDoModel: ToDoModel){
        todoRep.deleteTodo(toDoModel)
    }
    fun getToDos():LiveData<List<ToDoModel>> = todoRep.fetchToDos()
}