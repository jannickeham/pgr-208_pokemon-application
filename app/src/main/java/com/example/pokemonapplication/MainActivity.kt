package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemonapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private lateinit var binding : ActivityMainBinding
  private var fragmentManager = supportFragmentManager


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_upload)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val getImage = registerForActivityResult(
      ActivityResultContracts.GetContent(),
      //Fungerer ikke per nå
      ActivityResultCallback {
        binding.imageViewMainActivity.setImageURI(it)
      }
    )

    binding.btnUpload.setOnClickListener {
      getImage.launch("image/*")
    }

    //Set default fragment
    val fragmentUpload = FragmentUpload()
    val fragmentSearch = FragmentSearch()
    val fragmentSaved = FragmentSaved()


    /* btnUpload.setOnClickListener {
       supportFragmentManager.beginTransaction().apply {
         replace(R.id.flFragment, fragmentUpload)
         addToBackStack(null)
         commit()
       }
     }*/

    btnSearch.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragmentSearch)
        addToBackStack(null)
        commit()
      }
    }

    btnSaved.setOnClickListener {
      supportFragmentManager.beginTransaction().apply {
        replace(R.id.flFragment, fragmentSaved)
        addToBackStack(null)
        commit()
      }
    }
  }
}
