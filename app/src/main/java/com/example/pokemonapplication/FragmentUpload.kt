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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_upload.*
import com.androidnetworking.error.ANError

import org.json.JSONObject

import com.androidnetworking.interfaces.JSONObjectRequestListener

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority


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

        updateTextView.setOnClickListener{
            getImageFromGallery()
        }

        btnSubmit.setOnClickListener{
          submit(view)

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
                    Log.i(TAG, "PRINTER DETTE " + result.uri)
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

      //From string to Bitmap
      var imageBitMap = UriToBitmap(requireContext(), null, imageUri)

      //Vet ikke?
      image.setImageBitmap(imageBitMap)

      //Lager en fil
      val file = bitmapToFile(imageBitMap, "image.png", requireContext())
      Log.i(Globals.TAG, "SE HER DUUUUUU " + file)

      //POST-request to server
//      AndroidNetworking.post("http://api-edu.gtl.ai/ api/v1/imagesearch/upload")
//        .addFileBody(file) // posting any type of file
//        .setTag("test")
//        .setPriority(Priority.MEDIUM)
//        .build()
//        .getAsJSONObject(object : JSONObjectRequestListener {
//          override fun onResponse(response: JSONObject) {
//            // do anything with response
//          }
//
//          override fun onError(error: ANError) {
//            // handle error
//          }
//        })

//      Log.i(Globals.TAG, "DETTE SKRIVES UT<3 $imageUri")
      Toast.makeText(activity, "Fragment 1 onCreateView", Toast.LENGTH_SHORT).show()
    }
}
