package com.myapplication.controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.graphics.Bitmap
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import com.scanlibrary.ScanActivity
import com.scanlibrary.ScanConstants
import kotlinx.android.synthetic.main.activity_documents.*
import java.io.IOException

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.myapplication.R
import com.myapplication.model.SharedPreference
import com.myapplication.adapter.DocumentsAdapter
import com.myapplication.helper.database
import com.myapplication.model.Doc
import java.io.ByteArrayOutputStream


class Documents : AppCompatActivity() {
    var DB= database(this)
    var ident=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_documents)
        nomfile!!.setText("")
        val intent=intent
         ident = intent.getStringExtra("id")
        var sharedPreference= SharedPreference(this)
        sharedPreference.save("id_docs_user",ident)
        DB= database(this)
        val docs=DB.getAllDocs()
        val MydataAdapter = ArrayAdapter<String>(this@Documents, android.R.layout.simple_spinner_dropdown_item, docs)
        nomfile!!.setOnClickListener {
            nomfile!!.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard
            AlertDialog.Builder(this@Documents)
                .setTitle("Sélectionner le nom du document:")
                .setAdapter(MydataAdapter, DialogInterface.OnClickListener { dialog, which ->
                   nomfile!!.setText(docs[which])
                    dialog.dismiss()
                }).create().show()
        }

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_gallery -> {
                    openGallery()
                    recylerviewdocuments!!.visibility=View.INVISIBLE
                }
                R.id.action_camera -> {
                    openCamera()
                    recylerviewdocuments!!.visibility=View.INVISIBLE
                }
                R.id.action_documents -> {
                    var DocsName=DB.getAllDocsOfUser(ident)
                    recylerviewdocuments!!.layoutManager = LinearLayoutManager(this)
                    recylerviewdocuments!!.adapter= DocumentsAdapter(this, DocsName)
                    // adding inbuilt divider line
                    recylerviewdocuments!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
                    scannedImage!!.visibility=View.INVISIBLE
                    recylerviewdocuments!!.visibility=View.VISIBLE
                }
            }
            true
        }
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
                    scannedImage.setImageBitmap(bitmap)
                    scannedImage!!.visibility=View.VISIBLE
                    confirmer!!.visibility= View.VISIBLE
                    identi!!.visibility=View.VISIBLE
                    nomfile!!.visibility=View.VISIBLE
                    confirmer!!.setOnClickListener {
                        if (nomfile!!.text.toString()!=""){
                            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,nomfile.text.toString(),nomfile.text.toString())
                            confirmer!!.visibility= View.INVISIBLE
                            identi!!.visibility=View.INVISIBLE
                            nomfile!!.visibility=View.INVISIBLE
                            DB.addDoc(Doc(nomfile.text.toString(), ident, getBytes(bitmap)))
                            Toast.makeText(this@Documents,"L'image est enregistrée dans la galerie et la BDD",Toast.LENGTH_SHORT).show()
                        }
                    }
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
}