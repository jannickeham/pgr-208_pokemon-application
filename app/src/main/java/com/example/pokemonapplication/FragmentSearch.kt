package com.example.pokemonapplication

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapplication.Globals.TAG
import com.example.pokemonapplication.adapters.PokemonAdapter
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.adapter_pokemon.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.fragment_upload.image
import java.io.ByteArrayOutputStream

class FragmentSearch(var data: ArrayList<PokemonModel>) : Fragment() {

  private lateinit var dbHelper: FeedReaderDbHelper

  var pokemonAdapter: PokemonAdapter? = null
  lateinit var saveButton: Button

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



      var onSaveImageListener = (object : View.OnClickListener {
        override fun onClick(view: View?) {
          Log.i(TAG, "Clicked on an image")
          //val os = ByteArrayOutputStream()

          val position: Int = view?.tag.toString().toInt()
          val imageSearchResults: PokemonModel = data.get(position)
          val selectedImage = imageSearchResults.imageLink

          saveButton.setOnClickListener{
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
    //selectedPokemons()

  }

  val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
    if (result.resultCode == Activity.RESULT_OK) {
      val intent = result.data
      val updatedImageInfo:PokemonModel = (intent?.getSerializableExtra("selected_image") as PokemonModel)

      data.set(updatedImageInfo.imageLink.length, updatedImageInfo)

      pokemonAdapter?.notifyDataSetChanged()
    }
  }
  //3.event
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}

