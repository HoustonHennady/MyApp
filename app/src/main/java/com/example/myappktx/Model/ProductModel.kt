package com.example

import com.google.firebase.firestore.PropertyName


data class ProductModel
(@JvmField @PropertyName(NAME) var name: String = "",
 @JvmField @PropertyName(FULLNAME) var fullName: String = "",
 @JvmField @PropertyName(DESCRIPTION) var description: String = "",
 @JvmField @PropertyName(CATEGORY) var category: String = "",
 @JvmField @PropertyName(LISTCONFIGURATION) val listConfiguration: List<String> = emptyList(),
 @JvmField @PropertyName(PRICE) var price: Double = 0.0,
 @JvmField @PropertyName(PICTURE) var picture: String = "") {
    companion object {
        const val NAME = "name"
        const val FULLNAME = "fullName"
        const val DESCRIPTION = "description"
        const val CATEGORY = "category"
        const val LISTCONFIGURATION = "listConfiguration"
        const val PICTURE = "picture"
        const val PRICE = "price"
    }
}
