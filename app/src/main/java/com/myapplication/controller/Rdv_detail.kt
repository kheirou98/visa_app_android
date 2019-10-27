package com.myapplication.controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.myapplication.R
import com.myapplication.model.SharedPreference
import com.myapplication.helper.database
import com.myapplication.model.Rdvs
import kotlinx.android.synthetic.main.activity_rdv_detail.*
import kotlinx.android.synthetic.main.activity_rdv_detail.ok
import java.util.*

class Rdv_detail : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rdv_detail)
        var sharedPreference= SharedPreference(this)
        var DB= database(this)
        val intent=intent
        tv_nom.setText(intent.getStringExtra("nom"))
        tv_prenom.setText(intent.getStringExtra("prenom"))
       tv_depart.setText(intent.getStringExtra("depart"))
       tv_destination.setText(intent.getStringExtra("destination"))
        tv_agence.setText(intent.getStringExtra("agence"))
        tv_date.setText(intent.getStringExtra("date"))
        tv_heure.setText(intent.getStringExtra("heure"))
        tv_date.setOnClickListener {
            val calendar= Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(this@Rdv_detail, DatePickerDialog.OnDateSetListener
            {_, myear, monthOfYear, dayOfMonth ->

                tv_date.setText("" + dayOfMonth + "/" + (monthOfYear+1) + "/" + myear)

            }, year, month, day)
            datePickerDialog.show()
        }
        tv_heure.setOnClickListener {
            val calendar= Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(this@Rdv_detail, TimePickerDialog.OnTimeSetListener
            { _, mhour, mminute ->

                tv_heure.setText("" + mhour+ ":" + mminute)

            }, hour, minute,true )
            timePickerDialog.show()
        }
        val villes = DB.getAllWilaya()
        val MydataAdapter = ArrayAdapter<String>(this@Rdv_detail, android.R.layout.simple_spinner_dropdown_item, villes)
        tv_depart.setOnClickListener(View.OnClickListener {
            tv_depart.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
            AlertDialog.Builder(this@Rdv_detail)
                .setTitle("Sélectionner la ville de résidence:")
                .setAdapter(MydataAdapter, DialogInterface.OnClickListener { dialog, which ->
                    tv_depart.setText(villes[which])
                    dialog.dismiss()
                }).create().show()
        })
        val pays = DB.getAllPays()
        val MydataAdapterPays = ArrayAdapter<String>(this@Rdv_detail, android.R.layout.simple_spinner_dropdown_item, pays)
        tv_destination.setOnClickListener(View.OnClickListener {
            tv_destination.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
            AlertDialog.Builder(this@Rdv_detail)
                .setTitle("Sélectionner le pays de destination:")
                .setAdapter(MydataAdapterPays, DialogInterface.OnClickListener { dialog, which ->
                    tv_destination.setText(villes[which])
                    dialog.dismiss()
                }).create().show()
        })
        val agences = DB.getAllAgenceName()
        val MydataAdapterAgence = ArrayAdapter<String>(this@Rdv_detail, android.R.layout.simple_spinner_dropdown_item, agences)
        tv_agence.setOnClickListener(View.OnClickListener {
            tv_agence.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
            AlertDialog.Builder(this@Rdv_detail)
                .setTitle("Sélectionner l'agence du dépôt:")
                .setAdapter(MydataAdapterAgence, DialogInterface.OnClickListener { dialog, which ->
                    tv_agence.setText(agences[which])
                    dialog.dismiss()
                }).create().show()
        })
        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_delete -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Etes vous sur de supprimer le RDV?")
                        .setIcon(R.drawable.ic_close_black_24dp).setTitle("Suppression du rendez-vous:").setPositiveButton(
                            "Oui"
                        ) { _, _ ->
                            var id_visa =DB.getIdVisa(sharedPreference.getValueString("id_user_visa").toString(),intent.getStringExtra("nom"),intent.getStringExtra("prenom")
                                ,intent.getStringExtra("depart"),
                                intent.getStringExtra("destination"),intent.getStringExtra("agence"),
                                intent.getStringExtra("heure"),intent.getStringExtra("date"))
                            DB.deleteVISA(id_visa)
                            val intentcompte = Intent(this@Rdv_detail, RDV::class.java)
                            intentcompte.putExtra("id",sharedPreference.getValueString("id_user_visa").toString())
                            intentcompte.putExtra("nom",tv_nom.text.toString())
                            intentcompte.putExtra("prenom",tv_prenom.text.toString())
                            intentcompte.putExtra("bool","vrai")
                            this@Rdv_detail.finish()
                            startActivity(intentcompte)
                        }.setNegativeButton(
                            "Non"
                        ) { _, _-> }.show()
                }
                R.id.action_edit -> {
                    var id_visa =DB.getIdVisa(sharedPreference.getValueString("id_user_visa").toString(),intent.getStringExtra("nom"),intent.getStringExtra("prenom")
                        ,intent.getStringExtra("depart"),
                        intent.getStringExtra("destination"),intent.getStringExtra("agence"),
                        intent.getStringExtra("heure"),intent.getStringExtra("date"))
                    ok.visibility= View.VISIBLE
                    tv_nom.isEnabled=true
                    tv_prenom.isEnabled=true
                    tv_depart.isEnabled=true
                    tv_depart.isFocusable=false
                    tv_depart.isClickable=true
                    tv_destination.isEnabled=true
                    tv_destination.isFocusable=false
                    tv_destination.isClickable=true
                    tv_agence.isEnabled=true
                    tv_agence.isFocusable=false
                    tv_agence.isClickable=true
                    tv_date.isEnabled=true
                    tv_date.isFocusable=false
                    tv_date.isClickable=true
                    tv_heure.isEnabled=true
                    tv_heure.isFocusable=false
                    tv_heure.isClickable=true

                    ok.setOnClickListener {
                        var visas= Rdvs(
                            id_visa,
                            sharedPreference.getValueString("id_user_visa").toString(),
                            tv_agence.text.toString(),
                            tv_destination.text.toString(),
                            tv_depart.text.toString(),
                            tv_nom.text.toString(),
                            tv_prenom.text.toString(),
                            tv_heure.text.toString(),
                            tv_date.text.toString()
                        )
                        DB.updateVisa(visas)
                        ok.visibility= View.INVISIBLE
                        tv_nom.isEnabled=false
                        tv_prenom.isEnabled=false
                        tv_depart.isEnabled=false
                        tv_destination.isEnabled=false
                        tv_agence.isEnabled=false
                        tv_date.isEnabled=false
                        tv_heure.isEnabled=false
                        Toast.makeText(this, "Mise à jour du RDV avec succès!", Toast.LENGTH_SHORT).show()
                        var intentacc = Intent(this@Rdv_detail, Compte::class.java)
                        intentacc.putExtra("id",sharedPreference.getValueString("id_user_visa"))
                        intentacc.putExtra("nom",tv_nom.text.toString())
                        intentacc.putExtra("prenom",tv_prenom.text.toString())
                        startActivity(intentacc)
                        this@Rdv_detail.finish()
                    }
                }
                R.id.action_docs -> {
                    val docsintent = Intent(this@Rdv_detail, DocsCheckList::class.java)
                    startActivity(docsintent)
                }
            }
            true
        }
        bottomNav2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
