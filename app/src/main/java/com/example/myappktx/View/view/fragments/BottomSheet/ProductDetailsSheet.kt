package com.example.myappktx.View.view.fragments.BottomSheet

import android.content.Context
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_product_detail.view.*

class ProductDetailsSheet(context: Context, style:Int) : BottomSheetDialog(context, style) {

    var callback: BaseAdapterCallback<ProductModel>? = null

    fun attachCallback(baseAdapterCallback: BaseAdapterCallback<ProductModel>){
        this.callback = baseAdapterCallback

    }

    fun creteSheet(model:ProductModel){
        val view = layoutInflater.inflate(R.layout.bottom_sheet_product_detail, null)

        view.apply {
            name_productDetails.text = model.name
            description_ProductDetails.text = model.description
            price_productDetails.text = "${model.price} руб."
        }
        Glide.with(view.context).load(model.picture).into(view.image_productDetails)
        setContentView(view)
        show()
        view.button_AddToBasket.setOnClickListener {
            callback?.onItemClick(model = model)

        }
        view.button_AddMarker.setOnClickListener {
            callback?.onLongClick(model = model)
        }

    }




}