package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnetworking.AndroidNetworking
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  //Sandras kode
  //private var imageInfo = ArrayList<ImageInfo>()
  private var fragmentManager = supportFragmentManager
  private var data = ArrayList<PokemonModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    AndroidNetworking.initialize(applicationContext);


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
}
