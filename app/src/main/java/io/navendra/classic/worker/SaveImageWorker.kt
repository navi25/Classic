package io.navendra.classic.worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.navendra.classic.KEY_IMAGE_URI
import io.navendra.classic.makeStatusNotification
import io.navendra.classic.sleep
import java.text.SimpleDateFormat
import java.util.*

class SaveImageWorker(context: Context, params: WorkerParameters) : Worker(context,params){
    private val TAG by lazy { SaveImageWorker::class.java.simpleName }

    private val Title = "Blurred Image"
    private val dateFormatter = SimpleDateFormat(
        "yyyy.MM.dd 'at' HH:mm:ss z",
        Locale.getDefault()
    )

    override fun doWork(): Result {
        makeStatusNotification("Saving image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver

        return try{
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            val bitmap = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri)))
            val imageUrl = MediaStore.Images.Media.insertImage(
                resolver, bitmap, Title, dateFormatter.format(Date()))

            if(!imageUrl.isNullOrEmpty()){
                val output = Data.Builder()
                    .putString(KEY_IMAGE_URI, imageUrl)
                    .build()

                Result.success(output)
            }else{
                Log.e(TAG, "Writing to MediaStore failed")
                Result.failure()
            }
        }catch (t: Throwable){
            Result.failure()
        }
    }

}