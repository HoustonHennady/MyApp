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

class AdaperSecondCategoryProduuctDiffUtli : RecyclerView.Adapter<AdaperSecondCategoryProduuctDiffUtli.ViewHolder>() {
    var model: ArrayList<ProductModel> = ArrayList()

    fun setListDiff(list: ArrayList<ProductModel>) {
        var oldList = model
        var diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(DiffUtill(oldList, list))
        model = list
        diffResult.dispatchUpdatesTo(this)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_secondcategory_product, parent, false))

    }

    class DiffUtill(val oldlist: List<ProductModel>, val newList: List<ProductModel>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldlist[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun getOldListSize(): Int {
            return oldlist.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return false
        }

    }

    override fun getItemCount(): Int {
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = itemView.imageProduct
        val textView: TextView = itemView.textCategoryProduct



    }
}