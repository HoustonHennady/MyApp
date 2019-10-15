package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ProductModel
import com.example.myappktx.R
import kotlinx.android.synthetic.main.item_recycler_secondcategory_product.view.*

class AdapterSecondCategoryProduct : BaseAdapter<ProductModel>() {
    var model:List<ProductModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductModel> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_secondcategory_product, parent, false))
    }

    private class ViewHolder(itemView: View) : BaseViewHolder<ProductModel>(itemView = itemView) {
        val image: ImageView = itemView.imageProduct
        val textView: TextView = itemView.textCategoryProduct

        override fun bind(model: ProductModel) {
            textView.text = model.name
            Glide.with(itemView.context)
                     .load(model.picture)
                    .into(image)

        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        clearList()
    }
}