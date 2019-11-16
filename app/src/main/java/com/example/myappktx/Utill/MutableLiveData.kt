package com.example.myappktx.Utill

import androidx.lifecycle.MutableLiveData

fun <T: Any?> MutableLiveData<T>.default(initialValue: T) = apply { value = initialValue}