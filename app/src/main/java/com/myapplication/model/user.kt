package com.myapplication.model

class user {
    var id: String? = null
    var mdp: String? = null
    var nom: String? = null
    var prenom: String? = null
    var adresse: String? = null
    var numero: Int? = null
    var destination: String? = null
    var residence: String? = null
    var agence: String? = null
    constructor(id: String) {
        this.id = id
   }
    constructor(id: String, mdp: String) {
        this.id = id
        this.mdp = mdp
    }
    constructor(id: String, mdp: String,nom:String,prenom:String,adresse:String,numero:Int,destination:String,residence:String,agence:String) {
        this.id = id
        this.mdp = mdp
        this.nom = nom
        this.prenom = prenom
        this.adresse = adresse
        this.numero = numero
        this.destination = destination
        this.residence=residence
        this.agence=agence
    }
}