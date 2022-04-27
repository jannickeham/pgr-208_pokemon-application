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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_upload.*
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentUpload : Fragment() {

    private val GALLERY_REQUEST_CODE = 1234

    var imageUri: String? = null
    var data: ArrayList<PokemonModel> = ArrayList()

    lateinit var pokemonAdapter : PokemonAdapter
    lateinit var image: ImageView
    lateinit var updateTextView : TextView
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      pokemonAdapter = PokemonAdapter(requireContext(), data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = view.findViewById<ImageView>(R.id.image)
        updateTextView = view.findViewById<TextView>(R.id.update_textview)

        updateTextView.setOnClickListener {
            getImageFromGallery()
          }
        image.setOnClickListener{
          initRecyclerView()
          addDataset()
        }

        //Handling user can not choose image before pushing submit
        btnSubmit.setOnClickListener{
          if(imageUri == null ){
            Toast.makeText(activity, "Please upload an image", Toast.LENGTH_SHORT).show()
          } else{
            submit(view)
          }
        }
    }

  var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    imageUri = it.data?.data.toString()
    val bitmap_image = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)
    image.setImageBitmap(bitmap_image)
    image.background = BitmapDrawable(resources, bitmap_image)
  }

    // Mitch
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
                    Toast.makeText(activity, "Image selection error: Couldn't select that image from memory.", Toast.LENGTH_SHORT).show()
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    setImage(result.uri)
                    imageUri = result.uri.toString()
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(activity, "Could not crop", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //Mitch, Glide library
    private fun setImage(uri: Uri?) {
        Glide.with(this)
            .load(uri)
            .into(image)
    }

    //Mitch - Launching cropper
    private fun launchImageCrop(uri: Uri) {
        CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .start(requireContext(),this)
    }

    //Mitch - Gets image from gallery
    private fun getImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent, GALLERY_REQUEST_CODE)

    }

    //Mitch
    private fun addDataset(){
    val data = PokemonAdapter.createDataSet()
    pokemonAdapter.submitList(data)
  }

    //Mitch - initialises RecyclerView and sets layout
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
      //Get Uri-string and use bitmap to convert to file
      var imageUri = imageUri.toString()
        //User has to choose an image before submit
      if(imageUri == null){
        Toast.makeText(activity, "Please upload an image", Toast.LENGTH_SHORT).show()
      }
      var imageBitMap = UriToBitmap(requireContext(), null, imageUri)
      image.setImageBitmap(imageBitMap)
      val file = bitmapToFile(imageBitMap, "image.png", requireContext())

      //Calling POST-request function in main
      (activity as MainActivity).postImageToServer(file)
    }
}
