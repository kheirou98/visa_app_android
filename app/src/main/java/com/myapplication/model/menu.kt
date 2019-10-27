package com.myapplication.model

class menu {
    var name: String? = null
    var image: Int? = null
    var color:String?=null
    var notif:String?=null

    constructor(name: String, image: Int, color : String,notif:String) {
        this.name = name
        this.image = image
        this.color=color
        this.notif=notif
    }
}