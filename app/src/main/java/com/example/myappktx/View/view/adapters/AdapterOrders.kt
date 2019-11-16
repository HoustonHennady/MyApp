package com.example.myappktx.View.view.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.myappktx.Model.OrderModel
import com.example.myappktx.R
import kotlinx.android.synthetic.main.order_recycler_item.view.*

class AdapterOrders : BaseAdapter<OrderModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<OrderModel> {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.order_recycler_item,
                parent,
                false
            )
        )
    }


    class OrderViewHolder(view: View) : BaseViewHolder<OrderModel>(view) {
        private val title = view.text_orderTitle
        private val createData = view.create_data
        private val quantityProduct = view.quantity_Product
        private val iconOrderSheet = view.icon_orderState

        private fun setIconState(icon:Int){
            Glide.with(itemView.context).load(icon).into(iconOrderSheet)
        }
        override fun bind(model: OrderModel) {
            title.text = "Заказ №15"
            createData.text = "Принят 12.10.19"
            quantityProduct.text = "товаров " + model.productList.size.toString() + " на сумму " + model.totalPrice +"p"
            if (model.isComplete){
                setIconState(R.drawable.ic_done_black_24dp)
            }else {
                if (!model.isCancel) {
                    setIconState(R.drawable.ic_local_shipping_black_24dp)
                } else {
                    setIconState(R.drawable.ic_clear_black_24dp)
                }
            }

        }
    }
}
