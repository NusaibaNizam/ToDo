package com.saranusaibanizam.todo

import android.renderscript.RenderScript

data class ToDoModel(
  val id:Long=0L,
  val name:String,
  val isDone:Boolean=false,
  val priority: String,
  val date: Long=System.currentTimeMillis(),
  val time: Long=System.currentTimeMillis()
)