package com.myapplication.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.myapplication.R
import kotlinx.android.synthetic.main.activity_about.button4
import kotlinx.android.synthetic.main.activity_about.button5
import kotlinx.android.synthetic.main.activity_compte.*

class Compte : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compte)
        val intent=intent
        var ident = intent.getStringExtra("id")
        var nom=intent.getStringExtra("nom")
        var prenom=intent.getStringExtra("prenom")
        var notification=intent.getStringExtra("notification")
        rdv_notification!!.setText(notification)
        if (rdv_notification!!.text.toString()=="0"){
            rdv_notification!!.visibility=View.INVISIBLE
        }
        else {
            rdv_notification!!.visibility=View.VISIBLE
        }
        button4.setOnClickListener {
            val rdvintent = Intent(this@Compte, RDV::class.java)
            rdvintent.putExtra("bool","vrai")
            rdvintent.putExtra("id",ident)
            rdvintent.putExtra("nom",nom)
            rdvintent.putExtra("prenom",prenom)
            rdv_notification!!.visibility= View.INVISIBLE
            startActivity(rdvintent)
        }
        button5.setOnClickListener {
            val mapsintent = Intent(this@Compte, Centres::class.java)
            startActivity(mapsintent)
        }
        button3.setOnClickListener {
            val docsintent = Intent(this@Compte, DocsCheckList::class.java)
            startActivity(docsintent)
        }
    }
}
