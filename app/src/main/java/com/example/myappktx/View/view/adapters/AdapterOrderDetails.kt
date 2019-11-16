package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.R
import kotlinx.android.synthetic.main.item_recycler_order_details.view.*

class AdapterOrderDetails : BaseAdapter<ProductModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductModel> {
        return OrderDetailsViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_order_details,parent,false))
    }

    private class OrderDetailsViewHolder(view: View): BaseViewHolder<ProductModel>(itemView = view){
        private val textName = view.name_productOrderDetails
        private val quantity = view.quantity_ProductOrderDetails
        private val image = view.image_productOrderDetails
        override fun bind(model: ProductModel) {
            textName.text = model.name
            quantity.text = model.quantity.toString()
            Glide
                .with(itemView.context)
                .load(model.picture)
                .into(image)
        }

    }
}