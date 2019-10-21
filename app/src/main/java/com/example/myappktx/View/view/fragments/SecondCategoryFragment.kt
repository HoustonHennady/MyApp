package com.example.myappktx.View.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ProductModel
import com.example.myappktx.Model.SubCategoryModel
import com.example.myappktx.R
import com.example.myappktx.Utill.RecyclerDecoration
import com.example.myappktx.View.view.adapters.AdapterSecondCategoryCategory
import com.example.myappktx.View.view.adapters.AdapterSecondCategoryProduct
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_product_detail.view.*
import kotlinx.android.synthetic.main.fragment_second_fragment1.*


class SecondCategoryFragment : Fragment() {

    private lateinit var recyclerAdapterCategory: AdapterSecondCategoryCategory
    private lateinit var recyclerAdapterProduct: AdapterSecondCategoryProduct
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        onSubscribe()
        onClickRecycler()
    }

    private fun initialization() {
        viewModel = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        recyclerAdapterCategory = AdapterSecondCategoryCategory()
        recyclerAdapterProduct = AdapterSecondCategoryProduct()

    }

    private fun setupRecycler() {
        //CategoryRecycler
        order_category_recycler.apply {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
            adapter = recyclerAdapterCategory
            val decoration = RecyclerDecoration(7)
            addItemDecoration(decoration)
        }
        //ProductRecycler
        progressBar.visibility = View.VISIBLE
        order_product_recycler.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = recyclerAdapterProduct
            visibility = View.GONE
        }

    }

    private fun onSubscribe() {
        viewModel.getListSubCategory().observe(viewLifecycleOwner, Observer {
            recyclerAdapterCategory.setList(it)
            viewModel.fetchDataProductList(it[0].productCategory.toString())
        })
        viewModel.getProductList().observe(viewLifecycleOwner, Observer {
            recyclerAdapterProduct.setList(it)
            progressBar.visibility = View.GONE
            order_product_recycler.visibility = View.VISIBLE
        })
    }

    private fun onClickRecycler() {
        recyclerAdapterCategory.attachCallback(object : BaseAdapterCallback<SubCategoryModel> {
            override fun onLongClick(model: SubCategoryModel): Boolean {
                return false
            }

            override fun onItemClick(model: SubCategoryModel) {
                viewModel.fetchDataProductList(string = model.productCategory.toString())
                order_product_recycler.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        })
        recyclerAdapterProduct.attachCallback(object : BaseAdapterCallback<ProductModel> {
            override fun onLongClick(model: ProductModel): Boolean {
                return false
            }

            override fun onItemClick(model: ProductModel) {
                openProductDetailsSheet(model = model)
            }
        })
    }

    fun openProductDetailsSheet(model: ProductModel) {
        val productDetailsSheet =
                BottomSheetDialog(this@SecondCategoryFragment.context!!, R.style.BottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_product_detail, null)

        view.apply {
            name_productDetails.text = model.name
            description_ProductDetails.text = model.description
            price_productDetails.text = "${model.price} руб."
        }

        Glide.with(view.context).load(model.picture).into(view.image_productDetails)
        productDetailsSheet.setContentView(view)
        productDetailsSheet.show()
        view.button_AddToBasket.setOnClickListener {
            viewModel
                    .addToBasket(model = model)
            Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
            productDetailsSheet.dismiss()
        }
        view.button_AddMarker.setOnClickListener {
            Toast.makeText(context, "Добавлено в закладки", Toast.LENGTH_SHORT).show()
            viewModel.addToMarks(model)

        }
    }
}

