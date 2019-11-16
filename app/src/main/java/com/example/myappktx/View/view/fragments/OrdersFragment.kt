package com.example.myappktx.View.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappktx.Model.OrderModel
import com.example.myappktx.R
import com.example.myappktx.Utill.RecyclerDecorationBasket
import com.example.myappktx.View.view.activity.MainActivity.Companion.currentUser
import com.example.myappktx.View.view.adapters.AdapterOrders
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.View.view.fragments.BottomSheet.OrderDetailSheet
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : Fragment() {

    private lateinit var adapterOrders: AdapterOrders
    private lateinit var viewModel: MyViewModel
    private lateinit var orderDetailSheet: OrderDetailSheet
    private lateinit var recyclerDecoration: RecyclerDecorationBasket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setUpView()
        onClick()
    }

    private fun initialization() {
        adapterOrders = AdapterOrders()
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        viewModel.getOrderList(userId = currentUser)
        orderDetailSheet = OrderDetailSheet()
        recyclerDecoration = RecyclerDecorationBasket(15)
    }

    private fun subscribe() {
        viewModel.getOrderList().observe(viewLifecycleOwner, Observer {
            adapterOrders.setList(it)
        })
    }

    private fun setUpView() {
        recycler_ordersFragment.apply {
            adapter = adapterOrders
            layoutManager = LinearLayoutManager(recycler_ordersFragment.context, RecyclerView.VERTICAL, false)
            addItemDecoration(recyclerDecoration)
        }
    }

    private fun onClick(){
        adapterOrders.attachCallback(object :BaseAdapterCallback<OrderModel>{

            override fun onItemClick(model: OrderModel) {
                orderDetailSheet.setOrder(model)
                fragmentManager?.let {orderDetailSheet.show(it,"")}            }

            override fun onLongClick(model: OrderModel): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}


