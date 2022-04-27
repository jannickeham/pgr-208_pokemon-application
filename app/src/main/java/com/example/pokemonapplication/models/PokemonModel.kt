package com.example.pokemonapplication.models

data class PokemonModel(
  var thumbnail: String,
  var imageLink: String,


) {
  override fun toString(): String {
    return "Image(thumbnail='$thumbnail', imageLink='$imageLink')"
  }

/*  constructor( storeLink: String, thumbnail: String)  {
    this.thumbnail = thumbnail
    this.imageLink = imageLink
  }*/
}






