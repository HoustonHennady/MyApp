package com.example.myappktx.Model

import com.google.firebase.firestore.PropertyName

data class MainCategoryModel
(@JvmField @PropertyName(NAME) var name: String? = "",
 @JvmField @PropertyName(SUBCATEGORY) var subCategory: String? = "") {

    companion object {
        const val NAME = "name"
        const val SUBCATEGORY = "subCategory"
    }
}