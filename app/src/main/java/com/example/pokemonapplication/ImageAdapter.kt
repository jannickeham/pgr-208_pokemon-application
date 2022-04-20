package com.example.pokemonapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_search.view.*

class ImageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var items: List<ImagePost> = ArrayList()


  //Creating the different ViewHolders in the list
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return ImageViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.fragment_search, parent, false)
    )
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ImageViewHolder -> {
        holder.bind(items.get(position))
      }
    }
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun submitList(imageList: List<ImagePost>) {
    items = imageList
  }


  class ImageViewHolder constructor(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {

    val postImage = itemView.post_image

    fun bind(imagePost: ImagePost) {

      val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

      Glide.with(itemView.context)
        .applyDefaultRequestOptions(requestOptions)
        .load(imagePost.image)
        .into(postImage)
    }
  }
}


//
//  class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
//
//  }
//
//  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//    var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search, parent, false)
//     return ViewHolder(view)
//  }
//
//  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//    val imageInfo : ImageInfo = imageInfo.get(position)
//
//    holder.itemView.setTag(position)
//  }
//
//  override fun getItemCount(): Int {
//    return imageInfo.size
//  }

//
//
//
//  var dataList = emptyList<DataModel>()
//
//  internal fun setDataList(dataList : List<DataModel>){
//    this.dataList = dataList
//    notifyDataSetChanged()
//  }
//
//  class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {}
////  class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
////    var image : ImageView = itemView.findViewById(R.id.imageViewGallery)
////
////  }
//
//  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
//    var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search, parent, false)
//    return ViewHolder(view)
//  }
//
//  override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
//    val imageInfo : ImageInfo = imageInfo.get(position)
//
//    holder.itemView.setTag(position)
//    ((holder.itemView as GridLayoutManager).getChildAt(0) as GridLayoutManager)
//  }
//
//  override fun getItemCount(): Int {
//    return imageInfo.size
//  }


