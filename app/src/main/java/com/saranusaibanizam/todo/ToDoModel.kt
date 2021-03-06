package com.saranusaibanizam.todo

import android.renderscript.RenderScript
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_todo")
data class ToDoModel(
        @PrimaryKey(autoGenerate = true)
        val id:Long=0L,
        var name:String,
        @ColumnInfo(name = "done")
        var isDone:Boolean=false,
        var priority: String,
        var date: Long=System.currentTimeMillis(),
        var time: Long=System.currentTimeMillis(),
        var notify:Boolean=false,
        @ColumnInfo(name = "notify_time")
        var notifyTime:Long=0,
        @ColumnInfo(name = "notify_id")
        var notifId:String?=null
)