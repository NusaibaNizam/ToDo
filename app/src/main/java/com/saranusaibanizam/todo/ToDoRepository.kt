package com.saranusaibanizam.todo

import android.content.Context
import androidx.lifecycle.LiveData

class ToDoRepository(context: Context) {
    private val toDoDao:ToDoDao=ToDoDatabase.getInstance(context).getTodoDao()
    fun insertTodo(toDoModel: ToDoModel){
        toDoDao.insertToDo(toDoModel)
    }
    fun deleteTodo(toDoModel: ToDoModel){
        toDoDao.deleteToDo(toDoModel)
    }
    fun updateTodo(toDoModel: ToDoModel){
        toDoDao.updateToDo(toDoModel)
    }
    fun fetchToDos():LiveData<List<ToDoModel>>{
        return toDoDao.getAllToDo()
    }
}