package com.example.pokemonapplication

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonapplication.databinding.FragmentUploadBinding
import kotlinx.android.synthetic.main.activity_main.*

class FragmentUploadClass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: FragmentUploadBinding = FragmentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            //Fungerer ikke per nå
            ActivityResultCallback {
                binding.imageView.setImageURI(it)
            }
        )

        binding.selectImageBtn.setOnClickListener{
            getImage.launch("image/*")
        }
}





//        image_view.setOnClickListener {
//            openImageChooser()
//        }
//    }

/*    var bitmap = (image_view.drawable as BitmapDrawable).bitmap

    //Opens phones default gallery app
    val REQUEST_CODE = 100
    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            image_view.setImageURI(data?.data) // handle chosen image
        }
    }*/

 */

}