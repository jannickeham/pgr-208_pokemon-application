package com.example.pokemonapplication

import android.content.ContentValues
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.example.pokemonapplication.Globals.TAG
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_upload.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

  private var dbHelper = FeedReaderDbHelper(this)

  private var fragmentManager = supportFragmentManager
  private var data = ArrayList<PokemonModel>()
  lateinit var recyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    AndroidNetworking.initialize(applicationContext);

    val fragmentUpload = FragmentUpload()
    val fragmentSearch = FragmentSearch(data)
    val fragmentSaved = FragmentSaved()

    //Possibility to click on buttons and change fragment to corresponding fragment
    btnUpload.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.MainFragment, fragmentUpload)
        addToBackStack(null)
        commit()
      }
      background_image.visibility = View.GONE
    }
    btnSearch.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.MainFragment, fragmentSearch)
        addToBackStack(null)
        commit()
      }
      if(data.isEmpty()){
        Toast.makeText(this@MainActivity, "It's dangerouse to enter the grass alone!! Please upload an image.", Toast.LENGTH_SHORT).show()
      } else{
        background_image.visibility = View.GONE
      }

    }
    btnSaved.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.MainFragment, fragmentSaved)
        addToBackStack(null)
        commit()
      }
      background_image.visibility = View.GONE
    }

    thread {
      val results = ArrayList<PokemonModel>()

      for (index in 0 until data.size) {
        val thumbnail = (results.get(index) as JSONObject).getString("thumbnail_link")
        val imageLink = (results.get(index) as JSONObject).getString("image_link")

        data.add(
          PokemonModel(
            thumbnail,
            imageLink,
          )
        )
      }
    }
  }

  //POST request to server
  fun postImageToServer(file: File) {
    progressBar2.visibility = View.VISIBLE
    AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
      .addMultipartFile("image", file)
      .setTag("uploadTest")
      .setPriority(Priority.HIGH)
      .build()
      .setUploadProgressListener { bytesUploaded, totalBytes ->
      }
      .getAsString(object : StringRequestListener {
        override fun onResponse(response: String) {
          getImageFromServer(response)
        }

        override fun onError(error: ANError) {
          Toast.makeText(this@MainActivity, "An error occurred during upload to server", Toast.LENGTH_SHORT).show()
        }
      })
  }

  //GET request from
  fun getImageFromServer(responsePost: String) {
    val urlEndpointBing = "http://api-edu.gtl.ai/api/v1/imagesearch/bing"
    val urlEndpointGoogle = "http://api-edu.gtl.ai/api/v1/imagesearch/google"
    val urlEndpointTineye = "http://api-edu.gtl.ai/api/v1/imagesearch/tineye"
    var imageUrl = responsePost
    var urlBing = "$urlEndpointBing?url=$imageUrl"
    var urlGoogle = "$urlEndpointGoogle?url=$imageUrl"
    var urlTineye = "$urlEndpointTineye?url=$imageUrl"

    //Get from /bing
    getImageFromBing(urlBing)

    //Get from /tineye
    getImageFromTineye(urlTineye)

    //Get from /google
    getImageFromGoogle(urlGoogle)
  }

  private fun getImageFromTineye(
    urlTineye: String
  ) {
    AndroidNetworking.get(urlTineye)
      .addPathParameter("pageNumber", "0")
      .addHeaders("token", "1234")
      .setTag("download")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
            }
          }
        }

        override fun onError(error: ANError) {
          Toast.makeText(this@MainActivity, "An error occurred during download from server", Toast.LENGTH_SHORT).show()
        }
      })
  }

  private fun getImageFromGoogle(
    urlGoogle: String
  ) {
    AndroidNetworking.get(urlGoogle)
      .addPathParameter("pageNumber", "0")
      .addHeaders("token", "1234")
      .setTag("download")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
            }
          }
        }

        override fun onError(error: ANError) {
          Toast.makeText(this@MainActivity, "An error occurred during download from server", Toast.LENGTH_SHORT).show()
        }
      })
  }

  private fun getImageFromBing(
    urlBing: String
  ) {
    AndroidNetworking.get(urlBing)
      .addPathParameter("pageNumber", "0")
      .addHeaders("token", "1234")
      .setTag("download")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          progressBar2.visibility = View.GONE
          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
            }
          }
        }

        override fun onError(error: ANError) {
          Toast.makeText(this@MainActivity, "An error occurred during download from server", Toast.LENGTH_SHORT).show()
        }
      })
  }



  //Non-functionality for saving to database
  fun addSelectedImageToDb(imageSearchResults: PokemonModel){
    val os = ByteArrayOutputStream()
    getBitmap(applicationContext, null, imageSearchResults.imageLink, ::UriToBitmap).compress(Bitmap.CompressFormat.PNG, 100, os)

    dbHelper.writableDatabase.insert("pokemon", null, ContentValues().apply {
      put("image", os.toByteArray())
    })
  }

  //Non-functionality for saving to database
  fun submit(view: View) {
    val thumbnail =
      (fragmentManager.findFragmentByTag("FragmentSearch") as FragmentSearch).data.toString()
    val imageLink =
      (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUpload).imageUri.toString()
    val imageSearchResults: PokemonModel = PokemonModel(
      thumbnail,
      imageLink,
    )
    data.add(imageSearchResults)
  }

  override fun onStart() {
    super.onStart()
    Log.i(Globals.TAG, "Activity 1 onStart")
  }

  override fun onResume() {
    super.onResume()
    Log.i(Globals.TAG, "Activity 1 onResume")
  }

  override fun onPause() {
    super.onPause()
    Log.i(Globals.TAG, "Activity 1 onPause")
  }

  override fun onStop() {
    super.onStop()
    Log.i(Globals.TAG, "Activity 1 onStop")
  }

  override fun onRestart() {
    super.onRestart()
    Log.i(Globals.TAG, "Activity 1 onRestart")
  }
}
