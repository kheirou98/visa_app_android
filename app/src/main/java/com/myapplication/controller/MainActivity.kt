package com.myapplication.controller

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.info.view.*
import android.content.pm.PackageManager
import com.myapplication.*
import com.myapplication.helper.NewMessageNotification
import com.myapplication.helper.database
import com.myapplication.model.Rdvs
import com.myapplication.model.SharedPreference
import com.myapplication.model.menu
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var adapter: menuAdapter? = null
    var menuList = ArrayList<menu>()
    var boolean=false
    var np =""
    var id=""
    var idents=""
    var num:Int? = null
    var destination = ""
    var noms=""
    var prenoms=""
    var agence=""
    var residence=""
    var adresse =""
    var idenvisa=""
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    var now = Date()
    var idnotif = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val strTemp = intent.getStringExtra("nomprenom")
        val bool = intent.getStringExtra("bool")
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val view =navView.getHeaderView(0)
        val nompre=view.findViewById<TextView>(R.id.nompre)
        val iden=view.findViewById<TextView>(R.id.iden)
        val telephone=view.findViewById<TextView>(R.id.telephone)
        if (bool=="vrai"){
            boolean=true
            np=intent.getStringExtra("np")
            id=intent.getStringExtra("id")
            idents=id
            idenvisa=id
            num=intent.getIntExtra("num",0)
            destination=intent.getStringExtra("destination")
            adresse= intent.getStringExtra("adresse")
            noms=intent.getStringExtra("nom")
            prenoms=intent.getStringExtra("prenom")
            residence=intent.getStringExtra("residence")
            nomprenom.setText(strTemp)
            nompre.text=np
            iden.text=id
            telephone.text="0"+num.toString()
        }
        var DB= database(this)
        var sharedPreference= SharedPreference(this)
        //Notification code
        sharedPreference.save("nom_visa", noms)
        sharedPreference.save("prenom_visa", prenoms)
        sharedPreference.save("id_user_visa", id)
        var rdvs: java.util.ArrayList<Rdvs> =DB.getAllvisa(id)


        var ntf = NewMessageNotification
        for (i in 0..rdvs.size-1){
            var daterdv=""
            var heurerdv=""
            daterdv=rdvs.get(i).date
            heurerdv=rdvs.get(i).heure
            var datecompleterdv="$daterdv $heurerdv:00"
            var d1 = sdf.parse(sdf.format(now))
            var d2=sdf.parse(datecompleterdv)
            val diff = d2.time - d1.time
            val diffSeconds = diff / 1000 % 60
            val diffMinutes = diff / (60 * 1000) % 60
            val diffHours = diff / (60 * 60 * 1000) % 24
            val diffDays = diff / (24 * 60 * 60 * 1000)
            var difference= (diffDays).toString() + " jours, " + diffHours + " heures, " + diffMinutes + " minutes, " +
                    diffSeconds + " secondes."
            if(diffDays>0 || diffHours>0 || diffMinutes>0 || diffSeconds>0){
                idnotif++
                NewMessageNotification.notify(this, datecompleterdv, difference, idnotif)

            }
        }
        // load menu items
        menuList.add(menu("3313", R.drawable.tel, "#FC3D41", ""))
        menuList.add(
            menu(
                "Mon compte",
                R.drawable.ic_folder_open_black_24dp,
                "#993333",
                idnotif.toString()
            )
        )
        menuList.add(menu("Service de VTC", R.drawable.car, "#BB4398", ""))
        menuList.add(menu("Numériser les documents", R.drawable.num, "#9D1BE0", ""))
        adapter = menuAdapter(this, menuList)
        grid.adapter = adapter
        grid.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Get the GridView selected/clicked item text
                val texte=view.findViewById<View>(R.id.compte_notification)as TextView
                if (texte.text.toString()=="0"){
                    texte.visibility=View.INVISIBLE
                }
                else {
                texte.visibility=View.VISIBLE}
                val txt = view.findViewById<View>(R.id.textView3) as TextView
                // Display the selected/clicked item text and position on TextView
                if (txt.text.toString()=="3313") {
                    texte.visibility=View.INVISIBLE
                    val intent_phone = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txt.text.toString()))
                    startActivity(intent_phone)
                }
                if (txt.text.toString()=="Mon compte") {
                    val intent_account = Intent(this@MainActivity, Compte::class.java)
                    intent_account.putExtra("id",idenvisa)
                    intent_account.putExtra("nom",noms)
                    intent_account.putExtra("prenom",prenoms)
                    intent_account.putExtra("notification",idnotif.toString())
                    texte.visibility=View.INVISIBLE
                    startActivity(intent_account)

                }
                if (txt.text.toString()=="Service de VTC") {
                    texte.visibility=View.INVISIBLE
                   var isAppInstalled=appInstalledOrNot("com.classco.temtem")
                    if (isAppInstalled) {
                        //l'ouverture de l'application temtem si elle est installée
                        val LaunchIntent = packageManager
                            .getLaunchIntentForPackage("com.classco.temtem")
                        startActivity(LaunchIntent)
                    } else {
                        // Ouvrir le site en cas ou l'application temtem n'est pas installée
                        val intent_vtc = Intent(Intent.ACTION_VIEW, Uri.parse("https://temtem.yusofleet.com/new_front#/"))
                        startActivity(intent_vtc)
                    }

                }
                if (txt.text.toString()=="Numériser les documents") {
                    texte.visibility=View.INVISIBLE
                    val intent_docs = Intent(this@MainActivity, Documents::class.java)
                    intent_docs.putExtra("id",idenvisa)
                    startActivity(intent_docs)
                }
            }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navmenu.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        navView.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.profil -> {
                if (boolean==true) {
                    var intent = Intent(this@MainActivity, profil::class.java)
                    intent.putExtra("np",np)
                    intent.putExtra("id",id)
                    intent.putExtra("num",num)
                    intent.putExtra("residence",residence)
                    intent.putExtra("adresse",adresse)
                    intent.putExtra("nom",noms)
                    intent.putExtra("prenom",prenoms)
                    startActivity(intent)
                }
            }
            R.id.deconnexion -> {
                val intent = Intent(this@MainActivity, accueil::class.java)
                startActivity(intent)
                this@MainActivity.finish()
            }
            R.id.contact -> {
              val manager = supportFragmentManager.beginTransaction()
              val popupclass = Contact()
              popupclass.show(manager, null)
            }
            R.id.about -> {
                val intent = Intent(this@MainActivity, About::class.java)
                startActivity(intent)
            }
            R.id.quit -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Etes vous sur de quitter l'application?")
                    .setIcon(R.drawable.ic_close_black_24dp).setTitle("Quitter l'application").setPositiveButton(
                        "Oui"
                    ) { _, _ ->
                        finishAffinity()
                        Toast.makeText(this@MainActivity, "Merci d'avoir utilisé l'Application", Toast.LENGTH_LONG).show()}.setNegativeButton(
                        "Non"
                    ) { _, _-> }.show()

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

     fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        var app_installed:Boolean
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            app_installed = true
        } catch (e: PackageManager.NameNotFoundException) {
            app_installed = false
        }

        return app_installed
    }
    class menuAdapter : BaseAdapter {
        var menuList = ArrayList<menu>()
        var context: Context? = null

        constructor(context: Context, menuList: ArrayList<menu>) : super() {
            this.context = context
            this.menuList = menuList
        }

        override fun getCount(): Int {
            return menuList.size
        }

        override fun getItem(position: Int): Any {
            return menuList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val menu= this.menuList[position]
            var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var menuView = inflator.inflate(R.layout.info, null)
            menuView.imageView2.setImageResource(menu.image!!)
            menuView.textView3.text = menu.name!!
                if (position==0 || position==2 || position==3) {
                    menuView.compte_notification.visibility=View.INVISIBLE
                }
            if (position==1) {
                menuView.compte_notification.text = menu.notif!!
                if (menuView.compte_notification.text.toString() == "0") {
                    menuView.compte_notification.visibility = View.INVISIBLE
                }
            }
            menuView.setBackgroundColor(Color.parseColor(menu.color!!))
            return menuView
        }
    }
}
