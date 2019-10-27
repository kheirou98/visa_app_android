package com.myapplication.controller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.myapplication.R

class Contact : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceCreate: Bundle?): View? {
        val view= inflater.inflate(R.layout.activity_contact, container, false)
        dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(true)
        val phone=view.findViewById<Button>(R.id.phone)
        val email=view.findViewById<Button>(R.id.email)
        val fcbk=view.findViewById<Button>(R.id.fcbk)
        phone!!.setOnClickListener {
            Toast.makeText(context, "Appel téléphonique ...", Toast.LENGTH_SHORT).show()
            val callintent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+213661429090"))
            startActivity(callintent)
            this.dismiss()
        }
        fcbk!!.setOnClickListener {
            Toast.makeText(context, "Ouverture de la messagerie Facebook ...", Toast.LENGTH_SHORT)
            val facebookintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/messages/t/1748555722067749"))
            startActivity(facebookintent)
            this.dismiss()}
        email!!.setOnClickListener {
            Toast.makeText(context, "Ouverture de la messagerie Email...", Toast.LENGTH_SHORT).show()
            val emailintent = Intent(Intent.ACTION_SENDTO)
            emailintent.data = Uri.parse("mailto:recrutement.alger@marketel-algerie.com")
            startActivity(emailintent)
            this.dismiss()}
        return view
    }
}
