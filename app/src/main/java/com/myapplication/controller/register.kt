package com.myapplication.controller

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.myapplication.R
import com.myapplication.adapter.RegisterFormAdapterPays
import com.myapplication.adapter.RegisterFormAdapterWilayas
import com.myapplication.helper.database
import com.myapplication.model.user
import kotlinx.android.synthetic.main.activity_register_form.*

class register:  AppCompatActivity(){
    var agence="MARKETEL"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val DB= database(this)
        val wilayas= DB.getAllWilaya()
        val pays=DB.getAllPays()
        val view = findViewById<View>(R.id.register)
        var Adapter= RegisterFormAdapterPays(this, pays, pays, view)
        recyclerviewpays.layoutManager = LinearLayoutManager(this)
        recyclerviewpays.visibility=View.INVISIBLE
        recyclerviewpays.adapter=Adapter
        textViewpays.setOnClickListener {
            recyclerviewpays.visibility= View.VISIBLE
            searchViewpays.visibility= View.VISIBLE
            textViewpays.visibility= View.INVISIBLE
        }
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchViewpays.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // listening to search query text change
        searchViewpays!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                // filter recycler view when query submitted
                Adapter.getFilter().filter(query)
                recyclerviewpays.visibility= View.VISIBLE

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {

                // filter recycler view when query submitted
                Adapter.getFilter().filter(query)
                recyclerviewpays.visibility= View.VISIBLE
                return false
            }
        })
        val wilayaview = findViewById<View>(R.id.register)
        var wilayaAdapter= RegisterFormAdapterWilayas(this, wilayas, wilayas, wilayaview)
        recyclerviewwilaya.layoutManager = LinearLayoutManager(this)
        recyclerviewwilaya.visibility=View.INVISIBLE
        recyclerviewwilaya.adapter=wilayaAdapter
        textViewwilaya.setOnClickListener {
            recyclerviewwilaya.visibility= View.VISIBLE
            searchViewwilaya.visibility= View.VISIBLE
            textViewwilaya.visibility= View.INVISIBLE
        }
        val wilayasearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchViewwilaya.setSearchableInfo(wilayasearchManager.getSearchableInfo(componentName))
        // listening to search query text change
        searchViewwilaya!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                // filter recycler view when query submitted
                wilayaAdapter.getFilter().filter(query)
                recyclerviewwilaya.visibility= View.VISIBLE
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when query submitted
                wilayaAdapter.getFilter().filter(query)
                recyclerviewwilaya.visibility= View.VISIBLE
                return false
            }
        })
        button.setOnClickListener {
            if (id.text.toString() == "") {
                Toast.makeText(this, "Veuillez remplir le champs du nom de l'utilisateur svp!", Toast.LENGTH_SHORT
                ).show()
            } else {
                if (adresse.text.toString() == "") {
                    Toast.makeText(this, "Veuillez remplir le champs du nom svp!", Toast.LENGTH_SHORT).show()
                } else {
                    if (nom.text.toString() == "") {
                        Toast.makeText(this, "Veuillez remplir le champs du nom svp!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (prenom.text.toString() == "") {
                            Toast.makeText(this, "Veuillez remplir le champs du prénom svp!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (numero.text.toString() == "") {
                                Toast.makeText(this, "Veuillez remplir le champs du numéro svp!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else {
                                if (mdp.text.toString()==""){
                                    Toast.makeText(this, "Veuillez remplir le champs du mot de passe svp!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else {
                                    if (textViewpays.text.toString()=="") {
                                        Toast.makeText(this, "Veuillez choisir le pays de destination svp!", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    else{
                                        if (textViewwilaya.text.toString()==""){
                                            Toast.makeText(this, "Veuillez choisir la ville de résidence svp!", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        else {
                                                val progressDialog = ProgressDialog(this)
                                                progressDialog.setMessage("Création du compte en cours ... ")
                                                progressDialog.setCancelable(false)
                                            for(i in 1..15000){
                                                progressDialog.show()
                                            }
                                                var success: Boolean
                                                val user= user(
                                                    id.text.toString(),
                                                    mdp.text.toString(),
                                                    nom.text.toString(),
                                                    prenom.text.toString(),
                                                    adresse.text.toString(),
                                                    numero.text.toString().toInt(),
                                                    textViewpays.text.toString(),
                                                    textViewwilaya.text.toString(),
                                                    agence
                                                )
                                                success=DB.addUser(user)
                                                if (success){
                                                    Toast.makeText(this,"Utilisateur enregistré avec succès!", Toast.LENGTH_SHORT).show()
                                                }
                                                var  intent= Intent(this@register, MainActivity::class.java)
                                                intent.putExtra("bool","vrai")
                                                intent.putExtra("nomprenom",nom.text.toString().toUpperCase()+"\n"+prenom.text.toString())
                                                intent.putExtra("np",nom.text.toString().toUpperCase()+" "+prenom.text.toString())
                                                intent.putExtra("id",id.text.toString())
                                                intent.putExtra("adresse",adresse.text.toString())
                                                intent.putExtra("num",numero.text.toString().toInt())
                                                intent.putExtra("nom",nom.text.toString())
                                                intent.putExtra("prenom",prenom.text.toString())
                                                intent.putExtra("destination",textViewpays.text.toString())
                                                intent.putExtra("residence",textViewwilaya.text.toString())
                                                intent.putExtra("agence",agence)
                                                startActivity(intent)
                                                this@register.finish()
                                        }
                                    }

                                }

                            }
                        }
                    }
                }
            }

        }
    }
}