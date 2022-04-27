package com.example.pokemonapplication

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel

class FragmentSearch(var data: ArrayList<PokemonModel>) : Fragment() {

  private lateinit var dbHelper: FeedReaderDbHelper

  var pokemonAdapter: PokemonAdapter? = null
  lateinit var saveButton: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = LayoutInflater.from(context).inflate(R.layout.fragment_search, null)
    val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

      var onSaveImageListener = (object : View.OnClickListener {
        override fun onClick(view: View?) {


          val position: Int = view?.tag.toString().toInt()
          val imageSearchResults: PokemonModel = data.get(position)
          val selectedImage = imageSearchResults.imageLink

          saveButton.setOnClickListener{
            Toast.makeText(activity, "New Pokemon to party", Toast.LENGTH_SHORT).show()
            (activity as MainActivity).addSelectedImageToDb(imageSearchResults)

            dbHelper.writableDatabase.insert("pokemon", null, ContentValues().apply {
              put("image", selectedImage)
            })
          }

          val intent: Intent = Intent(activity, EditActivity::class.java)
          intent.putExtra("selected_image", selectedImage)
          startForResult.launch(intent)

        }
      })

    pokemonAdapter = PokemonAdapter(requireContext(), data)
    recyclerView.setAdapter(pokemonAdapter)

    return view
  }

  val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
    if (result.resultCode == Activity.RESULT_OK) {
      val intent = result.data
      val updatedImageInfo:PokemonModel = (intent?.getSerializableExtra("selected_image") as PokemonModel)
      data.set(updatedImageInfo.imageLink.length, updatedImageInfo)

      pokemonAdapter?.notifyDataSetChanged()
    }
  }
}

