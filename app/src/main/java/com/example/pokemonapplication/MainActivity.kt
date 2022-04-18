package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemonapplication.Globals.TAG
import com.example.pokemonapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_upload.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

  //Sandras kode
  private var imageInfo = ArrayList<ImageInfo>()
  private var fragmentManager = supportFragmentManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val fragmentUpload = FragmentUploadClass()
    val fragmentSearch = FragmentSearch()

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

   fun submit(view: View){

     var imageUri = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).imageUri.toString()
     Log.i(TAG, "submit: " + imageUri)
    /*var rect = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).actualCropRect!!
    var imgW = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image.width
    var imgH = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image.height

    var rect = (fragmentManager.findFragmentByTag("FragmentUpload") as FragmentUploadClass).image_view.actualCropRect

     //Hentet fra forelesning der man endrer og lagrer bildet ved editactivity
    val croppedImage: ImageInfo = ImageInfo(imageUri, rect.left, rect.top, rect.right, rect.bottom)

    imageInfo.add(croppedImage)*/

    Toast.makeText(this, "Submitted cropped image for search", Toast.LENGTH_LONG).show()
  }




}
