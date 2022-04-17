package com.example.pokemonapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edmodo.cropper.CropImageView

class FragmentSearchClass: Fragment() {

    public lateinit var image:


    override fun onCreate(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


         Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        image = view.findViewById<CropImageView>(R.id.image_view)

        return view
    }


}