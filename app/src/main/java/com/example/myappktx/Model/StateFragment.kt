package com.example.myappktx.Model

import androidx.lifecycle.MutableLiveData
import com.example.ProductModel

sealed class StateSecondFragment {
    object Loading: StateSecondFragment()
    object NoItems: StateSecondFragment()
    class Uploaded(val items: ArrayList<ProductModel>): StateSecondFragment()
    class ErrorLoading(val error: String): StateSecondFragment()
}
sealed class StateCategoryFragment {
    object Loading: StateCategoryFragment()
    object NoItems: StateCategoryFragment()
    class Uploaded(val items: ArrayList<MainCategoryModel>): StateCategoryFragment()
    class ErrorLoading(val error: String): StateCategoryFragment()
}
sealed class StateBasketFragment {
    object Loading: StateBasketFragment()
    object NoItems: StateBasketFragment()
    class Uploaded(var items: ArrayList<ProductModel>): StateBasketFragment()
    class Updated(var items: ArrayList<ProductModel>): StateBasketFragment()
    class ErrorLoading(val error: String): StateBasketFragment()
}
