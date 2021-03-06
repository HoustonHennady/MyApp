package com.example.myappktx.View.view.fragments.BottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ProductModel
import com.example.myappktx.R
import com.example.myappktx.Utill.OnClickCallbak
import com.example.myappktx.View.view.adapters.AdapterRecyclerBottomSheet
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.bottom_sheet_view_pager.*
import kotlinx.android.synthetic.main.bottom_sheet_view_pager.view.*

class MarkersFragment : Fragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerAdapter: AdapterRecyclerBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
        recyclerAdapter = AdapterRecyclerBottomSheet()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.bottom_sheet_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            textBottomSheet.text = "Закладки"
            textBottomSheetDesc.visibility = View.GONE
        }
        setRecycler()
        subscribe()
        onClick()
    }

    private fun setRecycler() {
        recyclerViewPager.apply {
            adapter = recyclerAdapter
            layoutManager =
                    LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun subscribe() {
        viewModel.getListMarkersProduct().observe(viewLifecycleOwner, Observer {
            recyclerAdapter.setList(it)
        })
    }

    private fun onClick() {
        recyclerAdapter.attachSingleCallBack(object : OnClickCallbak<ProductModel> {
            override fun onClick(model: ProductModel) {
                viewModel.addToBasket(model = model)
                viewModel.removFromMarkers(product = model)
            }
        })
    }


}