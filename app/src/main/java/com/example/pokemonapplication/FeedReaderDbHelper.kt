package com.example.pokemonapplication

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FeedReaderDbHelper(context: MainActivity) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table OriginalImg (id primary key autoincrement, thumbnail blob, image blob)")
        db.execSQL("create table PokemonSaved (id primary key autoincrement, thumbnail blob, image blob, original_image int, constraint fk_original_img foreign key (original_img) references OriginalImg(id))")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists PokemonSaved")
        db.execSQL("drop table if exists OriginalImg")
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PokemonApplication.db"
    }
}