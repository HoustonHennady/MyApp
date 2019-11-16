package com.example.myappktx.Model

import com.google.firebase.firestore.PropertyName

data class NewViewPagerModel
(@JvmField @PropertyName(NAME) val name: String = "",
 @JvmField @PropertyName(PICTURES) val pictures: String = "",
 @JvmField @PropertyName(PRODUCT) val product: String = "",
 @JvmField @PropertyName(DESCRIPTION) val description: String = "",
 @JvmField @PropertyName(STATE) val state: String = ""
) {

    companion object {
        const val NAME = "name"
        const val PICTURES = "picture"
        const val DESCRIPTION = "description"
        const val STATE = "state"
        const val PRODUCT = "product"
    }
}
