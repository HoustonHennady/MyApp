package com.example.myappktx.View.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.Model.StateBasketFragment
import com.example.myappktx.R
import com.example.myappktx.Utill.BasketAdapterCallback
import com.example.myappktx.Utill.RecyclerDecorationBasket
import com.example.myappktx.View.view.adapters.AdapterBasketRecycler
import com.example.myappktx.View.view.fragments.BottomSheet.CreateOrderSheet
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_product_details_basket.view.*
import kotlinx.android.synthetic.main.fragment_basket.*


class BasketFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerAdapter: AdapterBasketRecycler
    private lateinit var createOrderFragment: CreateOrderSheet
    private lateinit var recyclerDecorationBasket: RecyclerDecorationBasket


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        setRecycler()
        onClick()
    }

    private fun initialization() {
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        recyclerAdapter = AdapterBasketRecycler()
        recyclerDecorationBasket = RecyclerDecorationBasket(15)
        val itemTouchHelper = ItemTouchHelper(swipeRecycler)
        itemTouchHelper.attachToRecyclerView(recycler_basket)
    }

    private fun subscribe() {
        viewModel.getListBasketProduct().observe(viewLifecycleOwner, Observer {
            when (it) {
                is StateBasketFragment.NoItems -> {
                    recycler_basket.visibility = View.GONE
                    buttonBuy.visibility = View.GONE
                    image_basketNoItems.visibility = View.VISIBLE
                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                }
                is StateBasketFragment.Uploaded -> {
                    recycler_basket.visibility = View.VISIBLE
                    buttonBuy.visibility = View.VISIBLE
                    image_basketNoItems.visibility = View.GONE
                    recyclerAdapter.setList(it.items)
                    Toast.makeText(context, it.items.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setRecycler() {
        recycler_basket.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = recyclerAdapter
            addItemDecoration(recyclerDecorationBasket)
        }
        recyclerAdapter.attachCallback(object : BasketAdapterCallback<ProductModel> {
            override fun onClickImage(model: ProductModel) {
                showProductDetailsSheet(model)
            }

            override fun onClickButton(model: ProductModel) {
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun onClick() {
        buttonBuy.setOnClickListener {
            showCreateOrderSheet()
        }
        button_ShowMarkersFragment.setOnClickListener {
            it.findNavController().navigate(R.id.markersFragment)
        }
    }

    private fun showCreateOrderSheet() {
        viewModel.updateListBasket(list = recyclerAdapter.getList())
        createOrderFragment = CreateOrderSheet()
        fragmentManager?.let {
            createOrderFragment.show(it, "")
        }
    }


    private val swipeRecycler: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    recyclerAdapter.getList().removeAt(viewHolder.adapterPosition)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }

    private fun showProductDetailsSheet(product: ProductModel) {
        val productDetailsSheet =
                BottomSheetDialog(this@BasketFragment.context!!, R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_product_details_basket, null)

        view.apply {
            name_productDetails.text = product.name
            description_ProductDetails.text = product.description
            price_productDetails.text = "${product.price} руб."
        }

        Glide
                .with(view.context)
                .load(product.picture)
                .into(view.image_productDetails)

        productDetailsSheet.setContentView(view)
        productDetailsSheet.show()

        view.button_AddMarker.setOnClickListener {
            Toast.makeText(context, "Добавлено в закладки", Toast.LENGTH_SHORT).show()
            viewModel.addToMarks(product)
            productDetailsSheet.dismiss()
        }
    }

}

