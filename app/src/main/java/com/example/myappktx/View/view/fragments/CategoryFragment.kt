package com.example.myappktx.View.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myappktx.Model.MainCategory
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.R
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.View.view.adapters.CardAdapter
import com.example.myappktx.View.view.adapters.MainCategoryAdapter
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    private lateinit var cardAdapter: CardAdapter
    private lateinit var myViewModel: MyViewModel
    private lateinit var recyclerMainCategoryAdapter: MainCategoryAdapter
    private var bottomSheet: ViewPagerDetailsFragmet = ViewPagerDetailsFragmet()

    private var marginItemViewPager = 25
    private var paddingItemViewPager = 40

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater
                .inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecycler()
        subscribe()
        setViewPager()
        onClickBottomSheet()
        onClickRecycler()
        onClickImageApp()
    }

    private fun initialization() {
        myViewModel = ViewModelProviders
                .of(activity!!)
                .get(MyViewModel::class.java)
        recyclerMainCategoryAdapter = MainCategoryAdapter()
        cardAdapter = CardAdapter()

    }

    private fun setRecycler() {
        recycler_category.apply {
            isNestedScrollingEnabled = false
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = recyclerMainCategoryAdapter
        }
    }


    private fun subscribe() {
        myViewModel.listMainCategory
                .observe(viewLifecycleOwner, Observer<ArrayList<MainCategory>> {
                    recyclerMainCategoryAdapter.setList(it)
                })
        myViewModel.listViewPager
                .observe(viewLifecycleOwner, Observer<ArrayList<NewViewPagerModel>> {
                    cardAdapter.setList(it)
                })
    }

    private fun onClickImageApp() {
        myViewModel.fetchAllSubCategory()

        imageView3.setOnClickListener {
            it.findNavController().navigate(R.id.addToBaseFragment)
        }
    }

    private fun onClickRecycler() {
        recyclerMainCategoryAdapter.attachCallback(object : BaseAdapterCallback<MainCategory> {
            override fun onLongClick(model: MainCategory): Boolean {
                return false
            }

            override fun onItemClick(model: MainCategory) {
                myViewModel
                        .fetchDataSubCategory(model.subCategory.toString())
                recycler_category.findNavController()
                        .navigate(R.id.categorySecondFragment)
            }
        })
    }

    private fun setViewPager() {
        view_pager.apply {
            adapter = cardAdapter
            pageMargin = marginItemViewPager
            setPadding(paddingItemViewPager)
            offscreenPageLimit = 3
        }
        cardAdapter.attachCallback(object : BaseAdapterCallback<NewViewPagerModel> {
            override fun onItemClick(model: NewViewPagerModel) {}
            override fun onLongClick(model: NewViewPagerModel): Boolean {
                myViewModel.fetchDataRecyclerViewPagerList(string = model.product)
                bottomSheet.setListBottomSheet(model)
                fragmentManager?.let { bottomSheet.show(it, "") }
                return true
            }
        })
    }

    private fun onClickBottomSheet() {
        bottomSheet.attachCallback(object : BaseAdapterCallback<Boolean> {
            override fun onItemClick(model: Boolean) {
                bottomSheet.dismiss()
            }

            override fun onLongClick(model: Boolean): Boolean {
                return false
            }
        })
    }

}