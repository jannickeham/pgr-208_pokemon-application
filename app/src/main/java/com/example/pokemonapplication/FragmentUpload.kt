package com.example.pokemonapplication


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_upload.*
import com.androidnetworking.error.ANError

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import java.io.File
import org.json.JSONArray


class FragmentUpload : Fragment() {

    //Sandras kode
    private val GALLERY_REQUEST_CODE = 1234

    var imageUri: String? = null
    var actualCropRect: Rect? = null

    lateinit var image: ImageView
    lateinit var updateTextView : TextView

    //1.event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        image = view.findViewById<ImageView>(R.id.image)
        updateTextView = view.findViewById<TextView>(R.id.update_textview)

        updateTextView.setOnClickListener {
            getImageFromGallery()
          }

        //Handling user not choosing image before submit
        btnSubmit.setOnClickListener{
          if(imageUri == null ){
            Toast.makeText(activity, "Please upload an image", Toast.LENGTH_SHORT).show()
          } else{
            submit(view)
          }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
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
        postImageToServer(file)

    }

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
        var imageUrl = responsePost
        var url = "$urlEndpointBing?url=$imageUrl"
        var responsArray = ArrayList<JSONArray>()
        println("urlEndointBing + imageUrl $urlEndpointBing$imageUrl")

            AndroidNetworking.get(url)
                .addPathParameter("pageNumber", "0")
                //.addQueryParameter("url", imageUrl)
                .addHeaders("token", "1234")
                .setTag("downloadtest")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray) {
                        responsArray.add(response)
                        println(responsArray)
                    }

                    override fun onError(error: ANError) {
                        println("an error occurred $error")
                    }
                })
    }

}
