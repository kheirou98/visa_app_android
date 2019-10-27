package com.myapplication.model

class Rdvs {
    var id=""
    var id_user=""
    var centre=""
    var residence=""
    var destination=""
    var nom=""
    var prenom=""
    var heure=""
    var date=""
    constructor(id_user:String, centre: String,destination:String,residence:String,nom:String,prenom:String,heure:String,date:String) {
        this.id_user = id_user
        this.centre = centre
        this.residence = residence
        this.destination = destination
        this.nom = nom
        this.prenom = prenom
        this.heure=heure
        this.date=date
    }
    constructor(id: String,id_user:String, centre: String,destination:String,residence:String,nom:String,prenom:String,heure:String,date:String) {
        this.id=id
        this.id_user = id_user
        this.centre = centre
        this.residence = residence
        this.destination = destination
        this.nom = nom
        this.prenom = prenom
        this.heure=heure
        this.date=date
    }
}