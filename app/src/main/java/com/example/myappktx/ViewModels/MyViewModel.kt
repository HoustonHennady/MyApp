package com.example.myappktx.ViewModels

import androidx.lifecycle.*
import com.example.ProductModel
import com.example.myappktx.Model.MainCategoryModel
import com.example.myappktx.Model.NewViewPagerModel
import com.example.myappktx.Model.SubCategoryModel
import com.example.myappktx.Model.UserModel
import com.example.myappktx.Repository.MyFireStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(), LifecycleObserver {

    private val myFireStore = MyFireStore()

    //CategoryFragment
    private var listMainCategory: MutableLiveData<ArrayList<MainCategoryModel>> = MutableLiveData()
    private var listViewPager: MutableLiveData<ArrayList<NewViewPagerModel>> = MutableLiveData()
    private var listRecyclerViewPager: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()
    //SubCategoryFragment
    private var listSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()
    private var productList: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()
    //AddProductFragment
    private var listAllSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()
    //BasketFragment
    private var listBasketProduct: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData(ArrayList())
    //MarkersFragment
    private var listMarkersProduct: ArrayList<ProductModel> = ArrayList()


    fun getMainCategory(): LiveData<ArrayList<MainCategoryModel>> = listMainCategory
    fun getListViewPager(): LiveData<ArrayList<NewViewPagerModel>> = listViewPager
    fun getListRecyclerViewPager(): LiveData<ArrayList<ProductModel>> = listRecyclerViewPager
    fun getListSubCategory(): LiveData<ArrayList<SubCategoryModel>> = listSubCategory
    fun getProductList(): LiveData<ArrayList<ProductModel>> = productList
    fun getListAllSubCategory(): LiveData<ArrayList<SubCategoryModel>> = listAllSubCategory
    fun getListBasketProduct(): LiveData<ArrayList<ProductModel>> = listBasketProduct
    fun getListMarkersProduct(): ArrayList<ProductModel> = listMarkersProduct


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataMainCategory() {
        viewModelScope.async(Dispatchers.Main) {
            listMainCategory.value = myFireStore.fetchMainCategory()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataViewPager() {
        viewModelScope.async(Dispatchers.Main) {
            this@MyViewModel.listViewPager.value = myFireStore.getViewPagerPosts()
        }
    }


    fun fetchDataSubCategory(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            listSubCategory.value = myFireStore.fetchSubCategory(string)
        }
    }

    fun fetchDataProductList(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            productList.value = myFireStore.getProductList(string)
        }
    }


    fun fetchDataRecyclerViewPagerList(string: String) {
        viewModelScope.async(Dispatchers.Main) {
            listRecyclerViewPager.value = myFireStore.getViewPagerProductList(string)
        }
    }

    fun addToBasket(model: ProductModel) {
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

    fun setUserData(userData: UserModel, userId: String) {
        viewModelScope.launch {
            myFireStore.setUserInfo(userData, userId = userId)
        }
    }

    fun addToMarks(product: ProductModel) {
        listMarkersProduct.add(product)
    }

}










