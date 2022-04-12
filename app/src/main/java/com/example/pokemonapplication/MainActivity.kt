package com.example.pokemonapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set default fragment
        val fragmentUpload = FragmentUpload()
        val fragmentSearch = FragmentSearch()
        val fragmentSaved = FragmentSaved()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragmentUpload)
            commit()
        }

        btnUpload.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragmentUpload)
                addToBackStack(null)
                commit()
            }
            btnSearch.setOnClickListener {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, fragmentSearch)
                    addToBackStack(null)
                    commit()
                }

                btnSaved.setOnClickListener {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, fragmentSaved)
                        addToBackStack(null)
                        commit()
                    }
                }
            }
        }
    }
}