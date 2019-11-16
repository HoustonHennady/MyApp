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
import com.example.ProductModel
import com.example.myappktx.Model.StateSecondFragment
import com.example.myappktx.Model.SubCategoryModel
import com.example.myappktx.R
import com.example.myappktx.Utill.AppBarStateListener
import com.example.myappktx.Utill.RecyclerDecoration
import com.example.myappktx.View.view.adapters.AdapterSecondCategoryCategory
import com.example.myappktx.View.view.adapters.AdapterSecondCategoryProduct
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.View.view.fragments.BottomSheet.ProductDetailsSheet
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_second_fragment1.*


class SecondCategoryFragment : Fragment() {

    private lateinit var adapterCategory: AdapterSecondCategoryCategory
    private lateinit var adapterProduct: AdapterSecondCategoryProduct
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
        onChangeOffSetAppBar()
    }

    private fun onChangeOffSetAppBar() {
        appbar.addOnOffsetChangedListener(object : AppBarStateListener(){
            override fun onStateChanged(appBarLayout: AppBarLayout, state: AppBarState) {
                when(state){
                    is AppBarState.IDLE -> {
                    }
                    is AppBarState.COLLAPSED ->{
                        texttitle.setTextColor(resources.getColor(R.color.colorWhite))
                        texttitle.text = "Продукты"
                     }
                    is AppBarState.EXPENDED ->{
                        texttitle.setTextColor(resources.getColor(R.color.colorOrange1))
                        texttitle.text = "Категории"
                    }
                }
            }
        })
    }

    private fun initialization() {
        viewModel = ViewModelProviders.of(activity!!).get(MyViewModel::class.java)
        adapterCategory = AdapterSecondCategoryCategory()
        adapterProduct = AdapterSecondCategoryProduct()

    }

    private fun setupRecycler() {
        second_category_recycler.apply {
            layoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
            adapter = adapterCategory
            val decoration = RecyclerDecoration(7)
            addItemDecoration(decoration)
        }
        product_recycler.apply {
            layoutManager = GridLayoutManager(this.context,2)
            adapter = adapterProduct
        }

    }

    private fun onSubscribe() {
        viewModel.getListSubCategory().observe(viewLifecycleOwner, Observer {
            adapterCategory.setList(it)
            viewModel.fetchDataProductList(it[0].productCategory.toString())
        })
        viewModel.getProductList().observe(viewLifecycleOwner, Observer {
            when (it){
                is StateSecondFragment.Loading ->{
                    progressBar.visibility = View.VISIBLE
                    product_recycler.visibility = View.GONE
                }
                is StateSecondFragment.Uploaded -> {
                    adapterProduct.setList(it.items)
                    progressBar.visibility = View.GONE
                    product_recycler.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun onClickRecycler() {
        adapterCategory.attachCallback(object : BaseAdapterCallback<SubCategoryModel> {
            override fun onLongClick(model: SubCategoryModel): Boolean {
                return false
            }
            override fun onItemClick(model: SubCategoryModel) {
                viewModel.fetchDataProductList(string = model.productCategory.toString())

            }
        })
        adapterProduct.attachCallback(object : BaseAdapterCallback<ProductModel> {
            override fun onLongClick(model: ProductModel): Boolean {
                return false
            }
            override fun onItemClick(model: ProductModel) {
                openProductDetailsSheet(model = model)
            }
        })
    }

    fun openProductDetailsSheet(model: ProductModel) {
        val sheet = ProductDetailsSheet(
                context = view!!.context,
                style = R.style.BottomSheetDialog
        )
        sheet.creteSheet(model = model)
        sheet.attachCallback(object :BaseAdapterCallback<ProductModel>{
            override fun onItemClick(model: ProductModel) {
                viewModel
                    .addToBasket(model = model)
                Toast.makeText(context, "Добавлено в корзину", Toast.LENGTH_SHORT).show()
                sheet.dismiss()
            }

            override fun onLongClick(model: ProductModel): Boolean {
                Toast.makeText(context, "Добавлено в закладки", Toast.LENGTH_SHORT).show()
                viewModel.addToMarks(model)
                return true
            }
        })
    }
}

