package com.myapplication.controller

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.myapplication.R
import com.myapplication.model.SharedPreference
import com.myapplication.helper.database

class popup : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceCreate: Bundle?): View? {
        val view = inflater.inflate(R.layout.popup, container, false)
        dialog!!.setCanceledOnTouchOutside(true)
        dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val identifiant = view.findViewById<EditText>(R.id.identif)
        val mdpnew = view.findViewById<EditText>(R.id.mdpnew)
        val mdpnewconfirme = view.findViewById<EditText>(R.id.mdpnewconfirme)
        val confirmer = view.findViewById<Button>(R.id.confirmer)
        val quitter = view.findViewById<Button>(R.id.quitter)
        val thiscontext = activity!!.applicationContext
        val context = activity!!.applicationContext
        val sharedPreference = thiscontext?.let { SharedPreference(it) }
        val DB = database(context)
        var ident = ""
        var id=""
        var mdp = ""
        confirmer!!.setOnClickListener {
            if (identifiant.text.toString() == "") {
                Toast.makeText(context, "Veuillez remplire le champs de l'identifiant!", Toast.LENGTH_SHORT).show()
            } else {
                if (mdpnew.text.toString()==""){
                    Toast.makeText(context, "Veuillez introduire le nouveau mot de passe!", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (mdpnewconfirme.text.toString()==""){ Toast.makeText(context, "Veuillez confirmer le nouveau mot de passe!", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        if (mdpnew.text.toString().equals(mdpnewconfirme.text.toString())) {
                            sharedPreference?.save("iden", identifiant.text.toString())
                            sharedPreference?.save("mdpnew", mdpnew.text.toString())
                            id = identifiant.text.toString()
                            mdp = mdpnew.text.toString()
                            Toast.makeText(context, "Mot de passe changé avec succès!! ...", Toast.LENGTH_SHORT).show()
                            if (sharedPreference?.getValueString("iden") != null) {
                                ident = sharedPreference.getValueString("iden")!!.toString()
                            }
                            if (sharedPreference?.getValueString("mdpnew") != null) {
                                mdp = sharedPreference.getValueString("mdpnew")!!.toString()
                            }
                            if (DB.checkUser(ident)) {
                                DB.updateUser(ident, mdp)
                                Toast.makeText(
                                    context,
                                    "Mise à jour de la Base de données avec succès!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
                this.dismiss()
            }
            quitter!!.setOnClickListener {
                Toast.makeText(context, "Annulation du changement du mot de passe!", Toast.LENGTH_SHORT).show()
                this.dismiss()
            }
            return view
        }


}