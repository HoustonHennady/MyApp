package com.example.myappktx.View.view.fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ProductModel
import com.example.myappktx.Model.OrderModel
import com.example.myappktx.Model.StateBasketFragment
import com.example.myappktx.Model.UserModel
import com.example.myappktx.R
import com.example.myappktx.View.view.activity.MainActivity.Companion.currentUser
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_create_oreder.*


class CreateOrderSheet : BottomSheetDialogFragment() {
    private lateinit var viewModel: MyViewModel
    private var user = UserModel()
    private var productList:ArrayList<ProductModel> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_create_oreder, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        button_create_Order.setOnClickListener {
            val orderModel = OrderModel(
                user = user,
                productList = productList,
                isCancel = false,
                isComplete = false,
                totalPrice = productList.sumByDouble { it.price * it.quantity },
                userId = currentUser
            )
            viewModel.sendNewOrder(orderModel)
            viewModel.clearListBasket()
            dismiss()
        }
    }

    private fun initialization() {
        viewModel = ViewModelProviders
            .of(activity!!)
            .get(MyViewModel::class.java)
    }

    private fun subscribe() {
        viewModel.getUserList().observe(viewLifecycleOwner, Observer {
            user = it
            bind(it)
        })
        viewModel.getListBasketProduct().observe(viewLifecycleOwner, Observer {
            when(it){
                is StateBasketFragment.Updated ->{
                    productList.addAll(it.items)
                }
                is StateBasketFragment.Uploaded ->{
                    productList.addAll(it.items)
                }
            }
        })
    }

    private fun bind(userModel: UserModel) {
        title_createOrder.text = "Сделать заказ"
        userName_creteOrder.setText(userModel.userName)
        tel_creteOrder.setText(userModel.phoneNumber)
        address_creteOrder.setText(userModel.address)
    }
}

