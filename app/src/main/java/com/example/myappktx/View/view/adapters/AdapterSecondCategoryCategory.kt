package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myappktx.Model.SubCategoryModel
import com.example.myappktx.R
import kotlinx.android.synthetic.main.item_recycler_secondcategory_category.view.*

class AdapterSecondCategoryCategory: BaseAdapter<SubCategoryModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SubCategoryModel> {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_secondcategory_category, parent, false)
        )
    }
    class ViewHolder(itemVIew: View): BaseViewHolder<SubCategoryModel>(itemView = itemVIew) {
        private var textView: TextView = itemView.text_secondcategory_category
        override fun bind(model: SubCategoryModel) {
            textView.text = model.name

        }
    }
}