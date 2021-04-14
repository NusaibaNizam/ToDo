package com.saranusaibanizam.todo

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {
    @Insert
    suspend fun insertToDo(todo:ToDoModel)
    @Update
    suspend fun updateToDo(todo:ToDoModel)
    @Delete
    suspend fun deleteToDo(todo:ToDoModel)
    @Query("select * from (select * from table_todo order by time asc) order by done asc")
    fun getAllToDo():LiveData<List<ToDoModel>>
}