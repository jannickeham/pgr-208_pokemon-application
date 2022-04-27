package com.example.pokemonapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapplication.models.PokemonModel

//Non-functionality, intended for implementation to database
class EditActivity : AppCompatActivity() {
  var imageUri: String? = null
  private var dbHelper: FeedReaderDbHelper = FeedReaderDbHelper(context = MainActivity())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_search)

    val oldSelectedImage: PokemonModel =
      (intent.getSerializableExtra("selected_image") as PokemonModel)

    val imageView: ImageView = findViewById<ImageView>(R.id.image)
    imageView.isEnabled = false;

    var image: Bitmap? = null
    if (oldSelectedImage.imageLink.toString().startsWith("http")) {

      imageView.post {
        imageView.setImageBitmap(image!!)
      }

    } else if (oldSelectedImage.imageLink.toString().equals("db")) {
      val cursor = dbHelper?.writableDatabase?.query(
        "pokemon",
        arrayOf("image_lik"),
        null,
        null,
        null,
        null,
        null
      )
      while (cursor!!.moveToNext()) {

        if (cursor.getString(cursor.getColumnIndexOrThrow("image_link")) == oldSelectedImage.imageLink
        ) {
          val stream = cursor.getBlob(cursor.getColumnIndexOrThrow("image"))
          val bm: Bitmap = BitmapFactory.decodeByteArray(stream, 0, stream.size)

          imageView.post { imageView.setImageBitmap(bm!!) }
        }
      }
    } else {
      image = if (oldSelectedImage.imageLink != null)
        getBitmap(
          this,
          null,
          oldSelectedImage.imageLink,
          ::UriToBitmap
        ) else getBitmap(
        this,
        R.drawable.ic_launcher_foreground,
        null,
        ::VectorDrawableToBitmap
      )

      if (oldSelectedImage.imageLink != null) {
        image = Bitmap.createBitmap(
          image!!,
        )

        image = Bitmap.createScaledBitmap(
          image!!,
          (resources.displayMetrics.density * 200).toInt(),
          (resources.displayMetrics.density * 200).toInt(),
          false
        )
      }
      imageView.setImageBitmap(image!!)
    }

    val submitButton: Button = findViewById<Button>(R.id.addImageToDB)
    submitButton.setOnClickListener(object : View.OnClickListener {
      override fun onClick(view: View?) {

        val updatedSelectedImage: PokemonModel = oldSelectedImage.copy()
        updatedSelectedImage.imageLink = imageUri.toString()

        finish()
      }
    })
  }
}
