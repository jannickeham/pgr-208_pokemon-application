package com.example.pokemonapplication.adapters

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.view.menu.MenuView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.TraceCompat
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapplication.Globals.TAG
import com.example.pokemonapplication.R
import com.example.pokemonapplication.models.PokemonModel
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_pokemon.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_upload.*
//import kotlinx.android.synthetic.main.image_layout_fragment.view.*
import org.json.JSONArray
import com.androidnetworking.AndroidNetworking.put
import com.example.pokemonapplication.MainActivity
import com.example.pokemonapplication.UriToBitmap
import com.example.pokemonapplication.getBitmap
import java.io.ByteArrayOutputStream

//Pokemonfilm/Mitch
class PokemonAdapter(
  val context: Context,
  var data: List<PokemonModel>,

) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

  lateinit var onSaveImageListener: View.OnClickListener
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
   return ViewHolder(
     LayoutInflater.from(parent.context).inflate(R.layout.adapter_pokemon, parent, false))
  }

  fun submitList(pokemonModel: ArrayList<PokemonModel>) {
    data = pokemonModel
  }

  companion object {
    fun createDataSet(): ArrayList<PokemonModel> {
      val list = ArrayList<PokemonModel>()
      list.add(PokemonModel("thumbnail_link", "image_link"))
      return list
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    when (holder) {
      is ViewHolder -> {
        holder.bind(data.get(position))
      }
    }

  }

  class ViewHolder
  constructor(
    itemView: View
  ): RecyclerView.ViewHolder(itemView){

    val linearLayout = itemView.linear
    val image = itemView.image
    val saveButton = itemView.addImageToDB
    //val imageResult = itemView.image_result

    fun bind(pokemonModel: PokemonModel){

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      Glide.with(itemView.context)
        .applyDefaultRequestOptions(requestOptions)
        .load(pokemonModel.thumbnail)
        .into(image)

    }
  }
  override fun getItemCount(): Int {
    return data.size
  }
}




