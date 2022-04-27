package com.example.pokemonapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
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
import java.io.File
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

  //Sandras kode
  //private var imageInfo = ArrayList<ImageInfo>()
  lateinit var pokemonAdapter : PokemonAdapter
  private var pokemonModel = ArrayList<PokemonModel>()
  private var fragmentManager = supportFragmentManager
  private var data = ArrayList<PokemonModel>()
  lateinit var recyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    AndroidNetworking.initialize(applicationContext);
    pokemonModel = ArrayList<PokemonModel>()

    val fragmentUpload = FragmentUpload()
    val fragmentSearch = FragmentSearch(data)

//    //Used to set initial fragment to our container
//    supportFragmentManager.beginTransaction().apply{
//    replace(R.id.MainFragment, fragmentUpload)
//    commit()
//  }

    //Possibility to click on buttons and change fragment to corresponding fragment
    btnUpload.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.MainFragment, fragmentUpload)
        addToBackStack(null)
        commit()
      }
    }
    btnSearch.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.MainFragment, fragmentSearch)
        addToBackStack(null)
        commit()
      }
    }

    thread {
      val results = ArrayList<PokemonModel>()
      //val json = JSONArray(results)

      for (index in 0 until data.size) {
        val thumbnail = (data.get(index) as JSONObject).getString("thumbnail_link")
        val imageLink = (data.get(index) as JSONObject).getString("image_link")

        pokemonModel.add(
          PokemonModel(
            thumbnail,
            imageLink,
          )
        )
      }
    }
  }



  /*
  //Original kode
  private lateinit var binding : ActivityMainBinding
  private var fragmentManager = supportFragmentManager
  private var imageList = ArrayList<ImageSetting>()

  //Henter bilde fra galleri
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_upload)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }

  fun switchFragment(v: View) {

    fragmentManager = supportFragmentManager

    if (Integer.parseInt(v.getTag().toString()) == 1) {
      fragmentManager
        .beginTransaction()
        .replace(
          R.id.flFragment,
          FragmentUploadClass(),
          "FragmentUpload"
        )
        .commit()
    } else if(Integer.parseInt(v.getTag().toString()) == 2) {
      fragmentManager
        .beginTransaction()
        .replace(
          R.id.flFragment,
          FragmentSearch(),
          "FragmentSearch"
        )
        .commit()
    } else{
      fragmentManager
        .beginTransaction()
        .replace(
          R.id.flFragment,
          FragmentSaved(),
          "FragmentSaved"
        )
        .commit()
    }
  }*/
  //POST
  fun postImageToServer(file: File) {
    AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
      .addMultipartFile("image", file)
      .setTag("uploadTest")
      .setPriority(Priority.HIGH)
      .build()
      .setUploadProgressListener { bytesUploaded, totalBytes ->
        // do anything with progress
      }
      .getAsString(object : StringRequestListener {
        override fun onResponse(response: String) {
          println("Upload was success: $response")
          //Toast.makeText(this, "Upload to server success", Toast.LENGTH_SHORT).show()
          getImageFromServer(response)
        }

        override fun onError(error: ANError) {
          println("an error occurred $error")
          //Toast.makeText(this, "An error occurred during upload to server", Toast.LENGTH_SHORT).show()
        }
      })
  }


  //GET
  fun getImageFromServer(responsePost: String) {
    val urlEndpointBing = "http://api-edu.gtl.ai/api/v1/imagesearch/bing"
    val urlEndpointGoogle = "http://api-edu.gtl.ai/api/v1/imagesearch/google"
    val urlEndpointTineye = "http://api-edu.gtl.ai/api/v1/imagesearch/tineye"
    var imageUrl = responsePost
    var urlBing = "$urlEndpointBing?url=$imageUrl"
    var urlGoogle = "$urlEndpointGoogle?url=$imageUrl"
    var urlTineye = "$urlEndpointTineye?url=$imageUrl"

    var responseArrayBing = ArrayList<JSONArray>()
    var responsArrayGoogle = ArrayList<JSONArray>()
    var responsArrayTineye = ArrayList<JSONArray>()
    println("urlEndointBing + imageUrl $urlEndpointBing$imageUrl")

    //Get from /bing
    getImageFromBing(urlBing, responseArrayBing)

    //Get from /tineye
    getImageFromTineye(urlTineye, responsArrayTineye)

    //Get from /google
    getImageFromGoogle(urlGoogle, responsArrayGoogle)

    //val responseArray = ArrayList<JSONArray>()
    //println("Array med alle objekter funnet: $responsArray")

  }

  private fun getImageFromTineye(
    urlTineye: String,
    responsArrayTineye: ArrayList<JSONArray>
  ) {
    AndroidNetworking.get(urlTineye)
      .addPathParameter("pageNumber", "0")
      //.addQueryParameter("url", imageUrl)
      .addHeaders("token", "1234")
      .setTag("downloadtest")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          //responsArrayTineye.add(response)
          println("Response from Tineye: $responsArrayTineye")

          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
            }
          }
          Log.i(ContentValues.TAG, "Data with response result: $data")
        }

        override fun onError(error: ANError) {
          println("an error occurred $error")
        }
      })
  }

  private fun getImageFromGoogle(
    urlGoogle: String,
    responsArrayGoogle: ArrayList<JSONArray>
  ) {
    AndroidNetworking.get(urlGoogle)
      .addPathParameter("pageNumber", "0")
      .addHeaders("token", "1234")
      .setTag("downloadtest")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          //responsArrayGoogle.add(response)
          println("Response from Google: $responsArrayGoogle")

          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
            }
          }
          Log.i(ContentValues.TAG, "Data with response result: $data")
        }

        override fun onError(error: ANError) {
          println("an error occurred $error")
        }
      })
  }

  private fun getImageFromBing(
    urlBing: String,
    responseArrayBing: ArrayList<JSONArray>
  ) {
    AndroidNetworking.get(urlBing)
      .addPathParameter("pageNumber", "0")
      .addHeaders("token", "1234")
      .setTag("downloadtest")
      .setPriority(Priority.MEDIUM)
      .build()
      .getAsJSONArray(object : JSONArrayRequestListener {
        override fun onResponse(response: JSONArray) {
          //responseArrayBing.add(response)
          println("respons fra bing: $responseArrayBing")

          if (response.length() > 0) {
            for (index in 0 until response.length()) {

              val imageLink = (response.get(index) as JSONObject).getString("image_link")
              val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

              data.add(
                PokemonModel(imageLink, thumbnailLink)
              )
              Log.i(TAG, "HVOR ER DU DIN DRIIIT $data")
            }
          }
          Log.i(ContentValues.TAG, "Data with response result: $data")
        }

        override fun onError(error: ANError) {
          println("an error occurred $error")
        }
      })
  }

  fun submit(view: View) {
    val thumbnail =
      (fragmentManager.findFragmentByTag("FragmentSearch") as FragmentSearch).data.toString()
    val imageLink =
      (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUpload).imageUri.toString()
//    var submitButton =
//      (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUpload).btnSubmit.toString()
    val imageSearchResults: PokemonModel = PokemonModel(
      thumbnail,
      imageLink,
    )

    pokemonModel.add(imageSearchResults)
  }

}
