package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.Utill.BasketAdapterCallback
import kotlinx.android.synthetic.main.item_recycler_basket.view.*
import kotlin.collections.ArrayList

class AdapterBasketRecycler : RecyclerView.Adapter<AdapterBasketRecycler.ViewHolder>() {

    private var callback: BasketAdapterCallback<ProductModel>? = null
    private var model:ArrayList<ProductModel> = ArrayList()

    fun setList(arrayList: ArrayList<ProductModel>){
        model.clear()
        model = arrayList
        notifyDataSetChanged()
    }

    fun attachCallback(callback: BasketAdapterCallback<ProductModel>){
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_basket, parent, false),callback)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model = model[position])

    }

    class ViewHolder(view:View, val callback: BasketAdapterCallback<ProductModel>?): RecyclerView.ViewHolder(view){

        private val imageView = view.image_basket_Recycler
        private val textName = view.text_name_basket_recycler
        private val textPrice = view.text_price_basket_recycler
        private val buttonPlus = view.button_plus
        private val buttonMinus = view.button_minus
        private val textQuantity = view.text_quantity

        fun bind(model: ProductModel){

            Glide.with(itemView.context)
                .load(model.picture)
                .into(imageView)

            textName.text = model.name
            textPrice.text = "${model.price} руб"
            textQuantity.text = model.quantity.toString()

            buttonPlus.setOnClickListener{
                textQuantity.text = model.quantity++.toString()
                callback?.onClickButton(model)
            }

            buttonMinus.setOnClickListener{
                textQuantity.text = model.quantity--.toString()
                callback?.onClickButton(model)
            }

            imageView.setOnClickListener {
                callback?.onClickImage(model)
            }
        }
    }
}

