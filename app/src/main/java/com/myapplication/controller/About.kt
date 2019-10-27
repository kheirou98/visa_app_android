package com.myapplication.controller

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.myapplication.R
import kotlinx.android.synthetic.main.activity_about.*

class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        button4.setOnClickListener{
            Toast.makeText(this, "Ouverture du site Web ...", Toast.LENGTH_LONG).show()
            val browserintent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.marketel-services.com/presentation-marketel.html"))
            startActivity(browserintent)
        }
        button.setOnClickListener {
            Toast.makeText(this, "Ouverture de notre page Facebook ...", Toast.LENGTH_LONG).show()
            val browserintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MarketelAlgerie/"))
            startActivity(browserintent)
        }
        button5.setOnClickListener {
            Toast.makeText(this, "Ouverture de l'iténiraire sur HERE WeGo ...", Toast.LENGTH_LONG).show()
            val browserintent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wego.here.com/directions/mylocation/e-eyJuYW1lIjoiTWFya2V0ZWwgQWxnZXJpZSIsImFkZHJlc3MiOiIzOSAsIFJ1ZSBkZSBsYSBtYWRlbGVpbmUgSHlkcmEgQWxnZXIiLCJsYXRpdHVkZSI6MzYuNzYwNzUsImxvbmdpdHVkZSI6My4wMzU3MiwicHJvdmlkZXJOYW1lIjoiZmFjZWJvb2siLCJwcm92aWRlcklkIjoxNzQ4NTU1NzIyMDY3NzQ5fQ==?map=36.76075,3.03572,15,normal&ref=facebook&link=directions&fb_locale=fr_FR"))
            startActivity(browserintent)
        }
        button6.setOnClickListener {
            Toast.makeText(this, "Appel téléphonique ...", Toast.LENGTH_LONG).show()
            val intent_phone = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "3313"))
            startActivity(intent_phone)
        }

    }
}
