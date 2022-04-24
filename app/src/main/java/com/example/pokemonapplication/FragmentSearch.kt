package com.example.pokemonapplication


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.fragment_search.*

class FragmentSearch(var data: ArrayList<PokemonModel>) : Fragment() {

  var pokemonAdapter: PokemonAdapter? = null
  //private lateinit var imageAdapter : ImageAdapter


  //1.event
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Log.i(Globals.TAG, "FragmentSearch onCreate")
    Toast.makeText(activity, "FragmentSearch onCreate", Toast.LENGTH_SHORT).show()

  }

  //2.event
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    //return inflater.inflate(R.layout.fragment_search, container, false)
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_search, container, false)
    val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

    pokemonAdapter = PokemonAdapter(requireContext(), data)
    recyclerView.setAdapter(pokemonAdapter)

    return view
  }


  //3.event
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

//    initRecyclerView()
//    addDataset()
  }

//  private fun addDataset(){
//    val data = DataSource.createDataSet()
//    imageAdapter.submitList(data)
//  }
//
//  private fun initRecyclerView(){
//    recycler_view.apply{
//      layoutManager = GridLayoutManager(requireContext(), 3)
//      val topSpacingDecoration = TopSpacingItemDecoration(30)
//      addItemDecoration(topSpacingDecoration)
//      pokemonAdapter = PokemonAdapter()
//      recycler_view.adapter = imageAdapter
//    }
//  }
}
