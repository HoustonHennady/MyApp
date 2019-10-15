package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myappktx.R
import kotlinx.android.synthetic.main.item_recycler_secondcategory_category.view.*

class OrderAdapter : BaseAdapter<String>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_secondcategory_category,parent,false))
    }
    class ViewHolder(itemView: View): BaseViewHolder<String>(itemView = itemView) {
        override fun bind(model:String) {

        }
    }
}