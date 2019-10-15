package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myappktx.Model.MainCategory
import com.example.myappktx.R
import kotlinx.android.synthetic.main.item_recycler.view.*


class MainCategoryAdapter: BaseAdapter<MainCategory>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MainCategory> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false))
    }

    class ViewHolder(itemVIew: View): BaseViewHolder<MainCategory>(itemView = itemVIew) {
        private var textView: TextView = itemView.text_on_recycler
        override fun bind(model: MainCategory) {
            textView.text = model.name
        }
    }
}