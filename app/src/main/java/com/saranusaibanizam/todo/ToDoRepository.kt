package com.saranusaibanizam.todo

import android.content.Context
import androidx.lifecycle.LiveData

class ToDoRepository(val context: Context) {
    private val toDoDao =ToDoDatabase.getInstance(context).getTodoDao()
    suspend fun insertTodo(toDoModel: ToDoModel){
        toDoDao.insertToDo(toDoModel)
    }
    suspend fun deleteTodo(toDoModel: ToDoModel){
        toDoDao.deleteToDo(toDoModel)
    }
    suspend fun updateTodo(toDoModel: ToDoModel){
        toDoDao.updateToDo(toDoModel)
    }
    fun fetchToDos():LiveData<List<ToDoModel>>{
        return toDoDao.getAllToDo()
    }
}