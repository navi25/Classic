package io.navendra.classic.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.navendra.classic.OUTPUT_PATH
import io.navendra.classic.makeStatusNotification
import io.navendra.classic.sleep
import java.io.File

class CleanupWorker(context: Context, params: WorkerParameters) : Worker(context,params){

    private val TAG = CleanupWorker::class.java.simpleName

    override fun doWork(): Result {
        return try {
            makeStatusNotification("Cleaning up old temporary files", applicationContext)
            sleep()

            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)

            if(outputDirectory.exists()){
                outputDirectory.listFiles()?.forEach {
                    val fileName = it.name
                    if(fileName.isNullOrEmpty() && fileName.endsWith(".png")){
                        val deleted = it.delete()
                        Log.i(TAG, String.format("Deleted %s - %s", fileName, deleted))
                    }

                }
            }

            Result.success()
        }catch (throwable: Throwable){
            Log.e(TAG, "Error cleaning up", throwable)
            Result.failure()
        }
    }

}
