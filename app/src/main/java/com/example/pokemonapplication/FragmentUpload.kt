package com.example.pokemonapplication


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_upload.*
import com.androidnetworking.error.ANError

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.image_layout_fragment.*
import java.io.File
import org.json.JSONArray
import org.json.JSONObject


class FragmentUpload : Fragment() {

    //Sandras kode
    private val GALLERY_REQUEST_CODE = 1234

    var imageUri: String? = null
    var actualCropRect: Rect? = null
    var data: ArrayList<PokemonModel> = ArrayList()

    lateinit var pokemonAdapter : PokemonAdapter
    lateinit var image: ImageView
    //lateinit var imageResult: ImageView
    lateinit var updateTextView : TextView
    lateinit var recyclerView: RecyclerView

    //1.event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      pokemonAdapter = PokemonAdapter(requireContext(), data)
    }

    //2.event
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false)

    }

    //3.event
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        image = view.findViewById<ImageView>(R.id.image)
        //imageResult = view.findViewById<ImageView>(R.id.image_result)
        updateTextView = view.findViewById<TextView>(R.id.update_textview)

        updateTextView.setOnClickListener {
            getImageFromGallery()
          }

        image.setOnClickListener{
          initRecyclerView()
          addDataset()
        }



        //Handling user not choosing image before submit
        btnSubmit.setOnClickListener{
          if(imageUri == null ){
            Toast.makeText(activity, "Please upload an image", Toast.LENGTH_SHORT).show()
          } else{
            //startForResult.launch(i)
            submit(view)

          }
        }
    }

  var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    imageUri = it.data?.data.toString()

    val bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)
    image.apply {

    }
    image.setImageBitmap(bitmap_image)
    image.background = BitmapDrawable(resources, bitmap_image)
  }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                        //initRecyclerView()
                    }
                }
                else{
                    Log.e(TAG, "Image selection error: Couldn't select that image from memory." )
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                    imageUri = result.uri.toString()
                    Log.i(TAG, "image uri " + result.uri)
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Log.e(TAG, "Crop error: ${result.getError()}" )
                }
            }
        }
    }

    private fun setImage(uri: Uri?) {
        Glide.with(this)
            .load(uri)
            .into(image)
    }


    //Launching cropper
    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
            .start(requireContext(),this)
    }

    //Gets image from gallery
    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)

    }

    private fun addDataset(){
    val data = PokemonAdapter.createDataSet()
    pokemonAdapter.submitList(data)
  }

  private fun initRecyclerView(){
    recycler_view.apply{

      layoutManager = LinearLayoutManager(context)
      val topSpacingDecoration = TopSpacingItemDecoration(30)
      addItemDecoration(topSpacingDecoration)
      pokemonAdapter = PokemonAdapter(context, data)
      recycler_view.adapter = pokemonAdapter
    }
  }

    //Gets image uri and saves it in a var
    private fun submit(view: View){
      //Get Uri-string
      var imageUri = imageUri.toString()

      if(imageUri == null){
        Toast.makeText(activity, "Please upload an image", Toast.LENGTH_SHORT).show()
      }
      //From string to Bitmap
      var imageBitMap = UriToBitmap(requireContext(), null, imageUri)

      //Vet ikke?
      image.setImageBitmap(imageBitMap)

      //Lager en fil
      val file = bitmapToFile(imageBitMap, "image.png", requireContext())
      Log.i(Globals.TAG, "Dette sendes til server i file $file")


      //POST-request to server
      (activity as MainActivity).postImageToServer(file)
      //MainActivity.postImageToServer(file)


      //(activity as MainActivity).initRecyclerView()
      //(activity as MainActivity).addDataset()
      //initRecyclerView()
      //addDataset()

    }



    /*//POST
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
                    Toast.makeText(activity, "Upload to server success", Toast.LENGTH_SHORT).show()
                    getImageFromServer(response)
                }

                override fun onError(error: ANError) {
                    println("an error occurred $error")
                    Toast.makeText(activity, "An error occurred during upload to server", Toast.LENGTH_SHORT).show()
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

                  if(response.length() > 0){
                    for(index in 0 until response.length()){

                      val imageLink = (response.get(index) as JSONObject).getString("image_link")
                      val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

                      data.add(
                        PokemonModel(imageLink, thumbnailLink))
                    }
                  }
                  Log.i(TAG, "Data with response result: $data")
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

                  if(response.length() > 0){
                    for(index in 0 until response.length()){

                      val imageLink = (response.get(index) as JSONObject).getString("image_link")
                      val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

                      data.add(
                        PokemonModel(imageLink, thumbnailLink))
                    }
                  }
                  Log.i(TAG, "Data with response result: $data")
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

                  if(response.length() > 0){
                    for(index in 0 until response.length()){

                      val imageLink = (response.get(index) as JSONObject).getString("image_link")
                      val thumbnailLink = (response.get(index) as JSONObject).getString("image_link")

                      data.add(
                       PokemonModel(imageLink, thumbnailLink))
                    }
                  }
                  Log.i(TAG, "Data with response result: $data")
                }

                override fun onError(error: ANError) {
                    println("an error occurred $error")
                }
            })
    }

*/
}
