package com.example.myappktx.View.view.fragments


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
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.Utill.BasketAdapterCallback
import com.example.myappktx.View.view.adapters.AdapterBasketRecycler
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_product_detail.view.*
import kotlinx.android.synthetic.main.fragment_basket.*


class BasketFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerAdapter: AdapterBasketRecycler
    private var orderListProduct: ArrayList<ProductModel> = arrayListOf()
    private val bottomSheet: MarkersFragment = MarkersFragment()

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
        onClick()

    }

    private fun onClick() {
        buttonBuy.setOnClickListener {
            Toast.makeText(context, "${orderListProduct.sumByDouble { it.price * it.quantity }} руб.", Toast.LENGTH_SHORT).show()
        }
        button_ShowMarkersFragment.setOnClickListener {
            fragmentManager?.let {
                bottomSheet.show(it, "")
            }
        }
    }

    private fun subscribe() {
        viewModel.getListBasketProduct().observe(viewLifecycleOwner, Observer { it ->
            recyclerAdapter.setList(it)
            orderListProduct.addAll(it)
        })
    }

    private fun initialization() {
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        recyclerAdapter = AdapterBasketRecycler()
    }

    private fun setRecycler() {
        recycler_basket.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = recyclerAdapter

            recyclerAdapter.attachCallback(object : BasketAdapterCallback<ProductModel> {
                override fun onClickImage(model: ProductModel) {
                    openProductDetailsSheet(model)
                }

                override fun onClickButton(model: ProductModel) {
                    recyclerAdapter.notifyDataSetChanged()
                }
            })
        }
    }

    private fun openProductDetailsSheet(product: ProductModel) {
        val productDetailsSheet =
                BottomSheetDialog(this@BasketFragment.context!!, R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_product_details_basket, null)

        view.apply {
            name_productDetails.text = product.name
            description_ProductDetails.text = product.description
            price_productDetails.text = "${product.price} руб."
        }

        Glide.with(view.context).load(product.picture).into(view.image_productDetails)
        productDetailsSheet.setContentView(view)
        productDetailsSheet.show()

        view.button_AddMarker.setOnClickListener {
            Toast.makeText(context, "Добавлено в закладки", Toast.LENGTH_SHORT).show()
            viewModel.addToMarks(product)
            productDetailsSheet.dismiss()

        }
    }

}

