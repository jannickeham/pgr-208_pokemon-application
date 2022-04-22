package com.example.pokemonapplication


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.androidnetworking.error.ANError

import org.json.JSONArray

import com.androidnetworking.interfaces.JSONArrayRequestListener

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import kotlinx.android.synthetic.main.fragment_search.*


class FragmentSearch : Fragment() {

  private lateinit var imageAdapter : ImageAdapter

  //1.event
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  //2.event
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_search, container, false)
  }

  //3.event
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initRecyclerView()
    addDataset()
  }

  private fun addDataset(){
    val data = DataSource.createDataSet()
    imageAdapter.submitList(data)
  }

  private fun initRecyclerView(){
    recycler_view.apply{
      layoutManager = GridLayoutManager(requireContext(), 3)
      val topSpacingDecoration = TopSpacingItemDecoration(30)
      addItemDecoration(topSpacingDecoration)
      imageAdapter = ImageAdapter()
      recycler_view.adapter = imageAdapter
    }
  }
}
