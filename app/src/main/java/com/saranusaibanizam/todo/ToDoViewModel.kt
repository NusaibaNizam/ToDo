package com.saranusaibanizam.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*


class ToDoViewModel(context: Application):AndroidViewModel(context) {

    private val todoRep: ToDoRepository= ToDoRepository(context)
    fun getCorrectTime(time:Long, date:Long):Long{
        val cal= Calendar.getInstance()
        cal.timeInMillis=time
        val hour=cal.get(Calendar.HOUR_OF_DAY)
        val minute=cal.get(Calendar.MINUTE)
        cal.timeInMillis = date
        val year=cal.get(Calendar.YEAR)
        val month =cal.get(Calendar.MONTH)
        val day=cal.get(Calendar.DAY_OF_MONTH)
        cal.set(year, month, day, hour, minute)
        return cal.timeInMillis
    }
    fun addToDo(toDoModel: ToDoModel){
        viewModelScope.launch {
            todoRep.insertTodo(toDoModel)
        }
    }
    fun updateToDo(toDoModel: ToDoModel){
        viewModelScope.launch {
            todoRep.updateTodo(toDoModel)
        }
    }
    fun removeToDo(toDoModel: ToDoModel){
        viewModelScope.launch {
            todoRep.deleteTodo(toDoModel)
        }
    }
    fun getAllToDos():LiveData<List<ToDoModel>> = todoRep.fetchToDos()

    fun getRemainingToDos():LiveData<List<ToDoModel>> = todoRep.fetchRemainingToDos()

    fun getCompleteToDos():LiveData<List<ToDoModel>> = todoRep.fetchCompleteToDos()
}