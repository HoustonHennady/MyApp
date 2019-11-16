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
import com.example.myappktx.Model.StateCategoryFragment
import com.example.myappktx.Model.MainCategoryModel
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.R
import com.example.myappktx.View.view.adapters.BaseAdapterCallback
import com.example.myappktx.View.view.adapters.CardAdapter
import com.example.myappktx.View.view.adapters.MainCategoryAdapter
import com.example.myappktx.View.view.fragments.BottomSheet.ViewPagerDetailsFragment
import com.example.myappktx.ViewModels.MyViewModel
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    private lateinit var cardAdapter: CardAdapter
    private lateinit var myViewModel: MyViewModel
    private lateinit var recyclerMainCategoryAdapter: MainCategoryAdapter
    private var bottomSheet: ViewPagerDetailsFragment = ViewPagerDetailsFragment()

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
        myViewModel.getMainCategory().observe(viewLifecycleOwner, Observer {
            when(it){
                is StateCategoryFragment.Loading -> {
                    view_pager.visibility = View.GONE
                    recycler_category.visibility = View.GONE
                    textTitleCategory.visibility = View.GONE
                    progressBarCategoryFragment.visibility = View.VISIBLE
                }
                is StateCategoryFragment.Uploaded -> {
                    recyclerMainCategoryAdapter.setList(it.items)
                    view_pager.visibility = View.VISIBLE
                    recycler_category.visibility = View.VISIBLE
                    textTitleCategory.visibility = View.VISIBLE
                    progressBarCategoryFragment.visibility = View.GONE
                }
            }
        })

        myViewModel.getListViewPager()
                .observe(viewLifecycleOwner, Observer<ArrayList<NewViewPagerModel>> {
                    cardAdapter.setList(it)
                })
    }

    private fun onClickImageApp() {
        myViewModel.fetchAllSubCategory()
        imageLogoApp.setOnClickListener {
            it.findNavController().navigate(R.id.addToBaseFragment)
        }
    }

    private fun onClickRecycler() {
        recyclerMainCategoryAdapter.attachCallback(object : BaseAdapterCallback<MainCategoryModel> {
            override fun onLongClick(model: MainCategoryModel): Boolean {
                return false
            }

            override fun onItemClick(model: MainCategoryModel) {
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