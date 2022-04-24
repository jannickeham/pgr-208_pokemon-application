package com.example.pokemonapplication.adapters

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pokemonapplication.R
import com.example.pokemonapplication.models.PokemonModel
import kotlinx.android.synthetic.main.adapter_pokemon.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.json.JSONArray

class PokemonAdapter(
  val context: Context,
  val data: ArrayList<PokemonModel>,
  //val responseArray: ArrayList<JSONArray>,
  //val onImageClickListener: View.OnClickListener
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

  //class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  //var image: ImageView = itemView.findViewById(R.id.image)
  //var name: ImageView = itemView.findViewById(R.id.name)
  //}

  class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val recycle: ConstraintLayout = ConstraintLayout(parent.context)
    val postImage: ImageView = ImageView(parent.context)
    postImage.adjustViewBounds = true
    recycle.addView(postImage)

    return ViewHolder(recycle)
  }


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val images = data[position]
    val imageUriLink = Uri.parse(images.imageLink)
    val imageUriThumbnail = Uri.parse(images.thumbnail)
    holder.view.tag = position

    val view = ((holder.view as RecyclerView).getChildAt(0) as ImageView).post_image.recycler_view
    val postImage: ImageView = view.post_image

    Glide.with(context)
        .asBitmap()
        .load(images)
        .centerCrop()
        .into(postImage)
  }

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



