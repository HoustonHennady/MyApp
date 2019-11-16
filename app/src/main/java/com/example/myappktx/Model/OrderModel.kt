package com.example.myappktx.Model

import com.example.ProductModel
import com.google.firebase.firestore.PropertyName

data class OrderModel(
    @JvmField @PropertyName(USER)  val user: UserModel = UserModel(), 
    @JvmField @PropertyName(PRODUCT_LIST) val productList: ArrayList<ProductModel> = ArrayList(),
    @JvmField @PropertyName(IS_COMPLETE) val isComplete:Boolean = false,
    @JvmField @PropertyName(IS_CANCEL) val isCancel:Boolean = false,
    @JvmField @PropertyName(USER_ID) val userId:String = "",
    @JvmField @PropertyName(TOTAL_PRICE) val totalPrice:Double = 1.1
    ) {
    companion object{
        const val USER = "user"
        const val PRODUCT_LIST = "productList"
        const val IS_COMPLETE = "isComplete"
        const val IS_CANCEL = "isCancel"
        const val USER_ID = "userId"
        const val TOTAL_PRICE = "totalPrice"
    }

}