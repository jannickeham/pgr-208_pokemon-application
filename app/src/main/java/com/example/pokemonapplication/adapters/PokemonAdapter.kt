package com.example.pokemonapplication.adapters

import android.content.Context
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.TraceCompat
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapplication.R
import com.example.pokemonapplication.models.PokemonModel
//import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_pokemon.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.fragment_upload.*
//import kotlinx.android.synthetic.main.image_layout_fragment.view.*
import org.json.JSONArray

class PokemonAdapter(
  val context: Context,
  var data: List<PokemonModel>,
  //val responseArray: ArrayList<JSONArray>,
  //val onImageClickListener: View.OnClickListener
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

  //private var data: List<PokemonModel> = ArrayList()

  //class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  //var image: ImageView = itemView.findViewById(R.id.image)
  //var name: ImageView = itemView.findViewById(R.id.name)
  //}

 /* class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    //var imageSearchResults: ImageView = itemView.findViewById(R.id.imageSearch)
    //var recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
    var image: ImageView = itemView.findViewById(R.id.image)
    //var name: TextView = itemView.findViewById(R.id.name)
  }*/


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    //val linear: LinearLayout = LinearLayout(parent.context)
    //val postImage: ImageView = ImageView(parent.context)
    //linear.addView(postImage)
    //postImage.adjustViewBounds = true
    //linear.addView(postImage)

    //return ViewHolder(postImage)
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
    //val imageResult = itemView.image_result

    fun bind(pokemonModel: PokemonModel){

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      Glide.with(itemView.context)
        .applyDefaultRequestOptions(requestOptions)
        .load(pokemonModel.thumbnail)
        .into(image)

      // blog_title.setText(blogPost.title)
      //blog_author.setText(blogPost.username)

    }

  }



    /*val images = data[position]
    val imageUriLink = Uri.parse(images.imageLink)*/

    //val imageUriThumbnail = Uri.parse(images.thumbnail)
    //holder.view.tag = position

    //val view = ((holder.view as LinearLayout).getChildAt(1) as ImageView).image.recycler_view
    //val postImage: ImageView = view.image

    /*Glide.with(context)
        .asBitmap()
        .load(images)
        .centerCrop()
        .into(postImage)*/

    /* Glide.with(context)
      .asBitmap()
      .load(images)
      .into(holder.image)*/

    //val postImage = itemView.image
/*
    fun bind(imagePost: PokemonModel) {

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(imagePost.thumbnail)
        //.load(imagePost.thumbnailLink)
        .into(holder.image)

    }
  }*/
  override fun getItemCount(): Int {
    return data.size
  }
}



//    var image: ImageView = imageView.findViewById(R.id.post_image)
//    var recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
//
//    val postImage = itemView.post_image
//
//    fun bind(imagePost: PokemonModel) {
//
//      val requestOptions = RequestOptions()
//        .placeholder(R.drawable.ic_launcher_background)
//        .error(R.drawable.ic_launcher_background)
//
//      Glide.with(itemView.context)
//        .applyDefaultRequestOptions(requestOptions)
//        .load(imagePost.thumbnail)
////        .load(imagePost.thumbnailLink)
//        .into(image)
//
    //holder.name.setText(data.get(position).name)

//  fun submitList(imageList: ArrayList<PokemonModel>) {
//    data = imageList
//  }

//  companion object {
//    fun createDataSet(): ArrayList<JSONArray> {
//      val list = ArrayList<JSONArray>()
//      list.add(data)
//      return list
//    }
//  }




//  class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//    lateinit var image: ImageView
//    super(itemView)
//
//  }

//  class ViewHolder: RecyclerView.ViewHolder(view:Itemview) {
//    var image: ImageView
//    super()
//      public ViewHolder(view:Itemview){
//        super(itemView)
//          this.image = itemView.findViewById(R.id.post_image)
//
//      }
//  }



