package io.navendra.classic.worker

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.navendra.classic.R
import io.navendra.classic.KEY_IMAGE_URI
import io.navendra.classic.blurBitmap
import io.navendra.classic.makeStatusNotification
import io.navendra.classic.writeBitmapToFile
import java.lang.IllegalArgumentException

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context,params){

    private val TAG = BlurWorker::class.java.simpleName

    private val testImage = BitmapFactory.decodeResource(context.resources, R.drawable.test)

    override fun doWork(): Result {

        makeStatusNotification("Blurring Image", applicationContext)

        return try{

            val resourceUri = inputData.getString(KEY_IMAGE_URI)

            if(resourceUri.isNullOrEmpty()) {
                Log.e(TAG,"Resource URI shouldn't be null")
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver

            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val output = blurBitmap(picture, applicationContext)

            val outputUri = writeBitmapToFile(applicationContext, output)

            val outputData = Data.Builder().putString(KEY_IMAGE_URI,outputUri.toString()).build()

            Result.success(outputData)

        }catch (throwable:Throwable){
            Log.e(TAG,"Error Applying Blur", throwable)
            Result.failure()
        }
    }

}