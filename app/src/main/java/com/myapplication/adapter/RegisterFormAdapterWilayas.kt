package com.myapplication.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import android.widget.Toast
import com.myapplication.R

class RegisterFormAdapterWilayas(context: Context, var DocsName: ArrayList<String>, var listes : ArrayList<String>, view : View) : RecyclerView.Adapter<RegisterFormAdapterWilayas.MyViewHolder>() {

    var mContext = context
    var textview = view.findViewById<TextView>(R.id.textViewwilaya)
    var searchview = view.findViewById<android.support.v7.widget.SearchView>(R.id.searchViewwilaya)
    var recyclerview = view.findViewById<RecyclerView>(R.id.recyclerviewwilaya)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.spinner_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.TextView.text = DocsName[position]
        holder.setOnCostomItemClickListener(object : CostomItemClickListener {
            override fun onCostomItemClickListener(view: View, pos: Int) {
                searchview.visibility= View.INVISIBLE
                recyclerview.visibility= View.INVISIBLE
                textview.setText(DocsName[position])
                textview.visibility= View.VISIBLE
                Toast.makeText(mContext,DocsName[position], Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun getItemCount(): Int {
        return DocsName.size
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    DocsName = listes
                } else {
                    val listefiltree = ArrayList<String>()
                    for (row in listes) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
                            listefiltree.add(row)
                        }
                    }

                    DocsName = listefiltree
                }

                val filterResults = FilterResults()
                filterResults.values = DocsName
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                DocsName= filterResults.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        var TextView: TextView
        var costomItemClickListener: CostomItemClickListener?=null

        init {
            TextView = view.findViewById<View>(R.id.txtspinner) as TextView
            view.setOnClickListener(this)

        }
        fun setOnCostomItemClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(p0!!,adapterPosition)
        }



    }
}