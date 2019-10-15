package com.example.myappktx.View.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.R
import kotlinx.android.synthetic.main.view_pager_item.view.*

class CardAdapter : PagerAdapter() {

    private val model: ArrayList<NewViewPagerModel> = ArrayList()
    private var mCallback: BaseAdapterCallback<NewViewPagerModel>? = null

    fun attachCallback(callback: BaseAdapterCallback<NewViewPagerModel>) {
        this.mCallback = callback
    }
    fun setList(array: ArrayList<NewViewPagerModel>) {
        model.clear()
        model.addAll(array)
        notifyDataSetChanged()
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater
                .from(container.context)
                .inflate(R.layout.view_pager_item, null)
        val imageView = view.imageViewPager
        Glide
                .with(container.context)
                .load(model[position].pictures)
                .into(imageView)
        container.addView(view, 0)

        view.setOnLongClickListener {
            if (mCallback == null) {
                false
            } else {
                mCallback!!.onLongClick(model[position])
            }
        }
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
    override fun getCount(): Int {
        return model.count()
    }
}














