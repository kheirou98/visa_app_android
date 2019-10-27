package com.myapplication.controller

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SearchView
import com.myapplication.adapter.Mapadapter
import com.myapplication.R
import com.myapplication.helper.database
import kotlinx.android.synthetic.main.activity_centres.*

class Centres : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_centres)
        val DB= database(this)
        val view = findViewById<View>(R.id.am)
        var agence=""
        var agences: ArrayList<String> = DB.getAllAgenceName()
        var Adapter= Mapadapter(this, agences, agences, view)
        recyclerviewmap.layoutManager = LinearLayoutManager(this)
        recyclerviewmap.adapter=Adapter
        textView.setOnClickListener {
            recyclerviewmap.visibility= View.VISIBLE
            searchView.visibility= View.VISIBLE
            textView.visibility= View.INVISIBLE
        }
        // adding inbuilt divider line
        recyclerviewmap.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                    // filter recycler view when query submitted
                    Adapter.getFilter().filter(query)

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {

                    // filter recycler view when query submitted
                    Adapter.getFilter().filter(query)
                return false
            }
        })
        buttonconfirme.setOnClickListener {
            agence=textView.text.toString()
            val gmmIntentUri = Uri.parse("geo:0,0?q= "+agence)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}
