package com.example.myappktx.View.view.fragments.BottomSheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ProductModel
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.R
import com.example.myappktx.Utill.OnClickCallbak
import com.example.myappktx.View.view.adapters.AdapterRecyclerBottomSheet
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_view_pager.*

class ViewPagerDetailsFragment : BottomSheetDialogFragment() {

    private lateinit var adapterRecycler: AdapterRecyclerBottomSheet
    private lateinit var mymodel: MyViewModel
    private var callback: BaseAdapterCallback<Boolean>? = null
    var list: NewViewPagerModel = NewViewPagerModel()

    fun attachCallback(callback: BaseAdapterCallback<Boolean>) {
        this.callback = callback
    }

    fun setListBottomSheet(model: NewViewPagerModel) {
        list = model

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mymodel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
        adapterRecycler = AdapterRecyclerBottomSheet()

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.bottom_sheet_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        subscribe()
        onClick()
    }

    private fun subscribe() {
        mymodel.getListRecyclerViewPager().observe(viewLifecycleOwner, Observer {
            adapterRecycler.setList(it)
        })
    }

    private fun setupView() {
        view.apply {
            textBottomSheet.text = list.name
            textBottomSheetDesc.text = list.description
        }
        recyclerViewPager.apply {
            adapter = adapterRecycler
            layoutManager =
                    LinearLayoutManager(this@ViewPagerDetailsFragment.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun onClick() {
        adapterRecycler.attachSingleCallBack(object : OnClickCallbak<ProductModel> {
            override fun onClick(model: ProductModel) {
                mymodel.addToBasket(model)
                dismiss()
            }
        })
    }

}

