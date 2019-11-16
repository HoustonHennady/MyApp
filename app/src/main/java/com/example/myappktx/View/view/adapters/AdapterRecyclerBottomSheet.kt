package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.Utill.OnClickCallbak
import kotlinx.android.synthetic.main.item_sheet_view_pager.view.*
import kotlinx.android.synthetic.main.view_pager_item.view.imageViewPager

class AdapterRecyclerBottomSheet :BaseAdapter<ProductModel>(){

     lateinit var onClickCallback: OnClickCallbak<ProductModel>

     fun attachSingleCallBack(callback: OnClickCallbak<ProductModel>){
         this.onClickCallback = callback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductModel> {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_sheet_view_pager,parent,false),
             onClickCallback = onClickCallback)
    }

    class ViewHolder(itemView: View, val onClickCallback: OnClickCallbak<ProductModel>)
        : BaseViewHolder<ProductModel>(itemView = itemView){

        private val imageView = itemView.imageViewPager
        private val textView = itemView.nameProductViewPager
        private val buttonAdd = itemView.addToBaseViewPager

        override fun bind(model: ProductModel) {
            Glide.with(itemView.context)
                    .load(model.picture)
                    .into(imageView)
            textView.text = model.name
            buttonAdd.setOnClickListener {
                onClickCallback.onClick(model = model)
            }
        }
    }
}