package com.myapplication.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.myapplication.R
import com.myapplication.adapter.Rdvadapter
import com.myapplication.helper.database
import com.myapplication.model.Rdvs
import kotlinx.android.synthetic.main.activity_rdv.*
import kotlin.collections.ArrayList

class RDV : AppCompatActivity() {
    var ident=""
    var nom=""
    var prenom =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rdv)
        var DB= database(this)
        val intent = intent
        var bool=intent.getStringExtra("bool")
        if (bool=="vrai"){
        ident=intent.getStringExtra("id")
        nom=intent.getStringExtra("nom")
        prenom=intent.getStringExtra("prenom")
        }
        var rdvs: ArrayList<Rdvs> =DB.getAllvisa(ident)
        recyclerview.layoutManager= LinearLayoutManager(this)
        recyclerview.adapter= Rdvadapter(rdvs, this)
        // adding inbuilt divider line
        recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        button2.setOnClickListener {
            val intent= Intent(this@RDV, NewRdv::class.java)
            intent.putExtra("id",ident)
            intent.putExtra("nom",nom)
            intent.putExtra("prenom",prenom)
            this@RDV.finish()
            startActivity(intent)
        }
    }
}
