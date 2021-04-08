package com.saranusaibanizam.todo

import android.renderscript.RenderScript
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_todo")
data class ToDoModel(
  @PrimaryKey(autoGenerate = true)
  val id:Long=0L,
  val name:String,
  @ColumnInfo(name = "done")
  val isDone:Boolean=false,
  val priority: String,
  val date: Long=System.currentTimeMillis(),
  val time: Long=System.currentTimeMillis()
)