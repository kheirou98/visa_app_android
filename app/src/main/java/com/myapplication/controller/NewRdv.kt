package com.myapplication.controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.myapplication.R
import com.myapplication.helper.database
import com.myapplication.model.Rdvs
import kotlinx.android.synthetic.main.activity_new_rdv.*
import java.util.*

class NewRdv : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var nom=""
        var prenom=""
        var id=""
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_rdv)
        var DB= database(this)
        var intent=intent
        nom=intent.getStringExtra("nom")
        prenom=intent.getStringExtra("prenom")
        id=intent.getStringExtra("id")
        var pays=DB.getAllPays()
        val wilayas= DB.getAllWilaya()
        val agences =DB.getAllAgenceName()
        spinner!!.adapter= ArrayAdapter(this@NewRdv, R.layout.spinner_item2,pays)
        spinner!!.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        }
     spinner2!!.adapter= ArrayAdapter(this@NewRdv, R.layout.spinner_item2,wilayas)
     spinner2!!.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
      override fun onNothingSelected(parent: AdapterView<*>?) {
       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
     }
        spinner3!!.adapter= ArrayAdapter(this@NewRdv, R.layout.spinner_item2,agences)
        spinner3!!.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        }
        editText.setOnClickListener {
            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this@NewRdv, DatePickerDialog.OnDateSetListener
            { _, myear, monthOfYear, dayOfMonth ->

                editText.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + myear)

            }, year, month, day)
            datePickerDialog.show()
        }
        editText2.setOnClickListener {
            val calendar= Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this@NewRdv, TimePickerDialog.OnTimeSetListener
            { _, mhour, mminute ->

                editText2.setText("" + mhour+ ":" + mminute)

            }, hour, minute,true )
            timePickerDialog.show()
        }
        buttonconfirme.setOnClickListener {
            if (spinner.selectedItem.toString()==""){
                Toast.makeText(this, "Veuillez remplir le champs du pays de destination svp!", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                if(spinner2.selectedItem.toString()==""){
                    Toast.makeText(this, "Veuillez remplir le champs de la ville de résidence svp!", Toast.LENGTH_SHORT)
                        .show()
                }
                else {
                    if(spinner3.selectedItem.toString()==""){
                        Toast.makeText(this, "Veuillez remplir le champs du lieu de dépôt du dossier svp!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        if (editText.text.toString()==""){

                            Toast.makeText(this, "Veuillez remplir le champs de la date svp!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else{
                            if (editText2.text.toString()==""){

                                Toast.makeText(this, "Veuillez remplir le champs de l'heure svp!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else {
                                var rdv= Rdvs(
                                    intent.getStringExtra("id"),
                                    spinner3.selectedItem.toString(),
                                    spinner.selectedItem.toString(),
                                    spinner2.selectedItem.toString(),
                                    nom,
                                    prenom,
                                    editText2.text.toString(),
                                    editText.text.toString()
                                )
                                var successvisa=DB.addVisa(rdv)
                                if (successvisa){
                                    Toast.makeText(this,"RDV enregistré avec succès!", Toast.LENGTH_SHORT).show()
                                    val intentr= Intent(this@NewRdv, RDV::class.java)
                                    intentr.putExtra("bool","vrai")
                                    intentr.putExtra("id",id)
                                    intentr.putExtra("nom",nom)
                                    intentr.putExtra("prenom",prenom)
                                    startActivity(intentr)
                                    this@NewRdv.finish()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
