package com.example.pokemonapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.Serializable

object Globals {
    val TAG = "PokemonApplication"
}

//Needed for setting the crop size
data class ImageInfo(var imageUri: String, var x: Int, var y: Int, var h: Int, var w: Int, var position: Int=-1): Serializable {
}

fun VectorDrawableToBitmap(context: Context, id: Int?, uri: String?) : Bitmap {
    val drawable = (ContextCompat.getDrawable(context!!, id!!) as VectorDrawable)
    val image = Bitmap.createBitmap(
        drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(image)
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    drawable.draw(canvas)

    return image
}

fun UriToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
    val image: Bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, Uri.parse(uri))
    return image
}

//Kopiert av Marie
fun bitmapToByteArray(bitmap : Bitmap) : ByteArray{
  val outputStream = ByteArrayOutputStream()

  bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
  return outputStream.toByteArray()
}


fun getBitmap(context: Context, id: Int?, uri: String?, decoder: (Context, Int?, String?) -> Bitmap): Bitmap {
    return decoder(context, id, uri)
}

fun bitmapToFile(bitmap : Bitmap, filename : String, context: Context) : File{

  val file = File(context.getCacheDir(), filename)
  file.createNewFile()

  val bitmapdata = bitmapToByteArray(bitmap)

  val fileOutputStream = FileOutputStream(file)
  fileOutputStream.write(bitmapdata)
  fileOutputStream.flush()
  fileOutputStream.close()

  return file
}
