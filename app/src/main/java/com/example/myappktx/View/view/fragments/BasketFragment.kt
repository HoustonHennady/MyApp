package com.example.myappktx.View.view.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.Utill.BasketAdapterCallback
import com.example.myappktx.Utill.RecyclerDecorationBasket
import com.example.myappktx.View.view.adapters.AdapterBasketRecycler
import com.example.myappktx.View.view.adapters.BaseAdapter
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.fragment_basket.*
import javax.security.auth.callback.Callback


class BasketFragment : Fragment() {
    private lateinit var myViewModel: MyViewModel
    private lateinit var recyclerAdapter: AdapterBasketRecycler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setRecycler()

    }

    private fun subscribe(){
        myViewModel.listBasketProduct.observe(viewLifecycleOwner, Observer { it ->
            recyclerAdapter.setList(it)
           text_final_price.text = "${it.sumByDouble{ it.price }} руб."
        })
    }

    private fun initialization() {
        myViewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        recyclerAdapter = AdapterBasketRecycler()

    }
    private fun setRecycler(){
        recycler_basket.apply {
            layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
            adapter = recyclerAdapter

            recyclerAdapter.attachCallback(object : BasketAdapterCallback<ProductModel>{
                override fun onClickButton(model: ProductModel) {
                    recyclerAdapter.notifyDataSetChanged()
                }
            })
        }
    }
}

