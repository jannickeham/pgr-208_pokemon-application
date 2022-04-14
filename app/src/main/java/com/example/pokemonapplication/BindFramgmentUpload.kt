package com.example.pokemonapplication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.pokemonapplication.databinding.FragmentUploadBinding

class BindFramgmentUpload : Fragment(R.layout.fragment_upload) {

    private var fragmentUploadBinding: FragmentUploadBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentUploadBinding.bind(view)
        fragmentUploadBinding = binding
        binding.selectImageBtn.setOnClickListener {
            Log.i(ContentValues.TAG, "JAJAJAJJAJAJAJAJ")
            print("Fiskeboller")
        }

    }

    override fun onDestroyView() {
        fragmentUploadBinding = null
        super.onDestroyView()
    }
}