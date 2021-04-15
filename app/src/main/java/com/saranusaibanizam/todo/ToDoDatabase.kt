package com.saranusaibanizam.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ToDoModel::class],version = 2)
abstract class ToDoDatabase:RoomDatabase() {
    abstract fun getTodoDao():ToDoDao

    companion object{
        val migration_1_2:Migration=object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table 'table_todo' add column 'notify' integer not null default 0")
                database.execSQL("alter table 'table_todo' add column 'notify_time' integer not null default 0")
                database.execSQL("alter table 'table_todo' add column 'notify_id' string default null")
            }

        }
        private var INSTANCE:ToDoDatabase?=null
        fun getInstance(context:Context):ToDoDatabase{
            return INSTANCE?: synchronized(this){
                val db=Room
                    .databaseBuilder(context,ToDoDatabase::class.java,"db_todo")
                    .addMigrations(migration_1_2)
                    .build()
                INSTANCE=db
                db
            }
        }
    }
}