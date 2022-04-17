package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemonapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_upload.*

class MainActivity : AppCompatActivity() {
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
          FragmentSearchClass(),
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
  }

   fun submit(view: View){
    var imageUri = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).imageUri.toString()

    var rect = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).actualCropRect!!
    //var imgW = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image.width
    //var imgH = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image.height

    var imageRect = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image_view.actualCropRect

    val croppedImage: ImageSetting = ImageSetting(imageUri, rect.left, rect.top, rect.right, rect.bottom)

    imageList.add(croppedImage)

    Toast.makeText(this, "Submitted cropped image for search", Toast.LENGTH_LONG).show()
  }


}
