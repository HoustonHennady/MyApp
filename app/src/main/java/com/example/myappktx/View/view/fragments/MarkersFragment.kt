package com.example.myappktx.View.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappktx.R
import com.example.myappktx.View.view.adapters.AdapterRecyclerBottomSheet
import com.example.myappktx.ViewModels.MyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_view_pager.*
import kotlinx.android.synthetic.main.bottom_sheet_view_pager.view.*

class MarkersFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: MyViewModel
    private lateinit var recyclerAdapter: AdapterRecyclerBottomSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
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
    }

    private fun setRecycler() {
        recyclerViewPager.apply {
            adapter = recyclerAdapter
            layoutManager =
                    LinearLayoutManager(view?.context, RecyclerView.VERTICAL, false)
        }
    }

    private fun subscribe() {
        recyclerAdapter.setList(viewModel.getListMarkersProduct())
    }
}