package com.saranusaibanizam.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoModel::class],version = 1)
abstract class ToDoDatabase:RoomDatabase() {
    abstract fun getTodoDao():ToDoDao
    companion object{
        private var INSTANCE:ToDoDatabase?=null
        fun getInstance(context:Context):ToDoDatabase{
            return INSTANCE?: synchronized(this){
                val db=Room
                    .databaseBuilder(context,ToDoDatabase::class.java,"db_todo")
                    .allowMainThreadQueries()
                    .build()
                INSTANCE=db
                db
            }
        }
    }
}