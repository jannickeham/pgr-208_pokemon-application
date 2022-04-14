package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pokemonapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private lateinit var binding : ActivityMainBinding
  private var fragmentManager = supportFragmentManager

  //Henter bilde fra gallei
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
  }
}
