package com.example.myappktx.View.view.adapters

import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

/**
 * Created by agladkov on 25.12.17.
 * Use this adapter for any simple recycler view adapter you want
 */
abstract class BaseAdapter<P>: RecyclerView.Adapter<BaseViewHolder<P>>() {
    protected var mDataList: MutableList<P> = ArrayList()
    private var mCallback: BaseAdapterCallback<P>? = null

    var hasItems = false

    fun attachCallback(callback: BaseAdapterCallback<P>) {
        this.mCallback = callback
    }

    fun detachCallback() {
        this.mCallback = null
    }

    fun setList(dataList: List<P>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        hasItems = true
        notifyDataSetChanged()
    }
    fun clearList(){
        mDataList.clear()
        notifyDataSetChanged()
    }

    fun addItem(newItem: P) {
        mDataList.add(newItem)
        notifyItemInserted(mDataList.size - 1)
    }

    fun addItemToTop(newItem: P) {
        mDataList.add(0, newItem)
        notifyItemInserted(0)
    }

    fun updateItems(itemsList: ArrayList<P>) {
        mDataList.clear()
        setList(itemsList)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<P>, position: Int) {
        holder.bind(mDataList[position])

        holder.itemView. setOnClickListener {
            mCallback?.onItemClick(mDataList[position])
        }
        holder.itemView.setOnLongClickListener {
            if (mCallback == null) {
                false
            } else {
                mCallback!!.onLongClick(mDataList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return mDataList.count()
    }
}