package com.myapplication.helper

import android.content.Context

class DataBaseInitialisation() {
    fun DatabaseInitialisation(context: Context){
        var DB= database(context)
        DB.addAllWilaya()
        DB.addAllPays()
        DB.addAllAgence()
        DB.addAllDocs()
    }
}