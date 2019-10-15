package com.example.myappktx.View.view.adapters

import android.view.View

interface BaseAdapterCallback<T> {
    fun onItemClick(model: T)
    fun onLongClick(model: T): Boolean

}