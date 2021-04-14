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

    @Query("select * from (select * from (select * from table_todo order by priority asc)order by time asc )order by done asc ")
    fun getAllToDo():LiveData<List<ToDoModel>>

    @Query("select * from (select * from (select * from table_todo where done=0) order by priority asc) order by time asc")
    fun getRemainingTodo():LiveData<List<ToDoModel>>

    @Query("select * from (select * from (select * from table_todo where done=1) order by priority asc) order by time asc")
    fun getCompletedTodo():LiveData<List<ToDoModel>>
}