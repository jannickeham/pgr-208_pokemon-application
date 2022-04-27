package com.example.pokemonapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapplication.R
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.adapter_pokemon.view.*

//Mitch / Pokemon movie
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

    val image = itemView.image

    fun bind(pokemonModel: PokemonModel){
      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      //Glide library
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




