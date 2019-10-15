package com.example.myappktx.Model

import com.google.firebase.firestore.PropertyName

data class SubCategoryModel(
    @JvmField @PropertyName(NAME) var name: String? = "",
    @JvmField @PropertyName(CATEGORY) var category: String? = "",
    @JvmField @PropertyName(PRODUCT_CATEGORY) var productCategory: String? = ""
) {
    companion object {
        const val NAME = "name"
        const val CATEGORY = "category"
        const val PRODUCT_CATEGORY = "productCategory"
    }
}