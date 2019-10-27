package com.myapplication.controller

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.myapplication.R
import com.myapplication.model.SharedPreference
import com.myapplication.helper.database
import com.scanlibrary.ScanActivity
import com.scanlibrary.ScanConstants
import kotlinx.android.synthetic.main.activity_documents__detail.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class Documents_Detail : AppCompatActivity() {
    var bitmaps:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents__detail)
        var sharedPreference= SharedPreference(this)
        var id_user=sharedPreference.getValueString("id_docs_user")
        var DB= database(this)
        val intent=intent
        var nom=intent!!.getStringExtra("Docnom")
        var image=intent.getByteArrayExtra("Docimage")
        textViewdoc.setText(nom!!)
        imageViewdoc.setImageBitmap(getPhoto(image!!))
        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_editdoc -> {
                    okdocs.visibility=View.VISIBLE
                    val documents = DB.getAllDocs()
                    val MydataAdapter = ArrayAdapter<String>(this@Documents_Detail, android.R.layout.simple_spinner_dropdown_item, documents)
                    textViewdoc.setOnClickListener(View.OnClickListener {
                        textViewdoc.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
                        AlertDialog.Builder(this@Documents_Detail)
                            .setTitle("Sélectionner le nom de votre document:")
                            .setAdapter(MydataAdapter, DialogInterface.OnClickListener { dialog, which ->
                                textViewdoc.setText(documents[which])
                                dialog.dismiss()
                            }).create().show()
                    })
                    imageViewdoc.setOnClickListener {
                        val builder = AlertDialog.Builder(this)
                        builder.setMessage("Etes vous sur de changer l'image du document en question?")
                            .setIcon(R.drawable.ic_close_black_24dp).setTitle("Quitter l'application").setPositiveButton(
                                "Caméra"
                            ) { _, _ ->
                                // openCamera()
                            }.setNegativeButton(
                                "Galerie"
                            ) { _, _->
                                //openGallery()
                            }.show()
                    }
                    okdocs.setOnClickListener {
                        okdocs.visibility=View.INVISIBLE
                        var iddocs=DB. getIdDocsOfUser(id_user!!,nom)
                        if (bitmaps!=null){
                            DB.updateDocofUser(iddocs,nom,getBytes(bitmaps!!))}
                        else {
                            DB.updateDocofUser(iddocs,nom,image)
                        }
                        startActivity(Intent(this@Documents_Detail, Documents::class.java))
                    }
                }
                R.id.action_deletedoc -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Etes vous sur de quitter supprimer le document?")
                        .setIcon(R.drawable.ic_close_black_24dp).setTitle("Supprimer le document en question?").setPositiveButton(
                            "Oui"
                        ) { _, _ ->
                            DB.deleteDocofUser(id_user!!,textViewdoc.text.toString())
                            Toast.makeText(this@Documents_Detail,"Document supprimé avec succès!",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@Documents_Detail, Documents::class.java).apply { putExtra("id",id_user) })}.setNegativeButton(
                            "Non"
                        ) { _, _-> }.show()

                }
            }
            true
        }
        bottomNavDocs.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private val OPEN_THING = 99
  fun openGallery() {
      val preference = ScanConstants.OPEN_MEDIA
      val intent = Intent(this, ScanActivity::class.java)
      intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference)
      startActivityForResult(intent, OPEN_THING)
  }

  fun openCamera() {
      val preference = ScanConstants.OPEN_CAMERA
      val intent = Intent(this, ScanActivity::class.java)
      intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference)
      startActivityForResult(intent, OPEN_THING)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (resultCode == Activity.RESULT_OK && data != null) {
          if (requestCode == OPEN_THING) {
              val uri = data.extras!!.getParcelable<Uri>(ScanConstants.SCANNED_RESULT)
              var bitmap: Bitmap?
              try {
                  bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                  contentResolver.delete(uri!!, null, null)
                  imageViewdoc.setImageBitmap(bitmap)
                  bitmaps=bitmap
              } catch (e: IOException) {
                  e.printStackTrace()
              }

          }
      }
  }
    fun  getBytes(bitmap:Bitmap) : ByteArray {
        /* val stream = ByteArrayOutputStream()
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
          val byteArray = stream.toByteArray()*/
        var stream =  ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
    // convert from byte array to bitmap
    fun getPhoto(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }
}
