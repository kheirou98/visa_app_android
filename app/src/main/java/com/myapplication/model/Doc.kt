package com.myapplication.model

import java.io.Serializable

class Doc:Serializable {
    var nom:String?=null
    var id_user:String?=null
    var image:ByteArray?=null
    constructor(nom:String,id_user:String,image:ByteArray){
        this.nom=nom
        this.id_user=id_user
        this.image=image
    }
}