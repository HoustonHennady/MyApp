package com.example.myappktx.ViewModels

import androidx.lifecycle.*
import com.example.ProductModel
import com.example.myappktx.Model.*
import com.example.myappktx.Repository.MyFireStore
import com.example.myappktx.Utill.default
import com.example.myappktx.View.view.activity.MainActivity.Companion.currentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(), LifecycleObserver {

    private val myFireStore = MyFireStore()

    //CategoryFragment
    private var listMainCategory: MutableLiveData<StateCategoryFragment> = MutableLiveData()
    private var listViewPager: MutableLiveData<ArrayList<NewViewPagerModel>> = MutableLiveData()
    private var listRecyclerViewPager: MutableLiveData<ArrayList<ProductModel>> = MutableLiveData()

    //SubCategoryFragment
    private var listSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()
    private var productList: MutableLiveData<StateSecondFragment> = MutableLiveData()

    //AddProductFragment
    private var listAllSubCategory: MutableLiveData<ArrayList<SubCategoryModel>> = MutableLiveData()

    //BasketFragment
    private var listBasketProduct =
        MutableLiveData<StateBasketFragment>().default(initialValue = StateBasketFragment.NoItems)
    private var containerBasketProduct: ArrayList<ProductModel> = ArrayList()

    //MarkersFragment
    private var listMarkersProduct: MutableLiveData<ArrayList<ProductModel>> =
        MutableLiveData<ArrayList<ProductModel>>().default(initialValue = arrayListOf())
    private var containerMarkersProduct: ArrayList<ProductModel> = arrayListOf()

    //OrderFragment
    private var listOrders: MutableLiveData<ArrayList<OrderModel>> = MutableLiveData()
    private var listUserInfo: MutableLiveData<UserModel> = MutableLiveData()
    private var userId: MutableLiveData<String> = MutableLiveData()


    fun getCurrentUser() = userId
    fun getMainCategory() = listMainCategory
    fun getListViewPager() = listViewPager
    fun getListRecyclerViewPager() = listRecyclerViewPager
    fun getListSubCategory() = listSubCategory
    fun getProductList() = productList
    fun getListAllSubCategory() = listAllSubCategory
    fun getListMarkersProduct() = listMarkersProduct
    fun getContainerProductList() = containerBasketProduct
    fun getOrderList() = listOrders
    fun getListBasketProduct() = listBasketProduct
    fun getUserList(): LiveData<UserModel> {
        if (listUserInfo.value == null) {
            getUserData()
        }
        return listUserInfo
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun signIn() {
        viewModelScope.launch {
            userId.value = myFireStore.signin()
        }
    }

    //Main Category
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataMainCategory() {
        listMainCategory.value = StateCategoryFragment.Loading
        viewModelScope.launch(Dispatchers.Main) {
            listMainCategory.value = StateCategoryFragment.Uploaded(myFireStore.fetchMainCategory())
        }
    }

    //View Pager
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchDataViewPager() {
        viewModelScope.launch(Dispatchers.Main) {
            listViewPager.value = myFireStore.getViewPagerPosts()
        }
    }

    //SubCategory
    fun fetchDataSubCategory(category: String) {
        viewModelScope.launch(Dispatchers.Main) {
            listSubCategory.value = myFireStore.fetchSubCategory(string = category)
        }
    }

    fun fetchDataProductList(string: String) {
        productList.value = StateSecondFragment.Loading
        viewModelScope.launch(Dispatchers.Main) {
            productList.value = StateSecondFragment.Uploaded(myFireStore.getProductList(string))
        }
    }

    //View Pager Recycler
    fun fetchDataRecyclerViewPagerList(string: String) {
        viewModelScope.launch(Dispatchers.Main) {
            listRecyclerViewPager.value = myFireStore.getViewPagerProductList(string)
        }
    }

    //Basket Fragment
    fun addToBasket(model: ProductModel) {
        containerBasketProduct.add(model)
        listBasketProduct.value = StateBasketFragment.Uploaded(containerBasketProduct)

//        containerBasketProduct.add(model)
//        containerBasketProduct.trimToSize()
//        when (listBasketProduct.value) {
//            is StateBasketFragment.NoItems -> {
//                listBasketProduct.postValue(StateBasketFragment.Uploaded(containerBasketProduct))
//            }
//            is StateBasketFragment.Uploaded -> {
//                listBasketProduct.postValue(StateBasketFragment.Updated(containerBasketProduct))
//            }
//
//        }

    }

    fun updateListBasket(list: ArrayList<ProductModel>?) {
        if (list != null) {
            containerBasketProduct = list
        }
    }

    fun clearListBasket() {
        containerBasketProduct.clear()
        listBasketProduct.value = StateBasketFragment.NoItems
    }

    fun removFromBacket(product: ProductModel) {
        containerBasketProduct.remove(product)
    }

    //Add to base
    fun fetchAllSubCategory() {
        viewModelScope.launch(Dispatchers.Main) {
            listAllSubCategory.value = myFireStore.getSubCategoryList()
        }
    }

    fun addNewProduct(productModel: ProductModel) {
        viewModelScope.launch {
            myFireStore.addNewProductToBase(productModel)
        }
    }

    //CreteOrders
    fun setUserData(userData: UserModel, userId: String) {
        viewModelScope.launch {
            myFireStore.setUserInfo(userData, userId = userId)
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            listUserInfo.value = myFireStore.getUserInfo(currentUser)
        }
    }

    //Markers Fragment
    fun addToMarks(product: ProductModel) {
        containerMarkersProduct.add(product)
        listMarkersProduct.postValue(containerMarkersProduct)
    }

    fun removFromMarkers(product: ProductModel) {
        containerMarkersProduct.remove(product)
        containerMarkersProduct.trimToSize()
        listMarkersProduct.postValue(containerMarkersProduct)
    }

    //Orders Fragment
    fun sendNewOrder(newOrder: OrderModel) {
        viewModelScope.launch {
            myFireStore.addNewOrders(newOrder)
        }
    }

    fun getOrderList(userId: String) {
        viewModelScope.launch {
            listOrders.value = myFireStore.getOrderList(userId = userId)
        }
    }
}