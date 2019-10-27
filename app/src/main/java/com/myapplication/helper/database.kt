package com.myapplication.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.myapplication.model.Agence
import com.myapplication.model.Doc
import com.myapplication.model.Rdvs
import com.myapplication.model.user


class database(context: Context) :
    SQLiteOpenHelper(context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {
    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "MARKETELDB.db"
        val TABLE_USERS = "users"
        val TABLE_VISA="visas"
        val TABLE_PAYS="pays"
        val TABLE_WILAYA="wilaya"
        val TABLE_AGENCE="agence"
        val TABLE_DOCS="docs"
        val TABLE_DOCS_USER="docs_user"
        val TABLE_DOCS_CHECK="docs_check"
    /* colonnes de la table users*/
        val COLUMN_ID = "id"
        val COLUMN_MDP = "mdp"
        val COLUMN_NOM = "nom"
        val COLUMN_PRENOM = "prenom"
        val COLUMN_NUM = "numero"
        val COLUMN_ADRESSE = "adresse"
        /* colonnes de la table visa*/
        val COLUMN_ID_VISA = "id"
        val COLUMN_ID_USER = "id_user"
        val COLUMN_DESTINATION = "destination"
        val COLUMN_RESIDENCE = "residence"
        val COLUMN_AGENCE= "agence"
        val COLUMN_DATE = "date"
        val COLUMN_HEURE = "heure"
        /* colonnes de la table pays*/
        val COLUMN_ID_PAYS ="id_pays"
        val COLUMN_NOM_PAYS="nom_pays"
        /* colonnes de la table wilaya*/
        val COLUMN_ID_WILAYA ="id_wilaya"
        val COLUMN_NOM_WILAYA="nom_wilaya"
        /* colonnes de la table agence*/
        val COLUMN_ID_AGENCE ="id_agence"
        val COLUMN_NOM_AGENCE="nom_agence"
        val COLUMN_NUM_AGENCE ="num_agence"
        val COLUMN_ADRESSE_AGENCE="adresse_agence"
        val COLUMN_WEB_AGENCE="web_agence"
        val COLUMN_EMAIL_AGENCE="email_agence"
        /* colonnes de la table agence*/
        val COLUMN_ID_DOCS ="id_docs"
        val COLUMN_NOM_DOCS="nom_docs"
        /* colonnes de la table docs_user*/
        val COLUMN_ID_DOCS_USER ="id_docs_user"
        val COLUMN_NOM_DOCS_USER="nom_docs_user"
        val COLUMN_IMAGE_DOCS_USER="image_docs_user"
        /* colonnes de la table docs_user*/
        val COLUMN_ID_DOCS_CHECK ="id_docs_user"
        val COLUMN_NOM_DOCS_CHECK="nom_docs_user"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USERS_TABLE = ("CREATE TABLE " + TABLE_USERS +"("+ COLUMN_ID + " TEXT PRIMARY KEY," + COLUMN_MDP +" TEXT," +
                COLUMN_NOM +" TEXT," + COLUMN_PRENOM + " TEXT," + COLUMN_ADRESSE + " TEXT," + COLUMN_NUM + " NUMBER," + COLUMN_DESTINATION + " TEXT,"+
                COLUMN_RESIDENCE + " TEXT,"+ COLUMN_AGENCE + " TEXT)")
        val CREATE_VISA_TABLE = ("CREATE TABLE " + TABLE_VISA +"("+ COLUMN_ID_VISA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_ID_USER +" TEXT,"
                + COLUMN_AGENCE +" TEXT," +
                COLUMN_DESTINATION +" TEXT,"+
                COLUMN_NOM +" TEXT," +
                COLUMN_PRENOM +" TEXT," +
                COLUMN_RESIDENCE + " TEXT," +
                COLUMN_DATE + " TEXT,"
                + COLUMN_HEURE + " TEXT," +
                " FOREIGN KEY ("+ COLUMN_ID_USER +") REFERENCES "+ TABLE_USERS +"("+ COLUMN_ID +") )")
        val CREATE_PAYS_TABLE = ("CREATE TABLE "+ TABLE_PAYS +"("+ COLUMN_ID_PAYS + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NOM_PAYS + " TEXT)")
        val CREATE_WILAYA_TABLE = ("CREATE TABLE "+ TABLE_WILAYA +"("+ COLUMN_ID_WILAYA + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NOM_WILAYA + " TEXT)")
        val CREATE_AGENCE_TABLE = ("CREATE TABLE "+ TABLE_AGENCE +"("+ COLUMN_ID_AGENCE + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_NOM_AGENCE + " TEXT," + COLUMN_ADRESSE_AGENCE + " TEXT)")
        val CREATE_DOCS_TABLE = ("CREATE TABLE "+ TABLE_DOCS +"("+ COLUMN_ID_DOCS + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NOM_DOCS + " TEXT)")
        val CREATE_DOCS_USER_TABLE=("CREATE TABLE "+ TABLE_DOCS_USER +"("+ COLUMN_ID_DOCS_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NOM_DOCS_USER + " TEXT," + COLUMN_ID_USER +" TEXT,"+ COLUMN_IMAGE_DOCS_USER + " BLOB,"+
                " FOREIGN KEY ("+ COLUMN_ID_USER +") REFERENCES "+ TABLE_USERS +"("+ COLUMN_ID +") )")
        val CREATE_DOCS_CHECK_TABLE = ("CREATE TABLE "+ TABLE_DOCS_CHECK +"("+ COLUMN_ID_DOCS_CHECK + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_NOM_DOCS_CHECK + " TEXT,"+ COLUMN_ID_USER +" TEXT,"+" FOREIGN KEY ("+ COLUMN_ID_USER +") REFERENCES "+ TABLE_USERS +"("+ COLUMN_ID +") )")
        db!!.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_VISA_TABLE)
        db.execSQL(CREATE_PAYS_TABLE)
        db.execSQL(CREATE_WILAYA_TABLE)
        db.execSQL(CREATE_AGENCE_TABLE)
        db.execSQL(CREATE_DOCS_TABLE)
        db.execSQL(CREATE_DOCS_USER_TABLE)
        db.execSQL(CREATE_DOCS_CHECK_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISA)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WILAYA)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENCE)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCS_USER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCS_CHECK)
        onCreate(db)
    }
    //Inserting (Creating) 1 doc checked
    fun addDocsChecked(nom:String,iduser:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_DOCS_CHECK, nom)
        values.put(COLUMN_ID_USER,iduser)
        db.insert(TABLE_DOCS_CHECK, null, values)
        db.close()
    }
    /**
     * This method is to delete docs user record
     *
     * @param id_docs_user & nom_docs_user
     */
    fun deleteDocChecked(nom:String,iduser:String) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_DOCS_CHECK, "$COLUMN_ID_USER = ?  AND $COLUMN_NOM_DOCS_CHECK = ? ",
            arrayOf(iduser,nom))
        db.close()
    }
    //Inserting (Creating) 1 pays
    fun addPays(nom:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_PAYS, nom)
        db.insert(TABLE_PAYS, null, values)
        db.close()
    }
    // Inserting all pays
fun addAllPays()
    {
        addPays("Afghanistan")
        addPays("Afrique du Sud")
        addPays("Albanie")
        addPays("Algérie")
        addPays("Allemagne")
        addPays("Andorre")
        addPays("Angola")
        addPays("Anguilla")
        addPays("Antarctique")
        addPays("Antigua-et-Barbuda")
        addPays("Antilles néerlandaises")
        addPays("Arabie saoudite")
        addPays("Argentine")
        addPays("Arménie")
        addPays("Aruba")
        addPays("Australie")
        addPays("Autriche")
        addPays("Azerbaïdjan")
        addPays("Bahamas")
        addPays("Bahreïn")
        addPays("Bangladesh")
        addPays("Barbade")
        addPays("Bélarus")
        addPays("Belgique")
        addPays("Belize")
        addPays("Bénin")
        addPays("Bermudes")
        addPays("Bhoutan")
        addPays("Bolivie")
        addPays("Bosnie-Herzégovine")
        addPays("Botswana")
        addPays("Brésil")
        addPays("Brunéi Darussalam")
        addPays("Bulgarie")
        addPays("Burkina Faso")
        addPays("Burundi")
        addPays("Cambodge")
        addPays("Cameroun")
        addPays("Canada")
        addPays("Cap-Vert")
        addPays("Ceuta et Melilla")
        addPays("Chili")
        addPays("Chine")
        addPays("Chypre")
        addPays("Colombie")
        addPays("Comores")
        addPays("Congo-Brazzaville")
        addPays("Corée du Nord")
        addPays("Corée du Sud")
        addPays("Costa Rica")
        addPays("Côte d’Ivoire")
        addPays("Croatie")
        addPays("Cuba")
        addPays("Danemark")
        addPays("Diego Garcia")
        addPays("Djibouti")
        addPays("Dominique")
        addPays("Égypte")
        addPays("El Salvador")
        addPays("Émirats arabes unis")
        addPays("Équateur")
        addPays("Érythrée")
        addPays("Espagne")
        addPays("Estonie")
        addPays("État de la Cité du Vatican")
        addPays("États fédérés de Micronésie")
        addPays("États-Unis")
        addPays("Éthiopie")
        addPays("Fidji")
        addPays("Finlande")
        addPays("France")
        addPays("Gabon")
        addPays("Gambie")
        addPays("Géorgie")
        addPays("Géorgie du Sud et les îles Sandwich du Sud")
        addPays("Ghana")
        addPays("Gibraltar")
        addPays("Grèce")
        addPays("Grenade")
        addPays("Groenland")
        addPays("Guadeloupe")
        addPays("Guam")
        addPays("Guatemala")
        addPays("Guernesey")
        addPays("Guinée")
        addPays("Guinée équatoriale")
        addPays("Guinée-Bissau")
        addPays("Guyana")
        addPays("Guyane française")
        addPays("Haïti")
        addPays("Honduras")
        addPays("Hongrie")
        addPays("Île Bouvet")
        addPays("Île Christmas")
        addPays("Île Clipperton")
        addPays("Île de l'Ascension")
        addPays("Île de Man")
        addPays("Île Norfolk")
        addPays("Îles Åland")
        addPays("Îles Caïmans")
        addPays("Îles Canaries")
        addPays("Îles Cocos - Keeling")
        addPays("Îles Cook")
        addPays("Îles Féroé")
        addPays("Îles Heard et MacDonald")
        addPays("Îles Malouines")
        addPays("Îles Mariannes du Nord")
        addPays("Îles Marshall")
        addPays("Îles Mineures Éloignées des États-Unis")
        addPays("Îles Salomon")
        addPays("Îles Turks et Caïques")
        addPays("Îles Vierges britanniques")
        addPays("Îles Vierges des États-Unis")
        addPays("Inde")
        addPays("Indonésie")
        addPays("Irak")
        addPays("Iran")
        addPays("Irlande")
        addPays("Islande")
        addPays("Israël")
        addPays("Italie")
        addPays("Jamaïque")
        addPays("Japon")
        addPays("Jersey")
        addPays("Jordanie")
        addPays("Kazakhstan")
        addPays("Kenya")
        addPays("Kirghizistan")
        addPays("Kiribati")
        addPays("Koweït")
        addPays("Laos")
        addPays("Lesotho")
        addPays("Lettonie")
        addPays("Liban")
        addPays("Libéria")
        addPays("Libye")
        addPays("Liechtenstein")
        addPays("Lituanie")
        addPays("Luxembourg")
        addPays("Macédoine")
        addPays("Madagascar")
        addPays("Malaisie")
        addPays("Malawi")
        addPays("Maldives")
        addPays("Mali")
        addPays("Malte")
        addPays("Maroc")
        addPays("Martinique")
        addPays("Maurice")
        addPays("Mauritanie")
        addPays("Mayotte")
        addPays("Mexique")
        addPays("Moldavie")
        addPays("Monaco")
        addPays("Mongolie")
        addPays("Monténégro")
        addPays("Montserrat")
        addPays("Mozambique")
        addPays("Myanmar")
        addPays("Namibie")
        addPays("Nauru")
        addPays("Népal")
        addPays("Nicaragua")
        addPays("Niger")
        addPays("Nigéria")
        addPays("Niue")
        addPays("Norvège")
        addPays("Nouvelle-Calédonie")
        addPays("Nouvelle-Zélande")
        addPays("Oman")
        addPays("Ouganda")
        addPays("Ouzbékistan")
        addPays("Pakistan")
        addPays("Palaos")
        addPays("Panama")
        addPays("Papouasie-Nouvelle-Guinée")
        addPays("Paraguay")
        addPays("Pays-Bas")
        addPays("Pérou")
        addPays( "Philippines")
        addPays("Pitcairn")
        addPays("Pologne")
        addPays("Polynésie française")
        addPays("Porto Rico")
        addPays("Portugal")
        addPays("Qatar")
        addPays("R.A.S. chinoise de Hong Kong")
        addPays("R.A.S. chinoise de Macao")
        addPays( "régions éloignées de l’Océanie")
        addPays("République centrafricaine")
        addPays("République démocratique du Congo")
        addPays( "République dominicaine")
        addPays("République tchèque")
        addPays("Réunion")
        addPays("Roumanie")
        addPays("Royaume-Uni")
        addPays("Russie")
        addPays("Rwanda")
        addPays("Sahara occidental")
        addPays("Saint-Barthélémy")
        addPays("Saint-Kitts-et-Nevis")
        addPays("Saint-Marin")
        addPays("Saint-Martin")
        addPays("Saint-Pierre-et-Miquelon")
        addPays("Saint-Vincent-et-les Grenadines")
        addPays("Sainte-Hélène")
        addPays("Sainte-Lucie")
        addPays("Samoa")
        addPays("Samoa américaines")
        addPays("Sao Tomé-et-Principe")
        addPays("Sénégal")
        addPays("Serbie")
        addPays("Serbie-et-Monténégro")
        addPays("Seychelles")
        addPays("Sierra Leone")
        addPays("Singapour")
        addPays("Slovaquie")
        addPays("Slovénie")
        addPays("Somalie")
        addPays("Soudan")
        addPays("Sri Lanka")
        addPays("Suède")
        addPays("Suisse")
        addPays("Suriname")
        addPays("Svalbard et Île Jan Mayen")
        addPays("Swaziland")
        addPays("Syrie")
        addPays("Tadjikistan")
        addPays("Taïwan")
        addPays("Tanzanie")
        addPays("Tchad")
        addPays("Terres australes françaises")
        addPays("Territoire britannique de l'océan Indien")
        addPays("Territoire palestinien")
        addPays("Thaïlande")
        addPays("Timor oriental")
        addPays("Togo")
        addPays("Tokelau")
        addPays("Tonga")
        addPays("Trinité-et-Tobago")
        addPays("Tristan da Cunha")
        addPays("Tunisie")
        addPays("Turkménistan")
        addPays("Turquie")
        addPays("Tuvalu")
        addPays("Ukraine")
        addPays("Union européenne")
        addPays("Uruguay")
        addPays("Vanuatu")
        addPays("Venezuela")
        addPays("Viêt Nam")
        addPays("Wallis-et-Futuna")
        addPays("Yémen")
        addPays("Zambie")
        addPays("Zimbabwe")

    }
    // Getting all pays
    fun getAllPays() : ArrayList<String>{
        var pays: ArrayList<String> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_PAYS"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                   var result = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_PAYS)).toString()
                   pays.add(result)
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return pays
    }
    //Inserting (Creating) 1 wilaya
    fun addWilaya(nom:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_WILAYA, nom)
        db.insert(TABLE_WILAYA, null, values)
        db.close()
    }
    // Inserting all wilaya
    fun addAllWilaya()
    {
        addWilaya("Adrar")
        addWilaya("Chlef")
        addWilaya("Laghouat")
        addWilaya("Oum El Bouaghi")
        addWilaya("Batna")
        addWilaya("Béjaïa")
        addWilaya("Biskra")
        addWilaya("Béchar")
        addWilaya("Blida")
        addWilaya("Bouira")
        addWilaya("Tamanrasset")
        addWilaya("Tébessa")
        addWilaya("Tlemcen")
        addWilaya("Tiaret")
        addWilaya("Tizi Ouzou")
        addWilaya("Alger")
        addWilaya("Djelfa")
        addWilaya("Jijel")
        addWilaya("Sétif")
        addWilaya("Saïda")
        addWilaya("Skikda")
        addWilaya("Sidi Bel Abbès")
        addWilaya("Annaba")
        addWilaya("Guelma")
        addWilaya("Constantine")
        addWilaya("Médéa")
        addWilaya("Mostaganem")
        addWilaya("M'Sila")
        addWilaya( "Mascara")
        addWilaya("Ouargla")
        addWilaya("Oran")
        addWilaya("El Bayadh")
        addWilaya("Illizi")
        addWilaya("Bordj Bou Arreridj")
        addWilaya("Boumerdès")
        addWilaya("El Tarf")
        addWilaya("Tindouf")
        addWilaya( "Tissemsilt")
        addWilaya( "El Oued")
        addWilaya( "Khenchela")
        addWilaya("Souk Ahras")
        addWilaya("Tipaza")
        addWilaya("Mila")
        addWilaya("Aïn Defla")
        addWilaya("Naama")
        addWilaya("Aïn Témouchent")
        addWilaya("Ghardaïa")
        addWilaya("Relizane")


    }
    // Getting all wilaya
    fun getAllWilaya() : ArrayList<String>{
        var wilayas: ArrayList<String> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_WILAYA"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var result = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_WILAYA)).toString()
                    wilayas.add(result)
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return wilayas
    }
    //Inserting (Creating) 1 agence
    fun addagence(nom:String,adresse:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_AGENCE, nom)
        values.put(COLUMN_ADRESSE_AGENCE, adresse)
        db.insert(TABLE_AGENCE, null, values)
        db.close()
    }
    //Inserting all agence
    fun addAllAgence()
    {
        addagence("MARKETEL Alger","3,rue Ahmed Ouaked Dely Brahim")
        addagence("MARKETEL Oran","72, rue Djellat El Habib Cité Es Seddikia")
        addagence("TLS Alger","TLScontact Visa Application Center Alger, Alger")
        addagence("TLS Oran","TLScontact Visa Application Center Oran")
        addagence("TLS ANNABA ","TLScontact Visa Application Center Annaba")
        addagence("Vfs Alger","VFS GLOBAL FRANCE, Oued Smar")
        addagence("BLS Alger","Bls Espagne, Rue Ahmed El mansour Ben Cara, El Biar, Alger")
        addagence("Ambassade d'Allemagne","Ambassade d’Allemagne")
        addagence("Ambassade d'Argentine","Ambassade Argentine, Chemin Al-Bakri, El Biar")
        addagence("Ambassade d'Autriche","Ambassade d'Autriche, Hydra")
        addagence("Ambassade de Belgique","Ambassade de Belgique, Chemin Tayebi Youcef, Alger")
        addagence("Ambassade du Brésil","Ambassade du Brésil, Chemin Cheikh Bachir El Ibrahim, El Biar")
        addagence("Ambassade de Bulgarie","ambassade de bulgarie")
        addagence("Ambassade du Canada","ambassade canada algerie")
        addagence("Ambassade de Chine","Ambassade de Chine")
        addagence("China Visa Application Centre Alger","chinese visa application service centre à proximité de Alger, Sidi M'Hamed")
        addagence("Ambassade de Croatie","Ambassade de Croatie, Rue Hadj Ahmed Mohamed, Hydra")
        addagence("Ambassade d'Equateur","Embajada del Ecuador en Argelia")
        addagence("Ambassade d'Espagne","Ambassade d'Espagne")
        addagence("Ambassade des États-Unis ","Ambassade des États Unis, Chemin Cheikh Bachir El Ibrahim, El Biar")
        addagence("Ambassade de la Finlande","Ambassade de la finlande, Rue des Cèdres, El Mouradia")
        addagence("Ambassade de France","Ambassade de France / سفارة فرنسا, Chemin Abdelkader Gadouche, Hydra")
        addagence("Ambassade de Grèce","Ambassade De Grece، Boulevard Colonel Bougara, El Biar")
        addagence("Ambassade d'Hongrie","Ambassade d'Hongrie, Rue des Frere Oukil Elhadj, El Mouradia")
        addagence("Ambassade d'Italie","Ambassade d'Italie, Rue Mohamed Amellal Ouidir, El Biar")
        addagence("Ambassade du Japon","Ambassade du Japon, Chemin Al-Bakri, El Biar")
        addagence("Ambassade de la République de Corée","Ambassade de la République de Corée")
        addagence("Ambassade de la Norvège","Ambassade de la Norvège, Rue Doudoud Mokhtar, Ben Aknoun")
        addagence("Ambassade de Pologne","Ambassade de Pologne, Olof Palme, Nouveau-Paradou، Hydra")
        addagence("Ambassade du Portugal","Ambassade du Portugal, Boulevard du 11 Decembre 1960, El Biar")
        addagence("Ambassade du Danemark","07, Chemin Doudou Mokhtar, Ben Aknoun, Alger, Algérie")
        addagence("Ambassade du Royaume-Uni","Ambassade du Royaume-Uni en Algérie ")
        addagence("Ambassade de Russie","7, Chemin du Prince d’Annam, El-Biar, Alger, Algérie")
        addagence("Ambassade de Serbie","Ambassade de Serbie, Rue des Frères BENALI ABDELLAH, Hydra")
        addagence("Ambassade de Suède","Ambassade de Suède, Olof Palme Street، New Paradou")
        addagence("Ambassade de Suisse","Ambassade de Suisse, El Mouradia")

    }
    // Getting all agence
    fun getAllAgence() : ArrayList<Agence>{
        var agences: ArrayList<Agence> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_AGENCE"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_AGENCE)).toString()
                    var adresse= cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE_AGENCE)).toString()
                    agences.add(Agence(nom, adresse))
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return agences
    }
    fun getAgenceInfo(nom:String) : Agence {
        val db = readableDatabase
        var agences: Agence = Agence("", "")
        val selectionArgs= arrayOf(nom)
        val selectQuery = "SELECT * FROM $TABLE_AGENCE WHERE $COLUMN_NOM_AGENCE"+ "= ? "
        val cursor = db.rawQuery(selectQuery, selectionArgs)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_AGENCE)).toString()
                    var adresse= cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE_AGENCE)).toString()
                    agences= Agence(nom, adresse)
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return agences
    }
    // Getting all agence Name
    fun getAllAgenceName() : ArrayList<String>{
        var agences: ArrayList<String> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_AGENCE"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_AGENCE)).toString()
                    agences.add(nom)
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return agences
    }
    //Inserting (Creating) 1 doc
    fun addDocs(nom:String){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_DOCS, nom)
        db.insert(TABLE_DOCS, null, values)
        db.close()
    }
    // Inserting all docs
    fun addAllDocs()
    {
        addDocs("Un passeport valide au moins 6 mois après le dépôt de la demande de visa. Il doit contenir au moins deux pages vides")
        addDocs("Attestation de travail ou certificat de scolarité")
        addDocs("Titre de congé")
        addDocs("3 dernières fiches de paie")
        addDocs("Affiliation CNAS")
        addDocs("Une photo d’identité biométrique en couleur")
        addDocs("Attestation d’accueil ou réservation d’hôtel")
        addDocs("Prise en charge des frais par l’entité invitante")
        addDocs("Relevé bancaire des 6 derniers mois")
        addDocs("Autorisation paternelle pour mineur")
        addDocs("Formulaire C20")
        addDocs("Extrait de rôle")
        addDocs("Justificatif d’inscription au registre de commerce")
        addDocs("Carte professionnelle")
        addDocs("Attestation d’exploitant agricole")
        addDocs("Carte d’agriculteur")
        addDocs("Preuve de moyens suffisants pour couvrir les frais d’hébergement")
        addDocs("Attestation de perception d’une pension")
        addDocs("Invitation de la société/institution si visa affaire")

    }
    // Getting all docs
    fun getAllDocs() : ArrayList<String>{
        var pays: ArrayList<String> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_DOCS"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var result = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_DOCS)).toString()
                    pays.add(result)
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return pays
    }

    //Inserting (Creating) 1 Doc
    fun addDoc(document: Doc){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USER, document.id_user)
        values.put(COLUMN_NOM_DOCS_USER, document.nom)
        values.put(COLUMN_IMAGE_DOCS_USER, document.image)
        db.insert(TABLE_DOCS_USER, null, values)
        db.close()
    }
    // Getting all docs of user
    fun getAllDocsOfUser(iduser:String) : ArrayList<Doc>{
        var documents: ArrayList<Doc> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_DOCS_USER WHERE $COLUMN_ID_USER"+ " = ? "
        var selectionArgs= arrayOf(iduser)
        val cursor = db.rawQuery(selectALLQuery, selectionArgs)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    var id_users = cursor.getString(cursor.getColumnIndex(COLUMN_ID_USER)).toString()
                    var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_DOCS_USER)).toString()
                    var image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE_DOCS_USER))
                    documents.add(Doc(nom, id_users, image))
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return documents
    }
    // Getting if of docs user
    fun getIdDocsOfUser(iduser:String,nom:String) : String{
        var id_docs=""
        val db = readableDatabase
        val selectALLQuery = "SELECT $COLUMN_ID_DOCS_USER FROM $TABLE_DOCS_USER WHERE $COLUMN_ID_USER"+ " = ? AND  $COLUMN_NOM_DOCS_USER" + " = ? "
        var selectionArgs= arrayOf(iduser,nom)
        val cursor = db.rawQuery(selectALLQuery, selectionArgs)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                       id_docs = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_DOCS_USER)).toString()
                    cursor.moveToNext()
                }
            }
        }
        cursor.close()
        db.close()
        return id_docs
    }
    /**
     * This method to update docs user record
     *
     * @param id_docs
     */
    fun updateDocofUser(id:String,nom:String,image:ByteArray) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM_DOCS_USER, nom)
        values.put(COLUMN_IMAGE_DOCS_USER, image)
        // updating row
        db.update(
            TABLE_DOCS_USER, values, "$COLUMN_ID_DOCS_USER = ? ",
            arrayOf(id))
        Log.i("successs","update successful")
        db.close()
    }
    /**
     * This method is to delete docs user record
     *
     * @param id_docs_user & nom_docs_user
     */
    fun deleteDocofUser(id:String,nom:String) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_DOCS_USER, "$COLUMN_ID_USER = ?  AND $COLUMN_NOM_DOCS_USER = ?",
            arrayOf(id,nom))
        db.close()
    }
    //Inserting (Creating) User
    fun addUser(User: user): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, User.id)
        values.put(COLUMN_MDP, User.mdp)
        values.put(COLUMN_NOM, User.nom)
        values.put(COLUMN_PRENOM, User.prenom)
        values.put(COLUMN_ADRESSE, User.adresse)
        values.put(COLUMN_NUM, User.numero)
        values.put(COLUMN_DESTINATION, User.destination)
        values.put(COLUMN_RESIDENCE, User.residence)
        values.put(COLUMN_AGENCE, User.agence)
        val _success = db.insert(TABLE_USERS, null, values)
        db.close()
        Log.i("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    //get all users
    fun getAllUsers():ArrayList<user> {
        var users: ArrayList<user> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_USERS"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                    var mdp = cursor.getString(cursor.getColumnIndex(COLUMN_MDP))
                    var nom= cursor.getString(cursor.getColumnIndex(COLUMN_NOM))
                    var prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM))
                    var adresse = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE))
                    var numero = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM))
                    var destination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                    var residence= cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE))
                    var agence = cursor.getString(cursor.getColumnIndex(COLUMN_AGENCE))

                  users.add(
                      user(
                          id,
                          mdp,
                          nom,
                          prenom,
                          adresse,
                          numero,
                          destination,
                          residence,
                          agence
                      )
                  )

                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return users
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    fun updateUser(identifiant : String, mdp : String) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_ID, identifiant)
        values.put(COLUMN_MDP, mdp)

        // updating row
        db.update(
            TABLE_USERS, values, "$COLUMN_ID = ?",
            arrayOf(identifiant))
        db.close()
    }
    fun updateUser(identifiant : String,nom:String,prenom:String,numero:Int,adresse:String,residence:String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOM, nom)
        values.put(COLUMN_PRENOM, prenom)
        values.put(COLUMN_NUM, numero)
        values.put(COLUMN_ADRESSE, adresse)
        values.put(COLUMN_RESIDENCE, residence)
        // updating row
        db.update(
            TABLE_USERS, values, "$COLUMN_ID = ?",
            arrayOf(identifiant))
        db.close()
    }
    /**
     * This method is to delete user record
     *
     * @param user
     */
    fun deleteUser(User: user) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_USERS, "$COLUMN_ID = ?",
            arrayOf(User.id))
        db.close()
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(id: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_ID = ?"

        // selection argument
        val selectionArgs = arrayOf(id)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_USERS, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }

    /**
     * This method to check user exist or not
     *
     * @param id
     * @param password
     * @return true/false
     */
    fun checkUser(id: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(COLUMN_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COLUMN_ID = ? AND $COLUMN_MDP = ?"

        // selection arguments
        val selectionArgs = arrayOf(id, password)
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(
            TABLE_USERS, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }
    fun getName(identifiant:String) : user {
        var utilisateur= user(identifiant)
        // array of columns to fetch
        val db = this.readableDatabase

        // selection argument
        val selectionArgs = arrayOf(identifiant)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "+ COLUMN_ID + " = ? "
        val cursor = db.rawQuery(selectQuery, selectionArgs)
       /* val cursor = db.query(TABLE_USERS, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order*/

        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                var nom= cursor.getString(cursor.getColumnIndex(COLUMN_NOM))
                var prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM))
                var id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var mdp = cursor.getString(cursor.getColumnIndex(COLUMN_MDP))
                var adresse = cursor.getString(cursor.getColumnIndex(COLUMN_ADRESSE))
                var numero = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM))
                var destination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                var residence= cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE))
                var agence = cursor.getString(cursor.getColumnIndex(COLUMN_AGENCE))
                utilisateur.id= id
                utilisateur.mdp=mdp
                utilisateur.nom=nom
                utilisateur.prenom=prenom
                utilisateur.adresse= adresse
                utilisateur.numero= numero
                utilisateur.destination=destination
                utilisateur.residence=residence
                utilisateur.agence=agence
            }
        }
        cursor.close()
        db.close()
        return utilisateur

    }
    //Inserting (Creating) demande visa
    fun addVisa (rdv: Rdvs): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USER, rdv.id_user)
        values.put(COLUMN_AGENCE, rdv.centre)
        values.put(COLUMN_DESTINATION, rdv.destination)
        values.put(COLUMN_RESIDENCE, rdv.residence)
        values.put(COLUMN_NOM, rdv.nom)
        values.put(COLUMN_PRENOM, rdv.prenom)
        values.put(COLUMN_DATE, rdv.date)
        values.put(COLUMN_HEURE,rdv.heure)
        val _success = db.insert(TABLE_VISA, null, values)
        db.close()
        Log.i("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }
    //get all visas
    fun getAllvisa(identifiant: String): ArrayList<Rdvs> {
        var visas: ArrayList<Rdvs> = ArrayList()
        val db = readableDatabase
        val selectionArgs = arrayOf(identifiant)
        val selectALLQuery = "SELECT * FROM "+ TABLE_VISA + " WHERE "+ COLUMN_ID_USER +" = ? "
        val cursor = db.rawQuery(selectALLQuery, selectionArgs)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                    var centre = cursor.getString(cursor.getColumnIndex(COLUMN_AGENCE))
                    var residence= cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE))
                    var destination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                    var id_user = cursor.getString(cursor.getColumnIndex(COLUMN_ID_USER))
                    var date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                    var heure = cursor.getString(cursor.getColumnIndex(COLUMN_HEURE))
                    var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM))
                    var prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM))

                    visas.add(
                        Rdvs(
                            id.toString(),
                            id_user,
                            centre,
                            destination,
                            residence,
                            nom,
                            prenom,
                            heure,
                            date
                        )
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return visas
    }
    fun getIdVisa(id_user:String,nom:String,prenom:String,depart:String,destination:String,agence:String,heure:String,date:String) : String {
        var id=""
        //var rdv=Rdvs(id_user,nom,prenom,agence,destination,depart,heure,date)
        // array of columns to fetch
        val db = this.readableDatabase

        // selection criteria
        val selection = "SELECT $COLUMN_ID FROM $TABLE_VISA WHERE $COLUMN_ID_USER = ?  AND $COLUMN_NOM = ?  AND $COLUMN_PRENOM = ?  AND $COLUMN_AGENCE = ?  AND $COLUMN_RESIDENCE = ?  AND $COLUMN_DESTINATION = ?  AND $COLUMN_DATE = ? AND $COLUMN_HEURE = ? "

        // selection argument
        val selectionArgs = arrayOf(id_user,nom,prenom,agence,depart,destination,date,heure)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor =db.rawQuery(selection, selectionArgs)


        if (cursor!=null) {
            if (cursor.moveToFirst()) {
               id= cursor.getString(cursor.getColumnIndex(COLUMN_ID))
            }
        }
        cursor.close()
        db.close()
        return id

    }
    fun getVisa(id:String) : Rdvs {
        var rendezvous = Rdvs("", "", "", "", "", "", "", "", "")
        //var rdv=Rdvs(id_user,nom,prenom,agence,destination,depart,heure,date)
        // array of columns to fetch
        val db = this.readableDatabase

        // selection criteria
        val selection = "SELECT * FROM $TABLE_VISA WHERE $COLUMN_ID = ?"


        // selection argument
        val selectionArgs = arrayOf(id)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor =db.rawQuery(selection, selectionArgs)


        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                var id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                var centre = cursor.getString(cursor.getColumnIndex(COLUMN_AGENCE))
                var residence= cursor.getString(cursor.getColumnIndex(COLUMN_RESIDENCE))
                var destination = cursor.getString(cursor.getColumnIndex(COLUMN_DESTINATION))
                var id_user = cursor.getString(cursor.getColumnIndex(COLUMN_ID_USER))
                var date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE))
                var heure = cursor.getString(cursor.getColumnIndex(COLUMN_HEURE))
                var nom = cursor.getString(cursor.getColumnIndex(COLUMN_NOM))
                var prenom = cursor.getString(cursor.getColumnIndex(COLUMN_PRENOM))
                rendezvous= Rdvs(
                    id.toString(),
                    id_user,
                    centre,
                    destination,
                    residence,
                    nom,
                    prenom,
                    heure,
                    date
                )
            }
        }
        cursor.close()
        db.close()
        return rendezvous

    }
    /**
     * This method to update visa record
     *
     * @param rdv
     */
    fun updateVisa(rdv : Rdvs) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID_USER, rdv.id_user)
        values.put(COLUMN_AGENCE, rdv.centre)
        values.put(COLUMN_NOM,rdv.nom)
        values.put(COLUMN_PRENOM,rdv.prenom)
        values.put(COLUMN_DESTINATION, rdv.destination)
        values.put(COLUMN_RESIDENCE, rdv.residence)
        values.put(COLUMN_DATE, rdv.date)
        values.put(COLUMN_HEURE,rdv.heure)
        // updating row
        db.update(
            TABLE_VISA, values, "$COLUMN_ID = ?",
            arrayOf(rdv.id))
        Log.i("successs","update successful")
        db.close()
    }
    /**
     * This method is to delete rdv record
     *
     * @param rdv
     */
    fun deleteVISA(id:String) {

        val db = this.writableDatabase
        // delete user record by id
        db.delete(
            TABLE_VISA, "$COLUMN_ID = ?",
            arrayOf(id))
        db.close()
    }

}