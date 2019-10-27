package com.myapplication.controller

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import com.myapplication.R
import com.myapplication.helper.database
import kotlinx.android.synthetic.main.activity_profil.*

class profil : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        val DB= database(this)
        val intent = intent
        val id=intent.getStringExtra("id")
        val nom=intent.getStringExtra("nom")
        val prenom=intent.getStringExtra("prenom")
        val adresse = intent.getStringExtra("adresse")
        val num=intent.getIntExtra("num",0)
        val residence=intent.getStringExtra("residence")
        ed1.setText(nom)
        edprenom.setText(prenom)
        ed2.setText("0"+num.toString())
        ed3.setText(id)
        ed4.setText(adresse)
        ed5.setText(residence)
        modify.setOnClickListener {
            ed1.isEnabled=true
            ed2.isEnabled=true
            edprenom.isEnabled=true
            ed3.isEnabled=false
            ed4.isEnabled=true
            ed5.isEnabled=true
            ed5.isFocusable=false
            ed5.isClickable=true
            modify.visibility= View.INVISIBLE
            ok.visibility= View.VISIBLE
            val villes = DB.getAllWilaya()
            val MydataAdapter = ArrayAdapter<String>(this@profil, android.R.layout.simple_spinner_dropdown_item, villes)
            ed5.setOnClickListener(View.OnClickListener {
                ed5.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
                AlertDialog.Builder(this@profil)
                    .setTitle("Sélectionner la ville de résidence:")
                    .setAdapter(MydataAdapter, DialogInterface.OnClickListener { dialog, which ->
                        ed5.setText(villes[which])
                        dialog.dismiss()
                    }).create().show()
            })

        }
        ok.setOnClickListener {
            ed1.isEnabled=false
            edprenom.isEnabled=false
            ed2.isEnabled=false
            ed3.isEnabled=false
            ed4.isEnabled=false
            ed5.isEnabled=false
            DB.updateUser(ed3.text.toString(),ed1.text.toString(),edprenom.text.toString(),ed2.text.toString().toInt(),ed4.text.toString(),ed5.text.toString())
            ok.visibility= View.INVISIBLE
            modify.visibility= View.VISIBLE
        }
    }
}
