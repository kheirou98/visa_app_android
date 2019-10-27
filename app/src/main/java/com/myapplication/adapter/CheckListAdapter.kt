package com.myapplication.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import android.widget.Toast
import com.myapplication.R
import com.myapplication.model.SharedPreference
import com.myapplication.helper.database

class CheckListAdapter(context: Context,var DocsName: ArrayList<String>) : RecyclerView.Adapter<CheckListAdapter.MyViewHolder>() {
    var mContext = context
    var sharedPreference= SharedPreference(mContext)
    var DB= database(mContext)
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var simpleCheckedTextView: CheckedTextView
        init {
            simpleCheckedTextView = view.findViewById<View>(R.id.simpleCheckedTextView) as CheckedTextView
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.checklist_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.simpleCheckedTextView.text = DocsName[position]
        if (sharedPreference.getValueString(DocsName.get(position))=="true")
        {
            holder.simpleCheckedTextView.isChecked=true
            holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_black_24dp)
        }
        else {
            holder.simpleCheckedTextView.isChecked=false
            holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_close_black_24dp)
        }
        // perform on Click Event Listener on CheckedTextView
        holder.simpleCheckedTextView.setOnClickListener {
            val value = holder.simpleCheckedTextView.isChecked
            if (value) {
                // set check mark drawable and set checked property to false
                holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_close_black_24dp)
                holder.simpleCheckedTextView.isChecked = false
                sharedPreference.save(holder.simpleCheckedTextView.text.toString(),"false")
                DB.deleteDocChecked(holder.simpleCheckedTextView.text.toString(),sharedPreference.getValueString("id_user_visa")!!)
                Toast.makeText(mContext, "un-Checked", Toast.LENGTH_SHORT).show()
            } else {
                // set check mark drawable and set checked property to true
                holder.simpleCheckedTextView.setCheckMarkDrawable(R.drawable.ic_check_black_24dp)
                holder.simpleCheckedTextView.isChecked = true
                sharedPreference.save(holder.simpleCheckedTextView.text.toString(),"true")
                DB.addDocsChecked(holder.simpleCheckedTextView.text.toString(),sharedPreference.getValueString("id_user_visa")!!)
                Toast.makeText(mContext, "Checked", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun getItemCount(): Int {
        return DocsName.size
    }


}
