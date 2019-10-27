package com.myapplication.controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myapplication.*
import com.myapplication.helper.DataBaseInitialisation
import com.myapplication.helper.database
import com.myapplication.model.SharedPreference
import kotlinx.android.synthetic.main.activity_accueil.*

class accueil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        val mboolean: Boolean
        var sharedPreference= SharedPreference(this)
        mboolean = sharedPreference.getValueBoolean("FIRST_RUN", false)
        if (mboolean==false) {
            val databaseInitialisation = DataBaseInitialisation()
            databaseInitialisation.DatabaseInitialisation(this)
            sharedPreference = SharedPreference(this)
            sharedPreference.save("FIRST_RUN", true)

        }
        val DB= database(this)

        if (sharedPreference.getValueString("id")!=null) {
            identifiant.setText(sharedPreference.getValueString("id")!!)
            checkBox.isChecked=true
        }
        if (sharedPreference.getValueString("mdp")!=null) {
            mdp.setText(sharedPreference.getValueString("mdp")!!)
        }
        connecter.setOnClickListener {
            if (identifiant.text.toString() == ""){
                Toast.makeText(this, "Veuillez remplir le champs de l'identifiant svp!", Toast.LENGTH_SHORT).show()
            }
            else {
                if (mdp.text.toString()==""){
                    Toast.makeText(this, "Veuillez remplir le champs du mot de passe svp!", Toast.LENGTH_SHORT).show()
                }
                else {
                   if (DB.checkUser(identifiant.text.toString())) {
                        if (DB.checkUser(identifiant.text.toString(),mdp.text.toString())) {
                            if (checkBox.isChecked == true) {
                                sharedPreference.save("id", identifiant.text.toString())
                                sharedPreference.save("mdp", mdp.text.toString())
                            }
                            val progressDialog = ProgressDialog(this)
                            progressDialog.setMessage("Connexion en cours ...")
                            progressDialog.setCancelable(false)
                            for(i in 1..15000){
                                progressDialog.show()
                            }
                           // Handler().postDelayed({ progressDialog.dismiss() }, 2000)
                            var users=DB.getName(identifiant.text.toString())
                            val nomprenom= "${users.nom.toString().toUpperCase()}\n${users.prenom.toString()}"
                            val np= "${users.nom.toString().toUpperCase()}"+" "+"${users.prenom.toString()}"
                            var intent = Intent(this@accueil, MainActivity::class.java)
                            intent.putExtra("bool","vrai")
                            intent.putExtra("nomprenom",nomprenom)
                            intent.putExtra("nom",users.nom.toString())
                            intent.putExtra("prenom",users.prenom.toString())
                            intent.putExtra("np",np)
                            intent.putExtra("id",users.id.toString())
                            intent.putExtra("adresse",users.adresse.toString())
                            intent.putExtra("num",users.numero.toString().toInt())
                            intent.putExtra("destination",users.destination.toString())
                            intent.putExtra("residence",users.residence.toString())
                            intent.putExtra("agence",users.agence.toString())
                            startActivity(intent)
                            this@accueil.finish()
                        }
                        else {
                            Toast.makeText(this, "Le mot de passe que vous avez introduit est erroné veuillez saisir le mot de passe correct!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText(this, "Ce compte n'existe pas veuillez vous inscrire d'abord pour se connecter!", Toast.LENGTH_SHORT).show()
                    }


                }
            }

        }
        creer.setOnClickListener {
            var  intent= Intent(this@accueil, register::class.java)
            startActivity(intent)
            this@accueil.finish()
        }
        mdpoublie.setOnClickListener {
            val manager = supportFragmentManager.beginTransaction()
            val popupclass = popup()
            popupclass.show(manager, null)
        }


    }
}
