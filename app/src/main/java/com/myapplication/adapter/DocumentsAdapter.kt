package com.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.myapplication.controller.Documents_Detail
import com.myapplication.R
import com.myapplication.model.Doc


class DocumentsAdapter(context: Context, var DocsName: ArrayList<Doc>) : RecyclerView.Adapter<DocumentsAdapter.MyViewHolder>() {

    var mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.documents_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.TextView.text = DocsName.get(position).nom!!.toString()
        holder.image.setImageBitmap(getPhoto(DocsName.get(position).image!!))

        holder.setOnCostomItemClickListener(object : CostomItemClickListener {
            override fun onCostomItemClickListener(view: View, pos: Int) {
                val intentdocs= Intent(mContext, Documents_Detail::class.java)
                intentdocs.putExtra("Docnom",DocsName.get(position).nom)
                intentdocs.putExtra("Docimage",DocsName.get(position).image)
                mContext.startActivity(intentdocs
                )
            }
        })
    }


    override fun getItemCount(): Int {
        return DocsName.size
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        var TextView: TextView
        var image:ImageView
        var costomItemClickListener: CostomItemClickListener?=null

        init {
            TextView = view.findViewById(R.id.textView3)
            image= view.findViewById(R.id.imageView2)
            view.setOnClickListener(this)

        }
        fun setOnCostomItemClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(p0!!,adapterPosition)
        }

    }

    // convert from byte array to bitmap
    fun getPhoto(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }
}