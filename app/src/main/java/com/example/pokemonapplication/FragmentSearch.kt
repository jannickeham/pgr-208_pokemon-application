package com.example.pokemonapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapplication.Globals.TAG
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_upload.*

class FragmentSearch(var data: ArrayList<PokemonModel>) : Fragment() {

  var pokemonAdapter: PokemonAdapter? = null
  lateinit var checkBox: CheckBox
  lateinit var cardView: CardView
  lateinit var btnToDB: Button


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

    // Inflating fragment layout
    val view = LayoutInflater.from(context).inflate(R.layout.fragment_search, null)
    val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)


    pokemonAdapter = PokemonAdapter(requireContext(), data)
    recyclerView.setAdapter(pokemonAdapter)

    return view
    selectedPokemons()


  }


  private fun selectedPokemons(){
    if(checkBox.isChecked){
      val position: Int = view?.tag.toString().toInt()
      val selectedPokemon: PokemonModel = data.get(position)


      Log.i(TAG, "These pokemons are ready for database $image")

    }
  }

  //3.event
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
   /* val btnToDB : Button = view.findViewById<Button>(R.id.addImageToDB)

    btnToDB.setOnClickListener{
      save()
      Log.i(TAG, "I AM A BUTTTTON!!!!")
    }*/

   // val checkBox: CheckBox = view.findViewById<CheckBox>(R.id.checkbox)


    }

  fun save(){
    Log.i(TAG, "HALLO")
  }

}

