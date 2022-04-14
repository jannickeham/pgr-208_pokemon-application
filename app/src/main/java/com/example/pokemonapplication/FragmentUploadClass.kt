package com.example.pokemonapplication


import android.content.ContentValues.TAG

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.fragment.app.*
import com.example.pokemonapplication.databinding.FragmentUploadBinding


class FragmentUploadClass : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.selectImageBtn.setOnClickListener {

        }

        return view
    }


    override fun onDestroyView(){
        super.onDestroyView()
        _binding = null
    }
 }






