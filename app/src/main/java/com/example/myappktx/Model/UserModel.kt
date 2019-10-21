package com.example.myappktx.Model

import com.example.ProductModel
import com.google.firebase.firestore.PropertyName

data class UserModel(
        @JvmField @PropertyName(ID) var id: String = "",
        @JvmField @PropertyName(USER_NAME) var userName: String = "",
        @JvmField @PropertyName(PHONE_NUMBER) var phoneNumber: String = "",
        @JvmField @PropertyName(ADDRESS) var address: String = "",
        @JvmField @PropertyName(FAVORIT_LIST) var favoritList: ArrayList<ProductModel> = arrayListOf(),
        @JvmField @PropertyName(ORDER_LIST) var orderList: ArrayList<ProductModel> = arrayListOf()
)
{
    companion object {
        const val ID = "id"
        const val USER_NAME = "userName"
        const val PHONE_NUMBER = "phoneNumber"
        const val ADDRESS = "address"
        const val FAVORIT_LIST = "favoritList"
        const val ORDER_LIST = "orderList"
    }
}