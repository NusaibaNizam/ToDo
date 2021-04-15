package com.saranusaibanizam.todo

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.*
import java.util.concurrent.TimeUnit

class WorkManagerUtils(val context: Context) {
    private val workManager=WorkManager.getInstance(context)
    fun schedule(name:String,delay:Long):String{
        val request= OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay,TimeUnit.MILLISECONDS)
            .setInputData(workDataOf("name" to name))
            .build()

        workManager.enqueue(request)
        return request.id.toString()
    }
    fun cancelWork(id:String){
        workManager.cancelWorkById(UUID.fromString(id))
    }

}