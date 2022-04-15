package com.example.pokemonapplication


import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.*
import com.edmodo.cropper.CropImageView
import com.example.pokemonapplication.databinding.FragmentUploadBinding
import kotlinx.android.synthetic.main.fragment_upload.*


class FragmentUploadClass : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    public lateinit var image: CropImageView

    public var imageUri: String? = null

    public var actualCropRect: Rect? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_upload, container, false)

        image = view.findViewById<CropImageView>(R.id.image_view)
        image.setOnClickListener(View.OnClickListener{
            var i = Intent()
            i.action = Intent.ACTION_GET_CONTENT
            i.type = "*/*"

            startForResult.launch(i)
        })

        return view
    }

    var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            imageUri = it.data?.data.toString()

            val image: Bitmap = getBitmap(requireContext(), null, imageUri, ::UriToBitmap)
            image_view.layoutParams = image_view.layoutParams.apply {
                //Setting the right size for each image
                width = image!!.width
                height = image!!.height

            }
            //Front- and background is the same size
            image_view.setImageBitmap(image)
            image_view.background = BitmapDrawable(image)
        }
    }

    /*
    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }

     */
}