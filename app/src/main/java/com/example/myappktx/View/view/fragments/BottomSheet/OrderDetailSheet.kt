package com.example.myappktx.View.view.fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappktx.Model.OrderModel
import com.example.myappktx.R
import com.example.myappktx.View.view.adapters.AdapterOrderDetails
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.order_details_sheet.*

class OrderDetailSheet : BottomSheetDialogFragment() {

    private lateinit var recyclerAdapter: AdapterOrderDetails
    private lateinit var viewModel: MyViewModel
    private lateinit var order: OrderModel

    fun setOrder(order: OrderModel){
        this.order = order
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
        initialization()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.order_details_sheet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

    }
    private fun initialization(){
        recyclerAdapter = AdapterOrderDetails()
        recyclerAdapter.setList(order.productList)
    }
    private fun setupView(){
        recycler_orderDetails.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(view!!.context,RecyclerView.VERTICAL,false)
        }
        bindView(order)
    }
    private fun bindView(order: OrderModel){
        title_detailsOrder.text = "Заказ №15"
        name_user.text = "На имя " + order.user.userName
        tel_user.text = "Телефон " + order.user.phoneNumber
        address_user.text = "Адресс " + order.user.address
        status_order.text = if (order.isComplete){
            "Доставлен"
        }else{
            "Ожидается"
        }

    }
}