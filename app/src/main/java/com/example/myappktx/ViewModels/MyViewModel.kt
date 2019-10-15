package com.example.myappktx.ViewModels

import androidx.lifecycle.*
import com.example.ProductModel
import com.example.myappktx.Model.MainCategory
import com.example.myappktx.Repository.MyFireStore
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.Model.SubCategoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(), LifecycleObserver {

    private val myFireStore = MyFireStore()

    //CategoryFragment
    var listMainCategory: MutableLiveData<ArrayList<MainCategory>> = MutableLiveData()
    var listViewPager: MutableLiveData<ArrayList<NewViewPagerModel>> = MutableLiveData()
    var listRecyclerViewPager: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()

    //SubCategoryFragment
    var listSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()
    var listProductList: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()

    //AddProductFragment
    var listAllSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()

    //BasketFragment
    var listBasketProduct: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataMainCategory() {
        viewModelScope.async(Dispatchers.Main) {
            listMainCategory.value = myFireStore.fetchMainCategory()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataViewPager() {
        viewModelScope.async(Dispatchers.Main) {
            this@MyViewModel.listViewPager.value = myFireStore.getViewPagerList()
        }
    }


    fun fetchDataSubCategory(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            listSubCategory.value = myFireStore.fetchSubCategory(string)
        }
    }

    fun fetchDataProductList(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            listProductList.value = myFireStore.getProductList(string)
        }
    }


    fun fetchDataRecyclerViewPagerList(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            listRecyclerViewPager.value = myFireStore.getViewPagerRecyclerList(string)
        }
    }

    fun sendToBasket(model: ProductModel) {
        listBasketProduct.value?.add(model)
    }

    fun fetchAllSubCategory() {
        viewModelScope.async(Dispatchers.Main) {
            listAllSubCategory.value = myFireStore.getSubCategoryList()
        }
    }

    fun addNewProduct(productModel: ProductModel) {
        viewModelScope.launch {
            myFireStore.addNewProductToBase(productModel)
        }
    }
}










