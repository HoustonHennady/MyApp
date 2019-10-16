package com.example.myappktx.Utill

import com.example.ProductModel

interface BasketAdapterCallback<T> {
    fun onClickButton(model: T)
    fun onClickImage(model: T)
}