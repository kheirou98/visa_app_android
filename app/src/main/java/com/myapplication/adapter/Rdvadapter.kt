package com.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.myapplication.R
import com.myapplication.controller.Rdv_detail
import com.myapplication.model.Rdvs

class Rdvadapter (var rdvs: ArrayList<Rdvs>, context: Context) : RecyclerView.Adapter<Rdvadapter.ViewHolder>() {
    var mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rdv_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=rdvs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rdv_nomprenom.text=rdvs.get(position).nom.toUpperCase()+" "+rdvs.get(position).prenom
        holder.rdv_depart.text=rdvs.get(position).residence
        holder.rdv_destination.text=rdvs.get(position).destination
        holder.rdv_agence.text=rdvs.get(position).centre
        holder.rdv_heure.text=rdvs.get(position).heure
        holder.rdv_date.text=rdvs.get(position).date
        holder.setOnCostomItemClickListener(object : CostomItemClickListener {
            override fun onCostomItemClickListener(view: View, pos: Int) {
                val intent= Intent(mContext, Rdv_detail::class.java)
                intent.putExtra("nom",rdvs.get(position).nom)
                intent.putExtra("prenom",rdvs.get(position).prenom)
                intent.putExtra("depart",holder.rdv_depart.text)
                intent.putExtra("destination",holder.rdv_destination.text)
                intent.putExtra("agence",holder.rdv_agence.text)
                intent.putExtra("date",holder.rdv_date.text)
                intent.putExtra("heure",holder.rdv_heure.text)
                mContext.startActivity(intent)

            }
        })
    }
    class ViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) , View.OnClickListener{
        val rdv_nomprenom : TextView
        val rdv_depart:TextView
        val rdv_destination : TextView
        val rdv_agence:TextView
        val rdv_heure:TextView
        val rdv_date :TextView
        var costomItemClickListener: CostomItemClickListener?=null
        init {
            rdv_nomprenom=itemview.findViewById(R.id.rdv_nomprenom)
            rdv_depart=itemview.findViewById(R.id.rdv_depart)
            rdv_destination=itemview.findViewById(R.id.rdv_destination)
            rdv_agence=itemview.findViewById(R.id.rdv_agence)
            rdv_heure=itemview.findViewById(R.id.rdv_heure)
            rdv_date= itemview.findViewById(R.id.rdv_date)
            itemview.setOnClickListener(this)
        }
        fun setOnCostomItemClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(p0!!,adapterPosition)
        }

    }

}